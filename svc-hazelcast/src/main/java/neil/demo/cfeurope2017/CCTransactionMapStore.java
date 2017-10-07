package neil.demo.cfeurope2017;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import com.hazelcast.core.MapLoader;

import lombok.extern.slf4j.Slf4j;

/**
 * <P>If defined on a map, a {@code MapLoader} will assist {@code Map.get()} calls.
 * Any {@code Map.get()} that doesn't find data in Hazelcast will pass to this
 * code to try to find the data in any external store. If found, this data loaded
 * is then stored in Hazelcast.
 * </P>
 */
@Slf4j
public class CCTransactionMapStore implements MapLoader<String, CCTransaction> {

	/**
	 * <P>Turn a cache-miss into a cache-hit, by fetching data not found
	 * in Hazelcast from an external store such as an RDBMS.
	 * </P>
	 * <P>For simplicity, we simulate the presence of the RDBMS by
	 * generating random data.
	 * </P>
	 * 
	 * @param txnId The id of the transaction, which is assumed to exist
	 * @return The transaction detail
	 */
	@Override
	public CCTransaction load(String txnId) {
		int i = new Random().nextInt(TestData.PLACES.length);
		
		log.info("Loading TXNID '{}'", txnId);
		
		CCTransaction ccTransaction = new CCTransaction();
		
		ccTransaction.setTxnId(txnId);
		ccTransaction.setAmount(i);
		ccTransaction.setWhen(System.currentTimeMillis());
		ccTransaction.setWhere(TestData.PLACES[i]);

		// Deliberate delay, to simulate a slow RDBMS
		try {
			TimeUnit.MILLISECONDS.sleep(1000L);
		} catch (InterruptedException ignored) {
			;
		}
		return ccTransaction;
	}

	/**
	 * <P>Used by {@link com.hazelcast.core.IMap#getAll}</P>
	 */
	@Override
	public Map<String, CCTransaction> loadAll(Collection<String> keys) {
		Map<String, CCTransaction> results = new HashMap<>();

		for (String key : keys) {
			CCTransaction value = this.load(key);
			if (value!=null) {
				results.put(key, value);
			}
		}
		
		return results;
	}

	/**
	 * <P>Empty as not pre-loading</P>
	 */
	@Override
	public Iterable<String> loadAllKeys() {
		return Collections.emptyList();
	}

}
