package ethnikiGroup;

import java.util.ArrayList;
import java.util.List;

import org.joda.time.LocalDate;

import common.QueriesBanks;
import common.QueryConfiguration;
import common.Utils;

import virtuoso.jena.driver.VirtGraph;

/**
 * @author G. Razis
 * @author G. Vafeiadis
 * @author E. Galanos
 *
 * Creates the daily payments graphs of the Banks Groups:
 * [Ethniki, Peiraios, Alpha, Eurobank]
 */
public class BanksDailyMain {

	public static void main(String[] args) {
		
		QueriesBanks qsBanks = new QueriesBanks();
		
		String docId = null;
		String jsonString = null;
		List<Integer> ethnikiPaymentsList = new ArrayList<Integer>();
		List<Integer> peiraiosPaymentsList = new ArrayList<Integer>();
		List<Integer> alphaPaymentsList = new ArrayList<Integer>();
		List<Integer> eurobankPaymentsList = new ArrayList<Integer>();
		
		
		/* DiavgeiaII Graph */
		VirtGraph graphDiavgeiaII = new VirtGraph(QueryConfiguration.queryGraphDiavgeiaII, QueryConfiguration.connectionString, 
												  QueryConfiguration.username, QueryConfiguration.password);
		System.out.println("Connected to Diavgeia II Graph!");
		
		LocalDate startDate = new LocalDate("2015-01-01");
		LocalDate endDate = new LocalDate("2016-01-01");
		
		List<String> datesList = Utils.getInstance().getListOfDates(startDate, endDate);
		
		/** Banks Daily Payments **/
		docId = "Banks_Group_daily_payments";
		jsonString = "[" + Utils.getInstance().startJson(docId);
		
		for (String date : datesList) { //fetch the decisions of the specific date
			System.out.println("Querying date: " + date);
			ethnikiPaymentsList.add(qsBanks.getEthnikiGroupDailyPaymentsQuery(graphDiavgeiaII, date));
			peiraiosPaymentsList.add(qsBanks.getPeiraiosGroupDailyPaymentsQuery(graphDiavgeiaII, date));
			alphaPaymentsList.add(qsBanks.getAlphaGroupDailyPaymentsQuery(graphDiavgeiaII, date));
			eurobankPaymentsList.add(qsBanks.getEurobankGroupDailyPaymentsQuery(graphDiavgeiaII, date));
		}
		
		/*Ethniki Group Daily Payments */
		jsonString += "\"name\": \"Ethniki\", \"data_graph\": [";
		
		for (Integer amount : ethnikiPaymentsList) {
			jsonString += amount + ",";
		}
		
		jsonString = jsonString.substring(0, jsonString.length() - 1) + "]},";
		
		/* Peiraios Group Daily Payments */
		jsonString += "{\"name\": \"Peiraios\", \"data_graph\": [";
		
		for (Integer amount : peiraiosPaymentsList) {
			jsonString += amount + ",";
		}
		
		jsonString = jsonString.substring(0, jsonString.length() - 1) + "]},";
		
		/* Alpha Group Daily Payments */
		jsonString += "{\"name\": \"Alpha\", \"data_graph\": [";
		
		for (Integer amount : alphaPaymentsList) {
			jsonString += amount + ",";
		}
		
		jsonString = jsonString.substring(0, jsonString.length() - 1) + "]},";
		
		/* Eurobank Group Daily Payments */
		jsonString += "{\"name\": \"Eurobank\", \"data_graph\": [";
		
		for (Integer amount : eurobankPaymentsList) {
			jsonString += amount + ",";
		}
		
		jsonString = jsonString.substring(0, jsonString.length() - 1) + "]";
		
		//close JSON
		jsonString += Utils.getInstance().closeJson() + "]";
		
		Utils.getInstance().writeJsonFile(jsonString, "json", docId);
		
		/* Close DiavgeiaII Graph */
		graphDiavgeiaII.close();
		
		System.out.println("\nFinished!");
	}
	
}