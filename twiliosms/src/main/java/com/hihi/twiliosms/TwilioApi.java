package com.hihi.twiliosms;

import org.xmlpull.v1.XmlPullParserException;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.concurrent.Callable;

class TwilioApi {

    private final TwilioResponse.Factory response;

    TwilioApi(TwilioResponse.Factory response) {
        this.response = response;
    }

    Callable<TwilioResponse> call(String url, String auth, String data) {
        return () -> {
            int responseCode = 0;
            String messageBody = null;
            String errorCode = null;

            HttpURLConnection httpURLConnection = null;
            DataOutputStream outputStream = null;
            try {
                URL u = new URL(url);
                httpURLConnection = (HttpURLConnection) u.openConnection();
                httpURLConnection.setDoOutput(false);
                httpURLConnection.setRequestProperty("Authorization", auth);
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setRequestProperty("Accept-Charset", "UTF-8");

                httpURLConnection.setReadTimeout(3000);
                httpURLConnection.setConnectTimeout(5000);

                httpURLConnection.connect();

                outputStream = new DataOutputStream(httpURLConnection.getOutputStream());
                outputStream.writeBytes(data);
                outputStream.flush();
                outputStream.close();

                responseCode = httpURLConnection.getResponseCode();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if(outputStream != null) {
                    outputStream.close();
                }
            }

            try {
                BufferedReader reader;
                String input;
                if (responseCode >= 200 && responseCode <= 299) {
                    InputStream inputStream = new BufferedInputStream(httpURLConnection.getInputStream());
                    reader = new BufferedReader(new InputStreamReader(inputStream));
                    while ((input = reader.readLine()) != null)
                        messageBody = XmlParser.parseBody(new StringReader(input));
                } else {
                    reader = new BufferedReader(new InputStreamReader(httpURLConnection.getErrorStream()));
                    while ((input = reader.readLine()) != null)
                        errorCode = XmlParser.parseError(new StringReader(input));
                }

            } catch (IOException e) {
                e.printStackTrace();
            } catch (XmlPullParserException e) {
                e.printStackTrace();
            } finally {
                if (httpURLConnection != null) {
                    httpURLConnection.disconnect();
                }
            }

            return createResponse(responseCode, messageBody, errorCode);
        };
    }

    private TwilioResponse createResponse(int responseCode, String messageBody, String errorCode) {
        if (errorCode == null)
            return response.success(responseCode, messageBody);
        else
            return response.error(responseCode, errorCode);
    }

}
