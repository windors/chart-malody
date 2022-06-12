package cn.windors.file.dto;

import lombok.Getter;
import lombok.Setter;

/**
 * @author Windor
 */
@Getter
@Setter
public final class HttpDataResponse<T> extends HttpResponse {
    private T data;

    public HttpDataResponse(HttpResponseEnum code, T data) {
        super(code);
        this.data = data;
    }

    public static <T> HttpDataResponse<T> ok(T data) {
        return new HttpDataResponse<>(HttpResponseEnum.OK, data);
    }
}
