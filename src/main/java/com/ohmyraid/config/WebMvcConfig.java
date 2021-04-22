package com.ohmyraid.config;

import org.apache.catalina.filters.CorsFilter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.web.filter.CharacterEncodingFilter;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    // message source
    @Value("${spring.messages.basename}")
    private final String messagesBasename = null;
    @Value("${spring.messages.encoding}")
    private final String messagesEncoding = null;
    @Value("${spring.messages.cache-duration}")
    int messagesCacheSeconds = -1;
//
//    @Value("${springfox.documentation.swagger.ui-enabled}")
//    private final Boolean SWAGGER_UI_ENABLED = null;
//    @Value("${springfox.documentation.swagger.group-name}")
//    private final String SWAGGER_GROUP_NAME = null;
//    @Value("${springfox.documentation.swagger.description}")
//    private final String SWAGGER_DESCRIPTION = null;
//    @Value("${springfox.documentation.swagger.post-paths}")
//    private final String SWAGGER_POST_PATHS = null;
//    @Value("${springfox.documentation.swagger.use-service}")
//    private final String SWAGGER_USE_SERVICE = null;

//    @Bean
//    public FilterRegistrationBean<CorsFilter> simpleCorsFilter() {
//        FilterRegistrationBean<CorsFilter> bean = new FilterRegistrationBean<CorsFilter>();
//        bean.setFilter(new CorsFilter());
//        bean.addUrlPatterns("/*");
//
//        return bean;
//    }

    @Bean
    public ReloadableResourceBundleMessageSource messageSource() {
        ReloadableResourceBundleMessageSource messageSource = new ReloadableResourceBundleMessageSource();
        messageSource.setBasename(messagesBasename);
        messageSource.setDefaultEncoding(messagesEncoding);
        messageSource.setCacheSeconds(messagesCacheSeconds);
        return messageSource;
    }
//
//    @Bean
//    public CharacterEncodingFilter characterEncodingFilter(){
//        CharacterEncodingFilter characterEncodingFilter = new CharacterEncodingFilter();
//        characterEncodingFilter.setEncoding("UTF-8");
//        characterEncodingFilter.setForceEncoding(true);
//        return characterEncodingFilter;
//    }

//    @Bean
//    public Docket postsApi() {
//        List<String> seviecHead = new ArrayList<String>();
//        String[] seviecList = SWAGGER_USE_SERVICE.split(",");
//        for (String serviceId : seviecList) {
//            seviecHead.add(serviceId.toUpperCase().trim());
//        }
//        AllowableValues msaAllow = new AllowableListValues(seviecHead, "String");
//
//        List<Parameter> global = new ArrayList<Parameter>();
//        // Swagger Header에 Access Token 파라미터를 자동생성
//        global.add(new ParameterBuilder().name("Authorization").description("Authorization").parameterType("header")
//                .required(false).modelRef(new ModelRef("string")).build());
//
//
//        return new Docket(DocumentationType.SWAGGER_2).globalOperationParameters(global).groupName(SWAGGER_GROUP_NAME).apiInfo(apiInfo()).select()
//                .paths(postPaths()).apis(RequestHandlerSelectors.basePackage("com.ohmyraid")).build();
//    }
//
//    private Predicate<String> postPaths() {
//        return regex(SWAGGER_POST_PATHS);
//    }
//
//    private ApiInfo apiInfo() {
//        return new ApiInfoBuilder().title(SWAGGER_GROUP_NAME).description(SWAGGER_DESCRIPTION).version("v1").build();
//    }
}
