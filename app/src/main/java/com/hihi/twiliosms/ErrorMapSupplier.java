package com.hihi.twiliosms;

import org.json.JSONException;

import java.util.HashMap;
import java.util.Map;

class ErrorMapSupplier {

    private final ErrorMapParser parser;
    private final Map<String, String> errors;

    ErrorMapSupplier(ErrorMapParser parser) {
        this.parser = parser;
        errors = new HashMap<>();
    }

    Map<String, String> get() {
        if(errors.isEmpty()) {
            try {
                errors.putAll(parser.parse());
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return errors;
    }

}
