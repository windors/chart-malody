package cn.windors.malody.mapper;

import cn.windors.malody.entity.core.Chart;
import cn.windors.malody.entity.core.Fileinfo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author Wind_Yuan
 */
public interface FileinfoMapper extends BaseMapper<Fileinfo> {
    List<Chart> getChartFileIdBySid(Long sid);
    List<Long> getChartFileinfoByCid(Long cid);
    Long getChartFileinfoByCidAndMd5(@Param("cid") Long cid, @Param("md5") String md5);
}
