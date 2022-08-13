package cn.windors.malody.service.impl;

import cn.windors.malody.entity.core.Song;
import cn.windors.malody.mapper.SongMapper;
import cn.windors.malody.service.SongService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author Windor
 */
@Service
public class SongServiceImpl extends ServiceImpl<SongMapper, Song> implements SongService {

    @Resource
    private SongMapper songMapper;

    @Override
    public Page<Song> querySong(String word, int org, int mode, int levelGt, int levelLt, int beta, int from, int pageSize) {
        QueryWrapper<Song> queryWrapper = new QueryWrapper<Song>().ge("level", levelGt);
        if (levelLt > 0) {
            queryWrapper.le("c.level", levelLt);
        }
        if(word != null) {
            // TODO 给谱面也加上搜索条件
            queryWrapper.like("title", word);
        }
//        // 版本大于穿过来的值
//        if(beta != 0) {
//            log.warn("Malody客户端已更新，请及时处理beta: {}。API中beta指是否返回非stable谱面，本系统暂时设计为beta不是默认值会搜索stable谱面", beta);
//            queryWrapper.eq("type", ChartTypeEnum.STABLE);
//        }
        if (mode != -1) {
            // 根据歌曲mode查询，所以需要让mode在之内
            queryWrapper.eq("c.mode", mode);
        }
        queryWrapper.eq("is_deleted", 0);
        return songMapper.querySongFromSongAndChart(new Page<>(from, pageSize), queryWrapper);
    }

    @Override
    public List<Song> querySongBySidOrCid(Long sid, Long cid, Integer org) {
        QueryWrapper<Song> queryWrapper = new QueryWrapper<Song>().select("sid");
        if (sid != null) {
            queryWrapper.eq("s.id", sid);
        }
        if (cid != null){
            queryWrapper.eq("c.id", cid);
        }
        return songMapper.selectList(queryWrapper);
    }

    @Override
    public Song querySongById(Long id) {
        return songMapper.selectById(id);
    }
}
