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
 * Creates the Social Information graphs of the Banks
 */
public class SocialBankMain {

	public static void main(String[] args) {
		
		QueriesSocial qsSocial = new QueriesSocial();
		
		List<String[]> inflTrackerMetrics = new ArrayList<String[]>();
		
		List<String> accountsList = Arrays.asList("ibanknbg", "winbank_tweets", "alpha_bank", "eurobank_group");
		
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
				
		Utils.getInstance().writeJsonFile(jsonString, "json", docId);
		
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
				
		Utils.getInstance().writeJsonFile(jsonString, "json", docId);
		
	}
	
	private static void createSocialImpactJson() {
		
		QueriesSocial qsSocial = new QueriesSocial();
		
		String docId = "Social_Impact";
		String jsonString = Utils.getInstance().startJson(docId);
		
		List<String[]> socialImpactDataIbanknbg = qsSocial.getAccountSocialImpact("ibanknbg");
		List<String[]> socialImpactDataWinbank_tweets = qsSocial.getAccountSocialImpact("winbank_tweets");
		List<String[]> socialImpactDataAlpha_bank = qsSocial.getAccountSocialImpact("alpha_bank");
		List<String[]> socialImpactDataEurobank_group = qsSocial.getAccountSocialImpact("eurobank_group");
		
		/* add labels */
		jsonString += "\"labels\": [";
		
		for (String[] date : socialImpactDataIbanknbg) {
			jsonString += "\"" + date[0] + "\",";
		}
		
		jsonString = jsonString.substring(0, jsonString.length() - 1) + "],";
		
		jsonString += "\"datasets\": [";
		
		/* ibanknbg */
		jsonString += "{\"label\":\"ibanknbg\", \"fillColor\": \"rgba(0,128,0,0.5)\", " +
					  "\"strokeColor\": \"rgba(0,128,0,1)\", \"pointColor\": \"rgba(0,128,0,1)\", " +
					  "\"pointStrokeColor\": \"#fff\", \"pointHighlightFill\": \"#fff\", " +
					  "\"pointHighlightStroke\": \"rgba(0,128,0,1)\", \"data\": [";
		
		for (String[] values : socialImpactDataIbanknbg) {
			jsonString += Utils.getInstance().formatDisplayNumber(values[1]) + ",";
		}
		
		jsonString = jsonString.substring(0, jsonString.length() - 1);
		
		jsonString += "]},";
		
		/* winbank_tweets */
		jsonString += "{\"label\":\"winbank_tweets\", \"fillColor\": \"rgba(255,0,0,0.5)\", " +
					  "\"strokeColor\": \"rgba(255,0,0,0.7)\", \"pointColor\": \"rgba(255,0,0,1)\", " +
					  "\"pointStrokeColor\": \"#fff\", \"pointHighlightFill\": \"#fff\", " +
					  "\"pointHighlightStroke\": \"rgba(255,0,0,1)\", \"data\": [";
		
		for (String[] values : socialImpactDataWinbank_tweets) {
			jsonString += Utils.getInstance().formatDisplayNumber(values[1]) + ",";
		}
		
		jsonString = jsonString.substring(0, jsonString.length() - 1);
		
		jsonString += "]},";
		
		/* alpha_bank */
		jsonString += "{\"label\":\"alpha_bank\", \"fillColor\": \"rgba(0,128,0,0.5)\", " +
					  "\"strokeColor\": \"rgba(0,128,0,1)\", \"pointColor\": \"rgba(0,128,0,1)\", " +
					  "\"pointStrokeColor\": \"#fff\", \"pointHighlightFill\": \"#fff\", " +
					  "\"pointHighlightStroke\": \"rgba(0,128,0,1)\", \"data\": [";
		
		for (String[] values : socialImpactDataAlpha_bank) {
			jsonString += Utils.getInstance().formatDisplayNumber(values[1]) + ",";
		}
		
		jsonString = jsonString.substring(0, jsonString.length() - 1);
		
		jsonString += "]},";
		
		/* eurobank_group */
		jsonString += "{\"label\":\"eurobank_group\", \"fillColor\": \"rgba(0,0,255,0.5)\", " +
					  "\"strokeColor\": \"rgba(0,0,255,0.7)\", \"pointColor\": \"rgba(0,0,255,1)\", " +
					  "\"pointStrokeColor\": \"#fff\", \"pointHighlightFill\": \"#fff\", " +
					  "\"pointHighlightStroke\": \"rgba(0,0,255,1)\", \"data\": [";
		
		for (String[] values : socialImpactDataEurobank_group) {
			jsonString += Utils.getInstance().formatDisplayNumber(values[1]) + ",";
		}
		
		jsonString = jsonString.substring(0, jsonString.length() - 1);
		
		jsonString += "]}";
		
		//close JSON
		jsonString += "]" + Utils.getInstance().closeJson();
		
		Utils.getInstance().writeJsonFile(jsonString, "json", docId);
		
	}
	
}