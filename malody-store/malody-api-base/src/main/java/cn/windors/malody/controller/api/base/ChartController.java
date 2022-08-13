package cn.windors.malody.controller.api.base;


import cn.windors.malody.dto.ChartDto;
import cn.windors.malody.response.DownloadResponse;
import cn.windors.malody.response.MalodyListResponse;
import cn.windors.malody.dto.SongDto;
import cn.windors.malody.entity.core.Chart;
import cn.windors.malody.entity.core.Fileinfo;
import cn.windors.malody.entity.core.Song;
import cn.windors.malody.properties.MalodyCoreProperties;
import cn.windors.malody.service.ChartService;
import cn.windors.malody.service.FileinfoService;

import cn.windors.malody.service.SongService;
import cn.windors.malody.type.CoreFileTypeEnum;
import cn.windors.malody.util.DtoStaticFactory;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Wind_Yuan
 */

@Slf4j
@RestController
@RequestMapping("api/store")
public class ChartController {

    @Resource
    private SongService songService;

    @Resource
    private ChartService chartService;

    @Resource
    private FileinfoService fileinfoService;

    @ApiOperation(value = "歌曲列表（202103添加）")
    @ApiImplicitParams({
            @ApiImplicitParam(value = "搜索关键词", name = "word", allowEmptyValue = true, paramType = "string"),
            @ApiImplicitParam(value = "是否返回原始标题", name = "org", defaultValue = "0", paramType = "int"),
            @ApiImplicitParam(value = "返回指定模式谱面,定义参见**模式定义**", name = "mode", defaultValue = "-1", paramType = "int"),
            @ApiImplicitParam(value = "返回level大于于这个值的谱面", name = "lvge", defaultValue = "0", paramType = "int"),
            @ApiImplicitParam(value = "返回level小于这个值的谱面", name = "lvle", defaultValue = "0", paramType = "int"),
            @ApiImplicitParam(value = "返回非stable谱面", name = "beta", defaultValue = "0", paramType = "int"),
            @ApiImplicitParam(value = "翻页起点", name = "from", defaultValue = "0", paramType = "int")
    })
    @GetMapping("/list")
    public MalodyListResponse<SongDto> list(
            @RequestParam(value = "word", required = false) String word,
            @RequestParam(value = "org", defaultValue = "0") int org,
            @RequestParam(value = "mode", defaultValue = "-1") int mode,
            @RequestParam(value = "lvge", defaultValue = "0") int levelGt,
            @RequestParam(value = "lvle", defaultValue = "0") int levelLt,
            @RequestParam(value = "beta", defaultValue = "0") int beta,
            @RequestParam(value = "from", defaultValue = "1") int from) {
        Page<Song> songPage = songService.querySong(word, org, mode, levelGt, levelLt, beta, from, MalodyCoreProperties.pageSize);
        List<Song> songs = songPage.getRecords();

        // 筛选原始标题并封装到结果集中
        final List<SongDto> result = new ArrayList<>();
        songs.forEach(song -> {
            result.add(org == 0 ? DtoStaticFactory.getSongDtoOriginTitleInstance(song, MalodyCoreProperties.fileDownloadAddress) : DtoStaticFactory.getSongDtoInstance(song, MalodyCoreProperties.fileDownloadAddress));
        });
        return songPage.hasNext() ? MalodyListResponse.page(result, (int) (songPage.getCurrent() + 1)) : MalodyListResponse.noPage(result);
    }

    @GetMapping("/promote")
    @ApiOperation("获取当前推荐列表")
    public MalodyListResponse<SongDto> promote() {
        return list(null, 0, -1, 0,0,0,0);
    }

    @GetMapping("/charts")
    @ApiOperation("根据sid获取cid")
    @ApiImplicitParams({
            @ApiImplicitParam(value = "歌曲id", name = "sid", paramType = "Integer"),
            @ApiImplicitParam(value = "返回非stable谱面", name = "mode", paramType = "int", defaultValue = "0"),
            @ApiImplicitParam(value = "返回指定模式谱面,定义参见**模式定义**", name = "mode", defaultValue = "-1", paramType = "Integer"),
            @ApiImplicitParam(value = "下一页", name = "from", allowEmptyValue = true, paramType = "Integer"),
            @ApiImplicitParam(value = "是否从推荐列表来", name = "promote", allowEmptyValue = true, paramType = "Integer")
    })
    public MalodyListResponse<ChartDto> charts(@RequestParam Integer sid,
                                         @RequestParam(defaultValue = "0") Integer beta,
                                         @RequestParam(defaultValue = "-1") Integer mode,
                                         @RequestParam(defaultValue = "1") Integer from,
                                         Integer promote) {
        Page<Chart> chartPage = chartService.queryChart(sid, beta, mode, from, MalodyCoreProperties.pageSize, promote);
        List<Chart> charts = chartPage.getRecords();
        List<ChartDto> result = new ArrayList<>();
        charts.forEach(chart -> {
            result.add(DtoStaticFactory.getChartDtoInstance(chart));
        });
        if (chartPage.hasNext()) {
            return MalodyListResponse.page(result, chartPage.getCurrent() + 1);
        }
        return MalodyListResponse.noPage(result);
    }

    @GetMapping("query")
    @ApiOperation("当客户端输入s(\\d+)或c(\\d+)内容时，会自动提取数字部分，作为sid和cid调用此接口进行查询")
    public MalodyListResponse<SongDto> query(
            @RequestParam(required = false) Integer sid,
            @RequestParam(required = false) Integer cid,
            @RequestParam(defaultValue = "0") Integer org) {
        List<Song> songList = songService.querySongBySidOrCid(sid.longValue(), cid.longValue(), org);
        List<SongDto> result = new ArrayList<>();
        songList.forEach(song -> {
            result.add(org == 0 ? DtoStaticFactory.getSongDtoInstance(song, MalodyCoreProperties.fileDownloadAddress) : DtoStaticFactory.getSongDtoOriginTitleInstance(song, MalodyCoreProperties.fileDownloadAddress));
        });
        return MalodyListResponse.noPage(result);
    }

    @GetMapping("download")
    @ApiOperation("获取指定谱面的下载地址")
    public DownloadResponse download(@RequestParam Long cid) {
        List<Fileinfo> fileinfoList = fileinfoService.list(new QueryWrapper<Fileinfo>().eq("tid", cid).eq("type", CoreFileTypeEnum.CHART));
        if(fileinfoList.size() == 0) {
            return DownloadResponse.empty();
        }
        long sid = fileinfoList.get(0).getId();
        DownloadResponse response = new DownloadResponse(sid, cid);
        for (Fileinfo fileinfo : fileinfoList) {
            response.add(fileinfo, MalodyCoreProperties.fileDownloadAddress);
        }
        return response;
    }
}