package universittProj;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import com.google.gson.Gson;

public class APIConsumer {
    
    public static void main(String[] args) {
        String apiUrl = "http://universities.hipolabs.com/search?country";
        try {
            URL url = new URL(apiUrl);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Accept", "application/json");
            
            if (conn.getResponseCode() != 200) {
                throw new RuntimeException("HTTP error code : " + conn.getResponseCode());
            }
            
            BufferedReader br = new BufferedReader(new InputStreamReader((conn.getInputStream())));
            String output;
            StringBuilder json = new StringBuilder();
            
            while ((output = br.readLine()) != null) {
                json.append(output);
            }
            
            conn.disconnect();
            
            Gson gson = new Gson();
            University Uni[] = gson.fromJson(json.toString(), University[].class);
            for (int i = 0; i<Uni.length; i++) {
            	University newUni = Uni[i];
            	System.out.println("State province: " + newUni.state_province);
            	System.out.println("Country: " + newUni.country);
            	System.out.println("University Name: " + newUni.name);
            	System.out.println("alpha two code: " + newUni.alpha_two_code);
            	for (int j = 0; j< newUni.domains.length; j++) {
            	System.out.println("Domains : " + newUni.domains[j]);
            	}
            	
            	for(String x : newUni.web_pages) {
            		System.out.println("web pages: " + x);
            	}
            	System.out.println("---------------------------------------------------");



            	
            	
            }
            
            // Use myObj for further processing
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

