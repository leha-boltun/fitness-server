package ru.fitness.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.method.HandlerTypePredicate;
import org.springframework.web.servlet.config.annotation.PathMatchConfigurer;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {
    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/static/bundle.js")
                .setViewName("forward:/bundle.js");
        registry.addViewController("/{x:^(?!(?:|swagger-ui|pi|index.html|bundle.js)$).+$}")
                .setViewName("forward:/");
        registry.addViewController("/{x:^(?!(?:|swagger-ui|pi|index.html|bundle.js)$).+$}/**")
                .setViewName("forward:/");
    }

    @Override
    public void configurePathMatch(PathMatchConfigurer configurer) {
        configurer.addPathPrefix("pi", HandlerTypePredicate.forAnnotation(RestController.class));
    }
}
