package cn.windors.malody.controller.api.base.upload;


import cn.windors.malody.entity.core.Fileinfo;
import cn.windors.malody.handler.MalodyUploadJudgmentHandler;
import cn.windors.malody.properties.MalodyCoreProperties;
import cn.windors.malody.response.FileResponse;
import cn.windors.malody.service.ChartService;
import cn.windors.malody.service.FileinfoService;
import cn.windors.malody.type.CoreFileTypeEnum;
import cn.windors.malody.util.MalodyUtils;
import cn.windors.malody.util.RemoteFileSystemUtils;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Wind_Yuan
 */
@Slf4j
@RestController
@RequestMapping("/api/store/upload")
@Api(tags = {"Malody Upload API"})
public class SignController {
    @Autowired
    private ChartService chartService;

    @Autowired
    private FileinfoService fileinfoService;

    @Autowired
    private RemoteFileSystemUtils remoteFileSystemUtils;

    @Autowired
    private MalodyUploadJudgmentHandler uploadJudgmentHandler;


    /**
     * 需要请求 文件API 获得上传路径，然后返回给Malody
     * 并将初始信息保存到数据库
     */
    @PostMapping("/sign")
    @ApiOperation(value = "获取签名（202103添加）", notes = "对应上传阶段1")
    @ApiImplicitParams({
            @ApiImplicitParam(value = "song id", name = "sid", paramType = "int"),
            @ApiImplicitParam(value = "chart id", name = "cid", paramType = "int"),
            @ApiImplicitParam(value = "所有待文件名，以逗号连接", name = "name", allowEmptyValue = true, paramType = "string"),
            @ApiImplicitParam(value = "所有待文件md5，以逗号连接", name = "hash", allowEmptyValue = true, paramType = "string")
    })
    public FileResponse sign(Long uid,
                             @RequestParam Long sid,
                             @RequestParam Long cid,
                             @RequestParam String name,
                             @RequestParam String hash) {
        // 非法传值校验
        String[] names = name.split(",");
        String[] hashes = hash.split(",");
        FileResponse response = signCheck(names, hashes);
        if (response != null) {
            return response;
        }

        // 已存在铺面校验, 判断uid是否具有修改的权力
        String msg = uploadJudgmentHandler.reject(uid, cid);
        if (msg != null) {
            log.info("拒绝上传: 上传请求被拒绝, 拒绝原因:[{}] ,uid: [{}], cid: [{}],  name: [{}], hash: [{}]", msg, uid, cid, name, hash);
            return FileResponse.error(0, msg);
        }

        // 获取文件上传列表
        List<Fileinfo> fileinfos = getReusableFileinfoList(sid, cid, names, hashes);
        if (fileinfos.size() == 0) {
            log.info("拒绝上传: 谱面文件未被修改:uid: [{}], cid: [{}], name: [{}], hash: [{}]", uid, cid, name, hash);
            return FileResponse.error(0, "您的谱面文件已全部上传完毕，请勿重复上传");
        }
        List<Map<String, String>> metas = new ArrayList<>(names.length);
        for (Fileinfo fileinfo : fileinfos) {
            Map<String, String> meta = new HashMap<>(8);
            meta.put("name", "file");
            meta.put("id", fileinfo.getUri());
            meta.put("filename", fileinfo.getName());
            metas.add(meta);
        }

        // Malody客户端会对返回数据的长度做检查，长度不正确就不会发送传文件请求
        // T_T
        int cao = names.length - fileinfos.size();
        for (int i = 0; i < cao; i++) {
            Map<String, String> meta = new HashMap<>(8);
            meta.put("name", "file");
            meta.put("id", "-1");
            meta.put("filename", "");
            metas.add(meta);
        }
        return FileResponse.success(metas, MalodyCoreProperties.fileUploadAddress);
    }

    /**
     * 阶段一校验
     * @param names 所有待上传的文件名
     * @param hashes 所有待上传的文件的MD5值
     * @return 如果校验成功返回null，否则返回一个FileResponse对象，可直接返回给前端
     */
    private FileResponse signCheck(String[] names, String[] hashes) {
        // 非法传值校验
        if (names.length != hashes.length) {
            return FileResponse.lengthNotEqualError();
        }

        // 服务器可解析文件名校验
        if (MalodyUtils.getChartName(names) == null) {
            return FileResponse.error(0, "服务器未检测到可解析文件");
        }
        return null;
    }

    /**
     *  会覆盖文件服务器端的文件，实现最小存储
     * @return 只发生了改变的文件的上传集合
     */
    private List<Fileinfo> getReusableFileinfoList(long sid, long cid, String[] names, String[] hashes) {
        // 旧集合
        List<Fileinfo> oldList = fileinfoService.list(new QueryWrapper<Fileinfo>().eq("tid", cid).eq("type", CoreFileTypeEnum.CHART.getValue()));
        // 丢失了的集合，将来需要复用或申请新链接
        List<Fileinfo> lostList = new ArrayList<>(names.length);

        // 无需变动的上传链接信息
        List<Fileinfo> finalList = new ArrayList<>();

        for (int i = 0; i < names.length; i++) {
            Fileinfo nowInfo = Fileinfo.chartFileInstance(cid, hashes[i], names[i]);
            int index = oldList.indexOf(nowInfo);
            if (index < 0) {
                // 文件发生了改变
                lostList.add(nowInfo);
            } else {
                // 文件没有发生改变
                finalList.add(oldList.remove(index));
            }
        }

        assert finalList.size() + lostList.size() == names.length;
        // 此时需要比对旧集合与丢失集合的size，对数据库中条数进行更新
        if (oldList.size() != lostList.size()) {
            int poor = Math.abs(oldList.size() - lostList.size());
            if (oldList.size() > lostList.size()) {
                // 新文件删除了某些内容，此时需要删除数据库中部分条数
                for (int i = 0; i < poor; i++) {
                    Fileinfo remove = oldList.remove(0);
                    remoteFileSystemUtils.releaseRemoteFile(remove.getUri());
                    fileinfoService.removeById(remove.getId());
                }
            } else {
                // 需要申请新链接，并保存到oldList中
                for (int i = 0; i < poor; i++) {
                    String uri = remoteFileSystemUtils.getNewUploadUri();
                    Fileinfo fileinfo = new Fileinfo();
                    fileinfo.setUri(uri);
                    oldList.add(fileinfo);
                }
            }
        }

        // 此时lostList中的条数和oldList中的条数是相同的，lostList直接复用oldList中的uri即可。
        assert oldList.size() == lostList.size();
        int size = oldList.size();
        for (int i = 0; i < size; i++) {
            // 其他信息在第一步中已经做完，这里只需要uri和id，用来取代
            Fileinfo oldFileinfo = oldList.remove(0);
            lostList.get(i).setUri(oldFileinfo.getUri() == null ?
                    remoteFileSystemUtils.getNewUploadUri() : oldFileinfo.getUri());
            lostList.get(i).setId(oldFileinfo.getId());
        }
        fileinfoService.saveOrUpdateBatch(lostList);

        // 再检查已保存资源是否有问题，确保finalList中的文件在文件服务器中真的保存了文件
        for (Fileinfo fileinfo : finalList) {
            if(!remoteFileSystemUtils.checkFile(fileinfo.getUri(), fileinfo.getMd5())) {
                lostList.add(fileinfo);
            }
        }
        return lostList;
    }

    /**
     * 如果有文件发生了改变（equals不同），会直接删除原先的记录，然后申请新的记录。也就是说在不删除在文件服务器端的文件
     * @return 所有文件的上传信息集合
     */
    @Deprecated
    private List<Fileinfo> getSuccessFileinfoList2(long sid, long cid, String[] names, String[] hashes) {
        List<Fileinfo> oldList = fileinfoService.list(new QueryWrapper<Fileinfo>().eq("tid", cid).eq("type", CoreFileTypeEnum.CHART.getValue()));
        List<Fileinfo> needNewRequestList = new ArrayList<>(names.length);
        List<Fileinfo> resultList = new ArrayList<>(names.length);

        for (int i = 0; i < names.length; i++) {
            int index = oldList.indexOf(Fileinfo.chartFileInstance(cid, hashes[i], names[i]));
            if (index < 0) {
                String url = MalodyCoreProperties.fileGetUriAddress;
                log.info("file {} lost, need to request new uri for --- {}", names[i] + "[" + hashes[i] + "]", url);
                String uri = remoteFileSystemUtils.getNewUploadUri();
                log.info("get uri resource --- {}", uri);
                Fileinfo fileinfo = Fileinfo.chartFileInstance(cid, hashes[i], names[i]);
                fileinfo.setUri(uri);
                needNewRequestList.add(fileinfo);
                continue;
            }
            Fileinfo fileinfo = oldList.remove(index);
            resultList.add(fileinfo);
        }

        if (needNewRequestList.size() > 0) {
            // 此时resultList包含了数据库中已有的FileInfo
            resultList.addAll(needNewRequestList);
            // 此时needNewRequestList包含了新获取到上传路径的FileInfo，需要保存
            fileinfoService.saveBatch(needNewRequestList);
        }
        if (oldList.size() > 0) {
            // 此时oldList只剩下了未匹配成功的FileInfo，需要删除
            List<Long> idsList = new ArrayList<>();
            oldList.forEach(fileinfo -> idsList.add(fileinfo.getId()));
            fileinfoService.removeByIds(idsList);
        }
        return resultList;
    }
}
