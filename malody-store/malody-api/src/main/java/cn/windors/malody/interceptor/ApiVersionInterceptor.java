package cn.windors.malody.interceptor;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Windor
 * API版本分发
 */
@Slf4j
public class ApiVersionInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String api = request.getParameter("api");
        String path = request.getContextPath();
        log.info(path);
        request.getRequestDispatcher(path).forward(request, response);
        return false;
    }
}
