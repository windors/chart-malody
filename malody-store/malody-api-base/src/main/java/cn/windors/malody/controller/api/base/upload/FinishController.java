package cn.windors.malody.controller.api.base.upload;

import cn.windors.malody.dto.MalodyChart;
import cn.windors.malody.response.MalodyResponse;
import cn.windors.malody.entity.core.Chart;
import cn.windors.malody.entity.core.Fileinfo;
import cn.windors.malody.entity.core.Song;
import cn.windors.malody.service.ChartService;
import cn.windors.malody.service.FileinfoService;
import cn.windors.malody.service.SongService;
import cn.windors.malody.type.CoreChartModeEnum;
import cn.windors.malody.type.CoreFileTypeEnum;
import cn.windors.malody.type.CoreResolveStateEnum;
import cn.windors.malody.type.MalodyResponseEnum;
import cn.windors.malody.util.MalodyUtils;
import cn.windors.malody.util.RemoteFileSystemUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

import static cn.windors.malody.type.CoreResolveStateEnum.AUTO_UPDATED;

/**
 * @author Wind_Yuan
 */
@Slf4j
@RestController
@RequestMapping("/api/store/upload")
@Api(tags = {"Malody Upload API"})
public class FinishController {
    @Resource
    private ChartService chartService;

    @Resource
    private FileinfoService fileinfoService;

    @Resource
    private RemoteFileSystemUtils remoteFileSystemUtils;

    @Resource
    private SongService songService;

    /**
     * <p>需要将数据落实到核心表中，只有调用了这个方法才算真正的上传完毕，才从缓存中拿到当时保存的文件url，并将url写入到chart表中；并且一并
     * 更新其他属性</p>
     * 根据Malody提示的API(202112):mc文件会被压缩为zip再上传，服务器将会收到和mc同名的zip文件，需要对.mc文件进行下载单独处理
     * 根据MalodyAPI(202112)的返回结构：{code : }，这个操作暂时只是保留操作，除了更新以下数据库外不需要额外做什么
     * TODO 解析谱面添加异常处理流程
     */
    @PostMapping("/finish")
    @ApiOperation(value = "上传确认（202103添加）", notes = "对应上传阶段3")
    @ApiImplicitParams({
            @ApiImplicitParam(value = "song id", name = "sid", paramType = "int"),
            @ApiImplicitParam(value = "chart id", name = "cid", paramType = "int"),
            @ApiImplicitParam(value = "所有待文件名，以逗号连接", name = "name", allowEmptyValue = true, paramType = "string"),
            @ApiImplicitParam(value = "所有待文件md5，以逗号连接", name = "hash", allowEmptyValue = true, paramType = "string"),
            @ApiImplicitParam(value = "总文件大小", name = "size", allowEmptyValue = true, paramType = "string"),
            @ApiImplicitParam(value = "谱面主文件的md5", name = "main", allowEmptyValue = true, paramType = "string")
    })
    public MalodyResponse finish(
            Long uid,
            @RequestParam Long sid,
            @RequestParam Long cid,
            @RequestParam("name") String names,
            @RequestParam("hash") String hashes,
            @RequestParam Integer size,
            @RequestParam String main
    ) {
        Map<String, String> nameHashMap = MalodyUtils.getNameHashMap(names, hashes);
        // 非法传值校验
        if (nameHashMap == null) {
            log.info("finish阶段出错: 传值name和hash不相等。names: [{}], hases: [{}]", names, hashes);
            return new MalodyResponse(MalodyResponseEnum.LENGTH_NOT_EQUAL);
        }

        // 非法传值校验（连接数据库）
        // 将当前数据和数据库中sign阶段保存的上传信息进行核对，同时找到铺面文件的上传相关信息，方便从文件服务器中解析具体信息
        List<Fileinfo> list = fileinfoService.list(CoreFileTypeEnum.CHART, cid);

        if (list == null) {
            log.error("finish阶段出错: 未在数据库中查询到[sid: {}, cid: {}]的信息, 可能的原因：用户非法调用 或 sign阶段出错", sid, cid);
            return new MalodyResponse(MalodyResponseEnum.NOT_EXISTS);
        }

        // 谱面文件初始化信息（来自文件名）
        Fileinfo chartFileinfo = null;
        Fileinfo imageFileinfo = null;
        Fileinfo musicFileinfo = null;
        for (String name : nameHashMap.keySet()) {
            int index;
            String hash = nameHashMap.get(name);
            if ((index = list.indexOf(Fileinfo.chartFileInstance(cid, hash, name))) < 0) {
                log.error("数据库与参数不匹配 --- fid: [{}] file:[{}] md5: [{}]", cid, name, hash);
                return new MalodyResponse(MalodyResponseEnum.FILE_MISSING);
            }
            Fileinfo fileinfo = list.get(index);
            if (hash.equals(main)) {
                chartFileinfo = fileinfo;
                continue;
            }
            if (MalodyUtils.isPictureFile(name)) {
                // 歌曲封面获取默认背景图片
                imageFileinfo = fileinfo;
                continue;
            }
            if (MalodyUtils.isMusicFile(name)) {
                musicFileinfo = fileinfo;
            }
        }

        if (chartFileinfo == null) {
            log.error("finish阶段出错: 未找到谱面主文件(这种情况理论上不可能发生) cid: [{}]", cid);
            return new MalodyResponse(MalodyResponseEnum.FILE_MD5_NOT_IN_HASH);
        }

        // 由于后续需要根据song来查询chart，所以需要根据song的state判断是否需要自动解析，防止手动修改的条件被覆盖
        Song song = songService.querySongById(sid);
        // 初始化
        {
            if (song == null) {
                song = new Song();
                song.setId(sid);
                song.setMode(0);
                song.setResolveState(CoreResolveStateEnum.WAIT_UPDATE);
            }
            // 没有图片则自动获取一个图片
            if(song.getCoverUri() == null && imageFileinfo != null) {
                song.setCoverUri(imageFileinfo.getUri());
            }
            // 没有歌曲自动获取一个歌曲
            if(song.getSongUri() == null && musicFileinfo != null) {
                song.setSongUri(musicFileinfo.getUri());
            }
        }
        Chart chart = chartService.getById(cid);
        {
            if (chart == null) {
                chart = new Chart();
                chart.setResolveState(CoreResolveStateEnum.WAIT_UPDATE);
                chart.setSid(sid);
            }
            chart.setBackgroundUri(imageFileinfo == null ? null : imageFileinfo.getUri());
            chart.setSongUri(musicFileinfo == null ? null : musicFileinfo.getUri());
            chart.setId(cid);
            chart.setUid(uid);
            chart.setSize(size);
            chart.setMd5(main);
            chart.setMainUri(chartFileinfo.getUri());
            if(imageFileinfo != null) {
                chart.setBackgroundUri(imageFileinfo.getUri());
            }
            chart.setMainUri(chartFileinfo.getUri());
            if(musicFileinfo != null) {
                chart.setSongUri(musicFileinfo.getUri());
            }
        }
        songService.saveOrUpdate(song);
        chartService.saveOrUpdate(chart);


        // ------------------------------------------------
        // 以下应该作为另一项服务，但这里就免了

        // 调用远程服务器解析谱面
        switch (chart.getResolveState()) {
            case WAIT_UPDATE:
            case AUTO_UPDATED:
                song.setResolveState(AUTO_UPDATED);
                // 通过解析获取到歌曲的信息、谱面的信息
                MalodyChart.McMeta.McSong metaSong = solveChart(list, imageFileinfo, chartFileinfo, chart, song);

                if (metaSong != null) {
                    switch (song.getResolveState()) {
                        case WAIT_UPDATE:
                        case AUTO_UPDATED:
                            String songName = metaSong.getFile();
                            Fileinfo songFileinfo = list.get(list.indexOf(Fileinfo.chartFileInstance(cid, nameHashMap.get(songName), songName)));
                            // 如果解析谱面文件成功则使用谱面中绑定的歌曲
                            chart.setSongUri(songFileinfo.getUri());
                            solveSong(metaSong, songFileinfo, song);
                            break;
                        default:
                            break;
                    }
                }
            default:
                break;
        }
        songService.saveOrUpdate(song);
        chartService.saveOrUpdate(chart);
        return new MalodyResponse();
    }

    private void solveSong(MalodyChart.McMeta.McSong metaSong, Fileinfo songFileinfo, Song song) {
        // 标题
        song.setTitle(metaSong.getTitle());
        // 原始标题
        song.setOriginTitle(metaSong.getTitleorg());
        // 作者
        song.setArtist(metaSong.getArtist());
        // bpm
        song.setBpm(metaSong.getBpm());
        try {
            song.setLength(remoteFileSystemUtils.getSongInfo(songFileinfo.getUri()));
        } catch (Exception e) {
            log.error("解析歌曲长度失败");
            e.printStackTrace();
        }
    }

    /**
     * 对谱面进行解析，内部理论上不会抛出异常
     * @param chartFileinfo
     * @param chart
     * @param song
     * @return
     */
    private MalodyChart.McMeta.McSong solveChart(List<Fileinfo> list, Fileinfo imageFileinfo, Fileinfo chartFileinfo, Chart chart, Song song) {
        MalodyChart malodyChart = remoteFileSystemUtils.getMalodyChartInfo(chartFileinfo.getUri());
        if (malodyChart == null || malodyChart.getMeta() == null) {
            chart.setResolveState(CoreResolveStateEnum.AUTO_UPDATE_ERROR);
            return null;
        }
        MalodyChart.McMeta chartInfo = malodyChart.getMeta();
        // 铺面模式
        chart.setMode(CoreChartModeEnum.getModeByValue(chartInfo.getMode()));
        // 铺面创建者
        chart.setCreator(chartInfo.getCreator());
        // 铺面难度名
        chart.setVersion(chartInfo.getVersion());
        // 谱面难度
        chart.setLevel(solveLevel(chartInfo.getVersion()));
        // 更新歌曲状态
        int mode = song.getMode();
        mode |= 1 << chart.getMode().getValue();
        song.setMode(mode);

        if(chartInfo.getBackground() != null) {
            if (!chartInfo.getBackground().equals(imageFileinfo.getName())) {
                // 保存真正的背景uri
                imageFileinfo = null;
                for (Fileinfo fileinfo : list) {
                    if(fileinfo.getName().equals(chartInfo.getBackground())) {
                        imageFileinfo = fileinfo;
                        break;
                    }
                }
                if(imageFileinfo == null) {
                    log.error("解析谱面[{}]时未在文件列表中找到与之相对应的背景图片", chart.getId());
                }
            }
        }else{
            // 谱面没有背景图片
            // chart.setBackground(null);
        }
        return malodyChart.getMeta().getSong();
    }


    /**
     * 解析谱面难度
     * @param version
     * @return
     */
    private Integer solveLevel(String version) {
        // TODO null校验
        try {
            int index = version.lastIndexOf("Lv.");
            int start = index + "Lv.".length();
            int end = start;
            while (end < version.length() && Character.isDigit(version.charAt(end))) {
                end++;
            }
            return Integer.parseInt(version.substring(start, end));
        }catch (Exception e) {
            return 0;
        }
    }
}
