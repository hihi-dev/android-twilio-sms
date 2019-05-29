package com.hihi.twiliosms;

import android.util.Base64;

class EncodeBase64 {

    static String encodeCredentials(String sid, String token) {
        return String.format("Basic %s", Base64.encodeToString((sid + ":" + token).getBytes(), Base64.NO_WRAP));
    }

}
