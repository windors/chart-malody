package cn.windors.malody.interceptor;

import cn.windors.malody.util.RsaVerifyUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Windor
 */
@Slf4j
public class ApiSecurityInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // 202108及以下版本跳过验证
        if(202108 >= Integer.parseInt(request.getParameter("api"))) {
            return true;
        }

        // 验签
        String uid = request.getParameter("uid");
        String key = request.getParameter("key");
        boolean result = RsaVerifyUtil.verifySign(uid, key);
        if(!result) {
            log.info("验签失败, uid: {}", uid);
            return false;
        }
        return true;
    }
}
