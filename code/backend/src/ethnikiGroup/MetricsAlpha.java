package ethnikiGroup;

import java.io.FileOutputStream;
import java.io.OutputStreamWriter;

import com.hp.hpl.jena.query.QuerySolution;
import com.hp.hpl.jena.query.ResultSet;

import common.QueryConfiguration;

import virtuoso.jena.driver.VirtGraph;
import virtuoso.jena.driver.VirtuosoQueryExecution;
import virtuoso.jena.driver.VirtuosoQueryExecutionFactory;

/**
 * @author G. Razis
 * @author G. Vafeiadis
 * @author E. Galanos
 */
public class MetricsAlpha {
	
	public static void main(String[] args) {
		
		VirtGraph graph = new VirtGraph(QueryConfiguration.queryGraphDiavgeiaII, QueryConfiguration.connectionString, 
				  						QueryConfiguration.username, QueryConfiguration.password);

		String buyers = null;
		String cpvs = null;
		String postalCodes = null;

		String queryBuyers = "PREFIX gr: <http://purl.org/goodrelations/v1#> "
				+ " PREFIX dcTerms: <http://purl.org/dc/terms/> " + " SELECT (COUNT (DISTINCT (?org)) AS ?Count) "
				+ " FROM <http://linkedeconomy.org/DiavgeiaII/2015> "
				+ " FROM <http://publicspending.net/DiavgeiaI/CPV> " + " FROM <http://linkedeconomy.org/Organizations> "
				+ " WHERE { " + " ?expenseApproval elod:hasExpenditureLine ?expenditureLine ; "
				+ "                  dc:publisher ?unit; " + "                  elod:decisionTypeId ?decisionTypeId ; "
				+ "                  dcTerms:issued ?date . " + " ?expenditureLine elod:seller ?seller . "
				+ " ?org org:hasUnit ?unit . " + " FILTER (?date > \"2015-01-01T00:00:00Z\"^^xsd:dateTime) . "
				+ " FILTER (?date < \"2016-01-01T00:00:00Z\"^^xsd:dateTime) . "
				+ " FILTER NOT EXISTS {?expenseApproval elod:hasCorrectedDecision ?correctedDecision} . "
				+ " FILTER (?decisionTypeId= \"Β.2.1\"^^xsd:string || ?decisionTypeId= \"Β.2.2\"^^xsd:string ) . "
				+ "FILTER (?seller=<http://linkedeconomy.org/resource/Organization/094014249>) " 
				+ " } ";

		VirtuosoQueryExecution vqe1 = VirtuosoQueryExecutionFactory.create(queryBuyers, graph);
		ResultSet results1 = vqe1.execSelect();
		
		while (results1.hasNext()) {
			QuerySolution rs1 = (QuerySolution) results1.next();
			buyers = rs1.getLiteral("Count").getString();
			System.out.println(buyers);
		}
		
		vqe1.close();

		String queryCpvs = "PREFIX gr: <http://purl.org/goodrelations/v1#>"
				+ " PREFIX dcTerms: <http://purl.org/dc/terms/>" + " SELECT (COUNT (DISTINCT (?superCpv))AS ?Count)"
				+ " FROM <http://linkedeconomy.org/DiavgeiaII/2015>" + " FROM <http://publicspending.net/DiavgeiaI/CPV>"
				+ " FROM <http://linkedeconomy.org/Organizations>" + " WHERE {"
				+ " ?expenseApproval elod:hasExpenditureLine ?expenditureLine ;"
				+ "                  elod:buyer ?buyer;" + "                  elod:decisionTypeId ?decisionTypeId ;"
				+ "                  dcTerms:issued ?date ." + " ?expenditureLine elod:amount ?ups ;"
				+ "                  elod:hasCpv ?singleCpv ;" + "                  elod:seller ?seller ."
				+ " ?singleCpv elod:hasSuperCPVCode ?superCpv . " + " ?ups gr:hasCurrencyValue ?amount ."
				+ " FILTER (?date > \"2015-01-01T00:00:00Z\"^^xsd:dateTime) ."
				+ " FILTER (?date < \"2016-01-01T00:00:00Z\"^^xsd:dateTime) ."
				+ " FILTER NOT EXISTS {?expenseApproval elod:hasCorrectedDecision ?correctedDecision} ."
				+ " FILTER (?decisionTypeId= \"Β.2.1\"^^xsd:string || ?decisionTypeId= \"Β.2.2\"^^xsd:string ) ."
				+ "FILTER (?seller=<http://linkedeconomy.org/resource/Organization/094014249>) " 
				+ " } ";

		VirtuosoQueryExecution vqe2 = VirtuosoQueryExecutionFactory.create(queryCpvs, graph);
		ResultSet results2 = vqe2.execSelect();
		
		while (results2.hasNext()) {
			QuerySolution rs2 = (QuerySolution) results2.next();
			cpvs = rs2.getLiteral("Count").getString();
			System.out.println(cpvs);
		}
		
		vqe2.close();
		
		String queryPostalCodes = "PREFIX gr: <http://purl.org/goodrelations/v1#> "
				+ " PREFIX dcTerms: <http://purl.org/dc/terms/> "
				+ " SELECT (COUNT (DISTINCT (?buyerPostalCode)) AS ?Count) "
				+ " FROM <http://linkedeconomy.org/DiavgeiaII/2015> "
				+ " FROM <http://publicspending.net/DiavgeiaI/CPV> "
				+ " FROM <http://linkedeconomy.org/Organizations> "
				+ " WHERE { "
				+ " ?expenseApproval elod:hasExpenditureLine ?expenditureLine ; "
				+ "                  elod:buyer ?buyer; "
				+ "                  elod:decisionTypeId ?decisionTypeId ; "
				+ "                  dcTerms:issued ?date . "
				+ " ?expenditureLine elod:amount ?ups ;         "
				+ "                  elod:seller ?seller . "
				+ " ?ups gr:hasCurrencyValue ?amount . "
				+ " ?buyer vcard2006:hasAddress ?buyerAddress . "
				+ " ?buyerAddress vcard2006:postal-code ?buyerPostalCode "
				+ " FILTER (?date > \"2015-01-01T00:00:00Z\"^^xsd:dateTime) . "
				+ " FILTER (?date < \"2016-01-01T00:00:00Z\"^^xsd:dateTime) . "
				+ " FILTER NOT EXISTS {?expenseApproval elod:hasCorrectedDecision ?correctedDecision} . "
				+ " FILTER (?decisionTypeId= \"Β.2.1\"^^xsd:string || ?decisionTypeId= \"Β.2.2\"^^xsd:string ) . "
				+ "FILTER (?seller=<http://linkedeconomy.org/resource/Organization/094014249>) " 
				+ " } ";

		VirtuosoQueryExecution vqe3 = VirtuosoQueryExecutionFactory.create(queryPostalCodes, graph);
		ResultSet results3 = vqe3.execSelect();
		
		while (results3.hasNext()) {
			QuerySolution rs3 = (QuerySolution) results3.next();
			postalCodes = rs3.getLiteral("Count").getString();
			System.out.println(postalCodes);
		}
		
		vqe3.close();
		
		String json = "[{\"title\": \"Πληρωτές\",\"count\": "
				+ buyers + "},{\"title\": \"Υπηρεσίες\",\"count\": " + cpvs 
				+ "},{\"title\": \"Περιοχές(ΤΚ)\",\"count\": " + postalCodes
				+ " }]";
		System.out.println(json);
		
		String filePath = "C:/Users/Leuteris/Desktop/json/metrics_Alpha.json";
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
