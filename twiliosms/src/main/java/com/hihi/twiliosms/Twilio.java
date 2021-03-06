package com.hihi.twiliosms;

import android.content.Context;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.Callable;

import static com.hihi.twiliosms.EncodeBase64.encodeCredentials;
import static com.hihi.twiliosms.TwilioMessageBody.KEY_MEDIA;
import static com.hihi.twiliosms.TwilioMessageBody.KEY_MEDIA_LIST;

public class Twilio {

    private static final String TWILIO_URL = "https://api.twilio.com/2010-04-01/";

    private final String url;
    private final String auth;
    private final TwilioApi api;

    private Twilio(Context context, String url, String auth) {
        this.url = url;
        this.auth = auth;
        api = new TwilioApi(new TwilioResponse.Factory(new ErrorMapSupplier(new ErrorMapParser(context.getResources()))));
    }

    public static Twilio create(Context context, String sid, String token) {
        return new Twilio(context, formatUrl(sid), encodeCredentials(sid, token));
    }

    public Callable<TwilioResponse> send(TwilioMessage message) {
        return api.call(url, auth, buildPostData(TwilioMessageBody.create(message)));
    }

    private String buildPostData(HashMap<String, List<String>> params) {
        StringBuilder stringBuilder = new StringBuilder();

        for (String key : params.keySet()) {
            stringBuilder.append("&");

            final String value = (KEY_MEDIA_LIST.equals(key)) ? KEY_MEDIA : key;
            for (String data : params.get(key)) {
                stringBuilder.append(value).append("=").append(encodePostData(data));
            }
        }
        return stringBuilder.toString();
    }

    private static String encodePostData(String data) {
        try {
            return URLEncoder.encode(data, Charset.forName("UTF-8").name());
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return null;
    }

    private static String formatUrl(String sid) {
        return String.format("%sAccounts/%s/Messages", TWILIO_URL, sid);
    }

}
