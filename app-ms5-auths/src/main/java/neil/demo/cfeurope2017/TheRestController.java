package neil.demo.cfeurope2017;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.core.IMap;

import lombok.extern.slf4j.Slf4j;

/**
 * <P>The business logic of this microservice, return a list of authorisation holds
 * for a CSV list of ids.
 * </P>
 */
@RestController
@Slf4j
public class TheRestController {

	@Autowired
	private HazelcastInstance hazelcastInstance;
	
	@GetMapping("/ms5/auths/{authIds}")
    public CCAuthorisation[] auths(@PathVariable String authIds) {
		log.info("auths({})", authIds);
		
		IMap<String, CCAuthorisation> authorisationsMap
		= this.hazelcastInstance.getMap("cc-authorisations");

		Collection<CCAuthorisation> values =
			authorisationsMap.getAll(new HashSet<>(Arrays.asList(authIds.split(",")))).values();
	
		return values.toArray(new CCAuthorisation[values.size()]);
	}
	
}
