package cn.windors.malody.mapper;

import cn.windors.malody.entity.core.Chart;
import cn.windors.malody.entity.core.Song;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author Win
 */
public interface SongMapper extends BaseMapper<Song> {
    Page<Song> querySongFromSongAndChart(Page<Song> page, @Param(Constants.WRAPPER) QueryWrapper<Song> wrapper);
//
//    Page<Song> selectSongByWebCondition(Page<Song> page, @Param(Constants.WRAPPER) QueryWrapper<Song> wrapper);
//    List<Chart> selectChartByWebCondition(@Param("sid") Long id, @Param(Constants.WRAPPER) QueryWrapper<Song> wrapper);
}
