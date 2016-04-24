package common;

/**
 * @author G. Razis
 * @author G. Vafeiadis
 * @author E. Galanos
 */
public class QueryConfiguration {
	
	public static final String queryGraphDiavgeiaII = "http://linkedeconomy.org/DiavgeiaII/2015";
	public static final String queryGraphEspa = "http://linkedeconomy.org/Subsidies";
	public static final String queryGraphCpv = "http://publicspending.net/DiavgeiaI/CPV";
	public static final String queryGraphStats = "http://linkedeconomy.org/DiavgeiaIIStatistics";
	public static final String queryGraphOrganizations = "http://linkedeconomy.org/Organizations";
	public static final String queryGraphGeo = "http://linkedeconomy.org/GeoData";
	public static final String queryGraphFek = "http://linkedeconomy.org/FekProject";
	public static final String queryGraphAustralia = "http://linkedeconomy.org/Australia";
	public static final String queryGraphEufts = "http://linkedeconomy.org/EuFts";
	
	//JDBC driver name, database URL and database name
	public static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
	public static final String DB_URL = "jdbc:mysql://***:3306/";
	public static final String database = "***";
	
	public static final String connectionString = "jdbc:virtuoso://***:1111/autoReconnect=true/charset=UTF-8/log_enable=2";
	
	//SPARQL credentials
	public static final String username = "***";
	public static final String password = "***";
	
	//MySQL credentials
	public static final String usernameDb = "***";
	public static final String passwordDb = "***";
	
	public static final String currentYear = "2015";
	public static final String currentReferenceDuration = "12"; //OLV 6: "P1M"
	
	public static final String baseYear = "2015";
	
	//start edit vaf
	public static final String jsonFilepath = "***";
	//end edit vaf
	public static final boolean couchDbUsage = false;
}