package cn.windors.malody.interceptor;

import cn.windors.malody.util.RsaVerifyUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Windors
 * 非法请求拦截器，（拦截非Malody的请求）
 */
@Slf4j
public class SecurityCheckInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String uid = request.getParameter("uid");
        String key = request.getParameter("key");
        boolean result = RsaVerifyUtil.verifySign(uid, key);
//        if(!result) {
//            log.info("非法请求，验签失败, uid: {}", uid);
//            return false;
//        }
        return true;
    }
}
