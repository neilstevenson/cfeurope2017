package neil.demo.cfeurope2017;

/**
 * <P>Sample data.
 * </P>
 */
public class TestData {

	/**
	 * <P>Used by {@link CCTransactionMapStore#load()} to generate
	 * random transactions.
	 * </P>
	 */
	public static final String[] PLACES = new String[] {
			"Auditorium",
			"BSL Airport",
			"Swisshotel La Plaza",
			"Starbucks",
			"Uber",
	};
	
	
	/**
	 * <P>All users and some of their activities</P>
	 */
	public static final Object[][] USERS = new Object[][] {
		{ 1, "Neil", 	"Stevenson", 	"-1", 	"2,3", 				100 },
		{ 2, "Curly", 	"Howard", 		"", 		"4", 				100 },
		{ 3, "Larry", 	"Fine", 			"", 		"5", 				100 },
		{ 4, "Moe", 		"Howard", 		"-2", 	"6", 				500 },
		{ 5, "Shemp", 	"Howard", 		"", 		"7,8,9", 			100 },
		{ 6, "Joe", 		"Besser", 		"", 		"10,11,12,13", 		100 },
		{ 7, "Joe", 		"DeRita", 		"", 		"14,15,16,17,18", 	100 },
	};
	public static final Object[][] AUTHS = new Object[][] {
		{ -1, 50d, "Swisshotel Le Plaza" },
		{ -2, 150d, "Swisshotel Le Plaza" },
	};
	public static final Object[][] TXNS = new Object[][] {
		{ 2, 15, "Uber", 1507340253693L },
		{ 3, 50, "Pizza Hut", 1507400646936L },
	};

}
