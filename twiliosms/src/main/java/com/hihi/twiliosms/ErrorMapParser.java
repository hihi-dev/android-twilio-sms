package com.hihi.twiliosms;

import android.content.res.Resources;
import android.util.Xml;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

class ErrorMapParser {

    private static final String KEY_CODE = "code";
    private static final String KEY_MESSAGE = "message";

    private final Map<String, String> errorMap = new HashMap<>();
    private final Resources resources;

    ErrorMapParser(Resources resources) {
        this.resources = resources;
    }

    Map<String, String> parse() throws JSONException {
        JSONArray values = new JSONArray(getJsonFromFile(resources.openRawResource(R.raw.twilio_error_codes)));

        for (int i = 0; i < values.length(); i ++) {
            JSONObject errorObject = values.getJSONObject(i);
            String code = errorObject.getString(KEY_CODE);
            String message = errorObject.getString(KEY_MESSAGE);
            errorMap.put(code, message);
        }

        return errorMap;
    }

    String getJsonFromFile(InputStream stream) {
        try {
            int size = stream.available();
            byte[] buffer = new byte[size];
            stream.read(buffer);
            return new String(buffer, Xml.Encoding.UTF_8.name());
        } catch (IOException ex) {
            ex.printStackTrace();
            return "";
        } finally {
            if(stream != null) {
                try {
                    stream.close();
                } catch (IOException e) { /* Ignore */ }
            }
        }
    }
}
