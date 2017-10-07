package neil.demo.cfeurope2017;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.core.IMap;

import lombok.extern.slf4j.Slf4j;

/**
 * <P>The business logic of this microservice, return an individual user.
 * </P>
 */
@RestController
@Slf4j
public class TheRestController {

	@Autowired
	private HazelcastInstance hazelcastInstance;
	
	@GetMapping("/ms2/user/{userId}")
    public CCUser user(@PathVariable String userId) {
		log.info("user({})", userId);
		
		IMap<String, CCUser> usersMap
			= this.hazelcastInstance.getMap("cc-user");

		return usersMap.get(userId);
    }
	
}
