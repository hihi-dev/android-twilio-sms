package com.hihi.twiliosms;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;

class TwilioMessageBody {

    static final String KEY_TO = "To";
    static final String KEY_FROM = "From";
    static final String KEY_BODY = "Body";
    static final String KEY_MEDIA_LIST = "MediaUrlList";
    static final String KEY_MEDIA = "MediaUrl";
    static final String KEY_CALLBACK = "StatusCallback";

    static HashMap<String, List<String>> create(TwilioMessage message) {
        HashMap<String, List<String>> params = new HashMap<>();
        params.put(KEY_TO, Collections.singletonList(message.getTo()));
        params.put(KEY_FROM, Collections.singletonList(message.getFrom()));
        params.put(KEY_BODY, Collections.singletonList(message.getBody()));

        if (message.getMedia() != null)
            params.put(KEY_MEDIA_LIST, message.getMedia());

        if (message.getCallback() != null)
            params.put(KEY_CALLBACK, Collections.singletonList(message.getCallback()));

        return params;
    }

}
