package cn.windors.malody.config;

import cn.windors.malody.interceptor.ApiVersionInterceptor;
import cn.windors.malody.interceptor.SecurityCheckInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author Windor
 */
@Configuration
public class WebMvcConfiguration implements WebMvcConfigurer {

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new SecurityCheckInterceptor())
                .addPathPatterns("/api/**");
        registry.addInterceptor(new ApiVersionInterceptor())
                .addPathPatterns("/api/**");
    }
}