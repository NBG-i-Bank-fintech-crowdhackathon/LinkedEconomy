package fek;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;

import java.util.ArrayList;

import com.hp.hpl.jena.query.QuerySolution;
import com.hp.hpl.jena.query.ResultSet;
import com.hp.hpl.jena.rdf.model.Literal;

import common.QueryConfiguration;

import virtuoso.jena.driver.VirtGraph;
import virtuoso.jena.driver.VirtuosoQueryExecution;
import virtuoso.jena.driver.VirtuosoQueryExecutionFactory;

/**
 * @author G. Razis
 * @author G. Vafeiadis
 * @author E. Galanos
 */
public class FekJson {

	private static JsonInitialization ji = new JsonInitialization();
	
	/**
	 * Creates the fek json file for the given company vatId
	 * @param graphFek
	 * @param graphOrganization
	 * @throws IOException
	 */
	public void FekStatsPage(VirtGraph graphFek, VirtGraph graphOrganization) throws IOException {
		
		// vatId list of the OTE company
		String[] vatIdList = new String[]{"094019245", "094222303" , "094493766" , "094436540" ,  
										  "094282975" , "094493000" , "099760493"};
		
		String jsonString = null;
		
		String filePath = QueryConfiguration.jsonFilepath;		// Output file path
		
		FileOutputStream fos = new FileOutputStream(filePath,false);
	
		// jsonString start
		jsonString = ji.openCurlyBracket() + ji.getJsonName("FekDtls") +
					 ji.openBracket() + ji.openCurlyBracket();
			
		for (int i=0; i<vatIdList.length; i++){
			
			ArrayList<String[]> fekList = new ArrayList<String[]>();
			fekList = fekDetails(graphFek, graphOrganization, vatIdList[i]);					// get fek details
			
			for (int j=0; j<fekList.size(); j++){
				
				if (fekList.get(j)[0].equalsIgnoreCase("ʼλλες Εταιρικές Αποφάσεις")){			// change the incorrect fek subject
					fekList.get(j)[0] = "Ανακοινώσεις";
				}
				
					String name = companyName(vatIdList[i]);									// get the company name by the vatId
				
					// Write the company Name
					jsonString += ji.getJsonName("company") + ji.getJsonValue(name) + ji.getComma();
				
					// Write the fek details
					jsonString += ji.getJsonName("fekSubject")
							+ji.getJsonValue(fekList.get(j)[0])
							+ji.getComma()
							+ji.getJsonName("fekNumber")
							+ji.getJsonValue(fekList.get(j)[1])
							+ji.getComma()
							+ji.getJsonName("fekUrl")
							+ji.getJsonValue(fekList.get(j)[2])
							+ji.getComma()
							+ji.getJsonName("fekPublDate")
							+ji.getJsonValue(dateConverter(fekList.get(j)[3]));
				
					// check if we have more companies for the json
					if (i<vatIdList.length-1){
						jsonString += ji.closeCurlyBracket() + ji.getComma() + ji.openCurlyBracket();
					}
					
				}
			}
			
			// jsonString Close
			jsonString += ji.closeCurlyBracket() + ji.closeBracket() + ji.closeCurlyBracket();
			
			// Write the file to the output file
			try {
				OutputStreamWriter osw = new OutputStreamWriter(fos, "UTF-8");
				osw.write(jsonString);
				osw.close();
			}
			catch(Exception e) {
				e.printStackTrace();
			}
								
			fos.close();			
		}
	
	/**
	 * 
	 * @param graphFek
	 * @param graphOrganization
	 * @param organization
	 * @return String[]{subjectOfFek,fekNumber,documentUrl,date}
	 * @throws IOException
	 */
	public ArrayList<String[]> fekDetails(VirtGraph graphFek, VirtGraph graphOrganization, String organization) throws IOException {
			
		ArrayList<String[]> fekList = new ArrayList<String[]>();
				
		String subjectOfFek = null;
		String fekNumber = null;
		String documentUrl = null;
		String date = null;
		
		String query =  "PREFIX elod:<http://linkedeconomy.org/ontology#>\n"+
						"PREFIX dcterms:<http://purl.org/dc/terms#>\n"+
						"PREFIX gr:<http://purl.org/goodrelations/v1#>\n"+
						"PREFIX rov:<http://www.w3.org/ns/regorg#>\n"+
						"PREFIX skos:<http://www.w3.org/2004/02/skos/core#>\n" +
						"SELECT DISTINCT ?typeOfFek ?subjectOfFek ?originalDate ?fekNumber\n"+
								"?organization\n"+
						"FROM <" + QueryConfiguration.queryGraphFek + "> \n" +
						"WHERE {\n"+
						"?announcement elod:isAnnouncementOf ?organization;\n" +
				                       "dcterms:subject ?subject;\n" +
				                       "elod:relatedFek ?fek;\n"+
				                       "dcterms:modified ?date.\n"+
				        "?subject skos:prefLabel ?subjectOfFek.\n" +
				        "?fek dcterms:issued ?originalDate;\n" +
				        	  "elod:fekType ?fekType;\n" +
				        	  "elod:fekNumber ?fekNumber.\n" +
				        	   "?fekType skos:prefLabel ?typeOfFek.\n" +
				        "FILTER(LANGMATCHES(LANG(?subjectOfFek), \"el\"))\n" +
				        "FILTER (CONTAINS(str(?organization), \""+ organization +"\")).\n"+
				        "}\n"+
				        "ORDER BY DESC (?originalDate)\n"+
				        "LIMIT 1";

						
		VirtuosoQueryExecution vqe = VirtuosoQueryExecutionFactory.create(query, graphFek);		
		ResultSet results = vqe.execSelect();
		
		while (results.hasNext()) {
				QuerySolution result = results.nextSolution();
				subjectOfFek = cleanLiteral(result.getLiteral("subjectOfFek"));
				fekNumber = cleanLiteral(result.getLiteral("fekNumber"));
		    	documentUrl = getDocumentUrl(graphFek, fekNumber);
		    	date = cleanLiteral(result.getLiteral("originalDate"));
		    	
				String[] fekDtls = new String[]{subjectOfFek,fekNumber,documentUrl,date};
				fekList.add(fekDtls);
			}
				
		vqe.close();
		
	    return fekList;
	}
	
	/**
	 * 
	 * @param graphFek
	 * @param fekNumber
	 * @return document url
	 */
	public String getDocumentUrl(VirtGraph graphFek, String fekNumber) {
		
		String documentUrl = null;
		
		String queryUrl = "PREFIX elod:<http://linkedeconomy.org/ontology#>\n"+
						  "SELECT (STR(?documentUrl) AS ?documentUrl)\n"+
						  "FROM <" + QueryConfiguration.queryGraphFek + "> \n" +
						  "WHERE {\n"+
						  "?fek elod:documentUrl ?documentUrl;\n" +
						  		"elod:fekNumber \""+fekNumber+"\"^^xsd:string.\n"+
						  "}\n"+
						  "LIMIT 2";

		VirtuosoQueryExecution vqeUrl = VirtuosoQueryExecutionFactory.create(queryUrl, graphFek);		
		ResultSet resultsUrl = vqeUrl.execSelect();
		
		while (resultsUrl.hasNext()) {
			QuerySolution result = resultsUrl.nextSolution();
			documentUrl = result.getLiteral("documentUrl").toString();
		}
		
		vqeUrl.close();
		
		return documentUrl;
	}
	
	/**
	 * 
	 * @param organization afm
	 * @return organization name
	 * @throws IOException
	 */
	public String companyName(String organization) throws IOException {
		
		String name = null;
		
		if (organization.equalsIgnoreCase("094019245")){
			name = "OTE";
		}
		else if (organization.equalsIgnoreCase("094222303")){
			name = "OTEPLUS";
		}
		else if (organization.equalsIgnoreCase("094493766")){
			name = "COSMOTE";
		}
		else if (organization.equalsIgnoreCase("094436540")){
			name = "OTE ESTATE";
		}
		else if (organization.equalsIgnoreCase("094282975")){
			name = "ΓΕΡΜΑΝΟΣ";
		}
		else if (organization.equalsIgnoreCase("094493000")){
			name = "ΟΤΕSAT MARITEL";
		}
		else if (organization.equalsIgnoreCase("099760493")){
			name = "ΟΤΕ GLOBE SA";
		}
	
		return name;
	}
	
	/**
	 * Convert a date from 2013-08-01 to 01-08-2013
	 * @param date
	 * @return date
	 */
	public String dateConverter (String date){
		
		String dateParts[] = date.split("-");
		
		date = dateParts[2] + "-" + dateParts[1] + "-" + dateParts[0];
		
		return date.replaceAll("\\s+","");
	}
	
	/**
	 * 
	 * @param literal
	 * @return
	 */
	public String cleanLiteral(Literal literal) {
		
		String str = "";
		
		if (literal != null) {
			
			if (literal.toString().contains("@") == true){
				String literalParts[] = literal.toString().split("@");
				str = literalParts[0].replace("^", "");
			}
			else {
				String literalParts[] = literal.toString().split("http");
				str = literalParts[0].replace("^", "");
			}
		}
	
		return str;
	}
	
}