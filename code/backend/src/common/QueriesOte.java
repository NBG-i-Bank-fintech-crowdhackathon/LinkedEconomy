package common;

import java.util.ArrayList;
import java.util.List;

import virtuoso.jena.driver.VirtGraph;
import virtuoso.jena.driver.VirtuosoQueryExecution;
import virtuoso.jena.driver.VirtuosoQueryExecutionFactory;

import com.hp.hpl.jena.query.QuerySolution;
import com.hp.hpl.jena.query.ResultSet;
import com.hp.hpl.jena.rdf.model.Literal;
import com.hp.hpl.jena.rdf.model.Resource;

import common.QueryConfiguration;

/**
 * @author G. Razis
 * @author G. Vafeiadis
 * @author E. Galanos
 */
public class QueriesOte {
	
	QueriesCommon qsCommon = new QueriesCommon();
	
	public List<String> getOteGroupSubsidiariesQuery(VirtGraph graphDiavgeiaII) {
		
		List<String> subsidiariesList = new ArrayList<String>();
		
		String queryOte = "PREFIX dbo: <http://dbpedia.org/ontology/> " +
					"PREFIX gr: <http://purl.org/goodrelations/v1#> " +
					"SELECT ?legalName " + 
					"FROM <" + QueryConfiguration.queryGraphOrganizations + "> " +
					"WHERE { " +
					"<http://linkedeconomy.org/resource/Organization/094019245> dbo:subsidiary ?subsidiary . " +
					"?subsidiary gr:legalName ?legalName ." +
					"}";

		VirtuosoQueryExecution vqeOte = VirtuosoQueryExecutionFactory.create(queryOte, graphDiavgeiaII);
		ResultSet resultsOte = vqeOte.execSelect();
		
		while (resultsOte.hasNext()) {
			QuerySolution result = resultsOte.nextSolution();
			subsidiariesList.add( result.getLiteral("legalName").getString() );
		}
		
		vqeOte.close();
		
		return subsidiariesList;
	}
	
	/** Queries for: Sums and Counters **/
	public String[] getOteGroupPaymentDtlsQuery(VirtGraph graphDiavgeiaII, boolean currentYear) {
		
		String totalExpLines = null;
		String totalAmount = null;
		
		String dateFilter = null;
		String graphFilter = null;
		
		if (currentYear) {
			graphFilter = "FROM <" + QueryConfiguration.queryGraphDiavgeiaII + "> ";
			dateFilter = "FILTER (?date >= '" + QueryConfiguration.baseYear + "-01-01T00:00:00Z'^^xsd:dateTime) . " +
						 "FILTER (?date < '" + (Integer.parseInt(QueryConfiguration.baseYear) + 1) + "-01-01T00:00:00Z'^^xsd:dateTime) . ";
		} else {
			graphFilter = "FROM <http://linkedeconomy.org/DiavgeiaII> ";
			dateFilter = "FILTER (?date >= '" + (Integer.parseInt(QueryConfiguration.baseYear) - 1) + "-01-01T00:00:00Z'^^xsd:dateTime) . " +
					 	 "FILTER (?date < '" + QueryConfiguration.baseYear + "-01-01T00:00:00Z'^^xsd:dateTime) . ";
		}
		
		String queryOte = "PREFIX elod: <http://linkedeconomy.org/ontology#> " +
					"PREFIX dcterms: <http://purl.org/dc/terms/> " +
					"PREFIX pc: <http://purl.org/procurement/public-contracts#> " +
					"PREFIX gr: <http://purl.org/goodrelations/v1#> " +
					"PREFIX dbo: <http://dbpedia.org/ontology/> " +
					"SELECT (COUNT(DISTINCT ?expLineOTE) + COUNT(DISTINCT ?expLineSubs) AS ?totalExpLines) " +
						   "(SUM(xsd:decimal(?amountOTE)) + SUM(xsd:decimal(?amountSubs)) AS ?totalAmount) " + 
					graphFilter + 
					"FROM <" + QueryConfiguration.queryGraphOrganizations + "> " +
					"WHERE { " +
					"{ " +
						"?decision elod:hasExpenditureLine ?expLineOTE ; " +
						          "elod:decisionTypeId ?decisionTypeId ; " +
						          "dcterms:issued ?date . " +
						"?expLineOTE elod:amount ?ups ; " +
						            "elod:seller <http://linkedeconomy.org/resource/Organization/094019245> . " +
						"?ups gr:hasCurrencyValue ?amountOTE . " +
						dateFilter +
						"FILTER NOT EXISTS {?decision elod:hasCorrectedDecision ?correctedDecision} . " +
						"FILTER NOT EXISTS {?ups elod:riskError \"true\"^^<http://www.w3.org/2001/XMLSchema#boolean>} . " +
						"FILTER (?decisionTypeId = 'Β.2.1'^^xsd:string || ?decisionTypeId = 'Β.2.2'^^xsd:string ) . " +
					"} " +
					"UNION " +
					"{ " +
						"<http://linkedeconomy.org/resource/Organization/094019245> dbo:subsidiary ?subsidiary . " +
						"?decision elod:hasExpenditureLine ?expLineSubs ; " +
						          "elod:decisionTypeId ?decisionTypeId ; " +
						          "dcterms:issued ?date . " +
						"?expLineSubs elod:amount ?ups ; " +
						             "elod:seller ?subsidiary . " +
						"?ups gr:hasCurrencyValue ?amountSubs . " +
						dateFilter +
						"FILTER NOT EXISTS {?decision elod:hasCorrectedDecision ?correctedDecision} . " +
						"FILTER NOT EXISTS {?ups elod:riskError \"true\"^^<http://www.w3.org/2001/XMLSchema#boolean>} . " +
						"FILTER (?decisionTypeId = 'Β.2.1'^^xsd:string || ?decisionTypeId = 'Β.2.2'^^xsd:string ) . " +
					"} " +
					"}";

		VirtuosoQueryExecution vqeOte = VirtuosoQueryExecutionFactory.create(queryOte, graphDiavgeiaII);
		ResultSet resultsOte = vqeOte.execSelect();
		
		if (resultsOte.hasNext()) {
			QuerySolution result = resultsOte.nextSolution();
			totalExpLines = Utils.getInstance().formatNumber( result.getLiteral("totalExpLines").getString() );
			totalAmount = Utils.getInstance().formatNumber( (result.getLiteral("totalAmount")).getString() );
		}
		
		vqeOte.close();
		
		return new String[] {totalExpLines, totalAmount};
	}
	
	public String[] getOteGroupAssignmentDtlsQuery(VirtGraph graphDiavgeiaII, boolean currentYear) {
		
		String totalContracts = null;
		String totalAmount = null;

		String dateFilter = null;
		String graphFilter = null;
		
		if (currentYear) {
			graphFilter = "FROM <" + QueryConfiguration.queryGraphDiavgeiaII + "> ";
			dateFilter = "FILTER (?date >= '" + QueryConfiguration.baseYear + "-01-01T00:00:00Z'^^xsd:dateTime) . " +
						 "FILTER (?date < '" + (Integer.parseInt(QueryConfiguration.baseYear) + 1) + "-01-01T00:00:00Z'^^xsd:dateTime) . ";
		} else {
			graphFilter = "FROM <http://linkedeconomy.org/DiavgeiaII> ";
			dateFilter = "FILTER (?date >= '" + (Integer.parseInt(QueryConfiguration.baseYear) - 1) + "-01-01T00:00:00Z'^^xsd:dateTime) . " +
					 	 "FILTER (?date < '" + QueryConfiguration.baseYear + "-01-01T00:00:00Z'^^xsd:dateTime) . ";
		}
		
		String queryOte = "PREFIX elod: <http://linkedeconomy.org/ontology#> " +
					"PREFIX dcterms: <http://purl.org/dc/terms/> " +
					"PREFIX pc: <http://purl.org/procurement/public-contracts#> " +
					"PREFIX gr: <http://purl.org/goodrelations/v1#> " +
					"SELECT (COUNT(DISTINCT ?contract) as ?totalContracts) (SUM(xsd:decimal(?amount)) AS ?totalAmount) " + 
					graphFilter + 
					"WHERE { " +
					"{ " +
						"?contract pc:agreedPrice ?upsContract ; " +
						           "elod:decisionTypeId ?decisionTypeId ; " +
						           "dcterms:issued ?date ; " +
						           "elod:seller ?seller . " +
						"?upsContract gr:hasCurrencyValue ?amount . " +
						dateFilter +
						"FILTER NOT EXISTS {?contract elod:hasCorrectedDecision ?correctedDecision} . " +
						"FILTER NOT EXISTS {?upsContract elod:riskError \"true\"^^<http://www.w3.org/2001/XMLSchema#boolean>} . " +
						"FILTER (?decisionTypeId= 'Δ.1'^^xsd:string) . " +
						"FILTER (?seller=<http://linkedeconomy.org/resource/Organization/094019245> " +
						     "|| ?seller=<http://linkedeconomy.org/resource/Organization/094493766> " +
						     "|| ?seller=<http://linkedeconomy.org/resource/Organization/099760493> " +
						     "|| ?seller=<http://linkedeconomy.org/resource/Organization/094493000> " +
						     "|| ?seller=<http://linkedeconomy.org/resource/Organization/094222303> " +
						     "|| ?seller=<http://linkedeconomy.org/resource/Organization/094436540> " +
						     "|| ?seller=<http://linkedeconomy.org/resource/Organization/099760500> " +
						     "|| ?seller=<http://linkedeconomy.org/resource/Organization/094282975>) " +
					"} " +
					"UNION " +
					"{ " +
						"?contract pc:actualPrice ?upsContract ; " +
						           "elod:decisionTypeId ?decisionTypeId ; " +
						           "dcterms:issued ?date ; " +
						           "elod:seller ?seller . " +
						"?upsContract gr:hasCurrencyValue ?amount . " +
						dateFilter +
						"FILTER NOT EXISTS {?contract elod:hasCorrectedDecision ?correctedDecision} . " +
						"FILTER NOT EXISTS {?upsContract elod:riskError \"true\"^^<http://www.w3.org/2001/XMLSchema#boolean>} . " +
						"FILTER (?decisionTypeId= 'Δ.2.2'^^xsd:string) . " +
						"FILTER (?seller=<http://linkedeconomy.org/resource/Organization/094019245> " +
						     "|| ?seller=<http://linkedeconomy.org/resource/Organization/094493766> " +
						     "|| ?seller=<http://linkedeconomy.org/resource/Organization/099760493> " +
						     "|| ?seller=<http://linkedeconomy.org/resource/Organization/094493000> " +
						     "|| ?seller=<http://linkedeconomy.org/resource/Organization/094222303> " +
						     "|| ?seller=<http://linkedeconomy.org/resource/Organization/094436540> " +
						     "|| ?seller=<http://linkedeconomy.org/resource/Organization/099760500> " +
						     "|| ?seller=<http://linkedeconomy.org/resource/Organization/094282975>) " +
					"} " +
					"}";

		VirtuosoQueryExecution vqeOte = VirtuosoQueryExecutionFactory.create(queryOte, graphDiavgeiaII);
		ResultSet resultsOte = vqeOte.execSelect();
		
		if (resultsOte.hasNext()) {
			QuerySolution result = resultsOte.nextSolution();
			totalContracts = Utils.getInstance().formatNumber( result.getLiteral("totalContracts").getString() );
			totalAmount = Utils.getInstance().formatNumber( (result.getLiteral("totalAmount")).getString() );
		}
		
		vqeOte.close();
		
		return new String[] {totalContracts, totalAmount};
	}
	
	public String[] getOteGroupEspaDtlsQuery(VirtGraph graphDiavgeiaII) {
		
		String totalSubsidies = null;
		String totalAmount = null;
		
		String queryOte = "PREFIX elod: <http://linkedeconomy.org/ontology#> " +
					"PREFIX gr: <http://purl.org/goodrelations/v1#> " +
					"SELECT (COUNT(?subsidy) AS ?subsidyCounter) (SUM(xsd:decimal(?siAmount)) AS ?siAmount) " + 
					"FROM <" + QueryConfiguration.queryGraphEspa + "> " +
					"WHERE { " +
					"?subsidy elod:hasRelatedBudgetItem ?budgetItem ; " + 
					         "elod:hasRelatedContract ?contract ; " + 
					         "elod:hasRelatedSpendingItem ?spendingItem ; " + 
					         "elod:beneficiary ?beneficiary . " + 
					"?spendingItem elod:amount ?siUps . " + 
					"?siUps gr:hasCurrencyValue ?siAmount . " +
					"FILTER (?beneficiary=<http://linkedeconomy.org/resource/Organization/094019245> " +
					     "|| ?beneficiary=<http://linkedeconomy.org/resource/Organization/094493766> " +
					     "|| ?beneficiary=<http://linkedeconomy.org/resource/Organization/099760493> " +
					     "|| ?beneficiary=<http://linkedeconomy.org/resource/Organization/094493000> " +
					     "|| ?beneficiary=<http://linkedeconomy.org/resource/Organization/094222303> " +
					     "|| ?beneficiary=<http://linkedeconomy.org/resource/Organization/094436540> " +
					     "|| ?beneficiary=<http://linkedeconomy.org/resource/Organization/099760500> " +
						 "|| ?beneficiary=<http://linkedeconomy.org/resource/Organization/094282975>) " +
					"}";

		VirtuosoQueryExecution vqeOte = VirtuosoQueryExecutionFactory.create(queryOte, graphDiavgeiaII);	
		ResultSet resultsOte = vqeOte.execSelect();
		
		if (resultsOte.hasNext()) {
			QuerySolution result = resultsOte.nextSolution();
			totalSubsidies = Utils.getInstance().formatNumber( result.getLiteral("subsidyCounter").getString() );
			totalAmount = Utils.getInstance().formatNumber( (result.getLiteral("siAmount")).getString() );
		}
		
		vqeOte.close();
		
		return new String[] {totalSubsidies, totalAmount};
	}
	
	public String[] getOteGroupEuFtsDtlsQuery(VirtGraph graphEuFts) {
		
		String totalPayments = null;
		String totalAmount = null;
		
		String queryOte = "PREFIX dbo: <http://dbpedia.org/ontology/> " +
					"PREFIX gr: <http://purl.org/goodrelations/v1#> " +
					"PREFIX elod: <http://linkedeconomy.org/ontology#> " +
					"PREFIX pc: <http://purl.org/procurement/public-contracts#> " +
					"SELECT (COUNT(DISTINCT ?spendingItem) AS ?euftsCounter) (SUM(xsd:decimal(?amount)) AS ?euftsAmount) " +
					"FROM <" + QueryConfiguration.queryGraphEufts + "> " +
					"FROM <" + QueryConfiguration.queryGraphOrganizations + "> " +
					"WHERE { " +
					"{ " +
					"<http://linkedeconomy.org/resource/Organization/094019245> dbpedia-owl:subsidiary ?subsidiary . " +
					"?spendingItem elod:hasExpenditureLine ?expLine ; " +
					              "elod:hasRelatedContract ?contract . " +
					"?expLine elod:amount ?ups ; " +
							 "elod:seller ?subsidiary . " +
					"?ups gr:hasCurrencyValue ?amount . " +
					"} " +
					"UNION " +
					"{ " +
					"?spendingItem elod:hasExpenditureLine ?expLine ; " +
					              "elod:hasRelatedContract ?contract . " +
					"?expLine elod:amount ?ups ; " +
							 "elod:seller <http://linkedeconomy.org/resource/Organization/094019245> . " +
					"?ups gr:hasCurrencyValue ?amount . " +
					"} " +
					"} " +
					"ORDER BY DESC(?euftsAmount)";

		VirtuosoQueryExecution vqeOte = VirtuosoQueryExecutionFactory.create(queryOte, graphEuFts);
		ResultSet resultsOte = vqeOte.execSelect();
		
		if (resultsOte.hasNext()) {
			QuerySolution result = resultsOte.nextSolution();
			totalPayments = Utils.getInstance().formatNumber( result.getLiteral("euftsCounter").getString() );
			totalAmount = Utils.getInstance().formatNumber( (result.getLiteral("euftsAmount")).getString() );
		}
		
		vqeOte.close();
		
		return new String[] {totalPayments, totalAmount};
	}
	
	/** Queries for: Decisions **/
	public ArrayList<String[]> getOteGroupTopPaymentsQuery(VirtGraph graphDiavgeiaII, String jsonString) {
		
		Resource buyerUri = null;
		Resource sellerUri = null;
		Resource cpv = null;
		String ada = null;
		String date = null;
		String totalAmount = null;
		String[] singleDecision = null;
		ArrayList<String[]> expApprList = new ArrayList<String[]>();
		
		String queryDecision = "PREFIX elod: <http://linkedeconomy.org/ontology#> " +
					"PREFIX gr: <http://purl.org/goodrelations/v1#> " +
					"PREFIX dc: <http://purl.org/dc/elements/1.1/> " +
					"PREFIX org: <http://www.w3.org/ns/org#> " +
					"PREFIX dcterms: <http://purl.org/dc/terms/> " +
					"SELECT ?buyer ?seller ?date ?paymentAmount ?ada ?cpv " +
					"{ " +
						"SELECT ?buyer ?seller ?date (xsd:decimal(?amount) AS ?paymentAmount) ?ada ?cpv " +
						"FROM <" + QueryConfiguration.queryGraphDiavgeiaII + "> " +
						"FROM <" + QueryConfiguration.queryGraphOrganizations + "> " +
						"WHERE { " +
						"{ " +
							"?expenseApproval elod:hasExpenditureLine ?expenditureLine ; " +
							                 "elod:ada ?ada ; " +
							                 "dcterms:issued ?date ; " +
							                 "dc:publisher ?unit ; " +
							                 "rdf:type elod:ExpenseApprovalItem . " +
							"?expenditureLine elod:seller ?seller ; " +
							                 "elod:amount ?ups . " +
							"?ups gr:hasCurrencyValue ?amount . " +
							"?buyer org:hasUnit ?unit ; " + 
								   "gr:vatID ?buyerAfm . " +
							"OPTIONAL { " +
								"?expenditureLine elod:hasCpv ?cpv . " +
							"} . " +
							"FILTER NOT EXISTS {?expenseApproval elod:hasCorrectedDecision ?correctedDecision} . " +
							"FILTER NOT EXISTS {?ups elod:riskError \"true\"^^<http://www.w3.org/2001/XMLSchema#boolean>} . " +
							"FILTER (?date >= '" + QueryConfiguration.baseYear + "-01-01T00:00:00Z'^^xsd:dateTime) . " +
							"FILTER (?date < '" + (Integer.parseInt(QueryConfiguration.baseYear) + 1) + "-01-01T00:00:00Z'^^xsd:dateTime) . " +
							"FILTER (?seller=<http://linkedeconomy.org/resource/Organization/094019245> " +
							     "|| ?seller=<http://linkedeconomy.org/resource/Organization/094493766> " +
							     "|| ?seller=<http://linkedeconomy.org/resource/Organization/099760493> " +
							     "|| ?seller=<http://linkedeconomy.org/resource/Organization/094493000> " +
							     "|| ?seller=<http://linkedeconomy.org/resource/Organization/094222303> " +
							     "|| ?seller=<http://linkedeconomy.org/resource/Organization/094436540> " +
							     "|| ?seller=<http://linkedeconomy.org/resource/Organization/099760500> " +
							     "|| ?seller=<http://linkedeconomy.org/resource/Organization/094282975>) " +
						"} " +
						"UNION " +
						"{ " +
							"?payment elod:hasExpenditureLine ?expenditureLine ; " +
							         "elod:ada ?ada ; " +
							         "dcterms:issued ?date ; " +
							         "dc:publisher ?unit ; " +
							         "rdf:type elod:SpendingItem . " +
							"?expenditureLine elod:seller ?seller ; " +
										     "elod:hasCpv ?cpv ; " +
							                 "elod:amount ?ups . " +
							"?ups gr:hasCurrencyValue ?amount . " +
							"?buyer org:hasUnit ?unit ; " + 
								   "gr:vatID ?buyerAfm . " +
							"FILTER NOT EXISTS {?payment elod:hasCorrectedDecision ?correctedDecision} . " +
							"FILTER NOT EXISTS {?ups elod:riskError \"true\"^^<http://www.w3.org/2001/XMLSchema#boolean>} . " +
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
						"ORDER BY DESC (?paymentAmount) " +
						"LIMIT 1000 " +
					"} " +
					"ORDER BY DESC (?date)";

		VirtuosoQueryExecution vqeDecision = VirtuosoQueryExecutionFactory.create(queryDecision, graphDiavgeiaII);
		ResultSet resultsDecision = vqeDecision.execSelect();
		
		while (resultsDecision.hasNext()) {
			QuerySolution result = resultsDecision.nextSolution();
			buyerUri = result.getResource("buyer");
			sellerUri = result.getResource("seller");
			ada = result.getLiteral("ada").getString();
			cpv = result.getResource("cpv");
			date = result.getLiteral("date").getString().split("T")[0];
			totalAmount = Utils.getInstance().formatNumber( result.getLiteral("paymentAmount").getString() );
			
			Literal buyerNameLit = qsCommon.getAgentLegalName(graphDiavgeiaII, buyerUri.toString());
			String buyerNameStr = qsCommon.configureAgentName(graphDiavgeiaII, buyerNameLit, buyerUri);
			
			Literal sellerNameLit = qsCommon.getAgentLegalName(graphDiavgeiaII, sellerUri.toString());
			String sellerNameStr = qsCommon.configureAgentName(graphDiavgeiaII, sellerNameLit, sellerUri);
			
			String cpvGreekSubject = qsCommon.getCpvGreekSubject(graphDiavgeiaII, cpv);
			
			singleDecision = new String[] {buyerNameStr, sellerNameStr, date, totalAmount, ada, cpvGreekSubject};
			
			expApprList.add(singleDecision);
		}
		
		vqeDecision.close();
		
		return expApprList;
	}
	
	public ArrayList<String[]> getOteGroupTopAssignmentsQuery(VirtGraph graphDiavgeiaII, String jsonString) {
		
		Resource buyerUri = null;
		Resource sellerUri = null;
		Resource cpv = null;
		String ada = null;
		String date = null;
		String totalAmount = null;
		String[] singleDecision = null;
		ArrayList<String[]> decisionList = new ArrayList<String[]>();
		
		String queryDecision = "PREFIX elod: <http://linkedeconomy.org/ontology#> " +
					"PREFIX gr: <http://purl.org/goodrelations/v1#> " +
					"PREFIX dc: <http://purl.org/dc/elements/1.1/> " +
					"PREFIX org: <http://www.w3.org/ns/org#> " +
					"PREFIX dcterms: <http://purl.org/dc/terms/> " +
					"SELECT ?buyer ?seller ?date (xsd:decimal(?amount) AS ?paymentAmount) ?ada ?cpv " +
					"FROM <" + QueryConfiguration.queryGraphDiavgeiaII + "> " +
					"FROM <" + QueryConfiguration.queryGraphOrganizations + "> " +
					"WHERE { " +
					"{ " +
						"?contract pc:agreedPrice ?ups ; " +
						          "elod:ada ?ada ; " +
						          "elod:seller ?seller ; " +
						          "dcterms:issued ?date ; " +
						          "dc:publisher ?unit ; " +
						          "elod:decisionTypeId ?decisionTypeId . " +
						"?ups gr:hasCurrencyValue ?amount . " +
						"?buyer org:hasUnit ?unit ; " + 
							   "gr:vatID ?buyerAfm . " +
						"OPTIONAL { " +
							"?contract pc:mainObject ?cpv . " +
						"} . " +
						"FILTER NOT EXISTS {?contract elod:hasCorrectedDecision ?correctedDecision} . " +
						"FILTER NOT EXISTS {?ups elod:riskError \"true\"^^<http://www.w3.org/2001/XMLSchema#boolean>} . " +
						"FILTER (?date >= '" + QueryConfiguration.baseYear + "-01-01T00:00:00Z'^^xsd:dateTime) . " +
						"FILTER (?date < '" + (Integer.parseInt(QueryConfiguration.baseYear) + 1) + "-01-01T00:00:00Z'^^xsd:dateTime) . " +
						"FILTER (?decisionTypeId = 'Δ.1'^^xsd:string) . " +
						"FILTER (?seller=<http://linkedeconomy.org/resource/Organization/094019245> " +
						     "|| ?seller=<http://linkedeconomy.org/resource/Organization/094493766> " +
						     "|| ?seller=<http://linkedeconomy.org/resource/Organization/099760493> " +
						     "|| ?seller=<http://linkedeconomy.org/resource/Organization/094493000> " +
						     "|| ?seller=<http://linkedeconomy.org/resource/Organization/094222303> " +
						     "|| ?seller=<http://linkedeconomy.org/resource/Organization/094436540> " +
						     "|| ?seller=<http://linkedeconomy.org/resource/Organization/099760500> " +
						     "|| ?seller=<http://linkedeconomy.org/resource/Organization/094282975>) " +
					"} " +
					"UNION " +
					"{ " +
						"?contract pc:actualPrice ?ups ; " +
								  "elod:ada ?ada ; " +
								  "elod:decisionTypeId ?decisionTypeId ; " +
								  "dcterms:issued ?date ; " +
								  "elod:seller ?seller ; " +
						          "dc:publisher ?unit . " +
						"?ups gr:hasCurrencyValue ?amount . " +
						"?buyer org:hasUnit ?unit ; " + 
							   "gr:vatID ?buyerAfm . " +
						"OPTIONAL { " +
							"?contract pc:mainObject ?cpv . " +
						"} . " +
						"FILTER NOT EXISTS {?contract elod:hasCorrectedDecision ?correctedDecision} . " +
						"FILTER NOT EXISTS {?ups elod:riskError \"true\"^^<http://www.w3.org/2001/XMLSchema#boolean>} . " +
						"FILTER (?date >= '" + QueryConfiguration.baseYear + "-01-01T00:00:00Z'^^xsd:dateTime) . " +
						"FILTER (?date < '" + (Integer.parseInt(QueryConfiguration.baseYear) + 1) + "-01-01T00:00:00Z'^^xsd:dateTime) . " +
						"FILTER (?decisionTypeId = 'Δ.2.2'^^xsd:string) . " +
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
					"ORDER BY DESC (?paymentAmount) " +
					"LIMIT 1000";

		VirtuosoQueryExecution vqeDecision = VirtuosoQueryExecutionFactory.create(queryDecision, graphDiavgeiaII);
		ResultSet resultsDecision = vqeDecision.execSelect();
		
		while (resultsDecision.hasNext()) {
			QuerySolution result = resultsDecision.nextSolution();
			buyerUri = result.getResource("buyer");
			sellerUri = result.getResource("seller");
			ada = result.getLiteral("ada").getString();
			cpv = result.getResource("cpv");
			date = result.getLiteral("date").getString().split("T")[0];
			totalAmount = Utils.getInstance().formatNumber( result.getLiteral("paymentAmount").getString() );
			
			Literal buyerNameLit = qsCommon.getAgentLegalName(graphDiavgeiaII, buyerUri.toString());
			String buyerNameStr = qsCommon.configureAgentName(graphDiavgeiaII, buyerNameLit, buyerUri);
			
			Literal sellerNameLit = qsCommon.getAgentLegalName(graphDiavgeiaII, sellerUri.toString());
			String sellerNameStr = qsCommon.configureAgentName(graphDiavgeiaII, sellerNameLit, sellerUri);
			
			String cpvGreekSubject = qsCommon.getCpvGreekSubject(graphDiavgeiaII, cpv);
			
			singleDecision = new String[] {buyerNameStr, sellerNameStr, date, totalAmount, ada, cpvGreekSubject};
			
			decisionList.add(singleDecision);
		}
		
		vqeDecision.close();
		
		return decisionList;
	}
	
	public ArrayList<String[]> getOteGroupTopEspaQuery(VirtGraph graphDiavgeiaII, String jsonString) {
		
		String subject = null;
		String municName = null;
		String biAmount = null;
		String cntrAmount = null;
		String siAmount = null;
		String[] singleSubsidy = null;
		ArrayList<String[]> subsidyList = new ArrayList<String[]>();
		
		String querySubsidy = "PREFIX elod: <http://linkedeconomy.org/ontology#> " +
					"PREFIX gr: <http://purl.org/goodrelations/v1#> " +
					"PREFIX dc: <http://purl.org/dc/elements/1.1/> " +
					"PREFIX org: <http://www.w3.org/ns/org#> " +
					"PREFIX dcterms: <http://purl.org/dc/terms/> " +
					"SELECT ?subject ?municName (xsd:decimal(?biAmount) AS ?biAmount)  (xsd:decimal(?cntrAmount) AS ?cntrAmount) (xsd:decimal(?siAmount) AS ?siAmount) " +
					"FROM <" + QueryConfiguration.queryGraphEspa + "> " +
					"FROM <" + QueryConfiguration.queryGraphGeo + "> " +
					"WHERE { " +
					"?subsidy elod:hasRelatedBudgetItem ?budgetItem ; " +
					         "elod:hasRelatedContract ?contract ; " +
					         "dc:subject ?subject ; " +
					         "elod:projectStatus ?projectStatus ; " +
					         "elod:hasRelatedSpendingItem ?spendingItem ; " +
					         "elod:beneficiary ?beneficiary ; " +
					         "elod:subsidyMunicipality ?municipality .    " +      
					"?budgetItem elod:price ?biUps . " +
					"?biUps gr:hasCurrencyValue ?biAmount . " +
					"?contract pc:agreedPrice ?cntrUps . " +
					"?cntrUps gr:hasCurrencyValue ?cntrAmount. " +
					"?spendingItem elod:amount ?siUps . " +
					"?siUps gr:hasCurrencyValue ?siAmount. " +
					"?municipality elodGeo:name ?municName . " +
					"FILTER regex(?municName, \"@el\") . " +
					"?projectStatus skos:prefLabel ?statusLabel . " +
					"FILTER (LANG(?statusLabel) = \"en\") . " +
					"FILTER (?beneficiary=<http://linkedeconomy.org/resource/Organization/094019245> " +
					     "|| ?beneficiary=<http://linkedeconomy.org/resource/Organization/094493766> " +
					     "|| ?beneficiary=<http://linkedeconomy.org/resource/Organization/099760493> " +
					     "|| ?beneficiary=<http://linkedeconomy.org/resource/Organization/094493000> " +
					     "|| ?beneficiary=<http://linkedeconomy.org/resource/Organization/094222303> " +
					     "|| ?beneficiary=<http://linkedeconomy.org/resource/Organization/094436540> " +
					     "|| ?beneficiary=<http://linkedeconomy.org/resource/Organization/099760500> " +
					     "|| ?beneficiary=<http://linkedeconomy.org/resource/Organization/094282975>) " +
					"} " +
					"ORDER BY DESC (?biAmount) " +
					"LIMIT 1000";

		VirtuosoQueryExecution vqeSubsidy = VirtuosoQueryExecutionFactory.create(querySubsidy, graphDiavgeiaII);
		ResultSet resultsSubsidy = vqeSubsidy.execSelect();
		
		while (resultsSubsidy.hasNext()) {
			QuerySolution result = resultsSubsidy.nextSolution();
			subject = result.getLiteral("subject").getString();
			municName = result.getLiteral("municName").getString().split("@")[0];
			biAmount = Utils.getInstance().formatNumber( result.getLiteral("biAmount").getString() );
			cntrAmount = Utils.getInstance().formatNumber( result.getLiteral("cntrAmount").getString() );
			siAmount = Utils.getInstance().formatNumber( result.getLiteral("siAmount").getString() );
			
			singleSubsidy = new String[] {subject, municName, biAmount, cntrAmount, siAmount};
			
			subsidyList.add(singleSubsidy);
		}
		
		vqeSubsidy.close();
		
		return subsidyList;
	}
	
	public ArrayList<String[]> getOteGroupTopEuFtsQuery(VirtGraph graphEuFts, String jsonString) {
		
		String date = null;
		String buyerLegalName = null;
		String sellerLegalName = null;
		String description = null;
		String contractId = null;
		String amount = null;
		String[] singlePayment = null;
		ArrayList<String[]> paymentList = new ArrayList<String[]>();
		
		String queryPayment = "PREFIX elod: <http://linkedeconomy.org/ontology#> " +
					"PREFIX gr: <http://purl.org/goodrelations/v1#> " +
					"PREFIX org: <http://www.w3.org/ns/org#> " +
					"PREFIX dcterms: <http://purl.org/dc/terms/> " +
					"SELECT ?date (xsd:decimal(?amount) as ?paymentAmount) ?buyerLegalName ?sellerLegalName ?description ?contractId " +
					"FROM <" + QueryConfiguration.queryGraphEufts + "> " +
					"FROM <" + QueryConfiguration.queryGraphOrganizations + "> " +
					"WHERE { " +
					"{ " +
						"<http://linkedeconomy.org/resource/Organization/094019245> dbpedia-owl:subsidiary ?seller . " +
						"?spending elod:hasRelatedContract ?contract ;  " +
						          "elod:hasExpenditureLine ?expLine .  " +
						"?expLine elod:seller ?seller ;  " +
						         "elod:amount ?ups .  " +
						"?contract pc:item ?offering ; " +
						          "elod:contractId ?contractId ; " +
						          "elod:buyer ?buyer ;  " +
						          "dcterms:issued ?date .  " +
						"?ups gr:hasCurrencyValue ?amount .  " +
						"?offering gr:includesObject ?tqn . " +
						"?tqn gr:typeOfGood ?someItems .  " +
						"?someItems gr:description ?description . " +
						"?buyer gr:legalName ?buyerLegalName . " +
						"?seller gr:legalName ?sellerLegalName . " +
					"} " +
					"UNION " +
					"{ " +
						"?spending elod:hasRelatedContract ?contract ;  " +
						          "elod:hasExpenditureLine ?expLine .  " +
						"?expLine elod:seller ?seller ;  " +
						         "elod:amount ?ups .  " +
						"?contract pc:item ?offering ; " +
						          "elod:contractId ?contractId ; " +
						          "elod:buyer ?buyer ;  " +
						          "dcterms:issued ?date .  " +
						"?ups gr:hasCurrencyValue ?amount .  " +
						"?offering gr:includesObject ?tqn . " +
						"?tqn gr:typeOfGood ?someItems .  " +
						"?someItems gr:description ?description . " +
						"?buyer gr:legalName ?buyerLegalName . " +
						"?seller gr:legalName ?sellerLegalName . " +
						"FILTER (?seller = <http://linkedeconomy.org/resource/Organization/094019245>) " +
					"} " +
					"}";

		VirtuosoQueryExecution vqePayment = VirtuosoQueryExecutionFactory.create(queryPayment, graphEuFts);
		ResultSet resultsPayment = vqePayment.execSelect();
		
		while (resultsPayment.hasNext()) {
			QuerySolution result = resultsPayment.nextSolution();
			date = result.getLiteral("date").getString();
			buyerLegalName = result.getLiteral("buyerLegalName").getString();
			sellerLegalName = result.getLiteral("sellerLegalName").getString();
			description = result.getLiteral("description").getString();
			contractId = result.getLiteral("contractId").getString();
			amount = Utils.getInstance().formatNumber( result.getLiteral("paymentAmount").getString() );
			
			singlePayment = new String[] {date, buyerLegalName, sellerLegalName, description, contractId, amount};
			
			paymentList.add(singlePayment);
		}
		
		vqePayment.close();
		
		return paymentList;
	}
	
	/** OTE Group Yearly Payments **/
	public int getOteGroupMonthlyPaymentsQuery(VirtGraph graphDiavgeiaII, int currentMonth, int nextMonth) {
		
		int totalAmount = 0;
		
		String formattedCurrentMonth =  String.valueOf(currentMonth);
		String formattedNextMonth =  String.valueOf(nextMonth);
		
		String yearTo = QueryConfiguration.baseYear;
		
		if (currentMonth < 10) {
			formattedCurrentMonth = "0" + formattedCurrentMonth;
		}
		
		if (nextMonth < 10) {
			formattedNextMonth = "0" + nextMonth;
		} else if (nextMonth == 13) {
			yearTo = String.valueOf( (Integer.parseInt(QueryConfiguration.baseYear) + 1) );
			formattedNextMonth = "01";
		}
		
		String dateFilter = "FILTER (?date >= '" + QueryConfiguration.baseYear + "-" + formattedCurrentMonth + "-01T00:00:00Z'^^xsd:dateTime) . " +
				 			"FILTER (?date < '" + yearTo + "-" + formattedNextMonth + "-01T00:00:00Z'^^xsd:dateTime) . ";
		
		String queryOte = "PREFIX elod: <http://linkedeconomy.org/ontology#> " +
					"PREFIX dcterms: <http://purl.org/dc/terms/> " +
					"PREFIX pc: <http://purl.org/procurement/public-contracts#> " +
					"PREFIX gr: <http://purl.org/goodrelations/v1#> " +
					"PREFIX dbo: <http://dbpedia.org/ontology/> " +
					"SELECT (SUM(xsd:decimal(?amountOTE)) + SUM(xsd:decimal(?amountSubs)) AS ?totalAmount) " + 
					"FROM <" + QueryConfiguration.queryGraphDiavgeiaII + "> " +
					"FROM <" + QueryConfiguration.queryGraphOrganizations + "> " +
					"WHERE { " +
					"{ " +
						"?decision elod:hasExpenditureLine ?expLineOTE ; " +
						          "elod:decisionTypeId ?decisionTypeId ; " +
						          "dcterms:issued ?date . " +
						"?expLineOTE elod:amount ?ups ; " +
						            "elod:seller <http://linkedeconomy.org/resource/Organization/094019245> . " +
						"?ups gr:hasCurrencyValue ?amountOTE . " +
						dateFilter +
						"FILTER NOT EXISTS {?decision elod:hasCorrectedDecision ?correctedDecision} . " +
						"FILTER NOT EXISTS {?ups elod:riskError \"true\"^^<http://www.w3.org/2001/XMLSchema#boolean>} . " +
						"FILTER (?decisionTypeId = 'Β.2.1'^^xsd:string || ?decisionTypeId = 'Β.2.2'^^xsd:string ) . " +
					"} " +
					"UNION " +
					"{ " +
						"<http://linkedeconomy.org/resource/Organization/094019245> dbo:subsidiary ?subsidiary . " +
						"?decision elod:hasExpenditureLine ?expLineSubs ; " +
						          "elod:decisionTypeId ?decisionTypeId ; " +
						          "dcterms:issued ?date . " +
						"?expLineSubs elod:amount ?ups ; " +
						             "elod:seller ?subsidiary . " +
						"?ups gr:hasCurrencyValue ?amountSubs . " +
						dateFilter +
						"FILTER NOT EXISTS {?decision elod:hasCorrectedDecision ?correctedDecision} . " +
						"FILTER NOT EXISTS {?ups elod:riskError \"true\"^^<http://www.w3.org/2001/XMLSchema#boolean>} . " +
						"FILTER (?decisionTypeId = 'Β.2.1'^^xsd:string || ?decisionTypeId = 'Β.2.2'^^xsd:string ) . " +
					"} " +
					"}";

		VirtuosoQueryExecution vqeOte = VirtuosoQueryExecutionFactory.create(queryOte, graphDiavgeiaII);
		ResultSet resultsOte = vqeOte.execSelect();
		
		if (resultsOte.hasNext()) {
			QuerySolution result = resultsOte.nextSolution();
			totalAmount = result.getLiteral("totalAmount").getInt();
		}
		
		vqeOte.close();
		
		return totalAmount;
	}
	
}