package com.webservices.rest.DataFromJSONFile;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.StandardCharsets;

import org.json.JSONObject;

/**
 * @author
 * 
 */

public class RESTServiceClient {
	public static void main(String[] args) {
		String string = "";
		try {

			// Step1: Let's 1st read file from fileSystem
			// Change JSON.txt path here
			InputStream input = new FileInputStream(
					"h://Eclipse workspace/JSON.txt");
			InputStreamReader inputReader = new InputStreamReader(
					input);
			BufferedReader br = new BufferedReader(inputReader);
			String line;
			while ((line = br.readLine()) != null) {
				string += line + "\n";
			}

			JSONObject jsonObject = new JSONObject(string);
			System.out.println(jsonObject);

			// Step2: Now pass JSON File Data to REST Service
			try {
				URL url = new URL(
						"http://localhost:8080/RESTfulWS/api/PracticeRESTService");
				URLConnection connection = url.openConnection();
				connection.setDoOutput(true);
				connection.setRequestProperty("Content-Type",
						"application/json");
				connection.setConnectTimeout(15 * 1000);
				connection.setReadTimeout(15 * 1000);
				OutputStreamWriter out = new OutputStreamWriter(
						connection.getOutputStream());
				out.write(jsonObject.toString());
				out.close();

				BufferedReader in = new BufferedReader(new InputStreamReader(
						connection.getInputStream(), StandardCharsets.UTF_8));

				while (in.readLine() != null) {
				}
				System.out
						.println("\n REST Service Invoked Successfully..");
				in.close();
			} /*catch (Exception e) {
				System.out
						.println("\n Error while calling  REST Service");
				System.out.println(e);
			}*/
			 catch (IOException ex) {
				 System.out.println (ex.toString());
			        System.out.println("Error in calling");
				}

			br.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}