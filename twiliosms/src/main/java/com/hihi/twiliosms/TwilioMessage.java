package com.hihi.twiliosms;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import java.util.List;

public class TwilioMessage {

    private String to;
    private String from;
    private String body;
    private List<String> media;
    private String callback;

    private TwilioMessage(String to, String from, String body, String callback) {
        this.to = to;
        this.from = from;
        this.body = body;
        this.callback = callback;
    }

    private TwilioMessage(String to, String from, String body, List<String> media, String callback) {
        this.to = to;
        this.from = from;
        this.body = body;
        this.media = media;
        this.callback = callback;
    }

    public static TwilioMessage create(@NonNull String to,
                                       @NonNull String from,
                                       @NonNull String body,
                                       @Nullable String callback) {
        return new TwilioMessage(to, from, body, callback);
    }

    public static TwilioMessage create(@NonNull String to,
                                       @NonNull String from,
                                       @NonNull String body,
                                       @NonNull List<String> media,
                                       @Nullable String callback) {
        return new TwilioMessage(to, from, body, media, callback);
    }

    String getTo() {
        return to;
    }

    String getFrom() {
        return from;
    }

    String getBody() {
        return body;
    }

    List<String> getMedia() {
        return media;
    }

    public String getCallback() {
        return callback;
    }
}
