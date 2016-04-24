package oteGroup;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.joda.time.Days;
import org.joda.time.LocalDate;

import virtuoso.jena.driver.VirtGraph;

import common.QueriesMetrics;
import common.QueryConfiguration;
import common.Utils;

/**
 * @author G. Razis
 * @author G. Vafeiadis
 * @author E. Galanos
 * 
 * Creates several metrics regarding OTE Group
 */
public class WaitingMetrics {
	
	public static void main(String[] args) {

		/* DiavgeiaII Graph */
		VirtGraph graphDiavgeiaII = new VirtGraph(QueryConfiguration.queryGraphDiavgeiaII, QueryConfiguration.connectionString, 
												  QueryConfiguration.username, QueryConfiguration.password);
		System.out.println("Connected to Diavgeia II Graph!");
		
		oteGroupWaiting(graphDiavgeiaII);
		
		vodafoneGroupWaiting(graphDiavgeiaII);
		
		forthnetGroupWaiting(graphDiavgeiaII);
		
		cytaGroupWaiting(graphDiavgeiaII);
		
		windGroupWaiting(graphDiavgeiaII);
		
		/* close Graphs */
		graphDiavgeiaII.close();
		
		System.out.println("\nFinished!");
	}
	
	private static void oteGroupWaiting(VirtGraph graphDiavgeiaII) {
		
		QueriesMetrics qsMetrics = new QueriesMetrics();
		
		int avgDays = 0;
		int minDays = 0;
		int maxDays = 0;
		List<String> datesList = null;
		List<Integer> waitList = new ArrayList<Integer>();
		
		String docId = "OTE_Group_waiting";
		String jsonString = Utils.getInstance().startJson(docId);
		
		System.out.println("Fetching dates list");
		datesList = qsMetrics.getOteGroupPaymentDtlsQuery(graphDiavgeiaII);
		
		for (int i = 0; i < (datesList.size() - 1); i++) {
			LocalDate startDate = new LocalDate(datesList.get(i));
			LocalDate endDate = new LocalDate(datesList.get(i + 1));
			int days = Days.daysBetween(startDate, endDate).getDays();
			waitList.add(days);
			avgDays += days;
		}
		
		Collections.sort(waitList);
		
		minDays = waitList.get(0);
		maxDays = waitList.get(waitList.size() - 1);
		avgDays = avgDays / datesList.size();
		
		jsonString += "\"Αναμονή Πληρωμής\": {\"min\": \"" + minDays + "\", " +
					  "\"max\": \"" + maxDays + "\", \"avg\": \"" + avgDays + "\"}";
		
		//close JSON
		jsonString += Utils.getInstance().closeJson();
		
		Utils.getInstance().writeJsonFile(jsonString, "Others", docId);
		
	}
	
	private static void vodafoneGroupWaiting(VirtGraph graphDiavgeiaII) {
		
		QueriesMetrics qsMetrics = new QueriesMetrics();
		
		int avgDays = 0;
		int minDays = 0;
		int maxDays = 0;
		List<String> datesList = null;
		List<Integer> waitList = new ArrayList<Integer>();
		
		String docId = "Vodafone_Group_waiting";
		String jsonString = Utils.getInstance().startJson(docId);
		
		System.out.println("Fetching dates list");
		datesList = qsMetrics.getVodafoneGroupPaymentDtlsQuery(graphDiavgeiaII);
		
		for (int i = 0; i < (datesList.size() - 1); i++) {
			LocalDate startDate = new LocalDate(datesList.get(i));
			LocalDate endDate = new LocalDate(datesList.get(i + 1));
			int days = Days.daysBetween(startDate, endDate).getDays();
			waitList.add(days);
			avgDays += days;
		}
		
		Collections.sort(waitList);
		
		minDays = waitList.get(0);
		maxDays = waitList.get(waitList.size() - 1);
		avgDays = avgDays / datesList.size();
		
		jsonString += "\"Αναμονή Πληρωμής\": {\"min\": \"" + minDays + "\", " +
					  "\"max\": \"" + maxDays + "\", \"avg\": \"" + avgDays + "\"}";
		
		//close JSON
		jsonString += Utils.getInstance().closeJson();
		
		Utils.getInstance().writeJsonFile(jsonString, "Others", docId);
		
	}
	
	private static void forthnetGroupWaiting(VirtGraph graphDiavgeiaII) {
		
		QueriesMetrics qsMetrics = new QueriesMetrics();
		
		int avgDays = 0;
		int minDays = 0;
		int maxDays = 0;
		List<String> datesList = null;
		List<Integer> waitList = new ArrayList<Integer>();
		
		String docId = "Forthnet_Group_waiting";
		String jsonString = Utils.getInstance().startJson(docId);
		
		System.out.println("Fetching dates list");
		datesList = qsMetrics.getForthnetGroupPaymentDtlsQuery(graphDiavgeiaII);
		
		for (int i = 0; i < (datesList.size() - 1); i++) {
			LocalDate startDate = new LocalDate(datesList.get(i));
			LocalDate endDate = new LocalDate(datesList.get(i + 1));
			int days = Days.daysBetween(startDate, endDate).getDays();
			waitList.add(days);
			avgDays += days;
		}
		
		Collections.sort(waitList);
		
		minDays = waitList.get(0);
		maxDays = waitList.get(waitList.size() - 1);
		avgDays = avgDays / datesList.size();
		
		jsonString += "\"Αναμονή Πληρωμής\": {\"min\": \"" + minDays + "\", " +
					  "\"max\": \"" + maxDays + "\", \"avg\": \"" + avgDays + "\"}";
		
		//close JSON
		jsonString += Utils.getInstance().closeJson();
		
		Utils.getInstance().writeJsonFile(jsonString, "Others", docId);
		
	}
	
	private static void cytaGroupWaiting(VirtGraph graphDiavgeiaII) {
		
		QueriesMetrics qsMetrics = new QueriesMetrics();
		
		int avgDays = 0;
		int minDays = 0;
		int maxDays = 0;
		List<String> datesList = null;
		List<Integer> waitList = new ArrayList<Integer>();
		
		String docId = "Cyta_Group_waiting";
		String jsonString = Utils.getInstance().startJson(docId);
		
		System.out.println("Fetching dates list");
		datesList = qsMetrics.getCytaGroupPaymentDtlsQuery(graphDiavgeiaII);
		
		for (int i = 0; i < (datesList.size() - 1); i++) {
			LocalDate startDate = new LocalDate(datesList.get(i));
			LocalDate endDate = new LocalDate(datesList.get(i + 1));
			int days = Days.daysBetween(startDate, endDate).getDays();
			waitList.add(days);
			avgDays += days;
		}
		
		Collections.sort(waitList);
		
		minDays = waitList.get(0);
		maxDays = waitList.get(waitList.size() - 1);
		avgDays = avgDays / datesList.size();
		
		jsonString += "\"Αναμονή Πληρωμής\": {\"min\": \"" + minDays + "\", " +
					  "\"max\": \"" + maxDays + "\", \"avg\": \"" + avgDays + "\"}";
		
		//close JSON
		jsonString += Utils.getInstance().closeJson();
		
		Utils.getInstance().writeJsonFile(jsonString, "Others", docId);
		
	}
	
	private static void windGroupWaiting(VirtGraph graphDiavgeiaII) {
		
		QueriesMetrics qsMetrics = new QueriesMetrics();
		
		int avgDays = 0;
		int minDays = 0;
		int maxDays = 0;
		List<String> datesList = null;
		List<Integer> waitList = new ArrayList<Integer>();
		
		String docId = "Wind_Group_waiting";
		String jsonString = Utils.getInstance().startJson(docId);
		
		System.out.println("Fetching dates list");
		datesList = qsMetrics.getWindGroupPaymentDtlsQuery(graphDiavgeiaII);
		
		for (int i = 0; i < (datesList.size() - 1); i++) {
			LocalDate startDate = new LocalDate(datesList.get(i));
			LocalDate endDate = new LocalDate(datesList.get(i + 1));
			int days = Days.daysBetween(startDate, endDate).getDays();
			waitList.add(days);
			avgDays += days;
		}
		
		Collections.sort(waitList);
		
		minDays = waitList.get(0);
		maxDays = waitList.get(waitList.size() - 1);
		avgDays = avgDays / datesList.size();
		
		jsonString += "\"Αναμονή Πληρωμής\": {\"min\": \"" + minDays + "\", " +
					  "\"max\": \"" + maxDays + "\", \"avg\": \"" + avgDays + "\"}";
		
		//close JSON
		jsonString += Utils.getInstance().closeJson();
		
		Utils.getInstance().writeJsonFile(jsonString, "Others", docId);
		
	}
	
}