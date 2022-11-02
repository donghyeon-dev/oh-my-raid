package com.ohmyraid.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ParameterBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Parameter;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Configuration
@EnableSwagger2
public class SwaggerConfig {

    @Bean
    public Docket api() {

        // 스웨거창에 헤더 추가
        ParameterBuilder parameterBuilder = new ParameterBuilder();
        parameterBuilder.name("Authorization") // 헤더이름
                .description("Access Token")
                .modelRef(new ModelRef("string"))
                .parameterType("header")
                .required(false)
                .build();
        List<Parameter> parameters = new ArrayList<>();
        parameters.add(parameterBuilder.build());

        return new Docket(DocumentationType.SWAGGER_2)
                .produces(getProduceContentTypes())
                .globalOperationParameters(parameters)
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.ohmyraid")) // 현재 RequestMapping으로 할당된 모든 URL 리스트 추출
                .paths(PathSelectors.any())
                .build()
                .apiInfo(apiInfo());
    }

    /**
     * APIINFO 설정
     *
     * @return
     */
    private ApiInfo apiInfo() {
        return new ApiInfo(
                "Oh-My-Raid REST API", // Title
                "OMR Project API", // Description
                "v1", // Version
                "서비스 약관 URL",
                "contactName",
                "License",
                "localhost:8880"
        );
    }

    private Set<String> getProduceContentTypes() {
        Set<String> produces = new HashSet<>();
        produces.add("application/json");
        return produces;
    }

}
