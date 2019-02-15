package com.ipubu.demo;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.Scanner;

/**
 * @ClassName TestDaZhi
 * @Description
 * @Author jzy
 */
public class TestDaZhi {

	public static String getPredictedDomain(String sentence) {

        String predictedDomain = "";

        try {

            URL url = new URL("http://10.110.134.298:5000/classification");
            URLConnection conn = url.openConnection();
            HttpURLConnection connection = (HttpURLConnection)conn;
            connection.setRequestMethod("POST");
            connection.setDoOutput(true);

            connection.setRequestProperty("Content-Type", "application/json");

            String postData = sentence;

            OutputStreamWriter out = new OutputStreamWriter(
                    connection.getOutputStream());

            // Send the sentence to the domain classification service
            out.write(postData);
            out.flush();
            out.close();

            int status = connection.getResponseCode();
            System.out.println("connection status: " + status);

            BufferedReader in = new BufferedReader(
                    new InputStreamReader(connection.getInputStream(),
                            "UTF-8"));

            String line;
            while ((line = in.readLine()) != null) {
                // get the server processing result
                predictedDomain += line;
            }

            in.close();
        }
        catch (IOException e) {
            e.printStackTrace();
        }

        return predictedDomain;

    }

    public static void main(String args[]) throws IOException{
    	
    	Scanner s = new Scanner(System.in);
		while(true){
			System.out.println("---输入测试用例---");
			String line = s.nextLine();
			String str = line.trim();
			String result = getPredictedDomain(str);
			System.out.println("predicted domain: " + result);
		}
    	
    }
}
