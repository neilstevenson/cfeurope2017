package neil.demo.cfeurope2017;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.core.IMap;

import lombok.extern.slf4j.Slf4j;

/**
 * <P>Inject some test data into Hazelcast as part of start-up.
 * </P>
 */
@Component
@Slf4j
public class TestDataLoader implements CommandLineRunner {
	
	@Autowired
	private HazelcastInstance hazelcastInstance;

	@Override
	public void run(String... arg0) throws Exception {

		IMap<String, CCAuthorisation> authorisationsMap
			= this.hazelcastInstance.getMap("cc-authorisations");
		IMap<String, CCTransaction> transactionsMap
			= this.hazelcastInstance.getMap("cc-transactions");
		IMap<String, CCUser> usersMap
			= this.hazelcastInstance.getMap("cc-user");

		// This is true if we are not the first Hazelcast server in the cluster
		if (!usersMap.isEmpty()) {
			log.info("Skip loading, '{}' not empty", usersMap.getName());
			return;
		}
		
		Arrays.stream(TestData.USERS).forEach((Object[] datum) -> {
			CCUser ccUser = new CCUser();

			ccUser.setUserId(datum[0].toString());
			ccUser.setFirstName(datum[1].toString());
			ccUser.setLastName(datum[2].toString());
			ccUser.setAuthIds(Arrays.asList(datum[3].toString().split(",")));
			ccUser.setTxnIds(Arrays.asList(datum[4].toString().split(",")));
			ccUser.setCreditLimit(Integer.parseInt(datum[5].toString()));
			
			usersMap.put(ccUser.getUserId(), ccUser);
		});

		Arrays.stream(TestData.AUTHS).forEach((Object[] datum) -> {
			CCAuthorisation ccAuthorisation = new CCAuthorisation();

			ccAuthorisation.setAuthId(datum[0].toString());
			ccAuthorisation.setAmount(Double.parseDouble(datum[1].toString()));
			ccAuthorisation.setWhere(datum[2].toString());
			
			authorisationsMap.put(ccAuthorisation.getAuthId(), ccAuthorisation);
		});

		Arrays.stream(TestData.TXNS).forEach((Object[] datum) -> {
			CCTransaction ccTransaction = new CCTransaction();

			ccTransaction.setTxnId(datum[0].toString());
			ccTransaction.setAmount(Double.parseDouble(datum[1].toString()));
			ccTransaction.setWhere(datum[2].toString());
			ccTransaction.setWhen(Long.parseLong(datum[3].toString()));
			
			// Don't want old value, would invoke CCTransactionMapStore.load()
			transactionsMap.set(ccTransaction.getTxnId(), ccTransaction);
		});

		log.info("Loaded {} users, {} authorisations and {} transactions",
				usersMap.size(), authorisationsMap.size(), transactionsMap.size()
				);
	}

}
