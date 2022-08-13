package cn.windors.malody.service;

import cn.windors.malody.entity.core.Chart;
import cn.windors.malody.entity.core.Song;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Windor
 */
public interface SongService  extends IService<Song> {
    /**
     * 根据条件查询歌曲
     * @param word 关键字
     * @param org 是否返回原始标题
     * @param mode 返回指定模式谱面,定义参见**模式定义**
     * @param levelGt 返回level大于于这个值的谱面
     * @param levelLt 返回level小于这个值的谱面
     * @param beta 返回非stable谱面
     * @param from 翻页起点
     * @param pageSize 每页条数
     * @return 歌曲列表
     */
    Page<Song> querySong(String word,
                         int org,
                         int mode,
                         int levelGt,
                         int levelLt,
                         int beta,
                         int from,
                         int pageSize);

    List<Song> querySongBySidOrCid(Long sid, Long cid, Integer org);

    Song querySongById(Long id);

}
