package tdt4140.gr1802.app.core;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;

import org.json.*;


public final class BackendConnector {
        
        static String baseUrl = "http://10.22.45.236:8000/"; 
        public static JSONObject makeRequest(HashMap<String, String> dict, String path) throws Exception { 
                
                HttpURLConnection con = null;
                try {
                        URL obj = new URL(baseUrl + path);
                        con = (HttpURLConnection) obj.openConnection();
                        // connection to backend is open
                        
                        StringBuilder postData = new StringBuilder();
                for (HashMap.Entry<String,String> param : dict.entrySet()) { //gir: key1=value1&key1=value2&key3=value3...
                    if (postData.length() != 0) {
                          postData.append('&');
                    }
                    postData.append(URLEncoder.encode(param.getKey(), "UTF-8"));
                    postData.append('=');
                    postData.append(URLEncoder.encode(String.valueOf(param.getValue()), "UTF-8"));
                }
                byte[] postDataBytes = postData.toString().getBytes("UTF-8");
                        
//                        if (method == Method.POST) { // if POST
                                con.setRequestMethod("POST");    
//                        } else { //if GET
//                                con.setRequestMethod("GET"); 
//                        }
                        //con.setRequestProperty(key, value);
                        con.setDoOutput(true);
                        // send request
                        DataOutputStream wr = new DataOutputStream(con.getOutputStream());
                        wr.write(postDataBytes);
                        wr.flush();
                        wr.close();
                        // get response
                        BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
                        String inputLine;
                        StringBuffer response = new StringBuffer();
                
                        while ((inputLine = in.readLine()) != null) {
                                response.append(inputLine);
                        }
                        //System.out.print("-----");
                        //System.out.print(response.toString());
                        //System.out.print("-----");
                        in.close();
                        return new JSONObject(response.toString()); 
                } catch (Exception e) {
                        e.printStackTrace();
                } finally {
                        if (con != null) {
                                con.disconnect();
                        }
                }
                return new JSONObject();
        }
}