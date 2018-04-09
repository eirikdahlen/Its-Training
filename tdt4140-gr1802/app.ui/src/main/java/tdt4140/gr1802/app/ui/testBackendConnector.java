package tdt4140.gr1802.app.ui;

import java.util.HashMap;

import org.json.JSONObject;

public class testBackendConnector {
    public static void main(String[] args) throws Exception {
        // Prints "Hello, World" to the terminal window.
    		HashMap<String, String> myMap = new HashMap<String, String>(); 
    		myMap.put("name", "larserik2");
        JSONObject objektet = BackendConnector.makeRequest(myMap, "hello");
        System.out.print(objektet.toString());
    }

}
