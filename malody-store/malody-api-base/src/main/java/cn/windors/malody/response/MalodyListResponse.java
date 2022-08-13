package cn.windors.malody.response;


import cn.windors.malody.type.MalodyResponseEnum;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
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
@ApiModel("查询相关响应")
@JsonPropertyOrder({"code", "hasMore", "next", "data"})
public class MalodyListResponse<T> extends MalodyDataResponse<T> {
    @ApiModelProperty(value = "表示是否可以继续翻页")
    @JsonPropertyOrder("2")
    private boolean hasMore;

    @ApiModelProperty(value = "表示下一页的起点，客户端请求下一页时，会将next值通过from参数传回给服务器")
    @JsonPropertyOrder("3")
    private int next;

    /**
     * 返回一个不能翻页的Response
     * @param obj
     * @param <T>
     * @return
     */
    public static<T> MalodyListResponse<T> noPage(List<T> obj) {
        MalodyListResponse<T> response = new MalodyListResponse<>();
        response.setCode(MalodyResponseEnum.OK);
        response.setHasMore(false);
        response.setNext(0);
        response.setData(obj);
        return response;
    }

    /**
     * 返回一个可以翻页的相应
     * @param obj
     * @param next
     * @param <T>
     * @return
     */
    public static <T> MalodyListResponse<T> page(List<T> obj, long next) {
        MalodyListResponse<T> response = new MalodyListResponse<>();
        response.setCode(MalodyResponseEnum.OK);
        response.setHasMore(true);
        response.setNext((int) next);
        response.setData(obj);
        return response;
    }
}
