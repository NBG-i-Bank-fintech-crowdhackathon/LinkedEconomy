package social;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import common.QueriesSocial;
import common.Utils;

/**
 * @author G. Razis
 * @author G. Vafeiadis
 * @author E. Galanos
 * 
 * Creates the Social Information graphs of the TelCos
 */
public class SocialTelCoMain {

	public static void main(String[] args) {
		
		QueriesSocial qsSocial = new QueriesSocial();
		
		List<String[]> inflTrackerMetrics = new ArrayList<String[]>();
		
		List<String> accountsList = Arrays.asList("cosmote", "vodafone_gr", "forthnet", "cytahellascare", "windhellas");
		
		//get the metrics
		for (String account : accountsList) {
			inflTrackerMetrics.add(qsSocial.getAccountInfluenceTrackerMetrics(account));
		}
		
		/* Influence Metric */
		System.out.println("\nCreating Influence Metric JSON...");
		createInfluenceMetricJson(inflTrackerMetrics);
		
		/* Followers */
		System.out.println("\nCreating Followers JSON...");
		createFollowersJson(inflTrackerMetrics);
		
		/* Social Impact */
		System.out.println("\nCreating Social Impact JSON...");
		createSocialImpactJson();
		
		System.out.println("\nFinished!");
	}
	
	private static void createInfluenceMetricJson(List<String[]> inflTrackerMetrics) {
		
		String docId = "Influence_Metric";
		String jsonString = Utils.getInstance().startJson(docId);
		
		jsonString += "\"labels\": [\"Influence Metric\"], \"series\": [";
		
		for (String[] metrics : inflTrackerMetrics) {
			jsonString += "[" + metrics[1] + "],";
		}
		
		jsonString = jsonString.substring(0, jsonString.length() - 1) + "]";
		
		//close JSON
		jsonString += Utils.getInstance().closeJson();
				
		Utils.getInstance().writeJsonFile(jsonString, "Others", docId);
		
	}
	
	private static void createFollowersJson(List<String[]> inflTrackerMetrics) {
		
		String docId = "Followers";
		String jsonString = Utils.getInstance().startJson(docId);
		
		jsonString += "\"labels\": [\"Followers\"], \"series\": [";
		
		for (String[] metrics : inflTrackerMetrics) {
			jsonString += "[" + metrics[0] + "],";
		}
		
		jsonString = jsonString.substring(0, jsonString.length() - 1) + "]";
		
		//close JSON
		jsonString += Utils.getInstance().closeJson();
				
		Utils.getInstance().writeJsonFile(jsonString, "Others", docId);
		
	}
	
	private static void createSocialImpactJson() {
		
		QueriesSocial qsSocial = new QueriesSocial();
		
		String docId = "Social_Impact";
		String jsonString = Utils.getInstance().startJson(docId);
		
		List<String[]> socialImpactDataOte = qsSocial.getAccountSocialImpact("cosmote");
		List<String[]> socialImpactDataVodafone = qsSocial.getAccountSocialImpact("vodafone_gr");
		List<String[]> socialImpactDataForthnet = qsSocial.getAccountSocialImpact("forthnet");
		List<String[]> socialImpactDataCyta = qsSocial.getAccountSocialImpact("cytahellascare");
		List<String[]> socialImpactDataWind = qsSocial.getAccountSocialImpact("windhellas");
		
		/* add labels */
		jsonString += "\"labels\": [";
		
		for (String[] date : socialImpactDataOte) {
			jsonString += "\"" + date[0] + "\",";
		}
		
		jsonString = jsonString.substring(0, jsonString.length() - 1) + "],";
		
		jsonString += "\"datasets\": [";
		
		/* Cosmote */
		jsonString += "{\"label\":\"OTE\", \"fillColor\": \"rgba(0,128,0,0.5)\", " +
					  "\"strokeColor\": \"rgba(0,128,0,1)\", \"pointColor\": \"rgba(0,128,0,1)\", " +
					  "\"pointStrokeColor\": \"#fff\", \"pointHighlightFill\": \"#fff\", " +
					  "\"pointHighlightStroke\": \"rgba(0,128,0,1)\", \"data\": [";
		
		for (String[] values : socialImpactDataOte) {
			jsonString += Utils.getInstance().formatDisplayNumber(values[1]) + ",";
		}
		
		jsonString = jsonString.substring(0, jsonString.length() - 1);
		
		jsonString += "]},";
		
		/* Vodafone */
		jsonString += "{\"label\":\"VODAFONE\", \"fillColor\": \"rgba(255,0,0,0.5)\", " +
					  "\"strokeColor\": \"rgba(255,0,0,0.7)\", \"pointColor\": \"rgba(255,0,0,1)\", " +
					  "\"pointStrokeColor\": \"#fff\", \"pointHighlightFill\": \"#fff\", " +
					  "\"pointHighlightStroke\": \"rgba(255,0,0,1)\", \"data\": [";
		
		for (String[] values : socialImpactDataVodafone) {
			jsonString += Utils.getInstance().formatDisplayNumber(values[1]) + ",";
		}
		
		jsonString = jsonString.substring(0, jsonString.length() - 1);
		
		jsonString += "]},";
		
		/* Forthnet */
		jsonString += "{\"label\":\"FORTHNET\", \"fillColor\": \"rgba(0,128,0,0.5)\", " +
					  "\"strokeColor\": \"rgba(0,128,0,1)\", \"pointColor\": \"rgba(0,128,0,1)\", " +
					  "\"pointStrokeColor\": \"#fff\", \"pointHighlightFill\": \"#fff\", " +
					  "\"pointHighlightStroke\": \"rgba(0,128,0,1)\", \"data\": [";
		
		for (String[] values : socialImpactDataForthnet) {
			jsonString += Utils.getInstance().formatDisplayNumber(values[1]) + ",";
		}
		
		jsonString = jsonString.substring(0, jsonString.length() - 1);
		
		jsonString += "]},";
		
		/* Cyta */
		jsonString += "{\"label\":\"CYTA\", \"fillColor\": \"rgba(0,0,255,0.5)\", " +
					  "\"strokeColor\": \"rgba(0,0,255,0.7)\", \"pointColor\": \"rgba(0,0,255,1)\", " +
					  "\"pointStrokeColor\": \"#fff\", \"pointHighlightFill\": \"#fff\", " +
					  "\"pointHighlightStroke\": \"rgba(0,0,255,1)\", \"data\": [";
		
		for (String[] values : socialImpactDataCyta) {
			jsonString += Utils.getInstance().formatDisplayNumber(values[1]) + ",";
		}
		
		jsonString = jsonString.substring(0, jsonString.length() - 1);
		
		jsonString += "]},";
		
		/* Wind */
		jsonString += "{\"label\":\"WIND\", \"fillColor\": \"rgba(0,0,255,0.5)\", " +
					  "\"strokeColor\": \"rgba(0,0,255,0.7)\", \"pointColor\": \"rgba(0,0,255,1)\", " +
					  "\"pointStrokeColor\": \"#fff\", \"pointHighlightFill\": \"#fff\", " +
					  "\"pointHighlightStroke\": \"rgba(0,0,255,1)\", \"data\": [";
		
		for (String[] values : socialImpactDataWind) {
			jsonString += Utils.getInstance().formatDisplayNumber(values[1]) + ",";
		}
		
		jsonString = jsonString.substring(0, jsonString.length() - 1);
		
		jsonString += "]}";
		
		//close JSON
		jsonString += "]" + Utils.getInstance().closeJson();
		
		Utils.getInstance().writeJsonFile(jsonString, "Others", docId);
		
	}
	
}