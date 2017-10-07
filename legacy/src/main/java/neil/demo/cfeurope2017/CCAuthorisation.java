package neil.demo.cfeurope2017;

import java.io.Serializable;

import lombok.Data;

/**
 * <P>Represents a credit card authorisation, places a temporary hold on
 * some of the available balance.
 * </P>
 */
@SuppressWarnings("serial")
@Data
public class CCAuthorisation implements Serializable {

	private String authId;
	private double amount;
	private String where;
	
}
