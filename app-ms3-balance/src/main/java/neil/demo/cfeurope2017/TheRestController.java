package neil.demo.cfeurope2017;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import lombok.extern.slf4j.Slf4j;

/**
 * <P>The business logic of this microservice, get the users remaining balance.
 * </P>
 * <P>Specifically, this is derived from the credit limit, the sum of all transactions
 * that have occured, and the sum of authorisations (transactions that might occur).
 * </P>
 * <P>This information is obtained by requesting it from other microservices. We should
 * but don't handle these services being offline.
 * </P>
 */
@RestController
@Slf4j
public class TheRestController {

	@Autowired
    private RestTemplate restTemplate;

	@GetMapping("/ms3/balance/{userId}")
    public Double balance(@PathVariable String userId) {
		log.info("balance({})", userId);
		
		CCUser ccUser = this.getUser(userId);
		CCTransaction[] ccTransactions = this.getCCTransactions(ccUser.getTxnIds());
		CCAuthorisation[] ccAuthorisations = this.getCCAuthorisations(ccUser.getAuthIds());
		
		double balance = new Double(ccUser.getCreditLimit());

		for (CCTransaction ccTransaction : ccTransactions) {
			balance -= ccTransaction.getAmount();
		}
		for (CCAuthorisation ccAuthorisation : ccAuthorisations) {
			balance -= ccAuthorisation.getAmount();
		}

		// Do not allow to go further overdrawn
		return balance < 0 ? 0d : balance;
    }

	/**
	 * <P>REST call to obtain user's authorisation requests
	 * </P>
	 * 
	 * @param authIds Comma separated list
	 * @return
	 */
	private CCAuthorisation[] getCCAuthorisations(List<String> authIds) {
        String url = "http://localhost:8085/ms5/auths/" + String.join(",", authIds);

        try {
            ResponseEntity<CCAuthorisation[]> response 
    			= this.restTemplate.getForEntity(url, CCAuthorisation[].class);

            return response.getBody();
        } catch (Exception emptyList) {
        		return new CCAuthorisation[0];
        }
	}

	/**
	 * <P>REST call to obtain user's transactions
	 * </P>
	 * 
	 * @param txnIds Comma separated list
	 * @return
	 */
	private CCTransaction[] getCCTransactions(List<String> txnIds) {
        String url = "http://localhost:8084/ms4/txns/" + String.join(",", txnIds);

        try {
        		ResponseEntity<CCTransaction[]> response 
        		= this.restTemplate.getForEntity(url, CCTransaction[].class);

        		return response.getBody();
		} catch (Exception emptyList) {
    			return new CCTransaction[0];
		}
	}

	/**
	 * <P>REST call to obtain user.
	 * </P>
	 * 
	 * @param userId
	 * @return
	 */
	private CCUser getUser(String userId) {
        String url = "http://localhost:8082/ms2/user/" + userId;

        ResponseEntity<CCUser> response 
        		= this.restTemplate.getForEntity(url, CCUser.class);

		return response.getBody();
	}
	
}
