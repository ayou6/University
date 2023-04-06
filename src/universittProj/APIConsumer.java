package universittProj;

import java.io.BufferedReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

import com.google.gson.Gson;

public class APIConsumer {
	
	static ArrayList<University> uniList = new ArrayList<>();

	public static University[] main() {
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
			for (int i = 0; i < Uni.length; i++) {
				University newUniversity = Uni[i];
				System.out.println("State province: " + Uni[i].state_province);
				System.out.println("Country: " + Uni[i].country);
				System.out.println("University Name: " + Uni[i].name);
				System.out.println("alpha two code: " + Uni[i].alpha_two_code);
				for (int j = 0; j < Uni[i].domains.length; j++) {
					System.out.println("Domains : " + Uni[i].domains[j]);
				}

				for (String x : Uni[i].web_pages) {
					System.out.println("web pages: " + x);
				}
				System.out.println("-----------------------------------------------------");
				uniList.add(newUniversity);
				JDBC.createUniversityTable();
			}
			return Uni;

		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public static void saveIntoFile() {
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
            try {
                FileWriter writer = new FileWriter("universities.txt", true);
                writer.write("List of universities fetched from API:\n");
                for (int i = 0; i < Uni.length; i++) {
                    University newUniversity = Uni[i];
                    writer.write("\nState province: " + Uni[i].state_province);
                    writer.write("\nCountry: " + Uni[i].country);
                    writer.write("\nUniversity Name: " + Uni[i].name);
                    writer.write("\nalpha two code: " + Uni[i].alpha_two_code);
                    writer.write("\nDomains : ");
                    for (int j = 0; j < Uni[i].domains.length; j++) {
                        writer.write(Uni[i].domains[j] + ", ");
                    }
                    writer.write("\nWeb pages : ");
                    for (String x : Uni[i].web_pages) {
                        writer.write(x + ", ");
                    }
                    writer.write("\n-----------------------------------------------------\n");
                    uniList.add(newUniversity);
                    JDBC.createUniversityTable();
                }
                writer.close();
            } catch (IOException e) {
                System.out.println("An error occurred.");
                e.printStackTrace();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}


