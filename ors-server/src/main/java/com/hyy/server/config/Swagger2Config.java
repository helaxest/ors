package com.hyy.server.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.*;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;
import java.util.List;


/**
 * Description
 * Swagger2配置类
 *
 * @author helaxest
 * @date 2021/03/15  13:31
 * @since
 */
@Configuration
@EnableSwagger2
public class Swagger2Config {

    @Bean
    public Docket createRestApi() {
        //扫描哪些包下面生成swagger文档
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.hyy.server.controller"))
                .paths(PathSelectors.any())
                .build()
                //给swagger2添加全局的Authentication
                .securityContexts(securityContexts())
                .securitySchemes(securitySchemes());

    }


    private ApiInfo apiInfo() {
        //设置文档信息
        return new ApiInfoBuilder()
                .title("网上报销接口文档")
                .description("网上报销接口文档")
                .contact(new Contact("helaxest", "http:localhost:8081/doc.html", "2282561186@qq.com"))
                .version("1.0")
                .build();
    }
    private List<ApiKey> securitySchemes(){
        //设置请求头信息
        List<ApiKey> result=new ArrayList<>();
        //参数1为apikey的名字 后面为 k-v
        ApiKey apiKey=new ApiKey("Authorization","Authorization","Header");
        result.add(apiKey);
        return result;

    }
    private List<SecurityContext> securityContexts(){
        //设置需要登录认证的路径
        List<SecurityContext> result=new ArrayList<>();
        result.add(getContextByPath("/hello/.*"));
        return result;

    }

    private SecurityContext getContextByPath(String pathRegx) {
        return SecurityContext.builder()
                .securityReferences(defaultAuth())//默认的授权
                .forPaths(PathSelectors.regex(pathRegx))
                .build();
    }

    private List<SecurityReference> defaultAuth() {
        List<SecurityReference> result=new ArrayList<>();
        AuthorizationScope authorizationScope=new AuthorizationScope("global","accessEverything");//授权范围
        AuthorizationScope[] authorizationScopes=new AuthorizationScope[1];
        authorizationScopes[0]=authorizationScope;
        result.add(new SecurityReference("Authorization",authorizationScopes));
        return result;
    }


}
