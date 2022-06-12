package cn.windors.file.handler;

import cn.windors.file.entity.BaseEntity;
import cn.windors.file.entity.FileMap;
import cn.windors.file.enums.FileMapStateEnum;
import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.reflection.MetaObject;

import java.time.LocalDateTime;

/**
 * @author Windor
 * https://baomidou.com/pages/4c6bcf/
 * 填充原理是直接给entity的属性设置值!!!，默认属性有值则不覆盖,如果填充值为null则不填充
 */
@Slf4j
public class FileMetaObjectHandler implements MetaObjectHandler {
    @Override
    public void insertFill(MetaObject metaObject) {
        String user = getUser();
        if (metaObject.getOriginalObject() instanceof BaseEntity) {
            log.debug("自动填充通用字段");
            LocalDateTime now = LocalDateTime.now();
            metaObject.setValue("updateTime", now);
            metaObject.setValue("updateBy", user);
            metaObject.setValue("createTime", now);
            metaObject.setValue("createBy", user);
        }
        if (metaObject.getOriginalObject() instanceof FileMap) {
            log.debug("自动填充 state 为 WATTING");
            metaObject.setValue("state", FileMapStateEnum.WATING);
        }
    }

    @Override
    public void updateFill(MetaObject metaObject) {
        String user = getUser();
        LocalDateTime now = LocalDateTime.now();
        if (metaObject.getOriginalObject() instanceof BaseEntity) {
            metaObject.setValue("updateTime", now);
            metaObject.setValue("updateBy", user);
        }
    }

    /**
     * TODO 获取当前用户
     * @return 当前用户(String)
     */
    private String getUser() {
        return null;
    }
}
