package neil.demo.cfeurope2017;

import java.io.Serializable;
import java.util.List;

import lombok.Data;

/**
 * <P>Represents a credit card user.
 * </P>
 */
@SuppressWarnings("serial")
@Data
public class CCUser implements Serializable {

	private String userId;
	private String firstName;
	private String lastName;
	private List<String> authIds;
	private List<String> txnIds;
	private int creditLimit;
	
}
