package common;

import java.util.ArrayList;

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
public class QueriesOracle {

	QueriesCommon qsCommon = new QueriesCommon();

	public ArrayList<String[]> getOraclePaymentsEuftsQuery(VirtGraph graphEufts, String jsonString) {

		Resource buyerUri = null;
		Resource sellerUri = null;
		String description = null;
		String date = null;
		String totalAmount = null;
		String[] singlePayment = null;
		ArrayList<String[]> euftsList = new ArrayList<String[]>();

		String queryPayment = "PREFIX elod: <http://linkedeconomy.org/ontology#> "
				+ "PREFIX pc: <http://purl.org/procurement/public-contracts#> "
				+ "SELECT ?contract (xsd:decimal(?amount) as ?amount) ?buyer ?seller (str(?description) as ?description) (str(?date) as ?date) "
				+ "FROM <" + QueryConfiguration.queryGraphEufts + "> " + "WHERE { "
				+ "?spending elod:hasRelatedContract ?contract ; elod:hasExpenditureLine ?expLine . "
				+ "?expLine elod:seller ?seller ; " + "elod:amount ?ups . " + "?contract pc:item ?offering ; "
				+ "elod:buyer ?buyer ; dcterms:issued ?date . " + "?ups gr:hasCurrencyValue ?amount . "
				+ "?offering gr:includesObject ?tqn ." + "?tqn gr:typeOfGood ?someItems . "
				+ "?someItems gr:description ?description "
				+ "FILTER (?seller=<http://linkedeconomy.org/resource/Organization/ORACLE_BELGIUM_BVBA> "
				+ "|| ?seller=<http://linkedeconomy.org/resource/Organization/ORACLE_NEDERLAND_BV> "
				+ "|| ?seller=<http://linkedeconomy.org/resource/Organization/ORACLE_ITALIA_SRL> "
				+ "|| ?seller=<http://linkedeconomy.org/resource/Organization/ORACLE_IBERICA_SRL>) " + "} "
				+ "ORDER BY DESC (?amount) offset 20 limit 45";

		VirtuosoQueryExecution vqePayment = VirtuosoQueryExecutionFactory.create(queryPayment, graphEufts);
		ResultSet resultsDecision = vqePayment.execSelect();
		int i = 0;
		while (resultsDecision.hasNext()) {
			System.out.println("Data iteration: " + i);
			i++;
			QuerySolution result = resultsDecision.nextSolution();
			buyerUri = result.getResource("buyer");
			sellerUri = result.getResource("seller");
			description = result.getLiteral("description").getString();
			date = result.getLiteral("date").getString().split("\\+")[0];
			totalAmount = Utils.getInstance().formatNumber(result.getLiteral("amount").getString());

			Literal buyerNameLit = qsCommon.getBuyerNameEufts(graphEufts, buyerUri.toString());
			Literal sellerNameLit = qsCommon.getSellerNameEufts(graphEufts, sellerUri.toString());

			singlePayment = new String[] { buyerNameLit.getString(), sellerNameLit.getString(), description, date,
					totalAmount };

			euftsList.add(singlePayment);
		}

		vqePayment.close();

		return euftsList;
	}

	public ArrayList<String[]> getOraclePaymentDiavgeiaQuery(VirtGraph graphDiavgeiaII, String jsonString) {

		Resource buyerUri = null;
		Resource sellerUri = null;
		Resource cpv = null;
		String ada = null;
		String date = null;
		String totalAmount = null;
		String[] singlePayment = null;
		ArrayList<String[]> diavgeiaList = new ArrayList<String[]>();

		String queryPayment = "PREFIX elod: <http://linkedeconomy.org/ontology#> "
				+ "PREFIX gr: <http://purl.org/goodrelations/v1#> " + "PREFIX dc: <http://purl.org/dc/elements/1.1/> "
				+ "PREFIX org: <http://www.w3.org/ns/org#> " + "PREFIX dcterms: <http://purl.org/dc/terms/> "
				+ "SELECT ?buyer ?date (xsd:decimal(?amount) AS ?paymentAmount) ?seller ?ada ?cpv " + "FROM <"
				+ QueryConfiguration.queryGraphDiavgeiaII + "> " + "FROM <" + QueryConfiguration.queryGraphOrganizations
				+ "> " + "WHERE { " + "?expenseApproval elod:hasExpenditureLine ?expenditureLine ; "
				+ "elod:ada ?ada ; " + "dcterms:issued ?date ; elod:decisionTypeId ?decisionTypeId ; "
				+ "dc:publisher ?unit . " + "?expenditureLine elod:seller ?seller ; " + "elod:amount ?ups . "
				+ "?ups gr:hasCurrencyValue ?amount . " + "?buyer org:hasUnit ?unit ; " + "gr:vatID ?buyerAfm . "
				+ "OPTIONAL { " + "?expenditureLine elod:hasCpv ?cpv . " + "} . "
				+ "FILTER NOT EXISTS {?expenseApproval elod:hasCorrectedDecision ?correctedDecision} . "
				+ "FILTER NOT EXISTS {?ups elod:riskError \"true\"^^<http://www.w3.org/2001/XMLSchema#boolean>} . "
				+ "FILTER ((?decisionTypeId = \"Β.2.1\"^^xsd:string) || (?decisionTypeId = \"Β.2.2\"^^xsd:string))"
				+ "FILTER ((?seller = <http://linkedeconomy.org/resource/Organization/800420948>)" 
				+ "|| (?seller = <http://linkedeconomy.org/resource/Organization/094253457>))" 
				+ "}"
				+ "ORDER BY DESC (?paymentAmount) ";

		VirtuosoQueryExecution vqePayment = VirtuosoQueryExecutionFactory.create(queryPayment, graphDiavgeiaII);
		ResultSet resultsDecision = vqePayment.execSelect();

		while (resultsDecision.hasNext()) {
			QuerySolution result = resultsDecision.nextSolution();
			buyerUri = result.getResource("buyer");
			sellerUri = result.getResource("seller");
			ada = result.getLiteral("ada").getString();
			cpv = result.getResource("cpv");
			date = result.getLiteral("date").getString().split("T")[0];
			totalAmount = Utils.getInstance().formatNumber(result.getLiteral("paymentAmount").getString());

			Literal buyerNameLit = qsCommon.getAgentLegalName(graphDiavgeiaII, buyerUri.toString());
			String buyerNameStr = qsCommon.configureAgentName(graphDiavgeiaII, buyerNameLit, buyerUri);

			Literal sellerNameLit = qsCommon.getAgentLegalName(graphDiavgeiaII, sellerUri.toString());
			String sellerNameStr = qsCommon.configureAgentName(graphDiavgeiaII, sellerNameLit, sellerUri);

			String cpvGreekSubject = qsCommon.getCpvGreekSubject(graphDiavgeiaII, cpv);

			singlePayment = new String[] { buyerNameStr, sellerNameStr, date, totalAmount, ada, cpvGreekSubject };

			diavgeiaList.add(singlePayment);
		}

		vqePayment.close();

		return diavgeiaList;
	}

	public ArrayList<String[]> getOraclePaymentsAustraliaQuery(VirtGraph graphAustralia, String jsonString) {

		Resource buyerUri = null;
		Resource sellerUri = null;
		Literal description = null;
		String date = null;
		String totalAmount = null;
		String[] singlePayment = null;
		ArrayList<String[]> euftsList = new ArrayList<String[]>();

		String queryPayment = "PREFIX elod: <http://linkedeconomy.org/ontology#> "
				+ "PREFIX pc: <http://purl.org/procurement/public-contracts#> "
				+ "SELECT ?contract (xsd:decimal(?amount) as ?amount) ?buyer ?seller (str(?description) as ?description) (str(?date) as ?date) "
				+ "FROM <" + QueryConfiguration.queryGraphAustralia + "> " + "WHERE { "
				+ "?spending elod:hasRelatedContract ?contract ; elod:hasExpenditureLine ?expLine . "
				+ "?expLine elod:seller ?seller ; " + "elod:amount ?ups . " + "?contract pc:item ?offering ; "
				+ "elod:buyer ?buyer ; dcterms:issued ?date . " + "?ups gr:hasCurrencyValue ?amount . "
				+ "?offering gr:includesObject ?tqn ." + "?tqn gr:typeOfGood ?someItems . "
				+ "?someItems gr:description ?description "
				+ "FILTER (?seller=<http://linkedeconomy.org/resource/Organization/71474078909> "
				+ "|| ?seller=<http://linkedeconomy.org/resource/Organization/ORACLE_CORPORATION_AUSTRALIA_PTY_LTD> "
				+ "|| ?seller=<http://linkedeconomy.org/resource/Organization/77310752950> "
				+ "|| ?seller=<http://linkedeconomy.org/resource/Organization/ORACLE_AUSTRALIA> "
				+ "|| ?seller=<http://linkedeconomy.org/resource/Organization/87003145337> "
				+ "|| ?seller=<http://linkedeconomy.org/resource/Organization/16957131926> "
				+ "|| ?seller=<http://linkedeconomy.org/resource/Organization/19063655774> "
				+ "|| ?seller=<http://linkedeconomy.org/resource/Organization/80003074468> "
				+ "|| ?seller=<http://linkedeconomy.org/resource/Organization/ORACLE_CORPORATION_AUSTRALIA_P> "
				+ "|| ?seller=<http://linkedeconomy.org/resource/Organization/86768265615>) " + "} "
				+ "ORDER BY DESC (?amount)";

		VirtuosoQueryExecution vqePayment = VirtuosoQueryExecutionFactory.create(queryPayment, graphAustralia);
		ResultSet resultsDecision = vqePayment.execSelect();

		while (resultsDecision.hasNext()) {
			QuerySolution result = resultsDecision.nextSolution();
			buyerUri = result.getResource("buyer");
			sellerUri = result.getResource("seller");
			description = result.getLiteral("description");
			date = result.getLiteral("date").getString().split("\\+")[0];
			totalAmount = Utils.getInstance().formatNumber(result.getLiteral("amount").getString());

			Literal buyerNameLit = qsCommon.getBuyerNameAustralia(graphAustralia, buyerUri.toString());
			Literal sellerNameLit = qsCommon.getSellerNameAustralia(graphAustralia, sellerUri.toString());

			singlePayment = new String[] { buyerNameLit.getString(), sellerNameLit.getString(), description.getString(),
					date, totalAmount };

			euftsList.add(singlePayment);
		}

		vqePayment.close();

		return euftsList;
	}

	/** Queries for: Sums and Counters **/
	public String[] getOraclePaymentDtlsQuery(VirtGraph graphDiavgeiaII, boolean currentYear) {

		String totalExpLines = null;
		String totalAmount = null;

		String dateFilter = null;
		String graphFilter = null;

		if (currentYear) {
			graphFilter = "FROM <" + QueryConfiguration.queryGraphDiavgeiaII + "> ";
			dateFilter = "FILTER (?date >= '" + QueryConfiguration.baseYear + "-01-01T00:00:00Z'^^xsd:dateTime) . "
					+ "FILTER (?date < '" + (Integer.parseInt(QueryConfiguration.baseYear) + 1)
					+ "-01-01T00:00:00Z'^^xsd:dateTime) . ";
		} else {
			graphFilter = "FROM <http://linkedeconomy.org/DiavgeiaII> ";
			dateFilter = "FILTER (?date >= '" + (Integer.parseInt(QueryConfiguration.baseYear) - 1)
					+ "-01-01T00:00:00Z'^^xsd:dateTime) . " + "FILTER (?date < '" + QueryConfiguration.baseYear
					+ "-01-01T00:00:00Z'^^xsd:dateTime) . ";
		}

		String queryOracle = "PREFIX elod: <http://linkedeconomy.org/ontology#> "
				+ "PREFIX dcterms: <http://purl.org/dc/terms/> "
				+ "PREFIX pc: <http://purl.org/procurement/public-contracts#> "
				+ "PREFIX gr: <http://purl.org/goodrelations/v1#> " 
				+ "PREFIX dbo: <http://dbpedia.org/ontology/> "
				+ "SELECT (COUNT(DISTINCT ?expLine) AS ?totalExpLines) "
				+ "(SUM(xsd:decimal(?amount)) AS ?totalAmount) " 
				+ graphFilter
				+ "FROM <" + QueryConfiguration.queryGraphOrganizations + "> " 
				+ "WHERE { " 
				+ "?decision elod:hasExpenditureLine ?expLine ; " + "elod:decisionTypeId ?decisionTypeId ; "
				+ "dcterms:issued ?date . " + "?expLine elod:amount ?ups ; "
				+ "elod:seller ?seller . "
				+ "?ups gr:hasCurrencyValue ?amount . " 
				+ dateFilter
				+ "FILTER NOT EXISTS {?decision elod:hasCorrectedDecision ?correctedDecision} . "
				+ "FILTER NOT EXISTS {?ups elod:riskError \"true\"^^<http://www.w3.org/2001/XMLSchema#boolean>} . "
				+ "FILTER (?decisionTypeId = 'Β.2.1'^^xsd:string || ?decisionTypeId = 'Β.2.2'^^xsd:string ) . "
				+ "FILTER ((?seller = <http://linkedeconomy.org/resource/Organization/800420948>) || "
				+ "(?seller = <http://linkedeconomy.org/resource/Organization/094253457>))"
				+ "}";

		VirtuosoQueryExecution vqeOracle = VirtuosoQueryExecutionFactory.create(queryOracle, graphDiavgeiaII);
		ResultSet resultsOracle = vqeOracle.execSelect();

		if (resultsOracle.hasNext()) {
			QuerySolution result = resultsOracle.nextSolution();
			totalExpLines = Utils.getInstance().formatNumber(result.getLiteral("totalExpLines").getString());
			totalAmount = Utils.getInstance().formatNumber((result.getLiteral("totalAmount")).getString());
		}

		vqeOracle.close();

		return new String[] { totalExpLines, totalAmount };
	}
	
	public String[] getOraclePaymentAusDtlsQuery(VirtGraph graphAustralia) {

		String totalExpLines = null;
		String totalAmount = null;

		String queryOracle = "PREFIX elod: <http://linkedeconomy.org/ontology#> "
				+ "PREFIX pc: <http://purl.org/procurement/public-contracts#> "
				+ "SELECT (count(distinct ?spending) as ?totalExpLines) (sum(xsd:decimal(?amount)) as ?totalAmount) "
				+ "FROM <" + QueryConfiguration.queryGraphAustralia + "> " + "WHERE { "
				+ "?spending elod:hasRelatedContract ?contract ; elod:hasExpenditureLine ?expLine . "
				+ "?expLine elod:seller ?seller ; " + "elod:amount ?ups . " + "?contract pc:item ?offering ; "
				+ "elod:buyer ?buyer ; dcterms:issued ?date . " + "?ups gr:hasCurrencyValue ?amount . "
				+ "?offering gr:includesObject ?tqn ." + "?tqn gr:typeOfGood ?someItems . "
				+ "?someItems gr:description ?description "
				+ "FILTER (?seller=<http://linkedeconomy.org/resource/Organization/71474078909> "
				+ "|| ?seller=<http://linkedeconomy.org/resource/Organization/ORACLE_CORPORATION_AUSTRALIA_PTY_LTD> "
				+ "|| ?seller=<http://linkedeconomy.org/resource/Organization/77310752950> "
				+ "|| ?seller=<http://linkedeconomy.org/resource/Organization/ORACLE_AUSTRALIA> "
				+ "|| ?seller=<http://linkedeconomy.org/resource/Organization/87003145337> "
				+ "|| ?seller=<http://linkedeconomy.org/resource/Organization/16957131926> "
				+ "|| ?seller=<http://linkedeconomy.org/resource/Organization/19063655774> "
				+ "|| ?seller=<http://linkedeconomy.org/resource/Organization/80003074468> "
				+ "|| ?seller=<http://linkedeconomy.org/resource/Organization/ORACLE_CORPORATION_AUSTRALIA_P> "
				+ "|| ?seller=<http://linkedeconomy.org/resource/Organization/86768265615>) " + "} ";

		VirtuosoQueryExecution vqeOracle = VirtuosoQueryExecutionFactory.create(queryOracle, graphAustralia);
		ResultSet resultsOracle = vqeOracle.execSelect();

		if (resultsOracle.hasNext()) {
			QuerySolution result = resultsOracle.nextSolution();
			totalExpLines = Utils.getInstance().formatNumber(result.getLiteral("totalExpLines").getString());
			totalAmount = Utils.getInstance().formatNumber((result.getLiteral("totalAmount")).getString());
		}

		vqeOracle.close();

		return new String[] { totalExpLines, totalAmount };
	}
	
	public String[] getOraclePaymentEuftsDtlsQuery(VirtGraph graphEufts) {

		String totalExpLines = null;
		String totalAmount = null;

		String queryOracle = "PREFIX elod: <http://linkedeconomy.org/ontology#> "
				+ "PREFIX pc: <http://purl.org/procurement/public-contracts#> "
				+ "SELECT (count(distinct ?spending) as ?totalExpLines) (sum(xsd:decimal(?amount)) as ?totalAmount) "
				+ "FROM <" + QueryConfiguration.queryGraphEufts + "> " + "WHERE { "
				+ "?spending elod:hasRelatedContract ?contract ; elod:hasExpenditureLine ?expLine . "
				+ "?expLine elod:seller ?seller ; " + "elod:amount ?ups . " + "?contract pc:item ?offering ; "
				+ "elod:buyer ?buyer ; dcterms:issued ?date . " + "?ups gr:hasCurrencyValue ?amount . "
				+ "?offering gr:includesObject ?tqn ." + "?tqn gr:typeOfGood ?someItems . "
				+ "?someItems gr:description ?description "
				+ "FILTER (?seller=<http://linkedeconomy.org/resource/Organization/ORACLE_BELGIUM_BVBA> "
				+ "|| ?seller=<http://linkedeconomy.org/resource/Organization/ORACLE_NEDERLAND_BV> "
				+ "|| ?seller=<http://linkedeconomy.org/resource/Organization/ORACLE_ITALIA_SRL> "
				+ "|| ?seller=<http://linkedeconomy.org/resource/Organization/ORACLE_IBERICA_SRL>) " + "} ";

		VirtuosoQueryExecution vqeOracle = VirtuosoQueryExecutionFactory.create(queryOracle, graphEufts);
		ResultSet resultsOracle = vqeOracle.execSelect();

		if (resultsOracle.hasNext()) {
			QuerySolution result = resultsOracle.nextSolution();
			totalExpLines = Utils.getInstance().formatNumber(result.getLiteral("totalExpLines").getString());
			totalAmount = Utils.getInstance().formatNumber((result.getLiteral("totalAmount")).getString());
		}

		vqeOracle.close();

		return new String[] { totalExpLines, totalAmount };
	}

}