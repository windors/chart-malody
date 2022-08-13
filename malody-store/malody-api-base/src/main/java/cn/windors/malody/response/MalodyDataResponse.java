package cn.windors.malody.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * @author Windor
 */
@Getter
@Setter
@ApiModel("带有数据的响应")
public class MalodyDataResponse<T> extends MalodyResponse {
    @ApiModelProperty(value = "数据")
    private List<T> data;

    public static <T> MalodyDataResponse<T> success(List<T> data) {
        MalodyDataResponse<T> response = new MalodyDataResponse<>();
        response.setData(data);
        return response;
    }
}
