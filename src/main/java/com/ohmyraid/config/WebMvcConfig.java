package com.ohmyraid.config;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.mvc.method.RequestMappingInfoHandlerMapping;
import springfox.documentation.spring.web.plugins.WebMvcRequestHandlerProvider;
import springfox.documentation.spring.web.readers.operation.HandlerMethodResolver;

import javax.servlet.ServletContext;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Configuration
@Slf4j
@RequiredArgsConstructor
public class WebMvcConfig implements WebMvcConfigurer {

    // message source
    @Value("${spring.messages.basename}")
    private final String messagesBasename = null;

    @Value("${spring.messages.encoding}")
    private final String messagesEncoding = null;

    @Value("${spring.messages.cache-duration}")
    int messagesCacheSeconds = -1;

    @Value("${spring.redis.port}")
    private int redisPort;

    @Value("${spring.redis.host}")
    private String redisHost;

    @Value("${project.interceptor.authentication.exclude-path}")
    private final String AUTHENTICATION_EXCLUDE_PATH = null;

    @Value("${project.interceptor.authentication.add-path}")
    private final String AUTHENTICATION_ADD_PATH = null;

    private final AuthenticationInterceptor authenticationInterceptor;

    private final RequestInterceptor requestInterceptor;

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


    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("http://localhost:3000", "http://localhost:5000");
    }

    @Bean
    public ReloadableResourceBundleMessageSource messageSource() {
        ReloadableResourceBundleMessageSource messageSource = new ReloadableResourceBundleMessageSource();
        messageSource.setBasename(messagesBasename);
        messageSource.setDefaultEncoding(messagesEncoding);
        messageSource.setCacheSeconds(messagesCacheSeconds);
        return messageSource;
    }

    @Bean
    public RedisConnectionFactory redisConnectionFactory() {
        return new LettuceConnectionFactory(redisHost, redisPort);
    }

    @Bean
    public RedisTemplate<?, ?> redisTemplate() {
        RedisTemplate<String, Object> redisTemplate = new RedisTemplate<>();
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        redisTemplate.setValueSerializer(new GenericJackson2JsonRedisSerializer());
        redisTemplate.setConnectionFactory(redisConnectionFactory());
        return redisTemplate;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {

        log.info("WebMvcConfig :: excludePath is {}", AUTHENTICATION_EXCLUDE_PATH);
        String[] excludePath = AUTHENTICATION_EXCLUDE_PATH.split(",");
        String[] addPath = AUTHENTICATION_ADD_PATH.split(",");
        log.info("WebMvcConfig :: excludePath size is {}", excludePath.length);

        registry.addInterceptor(requestInterceptor);

        registry.addInterceptor(authenticationInterceptor)
                .addPathPatterns(addPath)
                .excludePathPatterns(excludePath);
    }

    @Bean
    public WebMvcRequestHandlerProvider webMvcRequestHandlerProvider(
            Optional<ServletContext> context,
            HandlerMethodResolver methodResolver,
            List<RequestMappingInfoHandlerMapping> handlerMappings) {
        handlerMappings = handlerMappings.stream()
                .filter(rh -> rh.getClass().getName().contains("RequestMapping")).collect(Collectors.toList());
        return new WebMvcRequestHandlerProvider(methodResolver, handlerMappings);
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
