package ethnikiGroup;

import java.util.ArrayList;
import java.util.List;

import common.QueriesEthniki;
import common.QueryConfiguration;
import common.Utils;

import virtuoso.jena.driver.VirtGraph;

/**
 * @author G. Razis
 * 
 * Creates the following:
 * 1) Subsidiary list,
 * 2) 3 first Boxes 
 *    [Πληρωμές Δημοσίου, Αναθέσεις Δημοσίου, Επιδοτήσεις ΕΣΠΑ, Πληρωμές E/E],
 * 3) the DataTable containing the decisions,
 * 	  [Πληρωμές Δημοσίου, Αναθέσεις Δημοσίου, Επιδοτήσεις ΕΣΠΑ, Πληρωμές E/E],
 * 4) Yearly Payments of OTE graph.
 */
public class BoxesAndTablesMain {
	//http://83.212.86.156:83/develop/#/profiles/ETHNIKI
	//https://docs.google.com/document/d/1jkxf8oXn5M6Uk7l3-8md2ji9B3gthCCU7_AKpAEatfg
	public static void main(String[] args) {
		
		QueriesEthniki qs = new QueriesEthniki();
		
		String docId = null;
		String jsonString = null;
		List<String> subsidiariesList = null;
		ArrayList<String[]> decisionList = null;
		
		/* DiavgeiaII Graph */
		VirtGraph graphDiavgeiaII = new VirtGraph(QueryConfiguration.queryGraphDiavgeiaII, QueryConfiguration.connectionString, 
												  QueryConfiguration.username, QueryConfiguration.password);
		System.out.println("Connected to Diavgeia II Graph!");
		
		/** Ethniki Group Subsidiaries **/
		docId = "Ethniki_Group_subsidiaries";
		jsonString = Utils.getInstance().startJson(docId);
		
		//ETHNIKI Group Subsidiaries
		System.out.println("Querying Ethniki Group Subsidiaries");
		subsidiariesList = qs.getETHNIKIGroupSubsidiariesQuery(graphDiavgeiaII);
		
		jsonString += "\"subsidiaries\": [";
		
		for (String subsidiary : subsidiariesList) {
			jsonString += "\"" + subsidiary + "\", ";
		}
		
		jsonString = jsonString.substring(0, jsonString.length() - 2) + "]";
		
		jsonString += Utils.getInstance().closeJson();
		
		Utils.getInstance().writeJsonFile(jsonString, "json", docId);
		
		/** Sums and Counters **/
		docId = "Ethniki_Group_dtls";
		jsonString = Utils.getInstance().startJson(docId);
		
		//ETHNIKI Group Payments Previous
		System.out.println("Querying Ethniki Group Previous Payments");
		String[] ethnikiGroupPaymentOldDts = qs.getETHNIKIGroupPaymentDtlsQuery(graphDiavgeiaII, false);
		
		//ETHNIKI Group Assignments Previous
		System.out.println("Querying Ethniki Group Previous Assignments");
		String[] ethnikiGroupAssignmentOldDts = qs.getETHNIKIGroupAssignmentDtlsQuery(graphDiavgeiaII, false);
		
		//ETHNIKI Group Payments Current
		System.out.println("Querying Ethniki Group Current Payments");
		String[] ethnikiGroupPaymentDts = qs.getETHNIKIGroupPaymentDtlsQuery(graphDiavgeiaII, true);
		
		//ETHNIKI Group Assignments Current
		System.out.println("Querying Ethniki Group Current Assignments");
		String[] ethnikiGroupAssignmentDts = qs.getETHNIKIGroupAssignmentDtlsQuery(graphDiavgeiaII, true);
		
		//variation
		String pmntPerc = Utils.getInstance().calculatePercentageVariation(Float.valueOf(ethnikiGroupPaymentOldDts[1].replace(",", "")), 
																		   Float.valueOf(ethnikiGroupPaymentDts[1].replace(",", "")));
		String assignPerc = Utils.getInstance().calculatePercentageVariation(Float.valueOf(ethnikiGroupAssignmentOldDts[1].replace(",", "")), 
																		     Float.valueOf(ethnikiGroupAssignmentDts[1].replace(",", "")));
		
		//Ethniki Group ESPA
		String[] ethnikiGroupEspaDts = qs.getETHNIKIGroupEspaDtlsQuery(graphDiavgeiaII);
		
		//store info at JSON
		jsonString += "\"paymentDtls\": " + "{\"amount\": \"" + ethnikiGroupPaymentDts[1] + "\", \"items\": \"" + ethnikiGroupPaymentDts[0] + 
					  "\", \"variation\": \"" + Utils.getInstance().formatNumber(pmntPerc) + "\", \"year\": \"2015\"}, " ;
		
		jsonString += "\"assignmentDtls\": " + "{\"amount\": \"" + ethnikiGroupAssignmentDts[1] + "\", \"items\": \"" + ethnikiGroupAssignmentDts[0] + 
					  "\", \"variation\": \"" + Utils.getInstance().formatNumber(assignPerc) + "\", \"year\": \"2015\"}, ";
		
		jsonString += "\"espaDtls\": " + "{\"amount\": \"" + ethnikiGroupEspaDts[1] + "\", \"items\": \"" + ethnikiGroupEspaDts[0] + "\", \"year\": \"2007-2013\"}";
		
		jsonString += Utils.getInstance().closeJson();
		
		Utils.getInstance().writeJsonFile(jsonString, "json", docId);
		
		/** Sums and Counters **/
		/* ETHNIKI Group Payment decisions */
		docId = "ETHNIKI_Group_payments";
		jsonString = Utils.getInstance().startJson(docId);
		
		System.out.println("Querying Ethniki Group Top Payments");
		decisionList = qs.getETHNIKIGroupTopPaymentsQuery(graphDiavgeiaII, jsonString);
		
		jsonString += "\"decisions\": [";
		
		for (int i = 0; i < decisionList.size(); i++) {
			jsonString += "{\"date\": \"" + decisionList.get(i)[2] + "\"," + "\"amount\": \"" + decisionList.get(i)[3] + "\"," +
						  "\"seller\": \"" + decisionList.get(i)[1] + "\"," + "\"buyer\": \"" + decisionList.get(i)[0] + "\"," + 
						  "\"cpv\": \"" + decisionList.get(i)[5] + "\"," + "\"ada\": \"" + decisionList.get(i)[4] + "\"," + 
					  	  "\"ada_url\": \"" + "https://diavgeia.gov.gr/decision/view/" + decisionList.get(i)[4] + "\"},";
		}
		
		jsonString = jsonString.substring(0, jsonString.length() - 1) + "]";
		
		jsonString += Utils.getInstance().closeJson();
		
		Utils.getInstance().writeJsonFile(jsonString, "json", docId);
		
		/* ETHNIKI Group Assignment decisions */
		docId = "ETHNIKI_Group_assignments";
		jsonString = Utils.getInstance().startJson(docId);
		
		System.out.println("Querying Ethniki Group Top Assignments");
		decisionList = qs.getETHNIKIGroupTopAssignmentsQuery(graphDiavgeiaII, jsonString);
		
		jsonString += "\"decisions\": [";
		
		for (int i = 0; i < decisionList.size(); i++) {
			jsonString += "{\"date\": \"" + decisionList.get(i)[2] + "\"," + "\"amount\": \"" + decisionList.get(i)[3] + "\"," +
						  "\"seller\": \"" + decisionList.get(i)[1] + "\"," + "\"buyer\": \"" + decisionList.get(i)[0] + "\"," + 
						  "\"cpv\": \"" + decisionList.get(i)[5] + "\"," + "\"ada\": \"" + decisionList.get(i)[4] + "\"," + 
					  	  "\"ada_url\": \"" + "https://diavgeia.gov.gr/decision/view/" + decisionList.get(i)[4] + "\"},";
		}
		
		jsonString = jsonString.substring(0, jsonString.length() - 1) + "]";
		
		jsonString += Utils.getInstance().closeJson();
		
		Utils.getInstance().writeJsonFile(jsonString, "json", docId);
		
		/* ETHNIKI Group ESPA decisions */
		docId = "Ethniki_Group_espa";
		jsonString = Utils.getInstance().startJson(docId);
		
		System.out.println("Querying Ethniki Group Top ESPA");
		decisionList = qs.getETHNIKIGroupTopAEspaQuery(graphDiavgeiaII, jsonString);
		
		jsonString += "\"decisions\": [";
		
		for (int i = 0; i < decisionList.size(); i++) {
			jsonString += "{\"subject\": \"" + decisionList.get(i)[0] + "\"," + "\"municName\": \"" + decisionList.get(i)[1] + "\"," +
						  "\"biAmount\": \"" + decisionList.get(i)[2] + "\"," + "\"cntrAmount\": \"" + decisionList.get(i)[3] + "\"," + 
						  "\"siAmount\": \"" + decisionList.get(i)[4] + "\"},";
		}
		
		jsonString = jsonString.substring(0, jsonString.length() - 1) + "]";
		
		jsonString += Utils.getInstance().closeJson();
		
		Utils.getInstance().writeJsonFile(jsonString, "json", docId);
		
		/* ETHNIKI Group Yearly Payments */
		docId = "Ethniki_Group_yearly_payments";
		jsonString = Utils.getInstance().startJson(docId);
		
		//add months
		jsonString += "\"labels\": [\"January\", \"February\", \"March\", \"April\", \"May\", \"June\", " +
					  "\"July\", \"August\", \"September\", \"October\", \"November\", \"December\"],";
		
		//add plot characteristics
		jsonString += "\"datasets\": [ { \"label\": \"Ethniki\", \"fillColor\": \"rgba(0,128,0,0.5)\", \"strokeColor\": " +
					  "\"rgba(0,128,0,1)\", \"pointColor\": \"rgba(0,128,0,1)\", \"pointStrokeColor\": \"#fff\", " +
					  "\"pointHighlightFill\": \"#fff\", \"pointHighlightStroke\": \"rgba(0,128,0,1)\",";
		
		//add monthly amounts
		jsonString += "\"data\": [";
		
		System.out.println("Querying Ethniki Group Yearly Payments");
		for (int i = 1; i <= 12; i++) {
			int amount = qs.getETHNIKIGroupMonthlyPaymentsQuery(graphDiavgeiaII, i, (i + 1));
			jsonString += amount + ", ";
		}
		
		jsonString = jsonString.substring(0, jsonString.length() - 2) + "]}]";
		
		jsonString += Utils.getInstance().closeJson();
		
		Utils.getInstance().writeJsonFile(jsonString, "json", docId);
		
		/* Close DiavgeiaII Graph */
		graphDiavgeiaII.close();
		
		System.out.println("\nFinished!");
	}
	
}