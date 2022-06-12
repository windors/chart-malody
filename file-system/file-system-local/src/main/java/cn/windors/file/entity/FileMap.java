package cn.windors.file.entity;

import cn.windors.file.enums.FileMapStateEnum;
import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * @author Windor
 * 文件实体类，存储本地映射
 */
@Data
@TableName("file_map")
public class FileMap extends BaseEntity {
    /**
     * id
     */
    private Long id;

    /**
     * 本地路径（相对）
     */
    private String local;

    /**
     * 文件md5值
     */
    private String md5;

    /**
     * 文件原始名
     */
    private String originalName;

    @TableField(fill = FieldFill.INSERT)
    private FileMapStateEnum state;
}
