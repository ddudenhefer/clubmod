package utils;

import java.io.*;
import java.net.*;


public class Results {

	public String postResult(String URL){
        StringBuffer sb= new StringBuffer();
        try {

            String finalUrl="";

            String[] parsedUrl=URL.split("\\?");
            String params=URLEncoder.encode(parsedUrl[1], "UTF-8").replace("%3D","=").replace("%26","&");

            URL url= new URL(parsedUrl[0]+"?"+params);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Accept", "application/json");

            conn.setDoOutput(true);
            DataOutputStream wr = new DataOutputStream(conn.getOutputStream());
            wr.writeBytes(params);
            wr.flush();
            wr.close();



            boolean redirect = false;
            // normally, 3xx is redirect
            int status = conn.getResponseCode();
            if (status != HttpURLConnection.HTTP_OK) {
                if (status == HttpURLConnection.HTTP_MOVED_TEMP
                        || status == HttpURLConnection.HTTP_MOVED_PERM
                        || status == HttpURLConnection.HTTP_SEE_OTHER)
                    redirect = true;
            }


            if (redirect) {
                // get redirect url from "location" header field
                String newUrl = conn.getHeaderField("Location");


                // open the new connnection again
                conn = (HttpURLConnection) new URL(newUrl).openConnection();
                conn.setRequestProperty("Accept", "application/json");
            }

            BufferedReader in = new BufferedReader(
                    new InputStreamReader(conn.getInputStream()));
            String inputLine;


            while ((inputLine = in.readLine()) != null) {
                sb.append(inputLine);
            }
            in.close();


        } catch (Exception e) {
            e.printStackTrace();
        }

        return sb.toString();
    }
}
