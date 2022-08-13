package cn.windors.malody.service;

import cn.windors.malody.entity.core.Fileinfo;
import cn.windors.malody.type.CoreFileTypeEnum;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * @author Windor
 */
public interface FileinfoService extends IService<Fileinfo> {
    List<Fileinfo> list(CoreFileTypeEnum type, Long tid);
}
