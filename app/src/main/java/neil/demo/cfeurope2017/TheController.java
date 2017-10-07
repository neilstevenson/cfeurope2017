package neil.demo.cfeurope2017;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.ModelAndView;

import lombok.extern.slf4j.Slf4j;

/**
 * <P>A MVC Controller to display results returned from the microservices
 * </P>
 */
@Controller
@Slf4j
public class TheController {

	@Autowired
    private RestTemplate restTemplate;

	/**
	 * <P>Render the front page
	 * </P>
	 * 
	 * @return View for {@code index.html} page
	 */
    @GetMapping(value = "/")
    public ModelAndView index() {
    		log.info("index()");
    		return new ModelAndView("index");
    }

	/**
	 * <P>Call the users microservice
	 * </P>
	 * 
	 * @return View for {@code users.html} page
	 */
    @GetMapping(value = "/users")
    public ModelAndView users() {
    		log.info("users()");

    		// Call microservice 1 to get users
    		String url = "http://localhost:8081/ms1/users";
    		ResponseEntity<Object[][]> response 
    			= this.restTemplate.getForEntity(url, Object[][].class);
    		Object[][] users = response.getBody();

    		// Reformat results for HTML
    		List<List<Object>> data = new ArrayList<>();
    		for (Object[] user : users) {
    			List<Object> datum = Arrays.asList(user);
    			data.add(datum);
        }

    		// Select view and add data objects
    		ModelAndView modelAndView = new ModelAndView("users");
    		modelAndView.addObject("data", data);
    		return modelAndView;
    }

	/**
	 * <P>Call the user and balance microservices
	 * </P>
	 * 
	 * @return View for {@code user.html} page
	 */
    @GetMapping(value = "/user")
    public ModelAndView user(@RequestParam(name="userId", required=true) String userId) {
		log.info("user({})", userId);

		// Call microservice 2 to get user details
		String url = "http://localhost:8082/ms2/user/" + userId;
		ResponseEntity<CCUser> response 
			= this.restTemplate.getForEntity(url, CCUser.class);
		CCUser ccUser = response.getBody();

		// Call microservice 3 to get user effective balance
		String url2 = "http://localhost:8083/ms3/balance/" + userId;
		ResponseEntity<Double> response2 
			= this.restTemplate.getForEntity(url2, Double.class);
		Double balance = response2.getBody();

	    // Select view and add data objects
	    ModelAndView modelAndView = new ModelAndView("user");
        modelAndView.addObject("ccUser", ccUser);
        modelAndView.addObject("balance", balance);
	    	return modelAndView;
	}

	/**
	 * <P>Call the transaction and auth microservices
	 * </P>
	 * 
	 * @return View for {@code detail.html} page
	 */
    @GetMapping(value = "/detail")
    public ModelAndView detail(@RequestParam(name="userId", required=true) String userId) {
		log.info("detail({})", userId);

		// Call microservice 2 to get user details
		String url = "http://localhost:8082/ms2/user/" + userId;
		ResponseEntity<CCUser> response 
			= this.restTemplate.getForEntity(url, CCUser.class);
		CCUser ccUser = response.getBody();

		// Call microservice 4 to get transactions
        String url2 = "http://localhost:8084/ms4/txns/" + String.join(",", ccUser.getTxnIds());
        CCTransaction[] ccTransactions;
        try {
        		ResponseEntity<CCTransaction[]> response2 
        		= this.restTemplate.getForEntity(url2, CCTransaction[].class);

        		ccTransactions = response2.getBody();
		} catch (Exception emptyList) {
			ccTransactions = new CCTransaction[0];
		}

		// Call microservice 5 to get authorisations
        String url3 = "http://localhost:8085/ms5/auths/" + String.join(",", ccUser.getAuthIds());
        CCAuthorisation[] ccAuthorisations;
        try {
        		ResponseEntity<CCAuthorisation[]> response3 
        		= this.restTemplate.getForEntity(url3, CCAuthorisation[].class);

        		ccAuthorisations = response3.getBody();
		} catch (Exception emptyList) {
			ccAuthorisations = new CCAuthorisation[0];
		}

	    // Select view and add data objects
	    ModelAndView modelAndView = new ModelAndView("detail");
	    modelAndView.addObject("userId", userId);
	    modelAndView.addObject("ccTransactions", ccTransactions);
	    modelAndView.addObject("ccAuthorisations", ccAuthorisations);
	    	return modelAndView;
	}

}
