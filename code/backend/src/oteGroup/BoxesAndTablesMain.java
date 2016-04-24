package oteGroup;

import java.util.ArrayList;
import java.util.List;

import common.QueriesOte;
import common.QueryConfiguration;
import common.Utils;

import virtuoso.jena.driver.VirtGraph;

/**
 * @author G. Razis
 * @author G. Vafeiadis
 * @author E. Galanos
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
	
	public static void main(String[] args) {
		
		/* DiavgeiaII Graph */
		VirtGraph graphDiavgeiaII = new VirtGraph(QueryConfiguration.queryGraphDiavgeiaII, QueryConfiguration.connectionString, 
												  QueryConfiguration.username, QueryConfiguration.password);
		System.out.println("Connected to Diavgeia II Graph!");
		
		/* DiavgeiaII Graph */
		VirtGraph graphEuFts = new VirtGraph(QueryConfiguration.queryGraphEufts, QueryConfiguration.connectionString, 
												  QueryConfiguration.username, QueryConfiguration.password);
		System.out.println("Connected to EU-FTS Graph!");
		
		/** OTE Group Subsidiaries **/
		createOteGroupSubsidiaries(graphDiavgeiaII);
		
		/** Sums and Counters **/
		createOteGroupDetails(graphDiavgeiaII, graphEuFts);
		
		/** Sums and Counters **/
		/* OTE Group Payment decisions */
		createOteGroupPaymentDecisions(graphDiavgeiaII);
		
		/* OTE Group Assignment decisions */
		createOteGroupAssignmentDecisions(graphDiavgeiaII);
		
		/* OTE Group ESPA decisions */
		createOteGroupEspaDecisions(graphDiavgeiaII);
		
		/* OTE Group EU-FTS decisions */
		createOteGroupEuFtsDecisions(graphEuFts);
		
		/* OTE Group Yearly Payments */
		createOteGroupYearlyPayments(graphDiavgeiaII);
		
		/* close Graphs */
		graphDiavgeiaII.close();
		graphEuFts.close();
		
		System.out.println("\nFinished!");
	}
	
	private static void createOteGroupSubsidiaries(VirtGraph graphDiavgeiaII) {
		
		QueriesOte qs = new QueriesOte();
		
		String docId = "OTE_Group_subsidiaries";
		String jsonString = Utils.getInstance().startJson(docId);
		
		//OTE Group Subsidiaries
		System.out.println("Querying Ote Group Subsidiaries");
		List<String> subsidiariesList = qs.getOteGroupSubsidiariesQuery(graphDiavgeiaII);
		
		jsonString += "\"subsidiaries\": [";
		
		for (String subsidiary : subsidiariesList) {
			jsonString += "\"" + subsidiary + "\", ";
		}
		
		jsonString = jsonString.substring(0, jsonString.length() - 2) + "]";
		
		jsonString += Utils.getInstance().closeJson();
		
		Utils.getInstance().writeJsonFile(jsonString, "Others", docId);
		
	}
	
	private static void createOteGroupDetails(VirtGraph graphDiavgeiaII, VirtGraph graphEuFts) {

		QueriesOte qs = new QueriesOte();
		
		String docId = "OTE_Group_dtls";
		String jsonString = Utils.getInstance().startJson(docId);
		
		//OTE Group Payments Previous
		System.out.println("Querying Ote Group Previous Payments");
		String[] oteGroupPaymentOldDts = qs.getOteGroupPaymentDtlsQuery(graphDiavgeiaII, false);
		
		//OTE Group Assignments Previous
		System.out.println("Querying Ote Group Previous Assignments");
		String[] oteGroupAssignmentOldDts = qs.getOteGroupAssignmentDtlsQuery(graphDiavgeiaII, false);
		
		//OTE Group Payments Current
		System.out.println("Querying Ote Group Current Payments");
		String[] oteGroupPaymentDts = qs.getOteGroupPaymentDtlsQuery(graphDiavgeiaII, true);
		
		//OTE Group Assignments Current
		System.out.println("Querying Ote Group Current Assignments");
		String[] oteGroupAssignmentDts = qs.getOteGroupAssignmentDtlsQuery(graphDiavgeiaII, true);
		
		//variation
		String pmntPerc = Utils.getInstance().calculatePercentageVariation(Float.valueOf(oteGroupPaymentOldDts[1].replace(",", "")), 
																		   Float.valueOf(oteGroupPaymentDts[1].replace(",", "")));
		String assignPerc = Utils.getInstance().calculatePercentageVariation(Float.valueOf(oteGroupAssignmentOldDts[1].replace(",", "")), 
																		     Float.valueOf(oteGroupAssignmentDts[1].replace(",", "")));
		
		//OTE Group ESPA
		String[] oteGroupEspaDts = qs.getOteGroupEspaDtlsQuery(graphDiavgeiaII);
		
		//OTE Group EU-FTS
		String[] oteGroupEuFtsDts = qs.getOteGroupEuFtsDtlsQuery(graphEuFts);
		
		//store info at JSON
		jsonString += "\"paymentDtls\": " + "{\"amount\": \"" + oteGroupPaymentDts[1] + "\", \"items\": \"" + oteGroupPaymentDts[0] + 
					  "\", \"variation\": \"" + Utils.getInstance().formatNumber(pmntPerc) + "\", \"year\": \"2015\"}, " ;
		
		jsonString += "\"assignmentDtls\": " + "{\"amount\": \"" + oteGroupAssignmentDts[1] + "\", \"items\": \"" + oteGroupAssignmentDts[0] + 
					  "\", \"variation\": \"" + Utils.getInstance().formatNumber(assignPerc) + "\", \"year\": \"2015\"}, ";
		
		jsonString += "\"espaDtls\": " + "{\"amount\": \"" + oteGroupEspaDts[1] + "\", \"items\": \"" + oteGroupEspaDts[0] + "\", \"year\": \"2007-2013\"},";
		
		jsonString += "\"euftsDtls\": " + "{\"amount\": \"" + oteGroupEuFtsDts[1] + "\", \"items\": \"" + oteGroupEuFtsDts[0] + "\", \"year\": \"2011-2014\"}";
		
		jsonString += Utils.getInstance().closeJson();
		
		Utils.getInstance().writeJsonFile(jsonString, "Others", docId);
		
	}
	
	private static void createOteGroupPaymentDecisions(VirtGraph graphDiavgeiaII) {

		QueriesOte qs = new QueriesOte();
		
		String docId = "OTE_Group_payments";
		String jsonString = Utils.getInstance().startJson(docId);
		
		System.out.println("Querying Ote Group Top Payments");
		ArrayList<String[]> decisionList = qs.getOteGroupTopPaymentsQuery(graphDiavgeiaII, jsonString);
		
		jsonString += "\"decisions\": [";
		
		for (int i = 0; i < decisionList.size(); i++) {
			jsonString += "{\"date\": \"" + decisionList.get(i)[2] + "\"," + "\"amount\": \"" + decisionList.get(i)[3] + "\"," +
						  "\"seller\": \"" + decisionList.get(i)[1] + "\"," + "\"buyer\": \"" + decisionList.get(i)[0] + "\"," + 
						  "\"cpv\": \"" + decisionList.get(i)[5] + "\"," + "\"ada\": \"" + decisionList.get(i)[4] + "\"," + 
					  	  "\"ada_url\": \"" + "https://diavgeia.gov.gr/decision/view/" + decisionList.get(i)[4] + "\"},";
		}
		
		jsonString = jsonString.substring(0, jsonString.length() - 1) + "]";
		
		jsonString += Utils.getInstance().closeJson();
		
		Utils.getInstance().writeJsonFile(jsonString, "Others", docId);
		
	}
	
	private static void createOteGroupAssignmentDecisions(VirtGraph graphDiavgeiaII) {
		
		QueriesOte qs = new QueriesOte();
		
		String docId = "OTE_Group_assignments";
		String jsonString = Utils.getInstance().startJson(docId);
		
		System.out.println("Querying Ote Group Top Assignments");
		ArrayList<String[]> decisionList = qs.getOteGroupTopAssignmentsQuery(graphDiavgeiaII, jsonString);
		
		jsonString += "\"decisions\": [";
		
		for (int i = 0; i < decisionList.size(); i++) {
			jsonString += "{\"date\": \"" + decisionList.get(i)[2] + "\"," + "\"amount\": \"" + decisionList.get(i)[3] + "\"," +
						  "\"seller\": \"" + decisionList.get(i)[1] + "\"," + "\"buyer\": \"" + decisionList.get(i)[0] + "\"," + 
						  "\"cpv\": \"" + decisionList.get(i)[5] + "\"," + "\"ada\": \"" + decisionList.get(i)[4] + "\"," + 
					  	  "\"ada_url\": \"" + "https://diavgeia.gov.gr/decision/view/" + decisionList.get(i)[4] + "\"},";
		}
		
		jsonString = jsonString.substring(0, jsonString.length() - 1) + "]";
		
		jsonString += Utils.getInstance().closeJson();
		
		Utils.getInstance().writeJsonFile(jsonString, "Others", docId);
		
	}
	
	private static void createOteGroupEspaDecisions(VirtGraph graphDiavgeiaII) {
		
		QueriesOte qs = new QueriesOte();
		
		String docId = "OTE_Group_espa";
		String jsonString = Utils.getInstance().startJson(docId);
		
		System.out.println("Querying Ote Group Top ESPA");
		ArrayList<String[]> decisionList = qs.getOteGroupTopEspaQuery(graphDiavgeiaII, jsonString);
		
		jsonString += "\"decisions\": [";
		
		for (int i = 0; i < decisionList.size(); i++) {
			jsonString += "{\"subject\": \"" + decisionList.get(i)[0] + "\"," + "\"municName\": \"" + decisionList.get(i)[1] + "\"," +
						  "\"biAmount\": \"" + decisionList.get(i)[2] + "\"," + "\"cntrAmount\": \"" + decisionList.get(i)[3] + "\"," + 
						  "\"siAmount\": \"" + decisionList.get(i)[4] + "\"},";
		}
		
		jsonString = jsonString.substring(0, jsonString.length() - 1) + "]";
		
		jsonString += Utils.getInstance().closeJson();
		
		Utils.getInstance().writeJsonFile(jsonString, "Others", docId);
		
	}
	
	private static void createOteGroupEuFtsDecisions(VirtGraph graphEuFts) {
		
		QueriesOte qs = new QueriesOte();
		
		String docId = "OTE_Group_EuFts";
		String jsonString = Utils.getInstance().startJson(docId);
		
		System.out.println("Querying Ote Group Top EU-FTS");
		ArrayList<String[]> decisionList = qs.getOteGroupTopEuFtsQuery(graphEuFts, jsonString);
		
		jsonString += "\"decisions\": [";
		
		for (int i = 0; i < decisionList.size(); i++) {
			jsonString += "{\"date\": \"" + decisionList.get(i)[0] + "\"," + "\"amount\": \"" + decisionList.get(i)[5] + "\"," +
					  	  "\"seller\": \"" + decisionList.get(i)[2] + "\"," + "\"buyer\": \"" + decisionList.get(i)[1] + "\"," + 
					  	  "\"description\": \"" + decisionList.get(i)[3] + "\"," + "\"cntrId\": \"" + decisionList.get(i)[4] + "\"},";
		}
		
		jsonString = jsonString.substring(0, jsonString.length() - 1) + "]";
		
		jsonString += Utils.getInstance().closeJson();
		
		Utils.getInstance().writeJsonFile(jsonString, "Others", docId);
		
	}
	
	private static void createOteGroupYearlyPayments(VirtGraph graphDiavgeiaII) {
		
		QueriesOte qs = new QueriesOte();
		
		String docId = "OTE_Group_yearly_payments";
		String jsonString = Utils.getInstance().startJson(docId);
		
		//add months
		jsonString += "\"labels\": [\"January\", \"February\", \"March\", \"April\", \"May\", \"June\", " +
					  "\"July\", \"August\", \"September\", \"October\", \"November\", \"December\"],";
		
		//add plot characteristics
		jsonString += "\"datasets\": [ { \"label\": \"OTE\", \"fillColor\": \"rgba(0,128,0,0.5)\", \"strokeColor\": " +
					  "\"rgba(0,128,0,1)\", \"pointColor\": \"rgba(0,128,0,1)\", \"pointStrokeColor\": \"#fff\", " +
					  "\"pointHighlightFill\": \"#fff\", \"pointHighlightStroke\": \"rgba(0,128,0,1)\",";
		
		//add monthly amounts
		jsonString += "\"data\": [";
		
		System.out.println("Querying Ote Group Yearly Payments");
		for (int i = 1; i <= 12; i++) {
			int amount = qs.getOteGroupMonthlyPaymentsQuery(graphDiavgeiaII, i, (i + 1));
			jsonString += amount + ", ";
		}
		
		jsonString = jsonString.substring(0, jsonString.length() - 2) + "]}]";
		
		jsonString += Utils.getInstance().closeJson();
		
		Utils.getInstance().writeJsonFile(jsonString, "Others", docId);
		
	}
	
}