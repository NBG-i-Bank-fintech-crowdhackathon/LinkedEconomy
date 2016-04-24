package oteGroup;

import java.util.ArrayList;
import java.util.List;

import org.joda.time.LocalDate;

import common.QueriesTelCo;
import common.QueryConfiguration;
import common.Utils;

import virtuoso.jena.driver.VirtGraph;

/**
 * @author G. Razis
 * @author G. Vafeiadis
 * @author E. Galanos
 * 
 * Creates the daily payments graphs of the TelCo Groups:
 * [OTE, Vodafone, Forthnet, Cyta, Wind]
 */
public class TelCoDailyMain {

	public static void main(String[] args) {
		
		/* DiavgeiaII Graph */
		VirtGraph graphDiavgeiaII = new VirtGraph(QueryConfiguration.queryGraphDiavgeiaII, QueryConfiguration.connectionString, 
												  QueryConfiguration.username, QueryConfiguration.password);
		System.out.println("Connected to Diavgeia II Graph!");
		
		LocalDate startDate = new LocalDate("2015-01-01");
		LocalDate endDate = new LocalDate("2016-01-01");
		
		List<String> datesList = Utils.getInstance().getListOfDates(startDate, endDate);
		
		/* TelCo Daily Payments */
		createTelCoGroupDailyPayments(graphDiavgeiaII, datesList);
		
		/* close DiavgeiaII Graph */
		graphDiavgeiaII.close();
		
		System.out.println("\nFinished!");
	}
	
	private static void createTelCoGroupDailyPayments(VirtGraph graphDiavgeiaII, List<String> datesList) {
		
		QueriesTelCo qsTelCo = new QueriesTelCo();
		
		List<Integer> otePaymentsList = new ArrayList<Integer>();
		List<Integer> vodafonePaymentsList = new ArrayList<Integer>();
		List<Integer> forthnetPaymentsList = new ArrayList<Integer>();
		List<Integer> cytaPaymentsList = new ArrayList<Integer>();
		List<Integer> windPaymentsList = new ArrayList<Integer>();
		
		String docId = "TelCo_Group_daily_payments";
		String jsonString = "[" + Utils.getInstance().startJson(docId);
		
		for (String date : datesList) { //fetch the decisions of the specific date
			System.out.println("Querying date: " + date);
			otePaymentsList.add(qsTelCo.getOteGroupDailyPaymentsQuery(graphDiavgeiaII, date));
			vodafonePaymentsList.add(qsTelCo.getVodafoneGroupDailyPaymentsQuery(graphDiavgeiaII, date));
			forthnetPaymentsList.add(qsTelCo.getForthnetGroupDailyPaymentsQuery(graphDiavgeiaII, date));
			cytaPaymentsList.add(qsTelCo.getCytaGroupDailyPaymentsQuery(graphDiavgeiaII, date));
			windPaymentsList.add(qsTelCo.getWindGroupDailyPaymentsQuery(graphDiavgeiaII, date));
		}
		
		/* Ote Group Daily Payments */
		jsonString += "\"name\": \"OTE\", \"data_graph\": [";
		
		for (Integer amount : otePaymentsList) {
			jsonString += amount + ",";
		}
		
		jsonString = jsonString.substring(0, jsonString.length() - 1) + "]},";
		
		/* Vodafone Group Daily Payments */
		jsonString += "{\"name\": \"Vodafone\", \"data_graph\": [";
		
		for (Integer amount : vodafonePaymentsList) {
			jsonString += amount + ",";
		}
		
		jsonString = jsonString.substring(0, jsonString.length() - 1) + "]},";
		
		/* Forthnet Group Daily Payments */
		jsonString += "{\"name\": \"Forthnet\", \"data_graph\": [";
		
		for (Integer amount : forthnetPaymentsList) {
			jsonString += amount + ",";
		}
		
		jsonString = jsonString.substring(0, jsonString.length() - 1) + "]},";
		
		/* Cyta Group Daily Payments */
		jsonString += "{\"name\": \"CYTA\", \"data_graph\": [";
		
		for (Integer amount : cytaPaymentsList) {
			jsonString += amount + ",";
		}
		
		jsonString = jsonString.substring(0, jsonString.length() - 1) + "]},";

		/* Wind Group Daily Payments */
		jsonString += "{\"name\": \"Wind\", \"data_graph\": [";
		
		for (Integer amount : windPaymentsList) {
			jsonString += amount + ",";
		}
		
		jsonString = jsonString.substring(0, jsonString.length() - 1) + "]";
		
		//close JSON
		jsonString += Utils.getInstance().closeJson() + "]";
		
		Utils.getInstance().writeJsonFile(jsonString, "Others", docId);
		
	}
	
}