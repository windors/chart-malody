package cn.windors.file.dto;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Windor
 * 统一返回对象（基类）
 */
@Getter
@Setter
public class HttpResponse implements Serializable {
    private final HttpResponseEnum code;
    private final String msg;

    private static Map<HttpResponseEnum, HttpResponse> enumHttpResponseMap = new HashMap<>(32, 1);

    HttpResponse(HttpResponseEnum code) {
        this.code = code;
        this.msg = code.getMsg();
    }

    HttpResponse(HttpResponseEnum code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    /**
     * 获取实例
     * @param code 状态枚举
     * @return 统一返回对象
     */
    public static HttpResponse getInstance(HttpResponseEnum code) {
        HttpResponse result = enumHttpResponseMap.get(code);
        if(result == null) {
            result = new HttpResponse(code);
            enumHttpResponseMap.put(code, result);
        }
        return result;
    }

    public static HttpResponse getInstance(HttpResponseEnum code, String msg) {
        return new HttpResponse(code, msg);
    }



    public static HttpResponse ok() {
        return getInstance(HttpResponseEnum.OK);
    }

    public static HttpResponse auto(boolean flag) {
        return flag ? ok() : getInstance(HttpResponseEnum.ERROR);
    }
}
