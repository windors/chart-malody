package cn.windors.malody.entity.core;

import cn.windors.malody.type.CoreFileTypeEnum;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Objects;

/**
 * @author Windor
 */
@Data
@NoArgsConstructor
@TableName("core_fileinfo")
public class Fileinfo implements Serializable {
    @ApiModelProperty("文件id")
    private Long id;

    @ApiModelProperty("文件信息id")
    private Long tid;

    @ApiModelProperty("tid的类型")
    private CoreFileTypeEnum type;

    @ApiModelProperty("对应文件服务器的uri")
    private String uri;

    @ApiModelProperty("文件的md5值")
    private String md5;

    @ApiModelProperty("文件的原始名称")
    private String name;

    public Fileinfo(Long tid, CoreFileTypeEnum type, String uri, String md5, String name) {
        this.tid = tid;
        this.type = type;
        this.uri = uri;
        this.md5 = md5;
        this.name = name;
    }

    public static Fileinfo songFileInstance(Long sid, String md5, String name) {
        return new Fileinfo(sid, CoreFileTypeEnum.SONG, null, md5, name);
    }

    public static Fileinfo chartFileInstance(Long cid, String md5, String name) {
        return new Fileinfo(cid, CoreFileTypeEnum.CHART, null, md5, name);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Fileinfo fileinfo = (Fileinfo) o;
        return Objects.equals(tid, fileinfo.tid) && type == fileinfo.type && Objects.equals(md5, fileinfo.md5) && Objects.equals(name, fileinfo.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(tid, type, md5, name);
    }
}

