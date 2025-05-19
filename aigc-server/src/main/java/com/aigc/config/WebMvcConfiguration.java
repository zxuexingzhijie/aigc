package com.aigc.config;

import com.aigc.interceptor.JwtTokenUserInterceptor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

/**
 * @author ：jiang
 * @date ：2024/4/15 16:29
 * @description ：配置类，注册web层相关组件
 */
@Configuration
@Slf4j
public class WebMvcConfiguration extends WebMvcConfigurationSupport {
    @Autowired
    private JwtTokenUserInterceptor jwtTokenUserInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        log.info("开始注册拦截器...");
        registry.addInterceptor(jwtTokenUserInterceptor)
                .addPathPatterns("/check/**")
                .excludePathPatterns("/user/login","/user/captcha","/user/register");
    }

    /**
     * springboot 中集成 knife4j 时，报错 No mapping for GET /doc.html
     * 出现这种情况可能是项目中含有继承WebMvcConfigurationSupport的类，这会导致 swagger 配置失效。
     * 解决方法，继承WebMvcConfigurationSupport下重写addResourceHandlers方法
     * @param registry
     */
    @Override
    protected void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("doc.html")
                .addResourceLocations("classpath:/META-INF/resources/");

        registry.addResourceHandler("swagger-ui.html")
                .addResourceLocations("classpath:/META-INF/resources/");
        registry.addResourceHandler("/webjars/**")
                .addResourceLocations("classpath:/META-INF/resources/webjars/");
    }

//    @Override
//    protected void addCorsMappings(CorsRegistry registry) {
//        registry.addMapping("/**") // 允许所有路径的跨域请求
//                .allowedOrigins("*") // 允许所有域的跨域请求，或者你可以指定具体的域名
//                .allowedMethods("GET", "POST", "PUT", "DELETE") // 允许的请求方法
//                .allowedHeaders("*") // 允许的头信息
//                .maxAge(168000); // 预检间隔时间
//    }
}
