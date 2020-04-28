package org.chen.rfid.config.swagger;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * @author 程强
 * @date 2020年01月18日 14:58
 * @Description:
 */
@Configuration
@EnableSwagger2
public class SwaggerConfig {
    @Bean
    public Docket createRestApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .pathMapping("/")
                .select()
                .apis(RequestHandlerSelectors.basePackage("org.chen.rfid"))
                .paths(PathSelectors.any())
                .build().apiInfo(new ApiInfoBuilder()
                        .title("机房人员室内定位管理系统")
                        .description("后端接口设计文档 😀")
                        .version("1.0.0")
                        .contact(new Contact("言少钱","https://github.com/cheng-qiang/","yiwannuofulasi@163.com"))
                        .license("江南一点雨")
                        .licenseUrl("https://www.javaboy.org/springboot/")
                        .build());
    }
}
