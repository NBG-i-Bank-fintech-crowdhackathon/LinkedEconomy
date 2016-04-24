package common;

import virtuoso.jena.driver.VirtGraph;
import virtuoso.jena.driver.VirtuosoQueryExecution;
import virtuoso.jena.driver.VirtuosoQueryExecutionFactory;

import com.hp.hpl.jena.query.QuerySolution;
import com.hp.hpl.jena.query.ResultSet;

/**
 * @author G. Razis
 * @author G. Vafeiadis
 * @author E. Galanos
 */
public class QueriesTelCo {
	
	public int getOteGroupDailyPaymentsQuery(VirtGraph graphDiavgeiaII, String date) {
		
		int totalAmount = 0;
		
		String queryAmount = "PREFIX dcterms: <http://purl.org/dc/terms/> " +
					"PREFIX gr: <http://purl.org/goodrelations/v1#> " +
					"SELECT ?displayDate (SUM((?totalAmount)) AS ?totalAmount) " +
					"{ " +
						"SELECT (CONCAT(STR(DAY(?date)), '/', STR(MONTH(?date)), '/', STR(YEAR(?date))) AS ?displayDate) " +
							   "((xsd:decimal(?amount)) AS ?totalAmount) " +
						"FROM <" + QueryConfiguration.queryGraphDiavgeiaII + "> " +
						"WHERE { " +
						"?expenseApproval elod:hasExpenditureLine ?expenditureLine ; " +
						                 "elod:decisionTypeId ?decisionTypeId ; " +
						                 "dcterms:issued ?date . " +
						"?expenditureLine elod:amount ?ups ; " +
						                 "elod:seller ?seller . " +
						"?ups gr:hasCurrencyValue ?amount . " +
						"FILTER STRSTARTS(STR(?date), \"" + date + "\") . " +
						"FILTER NOT EXISTS {?expenseApproval elod:hasCorrectedDecision ?correctedDecision} . " +
						"FILTER NOT EXISTS {?ups elod:riskError ?error} . " +
						"FILTER (?decisionTypeId = 'Β.2.1'^^xsd:string || ?decisionTypeId = 'Β.2.2'^^xsd:string) . " +
						"FILTER (?seller=<http://linkedeconomy.org/resource/Organization/094019245> " +
							 "|| ?seller=<http://linkedeconomy.org/resource/Organization/094493766> " +
							 "|| ?seller=<http://linkedeconomy.org/resource/Organization/099760493> " +
							 "|| ?seller=<http://linkedeconomy.org/resource/Organization/094493000> " +
							 "|| ?seller=<http://linkedeconomy.org/resource/Organization/094222303> " +
							 "|| ?seller=<http://linkedeconomy.org/resource/Organization/094436540> " +
							 "|| ?seller=<http://linkedeconomy.org/resource/Organization/099760500> " +
							 "|| ?seller=<http://linkedeconomy.org/resource/Organization/094282975>) " +
						"} " +
					"} " +
					"GROUP BY ?displayDate " +
					"ORDER BY ?displayDate";

		VirtuosoQueryExecution vqeAmount = VirtuosoQueryExecutionFactory.create(queryAmount, graphDiavgeiaII);
		ResultSet resultsAmount = vqeAmount.execSelect();
		
		if (resultsAmount.hasNext()) {
			QuerySolution result = resultsAmount.nextSolution();
			totalAmount = result.getLiteral("totalAmount").getInt();
		}
		
		vqeAmount.close();
		
		return totalAmount;
	}
	
	public int getVodafoneGroupDailyPaymentsQuery(VirtGraph graphDiavgeiaII, String date) {
		
		int totalAmount = 0;
		
		String queryAmount = "PREFIX dcterms: <http://purl.org/dc/terms/> " +
					"PREFIX gr: <http://purl.org/goodrelations/v1#> " +
					"SELECT ?displayDate (SUM((?totalAmount)) AS ?totalAmount) " +
					"{ " +
						"SELECT (CONCAT(STR(DAY(?date)), '/', STR(MONTH(?date)), '/', STR(YEAR(?date))) AS ?displayDate) " +
							   "((xsd:decimal(?amount)) AS ?totalAmount) " +
						"FROM <" + QueryConfiguration.queryGraphDiavgeiaII + "> " +
						"WHERE { " +
						"?expenseApproval elod:hasExpenditureLine ?expenditureLine ; " +
						                 "elod:decisionTypeId ?decisionTypeId ; " +
						                 "dcterms:issued ?date . " +
						"?expenditureLine elod:amount ?ups ; " +
						                 "elod:seller ?seller . " +
						"?ups gr:hasCurrencyValue ?amount . " +
						"FILTER STRSTARTS(STR(?date), \"" + date + "\") . " +
						"FILTER NOT EXISTS {?expenseApproval elod:hasCorrectedDecision ?correctedDecision} . " +
						"FILTER NOT EXISTS {?ups elod:riskError ?error} . " +
						"FILTER (?decisionTypeId = 'Β.2.1'^^xsd:string || ?decisionTypeId = 'Β.2.2'^^xsd:string) . " +
						"FILTER (?seller=<http://linkedeconomy.org/resource/Organization/094349850> " +
						     "|| ?seller=<http://linkedeconomy.org/resource/Organization/094222339>) . " +
						"} " +
					"} " +
					"GROUP BY ?displayDate " +
					"ORDER BY ?displayDate";

		VirtuosoQueryExecution vqeAmount = VirtuosoQueryExecutionFactory.create(queryAmount, graphDiavgeiaII);
		ResultSet resultsAmount = vqeAmount.execSelect();
		
		if (resultsAmount.hasNext()) {
			QuerySolution result = resultsAmount.nextSolution();
			totalAmount = result.getLiteral("totalAmount").getInt();
		}
		
		vqeAmount.close();
		
		return totalAmount;
	}
	
	public int getForthnetGroupDailyPaymentsQuery(VirtGraph graphDiavgeiaII, String date) {
		
		int totalAmount = 0;
		
		String queryAmount = "PREFIX dcterms: <http://purl.org/dc/terms/> " +
					"PREFIX gr: <http://purl.org/goodrelations/v1#> " +
					"SELECT ?displayDate (SUM((?totalAmount)) AS ?totalAmount) " +
					"{ " +
						"SELECT (CONCAT(STR(DAY(?date)), '/', STR(MONTH(?date)), '/', STR(YEAR(?date))) AS ?displayDate) " +
							   "((xsd:decimal(?amount)) AS ?totalAmount) " +
						"FROM <" + QueryConfiguration.queryGraphDiavgeiaII + "> " +
						"WHERE { " +
						"?expenseApproval elod:hasExpenditureLine ?expenditureLine ; " +
						                 "elod:decisionTypeId ?decisionTypeId ; " +
						                 "dcterms:issued ?date . " +
						"?expenditureLine elod:amount ?ups ; " +
						                 "elod:seller ?seller . " +
						"?ups gr:hasCurrencyValue ?amount . " +
						"FILTER STRSTARTS(STR(?date), \"" + date + "\") . " +
						"FILTER NOT EXISTS {?expenseApproval elod:hasCorrectedDecision ?correctedDecision} . " +
						"FILTER NOT EXISTS {?ups elod:riskError ?error} . " +
						"FILTER (?decisionTypeId = 'Β.2.1'^^xsd:string || ?decisionTypeId = 'Β.2.2'^^xsd:string) . " +
						"FILTER (?seller=<http://linkedeconomy.org/resource/Organization/094444827> " +
						     "|| ?seller=<http://linkedeconomy.org/resource/Organization/998179538>) . " +
						"} " +
					"} " +
					"GROUP BY ?displayDate " +
					"ORDER BY ?displayDate";

		VirtuosoQueryExecution vqeAmount = VirtuosoQueryExecutionFactory.create(queryAmount, graphDiavgeiaII);
		ResultSet resultsAmount = vqeAmount.execSelect();
		
		if (resultsAmount.hasNext()) {
			QuerySolution result = resultsAmount.nextSolution();
			totalAmount = result.getLiteral("totalAmount").getInt();
		}
		
		vqeAmount.close();
		
		return totalAmount;
	}
	
	public int getCytaGroupDailyPaymentsQuery(VirtGraph graphDiavgeiaII, String date) {
		
		int totalAmount = 0;
		
		String queryAmount = "PREFIX dcterms: <http://purl.org/dc/terms/> " +
					"PREFIX gr: <http://purl.org/goodrelations/v1#> " +
					"SELECT ?displayDate (SUM((?totalAmount)) AS ?totalAmount) " +
					"{ " +
						"SELECT (CONCAT(STR(DAY(?date)), '/', STR(MONTH(?date)), '/', STR(YEAR(?date))) AS ?displayDate) " +
							   "((xsd:decimal(?amount)) AS ?totalAmount) " +
						"FROM <" + QueryConfiguration.queryGraphDiavgeiaII + "> " +
						"WHERE { " +
						"?expenseApproval elod:hasExpenditureLine ?expenditureLine ; " +
						                 "elod:decisionTypeId ?decisionTypeId ; " +
						                 "dcterms:issued ?date . " +
						"?expenditureLine elod:amount ?ups ; " +
						                 "elod:seller ?seller . " +
						"?ups gr:hasCurrencyValue ?amount . " +
						"FILTER STRSTARTS(STR(?date), \"" + date + "\") . " +
						"FILTER NOT EXISTS {?expenseApproval elod:hasCorrectedDecision ?correctedDecision} . " +
						"FILTER NOT EXISTS {?ups elod:riskError ?error} . " +
						"FILTER (?decisionTypeId = 'Β.2.1'^^xsd:string || ?decisionTypeId = 'Β.2.2'^^xsd:string) . " +
						"FILTER (?seller=<http://linkedeconomy.org/resource/Organization/998646157>) . " +
						"} " +
					"} " +
					"GROUP BY ?displayDate " +
					"ORDER BY ?displayDate";

		VirtuosoQueryExecution vqeAmount = VirtuosoQueryExecutionFactory.create(queryAmount, graphDiavgeiaII);
		ResultSet resultsAmount = vqeAmount.execSelect();
		
		if (resultsAmount.hasNext()) {
			QuerySolution result = resultsAmount.nextSolution();
			totalAmount = result.getLiteral("totalAmount").getInt();
		}
		
		vqeAmount.close();
		
		return totalAmount;
	}
	
	public int getWindGroupDailyPaymentsQuery(VirtGraph graphDiavgeiaII, String date) {
		
		int totalAmount = 0;
		
		String queryAmount = "PREFIX dcterms: <http://purl.org/dc/terms/> " +
					"PREFIX gr: <http://purl.org/goodrelations/v1#> " +
					"SELECT ?displayDate (SUM((?totalAmount)) AS ?totalAmount) " +
					"{ " +
						"SELECT (CONCAT(STR(DAY(?date)), '/', STR(MONTH(?date)), '/', STR(YEAR(?date))) AS ?displayDate) " +
							   "((xsd:decimal(?amount)) AS ?totalAmount) " +
						"FROM <" + QueryConfiguration.queryGraphDiavgeiaII + "> " +
						"WHERE { " +
						"?expenseApproval elod:hasExpenditureLine ?expenditureLine ; " +
						                 "elod:decisionTypeId ?decisionTypeId ; " +
						                 "dcterms:issued ?date . " +
						"?expenditureLine elod:amount ?ups ; " +
						                 "elod:seller ?seller . " +
						"?ups gr:hasCurrencyValue ?amount . " +
						"FILTER STRSTARTS(STR(?date), \"" + date + "\") . " +
						"FILTER NOT EXISTS {?expenseApproval elod:hasCorrectedDecision ?correctedDecision} . " +
						"FILTER NOT EXISTS {?ups elod:riskError ?error} . " +
						"FILTER (?decisionTypeId = 'Β.2.1'^^xsd:string || ?decisionTypeId = 'Β.2.2'^^xsd:string) . " +
						"FILTER (?seller=<http://linkedeconomy.org/resource/Organization/999510393> " +
							 "|| ?seller=<http://linkedeconomy.org/resource/Organization/099936189>) . " +
						"} " +
					"} " +
					"GROUP BY ?displayDate " +
					"ORDER BY ?displayDate";

		VirtuosoQueryExecution vqeAmount = VirtuosoQueryExecutionFactory.create(queryAmount, graphDiavgeiaII);
		ResultSet resultsAmount = vqeAmount.execSelect();
		
		if (resultsAmount.hasNext()) {
			QuerySolution result = resultsAmount.nextSolution();
			totalAmount = result.getLiteral("totalAmount").getInt();
		}
		
		vqeAmount.close();
		
		return totalAmount;
	}
	
}