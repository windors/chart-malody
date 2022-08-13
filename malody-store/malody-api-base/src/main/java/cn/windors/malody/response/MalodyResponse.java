package cn.windors.malody.response;

import cn.windors.malody.type.MalodyResponseEnum;
import io.swagger.annotations.ApiModel;
import lombok.Getter;
import lombok.Setter;

/**
 * @author Windor
 */
@Getter
@Setter
@ApiModel("基本响应")
public class MalodyResponse {
    private MalodyResponseEnum code;
    public MalodyResponse() {
        code = MalodyResponseEnum.OK;
    }

    public MalodyResponse(MalodyResponseEnum code) {
        this.code = code;
    }
}
