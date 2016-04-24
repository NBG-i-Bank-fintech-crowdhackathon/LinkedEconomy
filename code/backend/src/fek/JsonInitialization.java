package fek;

/**
 * @author G. Razis
 * @author G. Vafeiadis
 * @author E. Galanos
 */
public class JsonInitialization {
    
	public String openCurlyBracket() {
        String character = "{";
        return character;
    }
    
    public String closeCurlyBracket() {
        String character = "}";
        return character;
    }
    
    public String openBracket() {
        String character = "[";
        return character;
    }
    
    public String closeBracket() {
        String character = "]";
        return character;
    }
    
    public String getComma() {
        String character = ",";
        return character;
    }
    
    public String getJsonName (String name) {
        String returnJsonName = "\"" + name + "\":";
        return returnJsonName;
    }
    
    public String getJsonValue (String value) {
        String returnJsonValue = "\"" + value + "\"";
        return returnJsonValue;
    }
    
    public String getJsonCpvIdValue (String cpvCode) {
        
        String returnJsonValue = "\"cpv?=cpv="+cpvCode+"\"";
        return returnJsonValue;
        
    }
    
}
