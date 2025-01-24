package com.timi.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

@Configuration
public class SwaggerConfig {
    @Bean
    public Docket customDocket(){
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.timi.controller"))
                .build();
    }

    private ApiInfo apiInfo() {
        Contact contact=new Contact("Timi的个人笔记","http://www.timigreat.cn","timi546d69@163.com");
        return new ApiInfoBuilder()
                .title("博客系统后台文档")
                .description("该文档描述了博客系统的后台接口")
                .contact(contact)
                .version("1.1.1")
                .build();
    }
}
