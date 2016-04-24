package fek;

import java.io.IOException;

import common.QueryConfiguration;

import virtuoso.jena.driver.VirtGraph;

/**
 * @author G. Razis
 * @author G. Vafeiadis
 * @author E. Galanos
 */
public class Main {

	private static FekJson fj = new FekJson();
	
	public static void main(String[] args) throws IOException {
		
		/* FEK GRAPH */
		VirtGraph graphFek = new VirtGraph (QueryConfiguration.queryGraphFek, QueryConfiguration.connectionString, 
			     							  QueryConfiguration.username, QueryConfiguration.password);
		System.out.println("Connected to Fek Graph!");

		/* ORGANIZATIONS GRAPH */
		VirtGraph graphOrganizations = new VirtGraph (QueryConfiguration.queryGraphOrganizations, QueryConfiguration.connectionString, 
			     							  QueryConfiguration.username, QueryConfiguration.password);
		System.out.println("Connected to Organizations Graph!");
		
		System.out.println("Fek json starts!");
		fj.FekStatsPage(graphFek, graphOrganizations);
		System.out.println("Fek json is ready!");
		
		graphFek.close();
		graphOrganizations.close();
		
	}

}