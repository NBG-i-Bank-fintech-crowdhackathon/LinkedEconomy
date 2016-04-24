package oracle;

import java.util.ArrayList;

import common.QueriesOracle;
import common.QueryConfiguration;
import common.Utils;

import virtuoso.jena.driver.VirtGraph;

/**
 * @author G. Razis
 * @author G. Vafeiadis
 * @author E. Galanos
 */
public class BoxesAndTablesOracle {
	
	public static void main(String[] args) {
		
		QueriesOracle qs = new QueriesOracle();
		
		String docId = null;
		String jsonString = null;
		ArrayList<String[]> paymentEuftsList = null;
		ArrayList<String[]> paymentAustraliaList = null;
		ArrayList<String[]> paymentDiavgeiaList = null;
		
		/* Eufts Graph */
		VirtGraph graphEufts = new VirtGraph(QueryConfiguration.queryGraphEufts, QueryConfiguration.connectionString, 
												  QueryConfiguration.username, QueryConfiguration.password);
		System.out.println("Connected to Eufts Graph!");
		

		/* Oracle Eufts Payments */
		docId = "Oracle_Eufts_payments";
		jsonString = Utils.getInstance().startJson(docId);
		
		System.out.println("Querying Eufts Payments");
		paymentEuftsList = qs.getOraclePaymentsEuftsQuery(graphEufts, jsonString);
		
		jsonString += "\"payments\": [";
		
		for (int i = 0; i < paymentEuftsList.size(); i++) {
			jsonString += "{\"date\": \"" + paymentEuftsList.get(i)[3] + "\"," + "\"amount\": \"" + paymentEuftsList.get(i)[4] + "\"," +
						  "\"seller\": \"" + paymentEuftsList.get(i)[1] + "\"," + "\"buyer\": \"" + paymentEuftsList.get(i)[0] + "\"," + 
						  "\"description\": \"" + paymentEuftsList.get(i)[2] + "\"},";
		}
		
		jsonString = jsonString.substring(0, jsonString.length() - 1) + "]";
		
		jsonString += Utils.getInstance().closeJson();
		
		Utils.getInstance().writeJsonFile(jsonString, "Others", docId);
		
		System.out.println("\nFinished!");
		
		/** Sums and Counters **/
		docId = "Oracle_Payment_Eufts_dtls";
		jsonString = Utils.getInstance().startJson(docId);
				
				
		//Oracle Payments Current
		System.out.println("Querying Oracle Payments");
		String[] oraclePaymentEuftsDts = qs.getOraclePaymentEuftsDtlsQuery(graphEufts);
				
		//store info at JSON
		jsonString += "\"paymentDtls\": " + "{\"totalAmount\": \"" + oraclePaymentEuftsDts[1] + "\", \"items\": \"" + oraclePaymentEuftsDts[0] + 
							  "\", \"year\": \"2007-2014\"}";
		
        jsonString += Utils.getInstance().closeJson();
		
		Utils.getInstance().writeJsonFile(jsonString, "Others", docId);
		
		System.out.println("\nFinished!");
		
		/* Close Eufts Graph */
		graphEufts.close();
		
		/* Australia Graph */
		VirtGraph graphAustralia = new VirtGraph(QueryConfiguration.queryGraphAustralia, QueryConfiguration.connectionString, 
												  QueryConfiguration.username, QueryConfiguration.password);
		System.out.println("Connected to Australia Graph!");
		
		/* Oracle Australia Payments */
		docId = "Oracle_Australia_payments";
		jsonString = Utils.getInstance().startJson(docId);
		
		System.out.println("Querying Oracle Australia Payments");
		paymentAustraliaList = qs.getOraclePaymentsAustraliaQuery(graphAustralia, jsonString);
		
		jsonString += "\"payments\": [";
		
		for (int i = 0; i < paymentAustraliaList.size(); i++) {
			jsonString += "{\"date\": \"" + paymentAustraliaList.get(i)[3] + "\"," + "\"amount\": \"" + paymentAustraliaList.get(i)[4] + "\"," +
						  "\"seller\": \"" + paymentAustraliaList.get(i)[1] + "\"," + "\"buyer\": \"" + paymentAustraliaList.get(i)[0] + "\"," + 
						  "\"description\": \"" + paymentAustraliaList.get(i)[2] + "\"},";
		}
		
		jsonString = jsonString.substring(0, jsonString.length() - 1) + "]";
		
		jsonString += Utils.getInstance().closeJson();
		
		Utils.getInstance().writeJsonFile(jsonString, "Others", docId);
		
		System.out.println("\nFinished!");
		
		/** Sums and Counters **/
		docId = "Oracle_Payment_Australia_dtls";
		jsonString = Utils.getInstance().startJson(docId);
				
				
		//Oracle Payments Current
		System.out.println("Querying Oracle Payments");
		String[] oraclePaymentAusDts = qs.getOraclePaymentAusDtlsQuery(graphAustralia);
				
		//store info at JSON
		jsonString += "\"paymentDtls\": " + "{\"totalAmount\": \"" + oraclePaymentAusDts[1] + "\", \"items\": \"" + oraclePaymentAusDts[0] + 
							  "\", \"year\": \"2005-2016\"}";
		
        jsonString += Utils.getInstance().closeJson();
		
		Utils.getInstance().writeJsonFile(jsonString, "Others", docId);
		
		System.out.println("\nFinished!");
		
		/* Close Australia Graph */
		graphAustralia.close();
		
		/* Diavgeia Graph */
		VirtGraph graphDiavgeiaII = new VirtGraph(QueryConfiguration.queryGraphDiavgeiaII, QueryConfiguration.connectionString, 
												  QueryConfiguration.username, QueryConfiguration.password);
		System.out.println("Connected to DiavgeiaII Graph!");
		
		/* Oracle Diavgeia Payments */
		docId = "Oracle_Diavgeia_payments";
		jsonString = Utils.getInstance().startJson(docId);
		
		System.out.println("Querying Oracle Diavgeia Payments");
		paymentDiavgeiaList = qs.getOraclePaymentDiavgeiaQuery(graphDiavgeiaII, jsonString);
		
		jsonString += "\"decisions\": [";
		
		for (int i = 0; i < paymentDiavgeiaList.size(); i++) {
			jsonString += "{\"date\": \"" + paymentDiavgeiaList.get(i)[2] + "\"," + "\"amount\": \"" + paymentDiavgeiaList.get(i)[3] + "\"," +
						  "\"seller\": \"" + paymentDiavgeiaList.get(i)[1] + "\"," + "\"buyer\": \"" + paymentDiavgeiaList.get(i)[0] + "\"," + 
						  "\"cpv\": \"" + paymentDiavgeiaList.get(i)[5] + "\"," + "\"ada\": \"" + paymentDiavgeiaList.get(i)[4] + "\"," + 
					  	  "\"ada_url\": \"" + "https://diavgeia.gov.gr/decision/view/" + paymentDiavgeiaList.get(i)[4] + "\"},";
		}
		
		jsonString = jsonString.substring(0, jsonString.length() - 1) + "]";
		
		jsonString += Utils.getInstance().closeJson();
		
		Utils.getInstance().writeJsonFile(jsonString, "Others", docId);
		
		System.out.println("\nFinished!");
		
		/** Sums and Counters **/
		docId = "Oracle_Payment_Diavgeia_dtls";
		jsonString = Utils.getInstance().startJson(docId);
		
		//Oracle Payments Previous
		System.out.println("Querying Oracle Previous Payments");
		String[] oraclePaymentOldDts = qs.getOraclePaymentDtlsQuery(graphDiavgeiaII, false);
				
				
		//Oracle Payments Current
		System.out.println("Querying Oracle Current Payments");
		String[] oraclePaymentDts = qs.getOraclePaymentDtlsQuery(graphDiavgeiaII, true);
		
		//amount 2014-2015
		Float amount = Float.valueOf(oraclePaymentOldDts[1].replace(",", "")) + Float.valueOf(oraclePaymentDts[1].replace(",", ""));
		
		//count 2014-2015
		Float count = Float.valueOf(oraclePaymentOldDts[0].replace(",", "")) + Float.valueOf(oraclePaymentDts[0].replace(",", ""));
		
		String totalAmount = Utils.getInstance().formatNumber(String.valueOf(amount));
		String totalCount = Utils.getInstance().formatNumber(String.valueOf(count));
		
		//store info at JSON
		jsonString += "\"paymentDtls\": " + "{\"totalAmount\": \"" + totalAmount + "\", \"items\": \"" + totalCount +
				"\", \"year\": \"2014-2015\"}";
		
        jsonString += Utils.getInstance().closeJson();
		
		Utils.getInstance().writeJsonFile(jsonString, "Others", docId);
		
		System.out.println("\nFinished!");
		
		/** Sums and Counters **/
		docId = "Oracle_Payment_All_dtls";
		jsonString = Utils.getInstance().startJson(docId);
		
		//total info of all datasets
		Float totalAmountDatasets = Float.valueOf(oraclePaymentOldDts[1].replace(",", "")) + Float.valueOf(oraclePaymentDts[1].replace(",", ""))
		+ Float.valueOf(oraclePaymentAusDts[1].replace(",", "")) + Float.valueOf(oraclePaymentEuftsDts[1].replace(",", ""));
		
		Float totalCountDatasets = Float.valueOf(oraclePaymentOldDts[0].replace(",", "")) + Float.valueOf(oraclePaymentDts[0].replace(",", ""))
		+ Float.valueOf(oraclePaymentAusDts[0].replace(",", "")) + Float.valueOf(oraclePaymentEuftsDts[0].replace(",", ""));
		
		String overallAmount = Utils.getInstance().formatNumber(String.valueOf(totalAmountDatasets));
		String overallCount = Utils.getInstance().formatNumber(String.valueOf(totalCountDatasets));
		
		// store info at JSON
		jsonString += "\"paymentDtls\": " + "{\"amountOverall\": \"" + overallAmount + "\", \"itemsOverall\": \""
				+ overallCount + "\"}";

		jsonString += Utils.getInstance().closeJson();

		Utils.getInstance().writeJsonFile(jsonString, "Others", docId);
		
		/* Close Diavgeia Graph */
		graphDiavgeiaII.close();
		
	}
	
}