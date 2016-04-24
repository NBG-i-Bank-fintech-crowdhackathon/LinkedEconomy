package common;

import java.util.ArrayList;
import java.util.List;

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
public class QueriesMetrics {
	
	public List<String> getOteGroupPaymentDtlsQuery(VirtGraph graphDiavgeiaII) {
		
		List<String> datesList = new ArrayList<String>();
		
		String queryDate = "PREFIX elod: <http://linkedeconomy.org/ontology#> " +
					"PREFIX dcterms: <http://purl.org/dc/terms/> " +
					"PREFIX gr: <http://purl.org/goodrelations/v1#> " +
					"PREFIX dbo: <http://dbpedia.org/ontology/> " +
					"SELECT DISTINCT ?date " + 
					"FROM <" + QueryConfiguration.queryGraphDiavgeiaII + "> " +
					"FROM <" + QueryConfiguration.queryGraphOrganizations + "> " +
					"WHERE { " +
					"{ " +
						"?decision elod:hasExpenditureLine ?expLineOTE ; " +
						          "elod:decisionTypeId ?decisionTypeId ; " +
						          "dcterms:issued ?date . " +
						"?expLineOTE elod:seller <http://linkedeconomy.org/resource/Organization/094019245> . " +
						"FILTER (?date >= '" + QueryConfiguration.baseYear + "-01-01T00:00:00Z'^^xsd:dateTime) . " +
						"FILTER (?date < '" + (Integer.parseInt(QueryConfiguration.baseYear) + 1) + "-01-01T00:00:00Z'^^xsd:dateTime) . " +
						"FILTER NOT EXISTS {?decision elod:hasCorrectedDecision ?correctedDecision} . " +
						"FILTER (?decisionTypeId = 'Β.2.1'^^xsd:string || ?decisionTypeId = 'Β.2.2'^^xsd:string ) . " +
					"} " +
					"UNION " +
					"{ " +
						"<http://linkedeconomy.org/resource/Organization/094019245> dbo:subsidiary ?subsidiary . " +
						"?decision elod:hasExpenditureLine ?expLineSubs ; " +
						          "elod:decisionTypeId ?decisionTypeId ; " +
						          "dcterms:issued ?date . " +
						"?expLineSubs elod:seller ?subsidiary . " +
						"FILTER (?date >= '" + QueryConfiguration.baseYear + "-01-01T00:00:00Z'^^xsd:dateTime) . " +
						"FILTER (?date < '" + (Integer.parseInt(QueryConfiguration.baseYear) + 1) + "-01-01T00:00:00Z'^^xsd:dateTime) . " +
						"FILTER NOT EXISTS {?decision elod:hasCorrectedDecision ?correctedDecision} . " +
						"FILTER (?decisionTypeId = 'Β.2.1'^^xsd:string || ?decisionTypeId = 'Β.2.2'^^xsd:string ) . " +
					"} " +
					"} " +
					"ORDER BY ASC (?date)";

		VirtuosoQueryExecution vqeDate = VirtuosoQueryExecutionFactory.create(queryDate, graphDiavgeiaII);
		ResultSet resultsDate = vqeDate.execSelect();
		
		while (resultsDate.hasNext()) {
			QuerySolution result = resultsDate.nextSolution();
			String date  = result.getLiteral("date").getString().split("T")[0];
			if (!datesList.contains(date)) {
				datesList.add(date);
			}
		}
		
		vqeDate.close();
		
		return datesList;
	}
	
	public List<String> getVodafoneGroupPaymentDtlsQuery(VirtGraph graphDiavgeiaII) {
		
		List<String> datesList = new ArrayList<String>();
		
		String queryDate = "PREFIX elod: <http://linkedeconomy.org/ontology#> " +
					"PREFIX dcterms: <http://purl.org/dc/terms/> " +
					"PREFIX gr: <http://purl.org/goodrelations/v1#> " +
					"PREFIX dbo: <http://dbpedia.org/ontology/> " +
					"SELECT DISTINCT ?date " + 
					"FROM <" + QueryConfiguration.queryGraphDiavgeiaII + "> " +
					"FROM <" + QueryConfiguration.queryGraphOrganizations + "> " +
					"WHERE { " +
					"?expenseApproval elod:hasExpenditureLine ?expenditureLine ; " +
					                 "elod:decisionTypeId ?decisionTypeId ; " +
					                 "dcterms:issued ?date . " +
					"?expenditureLine elod:seller ?seller . " +
					"FILTER NOT EXISTS {?expenseApproval elod:hasCorrectedDecision ?correctedDecision} . " +
					"FILTER (?date >= '" + QueryConfiguration.baseYear + "-01-01T00:00:00Z'^^xsd:dateTime) . " +
					"FILTER (?date < '" + (Integer.parseInt(QueryConfiguration.baseYear) + 1) + "-01-01T00:00:00Z'^^xsd:dateTime) . " +
					"FILTER (?decisionTypeId = 'Β.2.1'^^xsd:string || ?decisionTypeId = 'Β.2.2'^^xsd:string) . " +
					"FILTER (?seller=<http://linkedeconomy.org/resource/Organization/094349850> " +
					     "|| ?seller=<http://linkedeconomy.org/resource/Organization/094222339>) . " +
					"} " +
					"ORDER BY ASC (?date)";

		VirtuosoQueryExecution vqeDate = VirtuosoQueryExecutionFactory.create(queryDate, graphDiavgeiaII);
		ResultSet resultsDate = vqeDate.execSelect();
		
		while (resultsDate.hasNext()) {
			QuerySolution result = resultsDate.nextSolution();
			String date  = result.getLiteral("date").getString().split("T")[0];
			if (!datesList.contains(date)) {
				datesList.add(date);
			}
		}
		
		vqeDate.close();
		
		return datesList;
	}
	
	public List<String> getForthnetGroupPaymentDtlsQuery(VirtGraph graphDiavgeiaII) {
		
		List<String> datesList = new ArrayList<String>();
		
		String queryDate = "PREFIX elod: <http://linkedeconomy.org/ontology#> " +
					"PREFIX dcterms: <http://purl.org/dc/terms/> " +
					"PREFIX gr: <http://purl.org/goodrelations/v1#> " +
					"PREFIX dbo: <http://dbpedia.org/ontology/> " +
					"SELECT DISTINCT ?date " + 
					"FROM <" + QueryConfiguration.queryGraphDiavgeiaII + "> " +
					"FROM <" + QueryConfiguration.queryGraphOrganizations + "> " +
					"WHERE { " +
					"?expenseApproval elod:hasExpenditureLine ?expenditureLine ; " +
					                 "elod:decisionTypeId ?decisionTypeId ; " +
					                 "dcterms:issued ?date . " +
					"?expenditureLine elod:seller ?seller . " +
					"FILTER NOT EXISTS {?expenseApproval elod:hasCorrectedDecision ?correctedDecision} . " +
					"FILTER (?date >= '" + QueryConfiguration.baseYear + "-01-01T00:00:00Z'^^xsd:dateTime) . " +
					"FILTER (?date < '" + (Integer.parseInt(QueryConfiguration.baseYear) + 1) + "-01-01T00:00:00Z'^^xsd:dateTime) . " +
					"FILTER (?decisionTypeId = 'Β.2.1'^^xsd:string || ?decisionTypeId = 'Β.2.2'^^xsd:string) . " +
					"FILTER (?seller=<http://linkedeconomy.org/resource/Organization/094444827> " +
				     	 "|| ?seller=<http://linkedeconomy.org/resource/Organization/998179538>) . " +
					"} " +
					"ORDER BY ASC (?date)";

		VirtuosoQueryExecution vqeDate = VirtuosoQueryExecutionFactory.create(queryDate, graphDiavgeiaII);
		ResultSet resultsDate = vqeDate.execSelect();
		
		while (resultsDate.hasNext()) {
			QuerySolution result = resultsDate.nextSolution();
			String date  = result.getLiteral("date").getString().split("T")[0];
			if (!datesList.contains(date)) {
				datesList.add(date);
			}
		}
		
		vqeDate.close();
		
		return datesList;
	}
	
	public List<String> getCytaGroupPaymentDtlsQuery(VirtGraph graphDiavgeiaII) {
		
		List<String> datesList = new ArrayList<String>();
		
		String queryDate = "PREFIX elod: <http://linkedeconomy.org/ontology#> " +
					"PREFIX dcterms: <http://purl.org/dc/terms/> " +
					"PREFIX gr: <http://purl.org/goodrelations/v1#> " +
					"PREFIX dbo: <http://dbpedia.org/ontology/> " +
					"SELECT DISTINCT ?date " + 
					"FROM <" + QueryConfiguration.queryGraphDiavgeiaII + "> " +
					"FROM <" + QueryConfiguration.queryGraphOrganizations + "> " +
					"WHERE { " +
					"?expenseApproval elod:hasExpenditureLine ?expenditureLine ; " +
					                 "elod:decisionTypeId ?decisionTypeId ; " +
					                 "dcterms:issued ?date . " +
					"?expenditureLine elod:seller ?seller . " +
					"FILTER NOT EXISTS {?expenseApproval elod:hasCorrectedDecision ?correctedDecision} . " +
					"FILTER (?date >= '" + QueryConfiguration.baseYear + "-01-01T00:00:00Z'^^xsd:dateTime) . " +
					"FILTER (?date < '" + (Integer.parseInt(QueryConfiguration.baseYear) + 1) + "-01-01T00:00:00Z'^^xsd:dateTime) . " +
					"FILTER (?decisionTypeId = 'Β.2.1'^^xsd:string || ?decisionTypeId = 'Β.2.2'^^xsd:string) . " +
					"FILTER (?seller=<http://linkedeconomy.org/resource/Organization/998646157>) . " +
					"} " +
					"ORDER BY ASC (?date)";

		VirtuosoQueryExecution vqeDate = VirtuosoQueryExecutionFactory.create(queryDate, graphDiavgeiaII);
		ResultSet resultsDate = vqeDate.execSelect();
		
		while (resultsDate.hasNext()) {
			QuerySolution result = resultsDate.nextSolution();
			String date  = result.getLiteral("date").getString().split("T")[0];
			if (!datesList.contains(date)) {
				datesList.add(date);
			}
		}
		
		vqeDate.close();
		
		return datesList;
	}

	public List<String> getWindGroupPaymentDtlsQuery(VirtGraph graphDiavgeiaII) {
	
		List<String> datesList = new ArrayList<String>();
		
		String queryDate = "PREFIX elod: <http://linkedeconomy.org/ontology#> " +
					"PREFIX dcterms: <http://purl.org/dc/terms/> " +
					"PREFIX gr: <http://purl.org/goodrelations/v1#> " +
					"PREFIX dbo: <http://dbpedia.org/ontology/> " +
					"SELECT DISTINCT ?date " + 
					"FROM <" + QueryConfiguration.queryGraphDiavgeiaII + "> " +
					"FROM <" + QueryConfiguration.queryGraphOrganizations + "> " +
					"WHERE { " +
					"?expenseApproval elod:hasExpenditureLine ?expenditureLine ; " +
					                 "elod:decisionTypeId ?decisionTypeId ; " +
					                 "dcterms:issued ?date . " +
					"?expenditureLine elod:seller ?seller . " +
					"FILTER NOT EXISTS {?expenseApproval elod:hasCorrectedDecision ?correctedDecision} . " +
					"FILTER (?date >= '" + QueryConfiguration.baseYear + "-01-01T00:00:00Z'^^xsd:dateTime) . " +
					"FILTER (?date < '" + (Integer.parseInt(QueryConfiguration.baseYear) + 1) + "-01-01T00:00:00Z'^^xsd:dateTime) . " +
					"FILTER (?decisionTypeId = 'Β.2.1'^^xsd:string || ?decisionTypeId = 'Β.2.2'^^xsd:string) . " +
					"FILTER (?seller=<http://linkedeconomy.org/resource/Organization/999510393> " +
					 	 "|| ?seller=<http://linkedeconomy.org/resource/Organization/099936189>) . " +
					"} " +
					"ORDER BY ASC (?date)";
	
		VirtuosoQueryExecution vqeDate = VirtuosoQueryExecutionFactory.create(queryDate, graphDiavgeiaII);
		ResultSet resultsDate = vqeDate.execSelect();
		
		while (resultsDate.hasNext()) {
			QuerySolution result = resultsDate.nextSolution();
			String date  = result.getLiteral("date").getString().split("T")[0];
			if (!datesList.contains(date)) {
				datesList.add(date);
			}
		}
		
		vqeDate.close();
		
		return datesList;
	}
	
}