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

}
