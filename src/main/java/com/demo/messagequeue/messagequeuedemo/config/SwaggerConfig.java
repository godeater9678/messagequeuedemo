package com.bithumb.messagequeue.messagequeuedemo.config;

import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SwaggerConfig {
    public ApiInfo apiInfo(){
        return new ApiInfoBuilder().title("SampleWeb")
                .description("")
                .version("0.0.0")
                .build();
    }
}
