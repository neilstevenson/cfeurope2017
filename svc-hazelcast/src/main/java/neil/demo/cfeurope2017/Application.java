package neil.demo.cfeurope2017;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * <P>Start the service as a Spring Boot application. Spring Boot will
 * find the {@code hazelcast.xml} file, and correctly deduce we want
 * a Hazelcast server and build one as a {@code @Bean}.
 * </P>
 */
@SpringBootApplication
public class Application {

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

}
