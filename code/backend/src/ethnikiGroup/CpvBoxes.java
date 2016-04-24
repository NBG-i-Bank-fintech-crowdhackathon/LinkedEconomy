package ethnikiGroup;

import java.io.FileOutputStream;
import java.io.OutputStreamWriter;

import java.util.Locale;

import com.hp.hpl.jena.query.QuerySolution;
import com.hp.hpl.jena.query.ResultSet;

import com.ibm.icu.text.NumberFormat;

import common.QueryConfiguration;

import virtuoso.jena.driver.VirtGraph;
import virtuoso.jena.driver.VirtuosoQueryExecution;
import virtuoso.jena.driver.VirtuosoQueryExecutionFactory;

/**
 * @author G. Razis
 * @author G. Vafeiadis
 * @author E. Galanos
 */
public class CpvBoxes {

	public static void main(String[] args) {

		VirtGraph graph = new VirtGraph(QueryConfiguration.queryGraphDiavgeiaII, QueryConfiguration.connectionString, 
										QueryConfiguration.username, QueryConfiguration.password);

		String totalPaymentsTelecommunicationString = null;
		String totalPaymentsTelecommunicationForCosmoteString = null;
		String totalPaymentsMobileString = null;
		String totalPaymentsMobileForCosmoteString = null;
		String totalPaymentsPublicTelephonyString = null;
		String totalPaymentsPublicTelephonyForCosmoteString = null;

		Double totalPaymentsTelecommunicationDouble = null;
		Double totalPaymentsTelecommunicationForCosmoteDouble = null;
		Double totalPaymentsMobileDouble = null;
		Double totalPaymentsMobileForCosmoteDouble = null;
		Double totalPaymentsPublicTelephonyDouble = null;
		Double totalPaymentsPublicTelephonyForCosmoteDouble = null;

		Double percentagePaymentsTelecommunication = null;
		Double percentagePaymentsMobile = null;
		Double percentagePaymentsPublicTelephony = null;

		String queryPaymentsTelecommunication = "PREFIX gr: <http://purl.org/goodrelations/v1#>"
				+ "PREFIX dcTerms: <http://purl.org/dc/terms/>" + "SELECT (SUM(xsd:integer(?amount)) AS ?totalAmount)"
				+ "FROM <http://linkedeconomy.org/DiavgeiaII/2015>" + "FROM <http://publicspending.net/DiavgeiaI/CPV>"
				+ "FROM <http://linkedeconomy.org/Organizations>" + "WHERE {"
				+ "?expenseApproval elod:hasExpenditureLine ?expenditureLine ;"
				+ "                 elod:decisionTypeId ?decisionTypeId ;" + "                 dcTerms:issued ?date ."
				+ "?expenditureLine elod:amount ?ups ;"
				+ "                 elod:hasCpv <http://linkedeconomy.org/resource/CPV/66110000-4> ;"
				+ "                 elod:seller ?seller ." + "?ups gr:hasCurrencyValue ?amount ."
				+ "FILTER (?date > \"2015-01-01T00:00:00Z\"^^xsd:dateTime) ."
				+ "FILTER (?date < \"2016-01-01T00:00:00Z\"^^xsd:dateTime) ."
				+ "FILTER NOT EXISTS {?expenseApproval elod:hasCorrectedDecision ?correctedDecision} ."
				+ "FILTER (?decisionTypeId= \"Β.2.1\"^^xsd:string || ?decisionTypeId= \"Β.2.2\"^^xsd:string ) ." + "}";

		VirtuosoQueryExecution vqe1 = VirtuosoQueryExecutionFactory.create(queryPaymentsTelecommunication, graph);
		ResultSet results1 = vqe1.execSelect();
		
		while (results1.hasNext()) {
			QuerySolution rs1 = (QuerySolution) results1.next();
			totalPaymentsTelecommunicationString = rs1.getLiteral("totalAmount").getString();
			System.out.println(totalPaymentsTelecommunicationString);
		}
		
		vqe1.close();

		String queryPaymentsTelecommunicationForCosmote = "PREFIX gr: <http://purl.org/goodrelations/v1#>"
				+ "PREFIX dcTerms: <http://purl.org/dc/terms/>" + "SELECT (SUM(xsd:integer(?amount)) AS ?totalAmount)"
				+ "FROM <http://linkedeconomy.org/DiavgeiaII/2015>" + "FROM <http://publicspending.net/DiavgeiaI/CPV>"
				+ "FROM <http://linkedeconomy.org/Organizations>" + "WHERE {"
				+ "?expenseApproval elod:hasExpenditureLine ?expenditureLine ;"
				+ "                 elod:decisionTypeId ?decisionTypeId ;" + "                 dcTerms:issued ?date ."
				+ "?expenditureLine elod:amount ?ups ;"
				+ "                 elod:hasCpv <http://linkedeconomy.org/resource/CPV/66110000-4> ;"
				+ "                 elod:seller ?seller ." + "?ups gr:hasCurrencyValue ?amount ."
				+ "FILTER (?date > \"2015-01-01T00:00:00Z\"^^xsd:dateTime) ."
				+ "FILTER (?date < \"2016-01-01T00:00:00Z\"^^xsd:dateTime) ."
				+ "FILTER NOT EXISTS {?expenseApproval elod:hasCorrectedDecision ?correctedDecision} ."
				+ "FILTER (?decisionTypeId= \"Β.2.1\"^^xsd:string || ?decisionTypeId= \"Β.2.2\"^^xsd:string ) ."
				+ "FILTER (?seller=<http://linkedeconomy.org/resource/Organization/094014201> " 
				+ "|| ?seller=<http://linkedeconomy.org/resource/Organization/997799293> " 
				+ "|| ?seller=<http://linkedeconomy.org/resource/Organization/094328700> " 
				+ "|| ?seller=<http://linkedeconomy.org/resource/Organization/997983926>) " 
				+ "}";

		VirtuosoQueryExecution vqe2 = VirtuosoQueryExecutionFactory.create(queryPaymentsTelecommunicationForCosmote, graph);
		ResultSet results2 = vqe2.execSelect();
		
		while (results2.hasNext()) {
			QuerySolution rs2 = (QuerySolution) results2.next();
			totalPaymentsTelecommunicationForCosmoteString = rs2.getLiteral("totalAmount").getString();
			System.out.println(totalPaymentsTelecommunicationForCosmoteString);
		}
		
		vqe2.close();

		String queryPaymentsMobile = "PREFIX gr: <http://purl.org/goodrelations/v1#>"
				+ "PREFIX dcTerms: <http://purl.org/dc/terms/>" + "SELECT (SUM(xsd:integer(?amount)) AS ?totalAmount)"
				+ "FROM <http://linkedeconomy.org/DiavgeiaII/2015>" + "FROM <http://publicspending.net/DiavgeiaI/CPV>"
				+ "FROM <http://linkedeconomy.org/Organizations>" + "WHERE {"
				+ "?expenseApproval elod:hasExpenditureLine ?expenditureLine ;"
				+ "                 elod:decisionTypeId ?decisionTypeId ;" + "                 dcTerms:issued ?date ."
				+ "?expenditureLine elod:amount ?ups ;"
				+ "                 elod:hasCpv <http://linkedeconomy.org/resource/CPV/SA03-6> ;"
				+ "                 elod:seller ?seller ." + "?ups gr:hasCurrencyValue ?amount ."
				+ "FILTER (?date > \"2015-01-01T00:00:00Z\"^^xsd:dateTime) ."
				+ "FILTER (?date < \"2016-01-01T00:00:00Z\"^^xsd:dateTime) ."
				+ "FILTER NOT EXISTS {?expenseApproval elod:hasCorrectedDecision ?correctedDecision} ."
				+ "FILTER (?decisionTypeId= \"Β.2.1\"^^xsd:string || ?decisionTypeId= \"Β.2.2\"^^xsd:string ) ." + "}";

		VirtuosoQueryExecution vqe3 = VirtuosoQueryExecutionFactory.create(queryPaymentsMobile, graph);
		ResultSet results3 = vqe3.execSelect();
		
		while (results3.hasNext()) {
			QuerySolution rs3 = (QuerySolution) results3.next();
			totalPaymentsMobileString = rs3.getLiteral("totalAmount").getString();
			System.out.println(totalPaymentsMobileString);
		}
		
		vqe3.close();

		String queryPaymentsMobileForCosmote = "PREFIX gr: <http://purl.org/goodrelations/v1#>"
				+ "PREFIX dcTerms: <http://purl.org/dc/terms/>" + "SELECT (SUM(xsd:integer(?amount)) AS ?totalAmount)"
				+ "FROM <http://linkedeconomy.org/DiavgeiaII/2015>" + "FROM <http://publicspending.net/DiavgeiaI/CPV>"
				+ "FROM <http://linkedeconomy.org/Organizations>" + "WHERE {"
				+ "?expenseApproval elod:hasExpenditureLine ?expenditureLine ;"
				+ "                 elod:decisionTypeId ?decisionTypeId ;" + "                 dcTerms:issued ?date ."
				+ "?expenditureLine elod:amount ?ups ;"
				+ "                 elod:hasCpv <http://linkedeconomy.org/resource/CPV/SA03-6> ;"
				+ "                 elod:seller ?seller ." + "?ups gr:hasCurrencyValue ?amount ."
				+ "FILTER (?date > \"2015-01-01T00:00:00Z\"^^xsd:dateTime) ."
				+ "FILTER (?date < \"2016-01-01T00:00:00Z\"^^xsd:dateTime) ."
				+ "FILTER NOT EXISTS {?expenseApproval elod:hasCorrectedDecision ?correctedDecision} ."
				+ "FILTER (?decisionTypeId= \"Β.2.1\"^^xsd:string || ?decisionTypeId= \"Β.2.2\"^^xsd:string ) ."
				+ "FILTER (?seller=<http://linkedeconomy.org/resource/Organization/094014201> " 
				+ "|| ?seller=<http://linkedeconomy.org/resource/Organization/997799293> " 
				+ "|| ?seller=<http://linkedeconomy.org/resource/Organization/094328700> " 
				+ "|| ?seller=<http://linkedeconomy.org/resource/Organization/997983926>) " 
				+ "}";

		VirtuosoQueryExecution vqe4 = VirtuosoQueryExecutionFactory.create(queryPaymentsMobileForCosmote, graph);
		ResultSet results4 = vqe4.execSelect();
		
		while (results4.hasNext()) {
			QuerySolution rs4 = (QuerySolution) results4.next();
			totalPaymentsMobileForCosmoteString = rs4.getLiteral("totalAmount").getString();
			System.out.println(totalPaymentsMobileForCosmoteString);
		}
		
		vqe4.close();

		String queryPaymentsPublicTelephony = "PREFIX gr: <http://purl.org/goodrelations/v1#>"
				+ "PREFIX dcTerms: <http://purl.org/dc/terms/>" + "SELECT (SUM(xsd:integer(?amount)) AS ?totalAmount)"
				+ "FROM <http://linkedeconomy.org/DiavgeiaII/2015>" + "FROM <http://publicspending.net/DiavgeiaI/CPV>"
				+ "FROM <http://linkedeconomy.org/Organizations>" + "WHERE {"
				+ "?expenseApproval elod:hasExpenditureLine ?expenditureLine ;"
				+ "                 elod:decisionTypeId ?decisionTypeId ;" + "                 dcTerms:issued ?date ."
				+ "?expenditureLine elod:amount ?ups ;"
				+ "                 elod:hasCpv <http://linkedeconomy.org/resource/CPV/79220000-2> ;"
				+ "                 elod:seller ?seller ." + "?ups gr:hasCurrencyValue ?amount ."
				+ "FILTER (?date > \"2015-01-01T00:00:00Z\"^^xsd:dateTime) ."
				+ "FILTER (?date < \"2016-01-01T00:00:00Z\"^^xsd:dateTime) ."
				+ "FILTER NOT EXISTS {?expenseApproval elod:hasCorrectedDecision ?correctedDecision} ."
				+ "FILTER (?decisionTypeId= \"Β.2.1\"^^xsd:string || ?decisionTypeId= \"Β.2.2\"^^xsd:string ) ." + "}";

		VirtuosoQueryExecution vqe5 = VirtuosoQueryExecutionFactory.create(queryPaymentsPublicTelephony, graph);
		ResultSet results5 = vqe5.execSelect();
		
		while (results5.hasNext()) {
			QuerySolution rs5 = (QuerySolution) results5.next();
			totalPaymentsPublicTelephonyString = rs5.getLiteral("totalAmount").getString();
			System.out.println(totalPaymentsPublicTelephonyString);
		}
		
		vqe5.close();

		String queryPaymentsPublicTelephonyForCosmote = "PREFIX gr: <http://purl.org/goodrelations/v1#>"
				+ "PREFIX dcTerms: <http://purl.org/dc/terms/>" + "SELECT (SUM(xsd:integer(?amount)) AS ?totalAmount)"
				+ "FROM <http://linkedeconomy.org/DiavgeiaII/2015>" + "FROM <http://publicspending.net/DiavgeiaI/CPV>"
				+ "FROM <http://linkedeconomy.org/Organizations>" + "WHERE {"
				+ "?expenseApproval elod:hasExpenditureLine ?expenditureLine ;"
				+ "                 elod:decisionTypeId ?decisionTypeId ;" + "                 dcTerms:issued ?date ."
				+ "?expenditureLine elod:amount ?ups ;"
				+ "                 elod:hasCpv <http://linkedeconomy.org/resource/CPV/79220000-2> ;"
				+ "                 elod:seller ?seller ." + "?ups gr:hasCurrencyValue ?amount ."
				+ "FILTER (?date > \"2015-01-01T00:00:00Z\"^^xsd:dateTime) ."
				+ "FILTER (?date < \"2016-01-01T00:00:00Z\"^^xsd:dateTime) ."
				+ "FILTER NOT EXISTS {?expenseApproval elod:hasCorrectedDecision ?correctedDecision} ."
				+ "FILTER (?decisionTypeId= \"Β.2.1\"^^xsd:string || ?decisionTypeId= \"Β.2.2\"^^xsd:string ) ."
				+ "FILTER (?seller=<http://linkedeconomy.org/resource/Organization/094014201> " 
				+ "|| ?seller=<http://linkedeconomy.org/resource/Organization/997799293> " 
				+ "|| ?seller=<http://linkedeconomy.org/resource/Organization/094328700> " 
				+ "|| ?seller=<http://linkedeconomy.org/resource/Organization/997983926>) " 
				+ "}";

		VirtuosoQueryExecution vqe6 = VirtuosoQueryExecutionFactory.create(queryPaymentsPublicTelephonyForCosmote, graph);
		ResultSet results6 = vqe6.execSelect();
		
		while (results6.hasNext()) {
			QuerySolution rs6 = (QuerySolution) results6.next();
			totalPaymentsPublicTelephonyForCosmoteString = rs6.getLiteral("totalAmount").getString();
			System.out.println(totalPaymentsPublicTelephonyForCosmoteString);
		}
		
		vqe6.close();

		totalPaymentsTelecommunicationDouble = Double.valueOf(totalPaymentsTelecommunicationString);
		totalPaymentsTelecommunicationForCosmoteDouble = Double.valueOf(totalPaymentsTelecommunicationForCosmoteString);
		totalPaymentsMobileDouble = Double.valueOf(totalPaymentsMobileString);
		totalPaymentsMobileForCosmoteDouble = Double.valueOf(totalPaymentsMobileForCosmoteString);
		totalPaymentsPublicTelephonyDouble = Double.valueOf(totalPaymentsPublicTelephonyString);
		totalPaymentsPublicTelephonyForCosmoteDouble = Double.valueOf(totalPaymentsPublicTelephonyForCosmoteString);

		percentagePaymentsTelecommunication = (totalPaymentsTelecommunicationForCosmoteDouble
				/ totalPaymentsTelecommunicationDouble) * 10000;
		percentagePaymentsTelecommunication = (double) Math.round(percentagePaymentsTelecommunication);
		percentagePaymentsTelecommunication = percentagePaymentsTelecommunication / 100;
		System.out.println(percentagePaymentsTelecommunication);

		percentagePaymentsMobile = (totalPaymentsMobileForCosmoteDouble / totalPaymentsMobileDouble) * 10000;
		percentagePaymentsMobile = (double) Math.round(percentagePaymentsMobile);
		percentagePaymentsMobile = percentagePaymentsMobile / 100;
		System.out.println(percentagePaymentsMobile);

		percentagePaymentsPublicTelephony = (totalPaymentsPublicTelephonyForCosmoteDouble
				/ totalPaymentsPublicTelephonyDouble) * 10000;
		percentagePaymentsPublicTelephony = (double) Math.round(percentagePaymentsPublicTelephony);
		percentagePaymentsPublicTelephony = percentagePaymentsPublicTelephony / 100;
		System.out.println(percentagePaymentsPublicTelephony);

		System.out.println(NumberFormat.getNumberInstance(Locale.US).format(totalPaymentsTelecommunicationDouble));
		System.out.println(NumberFormat.getNumberInstance(Locale.US).format(totalPaymentsMobileDouble));
		System.out.println(NumberFormat.getNumberInstance(Locale.US).format(totalPaymentsPublicTelephonyDouble));

		totalPaymentsTelecommunicationString = NumberFormat.getNumberInstance(Locale.US)
				.format(totalPaymentsTelecommunicationDouble);
		totalPaymentsMobileString = NumberFormat.getNumberInstance(Locale.US)
				.format(totalPaymentsMobileDouble);
		totalPaymentsPublicTelephonyString = NumberFormat.getNumberInstance(Locale.US)
				.format(totalPaymentsPublicTelephonyDouble);

		String json = "[{\"title\": \"Τραπεζικές υπηρεσίες\",\"percentage\": "
				+ percentagePaymentsTelecommunication + ", \"amount\": \"" + totalPaymentsTelecommunicationString
				+ "\" },{\"title\": \"Για δάνεια\",\"percentage\": " + percentagePaymentsMobile
				+ ", \"amount\": \"" + totalPaymentsMobileString
				+ "\"},{\"title\": \"Φορολογικές υπηρεσίες\",\"percentage\": " + percentagePaymentsPublicTelephony
				+ ", \"amount\": \"" + totalPaymentsPublicTelephonyString + "\" }]";
		System.out.println(json);

		String filePath = "C:/Users/Leuteris/Desktop/json/metrics_cpv.json";
		FileOutputStream fos;
		try {
			fos = new FileOutputStream(filePath, false);
			OutputStreamWriter osw = new OutputStreamWriter(fos, "UTF-8");
			osw.write(json);
			osw.close();
			fos.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		graph.close();

	}

}
