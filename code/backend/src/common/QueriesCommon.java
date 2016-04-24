package common;

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
public class QueriesCommon {
	
	/** Names **/
	public Literal getAgentLegalName(VirtGraph graphOrganizations, String buyerUri) {
		
		Literal orgName = null;
		
		if (checkUri(buyerUri)) {
			String queryName = "PREFIX gr: <http://purl.org/goodrelations/v1#> " +
			  		  "SELECT ?legalName " +
			  		  "FROM <" + QueryConfiguration.queryGraphOrganizations + "> " +
			  		  "WHERE { " +
			  		  "<" + buyerUri +"> gr:legalName ?legalName . " +
			  		  "}";
	
			VirtuosoQueryExecution vqeName = VirtuosoQueryExecutionFactory.create(queryName, graphOrganizations);		
			ResultSet resultsName = vqeName.execSelect();
			
			while (resultsName.hasNext()) {
				QuerySolution result = resultsName.nextSolution();
				orgName = result.getLiteral("legalName");
			}
			
			vqeName.close();
		}
		
		return orgName;
	}
	
	public String configureAgentName(VirtGraph graphOrganizations, Literal buyerlegalName, Resource orgUri) {
		
		String buyerNameStr = "";
		
		if (buyerlegalName != null) { //Legal Name
			buyerNameStr = Utils.getInstance().cleanInvalidCharsJsonData(buyerlegalName.getString());
		} else { //Name
			Literal buyerName = getBuyerName(graphOrganizations, orgUri.getURI());
			if (buyerName != null) {
				buyerNameStr = Utils.getInstance().cleanInvalidCharsJsonData(buyerName.getString());
			} else {
				buyerNameStr = "Name not found";
			}
		}
		
		return buyerNameStr.trim();
	}
	
	private Literal getBuyerName(VirtGraph graphOrganizations, String buyerUri) {
		
		Literal orgName = null;
		
		if (checkUri(buyerUri)) {
			String queryName = "PREFIX gr: <http://purl.org/goodrelations/v1#> " +
			  		  "SELECT ?name " +
			  		  "FROM <" + QueryConfiguration.queryGraphOrganizations + "> " +
			  		  "WHERE { " +
			  		  "<" + buyerUri +"> gr:name ?name . " +
			  		  "}";
	
			VirtuosoQueryExecution vqeName = VirtuosoQueryExecutionFactory.create(queryName, graphOrganizations);		
			ResultSet resultsName = vqeName.execSelect();
			
			while (resultsName.hasNext()) {
				QuerySolution result = resultsName.nextSolution();
				orgName = result.getLiteral("name");
			}
			
			vqeName.close();
		}
		
		return orgName;
	}
	
	public Literal getBuyerNameAustralia(VirtGraph graphAustralia, String buyerUri) {

		Literal buyerName = null;

		if (checkUri(buyerUri)) {
			String queryName = "PREFIX gr: <http://purl.org/goodrelations/v1#> " + "SELECT ?legalName " + "FROM <"
					+ QueryConfiguration.queryGraphAustralia + "> " + "WHERE { " + "<" + buyerUri
					+ "> elod:hasSupervisorOrganization ?superUri . " + "?superUri gr:legalName ?legalName . " + "}";

			VirtuosoQueryExecution vqeName = VirtuosoQueryExecutionFactory.create(queryName, graphAustralia);
			ResultSet resultsName = vqeName.execSelect();

			while (resultsName.hasNext()) {
				QuerySolution result = resultsName.nextSolution();
				buyerName = result.getLiteral("legalName");
			}

			vqeName.close();
		}

		return buyerName;
	}
	
	public Literal getSellerNameAustralia(VirtGraph graphAustralia, String sellerUri) {

		Literal sellerName = null;

		if (checkUri(sellerUri)) {
			String queryName = "SELECT (SAMPLE(?name) AS ?name) " 
					+ "FROM <"
					+ QueryConfiguration.queryGraphAustralia + "> "
					+ "WHERE {" + "<" + sellerUri+ "> "
					+ "gr:name ?name ." + "}";

			VirtuosoQueryExecution vqeName = VirtuosoQueryExecutionFactory.create(queryName, graphAustralia);
			ResultSet resultsName = vqeName.execSelect();

			while (resultsName.hasNext()) {
				QuerySolution result = resultsName.nextSolution();
				sellerName = result.getLiteral("name");
			}

			vqeName.close();
		}

		return sellerName;
	}
	
	public Literal getSellerNameEufts(VirtGraph graphEufts, String orgUri) {

		Literal orgName = null;

		if (checkUri(orgUri)) {
			String queryName = "PREFIX gr: <http://purl.org/goodrelations/v1#> "
					+ "SELECT (SAMPLE(?name) AS ?name) "
					+ "FROM <" + QueryConfiguration.queryGraphEufts + "> " 
					+ "WHERE { "
					+ "<" + orgUri + "> gr:name ?name . " + "}";

			VirtuosoQueryExecution vqeName = VirtuosoQueryExecutionFactory.create(queryName, graphEufts);
			ResultSet resultsName = vqeName.execSelect();

			while (resultsName.hasNext()) {
				QuerySolution result = resultsName.nextSolution();
				orgName = result.getLiteral("name");
			}

			vqeName.close();
		}

		return orgName;
	}
	
	public Literal getBuyerNameEufts(VirtGraph graphEufts, String orgUri) {

		Literal orgName = null;

		if (checkUri(orgUri)) {
			String queryName = "PREFIX gr: <http://purl.org/goodrelations/v1#> "
					+ "SELECT ?name " + "WHERE { "
					+ "<" + orgUri + "> gr:legalName ?name . " + "}";

			VirtuosoQueryExecution vqeName = VirtuosoQueryExecutionFactory.create(queryName, graphEufts);
			ResultSet resultsName = vqeName.execSelect();

			while (resultsName.hasNext()) {
				QuerySolution result = resultsName.nextSolution();
				orgName = result.getLiteral("name");
			}

			vqeName.close();
		}
		
		return orgName;
	}
	
	private boolean checkUri(String uri) {
		
		boolean correctUriFlag = true;
		
		if (uri.equalsIgnoreCase("http://linkedeconomy.org/resource/Organization/")) {
			correctUriFlag = false;
		} else if (uri.equalsIgnoreCase("http://linkedeconomy.org/resource/OrganizationalUnit/")) {
			correctUriFlag = false;
		} else if (uri.equalsIgnoreCase("http://linkedeconomy.org/resource/Person/")) {
			correctUriFlag = false;
		} else if ( uri.equalsIgnoreCase("http://linkedeconomy.org/resource/Organization/..") || 
				    uri.equalsIgnoreCase("http://linkedeconomy.org/resource/Organization/.") ) {
			correctUriFlag = false;
		}
		
		if ( uri.contains(" ") || uri.contains("`") || uri.contains("\"") ) {
			correctUriFlag = false;
		}
		
		return correctUriFlag;
	}

	/** CPV **/
	public String getCpvGreekSubject(VirtGraph graphCpv, Resource cpvUri) {
		
		String cpvGreekSubject = null;
		
		if (cpvUri != null) {
			if (!cpvUri.getURI().trim().equalsIgnoreCase("http://linkedeconomy.org/resource/CPV/")) {
				
				String queryCpv = "PREFIX elod: <http://linkedeconomy.org/ontology#>  " +
				  		  "SELECT ?cpvGreekSubject  " +
				  		  "FROM <" + QueryConfiguration.queryGraphCpv + "> " +
				  		  "WHERE { " +
				  		  "<" + cpvUri.getURI().trim() +"> elod:cpvGreekSubject ?cpvGreekSubject . " +
				  		  "}";
		
				VirtuosoQueryExecution vqeCpvDesc = VirtuosoQueryExecutionFactory.create(queryCpv, graphCpv);		
				ResultSet resultsCpvDesc = vqeCpvDesc.execSelect();
				
				if (resultsCpvDesc.hasNext()) {
					QuerySolution result = resultsCpvDesc.nextSolution();
					cpvGreekSubject = Utils.getInstance().cleanInvalidCharsJsonData(result.getLiteral("cpvGreekSubject").getString());
				}
				
				vqeCpvDesc.close();
			} else { 
				cpvGreekSubject = "-";
			}
		} else { 
			cpvGreekSubject = "-";
		}
		
		return cpvGreekSubject;
	}
	
}