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
 * <P>The business logic of this microservice, return a list of transaction holds
 * for a CSV list of ids.
 */
@RestController
@Slf4j
public class TheRestController {

	@Autowired
	private HazelcastInstance hazelcastInstance;
	
	@GetMapping("/ms4/txns/{txnIds}")
    public CCTransaction[] txns(@PathVariable String txnIds) {
		log.info("txns({})", txnIds);
		
		IMap<String, CCTransaction> transactionsMap
		= this.hazelcastInstance.getMap("cc-transactions");

		Collection<CCTransaction> values =
				transactionsMap.getAll(new HashSet<>(Arrays.asList(txnIds.split(",")))).values();
		
		return values.toArray(new CCTransaction[values.size()]);
    }
	
}
