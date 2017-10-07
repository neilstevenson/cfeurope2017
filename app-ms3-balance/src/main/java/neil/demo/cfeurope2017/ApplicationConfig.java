package neil.demo.cfeurope2017;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

/**
 * <P>A {@code @Bean} for use in {@link TheRestController}.
 * </P>
 */
@Configuration
public class ApplicationConfig {

    @Bean
    public RestTemplate restTemplate() {
            return new RestTemplate();
    }    
}
