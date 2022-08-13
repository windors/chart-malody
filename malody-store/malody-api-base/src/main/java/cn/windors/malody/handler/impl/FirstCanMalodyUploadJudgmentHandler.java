package cn.windors.malody.handler.impl;


import cn.windors.malody.entity.core.Chart;
import cn.windors.malody.handler.MalodyUploadJudgmentHandler;
import cn.windors.malody.service.ChartService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author Wind_Yuan
 */
@Slf4j
public class FirstCanMalodyUploadJudgmentHandler implements MalodyUploadJudgmentHandler {
    @Autowired
    private ChartService chartService;

    @Override
    public String reject(Long uid, Long cid) {
        Chart chart = chartService.getById(cid);
        if(chart == null || uid.equals(chart.getUid())) {
            return null;
        }
       return "你不是该铺面首次上传者, 不能对该铺面进行修改!";
    }
}
