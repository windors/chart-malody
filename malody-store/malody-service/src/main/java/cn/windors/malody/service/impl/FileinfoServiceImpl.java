package cn.windors.malody.service.impl;

import cn.windors.malody.entity.core.Fileinfo;
import cn.windors.malody.mapper.FileinfoMapper;
import cn.windors.malody.service.FileinfoService;
import cn.windors.malody.type.CoreFileTypeEnum;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Windor
 */
@Service
public class FileinfoServiceImpl extends ServiceImpl<FileinfoMapper, Fileinfo> implements FileinfoService {
    @Override
    public List<Fileinfo> list(CoreFileTypeEnum type, Long tid) {
        return list(new QueryWrapper<Fileinfo>().eq("tid", tid).eq("type", type.getValue()));
    }
}
