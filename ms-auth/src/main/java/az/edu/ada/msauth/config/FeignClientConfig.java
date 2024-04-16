package az.edu.ada.msauth.config;

import feign.RequestInterceptor;
import feign.RequestTemplate;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import az.edu.ada.msauth.model.entities.UserDetailsImpl;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@Configuration
@EnableFeignClients(basePackages = "az.edu.ada.msauth.client")
public class FeignClientConfig {
    private static final Logger logger = LoggerFactory.getLogger(FeignClientConfig.class);

    @Bean
    public RequestInterceptor requestInterceptor() {
        return requestTemplate -> {
            HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
            String jwtToken = (String) request.getAttribute("jwtToken");
            if (jwtToken != null) {
                requestTemplate.header("Authorization", "Bearer " + jwtToken);
                logger.info("Added JWT token to Feign request headers: {}", jwtToken);
            } else {
                logger.error("JWT token is null, cannot add to Feign request headers");
            }
        };
    }
}

