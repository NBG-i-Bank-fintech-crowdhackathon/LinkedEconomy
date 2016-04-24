package common;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import java.util.ArrayList;
import java.util.List;

/**
 * @author G. Razis
 * @author G. Vafeiadis
 * @author E. Galanos
 */
public class QueriesSocial {
	
	public List<String[]> getAccountSocialImpact(String account) {
		
		List<String[]> socialImpactData = new ArrayList<String[]>();
		
		try {
			//Register JDBC driver
			Class.forName(QueryConfiguration.JDBC_DRIVER);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		
		try {
			Connection conn = DriverManager.getConnection(QueryConfiguration.DB_URL, 
									QueryConfiguration.usernameDb, QueryConfiguration.passwordDb);

			Statement stmt = null;
			
			String query = "SELECT retrievedOn, hIndexRT, avgTweets, hIndexRT / (100 / avgTweets) AS hIndexDaily " +
					"FROM " + QueryConfiguration.database + ".userdataall " +
					"WHERE (username = '" + account + "');";
			
			try {
				System.out.println("Querying " + account + "...");
				stmt = conn.createStatement();
				ResultSet rs = stmt.executeQuery(query);
				
				while (rs.next()) {
					String retrievedOn = rs.getString("retrievedOn");
					String username = rs.getString("hIndexDaily");
					String[] data = new String[] {retrievedOn, username};
					socialImpactData.add(data);
				}
			} catch (SQLException e) {
				e.printStackTrace();
			} finally {
				if (stmt != null) {
					stmt.close();
				}
				if (conn != null) { //Close connection
					conn.close();
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return socialImpactData;		
	}
	
	public String[] getAccountInfluenceTrackerMetrics(String account) {
		
		String[] metricsData = null;
		
		try {
			//Register JDBC driver
			Class.forName(QueryConfiguration.JDBC_DRIVER);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		
		try {
			Connection conn = DriverManager.getConnection(QueryConfiguration.DB_URL, 
									QueryConfiguration.usernameDb, QueryConfiguration.passwordDb);

			Statement stmt = null;
			
			String query = "SELECT followers, influence " +
					"FROM " + QueryConfiguration.database + ".userdatanewest " +
					"WHERE (username = '" + account + "');";
			
			try {
				System.out.println("Querying " + account + "...");
				stmt = conn.createStatement();
				ResultSet rs = stmt.executeQuery(query);
				
				while (rs.next()) {
					String followers = rs.getString("followers");
					String influence = rs.getString("influence");
					metricsData = new String[] {followers, influence};
				}
			} catch (SQLException e) {
				e.printStackTrace();
			} finally {
				if (stmt != null) {
					stmt.close();
				}
				if (conn != null) { //Close connection
					conn.close();
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return metricsData;		
	}
	
}