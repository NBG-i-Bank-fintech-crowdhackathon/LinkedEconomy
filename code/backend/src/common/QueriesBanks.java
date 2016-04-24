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
public class QueriesBanks {
	
	public int getEthnikiGroupDailyPaymentsQuery(VirtGraph graphDiavgeiaII, String date) {
		
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
						"FILTER (?seller=<http://linkedeconomy.org/resource/Organization/094014201> " +
							"|| ?seller=<http://linkedeconomy.org/resource/Organization/997799293> " +
							"|| ?seller=<http://linkedeconomy.org/resource/Organization/094328700> " +
							"|| ?seller=<http://linkedeconomy.org/resource/Organization/997983926>) " +
						"} " +
					"} " +
					"GROUP BY ?displayDate " +
					"ORDER BY ?displayDate";

		VirtuosoQueryExecution vqeAmount = VirtuosoQueryExecutionFactory.create(queryAmount, graphDiavgeiaII);
		ResultSet resultsAmount = vqeAmount.execSelect();
		
		if (resultsAmount.hasNext()) {
			QuerySolution result = resultsAmount.nextSolution();
			if (result.getLiteral("totalAmount")!=null) {
				totalAmount = result.getLiteral("totalAmount").getInt();
			} else {
				totalAmount=0;
			}			
		}
		
		vqeAmount.close();
		
		return totalAmount;
	}
	
	public int getPeiraiosGroupDailyPaymentsQuery(VirtGraph graphDiavgeiaII, String date) {
		
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
						"FILTER (?seller=<http://linkedeconomy.org/resource/Organization/094014298> " +
							"|| ?seller=<http://linkedeconomy.org/resource/Organization/099555381> " +
							"|| ?seller=<http://linkedeconomy.org/resource/Organization/094505063>) " +
						"} " +
					"} " +
					"GROUP BY ?displayDate " +
					"ORDER BY ?displayDate";

		VirtuosoQueryExecution vqeAmount = VirtuosoQueryExecutionFactory.create(queryAmount, graphDiavgeiaII);
		ResultSet resultsAmount = vqeAmount.execSelect();
		
		if (resultsAmount.hasNext()) {
			QuerySolution result = resultsAmount.nextSolution();
			if (result.getLiteral("totalAmount")!=null) {
				totalAmount = result.getLiteral("totalAmount").getInt();
			} else {
				totalAmount=0;
			}
		}
		
		vqeAmount.close();
		
		return totalAmount;
	}
	
	public int getAlphaGroupDailyPaymentsQuery(VirtGraph graphDiavgeiaII, String date) {
		
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
						"FILTER (?seller=<http://linkedeconomy.org/resource/Organization/094014249>) " +
						"} " +
					"} " +
					"GROUP BY ?displayDate " +
					"ORDER BY ?displayDate";

		VirtuosoQueryExecution vqeAmount = VirtuosoQueryExecutionFactory.create(queryAmount, graphDiavgeiaII);
		ResultSet resultsAmount = vqeAmount.execSelect();
		
		if (resultsAmount.hasNext()) {
			QuerySolution result = resultsAmount.nextSolution();
			if (result.getLiteral("totalAmount")!=null) {
				totalAmount = result.getLiteral("totalAmount").getInt();
			} else {
				totalAmount=0;
			}
		}
		
		vqeAmount.close();
		
		return totalAmount;
	}
	
	public int getEurobankGroupDailyPaymentsQuery(VirtGraph graphDiavgeiaII, String date) {
		
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
						"FILTER (?seller=<http://linkedeconomy.org/resource/Organization/094014250> " +
							"|| ?seller=<http://linkedeconomy.org/resource/Organization/099554673> " +
							"|| ?seller=<http://linkedeconomy.org/resource/Organization/094324854> " +
							"|| ?seller=<http://linkedeconomy.org/resource/Organization/094543092> " +
							"|| ?seller=<http://linkedeconomy.org/resource/Organization/099755919> " +
							"|| ?seller=<http://linkedeconomy.org/resource/Organization/099758713> " +
							"|| ?seller=<http://linkedeconomy.org/resource/Organization/094495747>) " +
						"} " +
					"} " +
					"GROUP BY ?displayDate " +
					"ORDER BY ?displayDate";

		VirtuosoQueryExecution vqeAmount = VirtuosoQueryExecutionFactory.create(queryAmount, graphDiavgeiaII);
		ResultSet resultsAmount = vqeAmount.execSelect();
		
		if (resultsAmount.hasNext()) {
			QuerySolution result = resultsAmount.nextSolution();
			if (result.getLiteral("totalAmount")!=null) {
				totalAmount = result.getLiteral("totalAmount").getInt();
			} else {
				totalAmount=0;
			}
		}
		
		vqeAmount.close();
		
		return totalAmount;
	}
	
}