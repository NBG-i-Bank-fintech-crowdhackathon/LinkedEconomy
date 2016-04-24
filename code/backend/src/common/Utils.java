package common;

import java.io.FileNotFoundException;
import java.io.PrintWriter;

import java.text.NumberFormat;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import org.joda.time.Days;
import org.joda.time.DurationFieldType;
import org.joda.time.LocalDate;

import common.QueryConfiguration;

/**
 * @author G. Razis
 * @author G. Vafeiadis
 * @author E. Galanos
 */
public class Utils {
	
	public static Utils	instance; //singleton pattern
	
	public static Utils	getInstance() {
		
		if (instance == null) {
			instance = new Utils();
		}
		
		return instance;
	}

	public String formatNumber(String inputNumber) {
		
		//keep two decimals and format number
		double numberDouble = Double.parseDouble(inputNumber);
		
		NumberFormat nf = NumberFormat.getNumberInstance(Locale.UK);
		nf.setMaximumFractionDigits(0);
		String formattedNum = nf.format(numberDouble);
		
		return formattedNum;
	}
	
	public void writeJsonFile(String jsonString, String folderName, String filename) {
		
		PrintWriter writer = null;
		
		try {
			writer = new PrintWriter(QueryConfiguration.jsonFilepath + folderName + "/" + filename + ".json");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
		writer.println(jsonString);	
		writer.close();
	}
	
	public String startJson(String docId) {
		
		/* initialize the JSON */
		String jsonString = "";
		
		/* start the 'couchDb' JSON */
		if (QueryConfiguration.couchDbUsage) {
			jsonString += "{\"docs\": [";
		}
		
		/* start the 'normal' JSON */
		jsonString += "{";
			
		/** create _id tag **/
		if (QueryConfiguration.couchDbUsage) {
			jsonString += "\"_id\": \"dashboard?=afm=" + docId + "\", ";
		}
		
		return jsonString;
	}
	
	public String closeJson() {
		
		/* close the 'normal' JSON */
		String jsonString = "}";
		
		/* close the 'couchDb' JSON */
		if (QueryConfiguration.couchDbUsage) {
			jsonString += "]}";
		}
		
		return jsonString;		
	}
	
	/**
     * Handle the invalid characters of provided string.
     * 
     * @param String a string to be cleaned
     * @return String the cleaned representation of the input string
     */
	public String cleanInvalidCharsJsonData(String aString) {
		
		String cleanedString = aString.replace("\"", "'");
		cleanedString = cleanedString.replace("\\", "");
		
		return cleanedString;
	}
	
	/**
     * Calculate the Percentage change of the provided amounts.
     * 
     * @param float the initial amount
     * @param float the final amount
     * @return String the Percentage change of the provided amounts
     */
	public String calculatePercentageVariation(float previousAmount, float currentAmount) {
		
		float percentage = 0;
		
		if ( (previousAmount == 0) && (currentAmount == 0) ) {
			percentage = 0;
		} else if (previousAmount == 0) {
			percentage = Float.POSITIVE_INFINITY;
		} else {
			percentage = ( (currentAmount - previousAmount) / previousAmount) * 100;
			percentage = Math.round(percentage * 10); //for rounding purposes
			percentage = percentage / 10; //for rounding purposes
		}
		
		return String.valueOf(percentage);
	}
	
	/**
     * Get the list of dates among the starting and ending date.
     * 
     * @param LocalDate the starting date
     * @param LocalDate the ending date
     * @return List<String> the list of dates between the starting and ending date
     */
	public List<String> getListOfDates(LocalDate startDate, LocalDate endDate) {
		
		List<String> datesList = new ArrayList<String>();
		
		int days = Days.daysBetween(startDate, endDate).getDays();
		
		for (int i = 0; i < days; i++) {
		    LocalDate dt = startDate.withFieldAdded(DurationFieldType.days(), i);
		    datesList.add(dt.toString());
		}
		
		return datesList;
	}
	
	public String formatDisplayNumber(String number) {
		
		String formattedNumber = String.format("%.3f", Float.parseFloat(number)).replace(",", ".");
		
		return formattedNumber;
	}
	
}