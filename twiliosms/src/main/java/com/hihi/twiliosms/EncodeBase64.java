package com.hihi.twiliosms;

import static android.util.Base64.NO_WRAP;
import static android.util.Base64.encodeToString;

class EncodeBase64 {

    static String encodeCredentials(String sid, String token) {
        return String.format("Basic %s", encodeToString(String.format("%s:%s", sid, token).getBytes(), NO_WRAP));
    }

}
