package neil.demo.cfeurope2017;

import java.io.Serializable;

import lombok.Data;

/**
 * <P>Represents a credit card transaction, an actual spend.
 * </P>
 */
@SuppressWarnings("serial")
@Data
public class CCTransaction implements Serializable {

	private String txnId;
	private double amount;
	private String where;
	private long when;
	
}
