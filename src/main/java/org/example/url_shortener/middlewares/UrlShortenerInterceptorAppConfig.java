package org.example.url_shortener.middlewares;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class UrlShortenerInterceptorAppConfig implements WebMvcConfigurer {
    UrlShortenerInterceptor urlShortenerInterceptor;

    public UrlShortenerInterceptorAppConfig(UrlShortenerInterceptor urlShortenerInterceptor) {
        this.urlShortenerInterceptor = urlShortenerInterceptor;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(urlShortenerInterceptor)
                .addPathPatterns("/redirect/**")
                .excludePathPatterns("/create");
    }
}
