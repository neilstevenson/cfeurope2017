package neil.demo.cfeurope2017;

import java.util.Collection;
import java.util.Map.Entry;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.core.IMap;
import com.hazelcast.projection.Projection;
import com.hazelcast.projection.Projections;

import lombok.extern.slf4j.Slf4j;

/**
 * <P>The business logic of this microservice, return a list of all credit card users.
 * </P>
 */
@RestController
@Slf4j
public class TheRestController {

	@Autowired
	private HazelcastInstance hazelcastInstance;
	
	@GetMapping("/ms1/users")
    public Object[][] users() {
		log.info("users()");
		
		IMap<String, CCUser> usersMap
			= this.hazelcastInstance.getMap("cc-user");

		Projection<Entry<String, CCUser>, Object[]> projection
			= Projections.multiAttribute("userId", "firstName");
		
		Collection<Object[]> users = usersMap.project(projection);
		
		return users.toArray(new Object[users.size()][]);
    }
	
}
