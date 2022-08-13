package cn.windors.malody.response;


import cn.windors.malody.type.MalodyResponseEnum;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.Map;

/**
 * @author Windor
 */
@Data
@ApiModel("文件相关响应")
@Slf4j
public class FileResponse extends MalodyResponse {
    @ApiModelProperty("当服务器认为待上传文件有问题时，返回出问题的文件序号，默认值-1表示没有问题")
    private Integer errorIndex;
    @ApiModelProperty("出错的原因文字描述")
    private String errorMsg;
    @ApiModelProperty("上传的目标服务器地址")
    private String host;
    @ApiModelProperty("需要添加到form-data的字段key-value对")
    private List<Map<String, String>> meta;


    public static FileResponse error(int errorIndex, String errorMsg) {
        FileResponse response = new FileResponse();
        response.setErrorIndex(errorIndex);
        response.setErrorMsg(errorMsg);
        log.info("上传失败,返回信息: " + response);
        return response;
    }

    public static FileResponse success(List<Map<String, String>> meta, String host) {
        FileResponse response = new FileResponse();
        // -1 表示没有问题
        response.setErrorIndex(-1);
        response.setHost(host);
        response.setMeta(meta);
        log.info("上传通过,返回信息: " + response);
        return response;
    }

    public static FileResponse lengthNotEqualError() {
        FileResponse response = new FileResponse();
        response.setCode(MalodyResponseEnum.LENGTH_NOT_EQUAL);
        response.setErrorIndex(0);
        response.setErrorMsg("文件名中不能带有','!");
        log.info("上传失败,返回信息: " + response);
        return response;
    }
}
