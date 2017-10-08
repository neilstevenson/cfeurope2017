package neil.demo.cfeurope2017;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

import com.hazelcast.client.HazelcastClient;
import com.hazelcast.client.config.ClientConfig;
import com.hazelcast.client.config.XmlClientConfigBuilder;
import com.hazelcast.core.HazelcastInstance;


/**
 * <P>Spring Boot does not yet automatically create the necessary
 * beans for a Hazelcast client.
 * </P>
 */
@Configuration
public class ApplicationConfig {

	/**
	 * <P>Load config by looking for {@code hazelcast-client.xml} 
	 * on the classpath.
	 * </P>
	 * 
	 * @return Config to connect client to server
	 * @throws Exception
	 */
    @Bean
    public ClientConfig clientConfig() throws Exception {
            return new XmlClientConfigBuilder().build();
    }

    /**
     * <P>Create the client and connect to the server(s).
     * </P>
     * 
     * @param clientConfig The {@code @Bean} created above
     * @return A Hazelcast client instance
     */
    @Bean
    public HazelcastInstance hazelcastInstance(ClientConfig clientConfig) {
            return HazelcastClient.newHazelcastClient(clientConfig);
    }

    /**
     * <P>For REST, process to process calls.
     * </P>
     * 
     * @return
     */
    @Bean
    public RestTemplate restTemplate() {
            return new RestTemplate();
    }    
    
}
