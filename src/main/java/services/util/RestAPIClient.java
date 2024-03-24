package services.util;

import javax.net.ssl.HttpsURLConnection;
import java.io.*;
import java.net.URL;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

import services.logger.Log;

public class RestAPIClient {

    public Map<String, String> invokeAPI(String restUrl, Map<String, String> headers, String methodName, String reqData) {

        HttpsURLConnection conn = null;
        String statusCode = null;
        String inputLine;
        StringBuffer response = new StringBuffer();
        String saveFilePath = null;
        final int BUFFER_SIZE = 1096;
        Map<String, String> apiResponse = new HashMap<String, String>();


        try {
            URL url = new URL(restUrl);
            conn = (HttpsURLConnection) url.openConnection();
// provide headers
            for (Map.Entry<String, String> key : headers.entrySet()) {
                conn.setRequestProperty(key.getKey(), key.getValue());
            }
            if (reqData.contains("Envelope")) {
                String encoding = Base64.getEncoder().encodeToString(headers.get("encodingDetails").getBytes());
                conn.setRequestProperty("Authorization", "Basic" + encoding);
            }
            conn.setRequestMethod(methodName);
// Setting ReqBody to connection
            if (!reqData.isEmpty() && !reqData.equals("NA")) {
                conn.setDoOutput(true);
                DataOutputStream wr = new DataOutputStream(conn.getOutputStream());
                wr.writeBytes(reqData);
                wr.flush();
                wr.close();
            }

            Log.info("## Request Url:" + restUrl + " for " + methodName);
            Log.info("## Request Body: " + reqData + " ##");

            statusCode = String.valueOf(conn.getResponseCode());


            if (conn.getResponseCode() != HttpsURLConnection.HTTP_OK
                    && conn.getResponseCode() != HttpsURLConnection.HTTP_ACCEPTED
                    && conn.getResponseCode() != HttpsURLConnection.HTTP_CREATED
                    && conn.getResponseCode() != HttpsURLConnection.HTTP_NO_CONTENT) {
                Log.info("## Response Code is: " + conn.getResponseCode() + " ##");
                BufferedReader inerror = new BufferedReader(new InputStreamReader(conn.getErrorStream()));
                while ((inputLine = inerror.readLine()) != null) {
                    response.append(inputLine);
                }
            } else {
                String fileName = "";
                String disposition = conn.getHeaderField("content-disposition");
                if (disposition != null) {
// extracts file name from header field
                    int index = disposition.indexOf("filename=");
                    if (index > 0) {
                        fileName = disposition.substring(index + 9, disposition.length());
                    }
                    System.out.println("content-disposition = " + disposition);

// opens input stream from the HTTP connection
                    InputStream inputStream = conn.getInputStream();
                    saveFilePath = System.getProperty("user.dir") + File.separator + "target" + File.separator + fileName;

//opens an output stream to save into file
                    FileOutputStream outputStream = new FileOutputStream(saveFilePath);

                    int bytesRead = -1;
                    byte[] buffer = new byte[BUFFER_SIZE];
                    while((bytesRead = inputStream.read(buffer)) != -1) {
                        outputStream.write(buffer, 0, bytesRead);
                    }

                    outputStream.close();
                    inputStream.close();
                    System.out.println("File Downloaded");
                } else {
                    Log.info("No File found in the attachment");
                }
                Log.info("## Response Code is: " + conn.getResponseCode() + " ##");
                BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine);
                }
            }
        } catch (Exception e) {

            Log.error(e.getMessage());
        } finally {
            if (headers.containsKey("Content-Type") && headers.get("Content-Type").equals("text/plain")) {
                try {
                    apiResponse.put("requestHeaders", headers.entrySet().toString());
                    apiResponse.put("responseHeader", conn.getHeaderFields().toString());
                    apiResponse.put("responseCode", statusCode);

                } catch (Exception e) {
                    Log.warn(e.getMessage());
                }
            } else {
                apiResponse.put("requestBody", reqData);
                apiResponse.put("requestHeaders", headers.entrySet().toString());
                apiResponse.put("responseHeader", conn.getHeaderFields().toString());
                apiResponse.put("responseCode", statusCode);
                apiResponse.put("responseMessage", response.toString());
                apiResponse.put("saveFilePath", saveFilePath);
            }
            //Log.info("## Response Message is : " + apiResponse.get("responseMessage") + "##\r\n");

            return apiResponse;


        }

    }
}

