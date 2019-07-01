package com.hihi.twiliosms;

public class TwilioResponse {

    private final int responseCode;
    private final String message;
    private final String errorCode;

    private TwilioResponse(int responseCode, String message, String errorCode) {
        this.responseCode = responseCode;
        this.message = message;
        this.errorCode = errorCode;
    }

    private static TwilioResponse create(int code, String message, String errorCode) {
        return new TwilioResponse(code, message, errorCode);
    }

    public boolean isSuccessful() {
        return errorCode == null;
    }

    public int getResponseCode() {
        return responseCode;
    }

    public String getMessage() {
        return message;
    }

    public String getErrorCode() {
        return errorCode;
    }

    static class Factory {

        private final ErrorMapSupplier errors;

        Factory(ErrorMapSupplier errors) {
            this.errors = errors;
        }

        TwilioResponse error(int responseCode, String errorCode) {
            final String body = errors.get().get(errorCode);
            if(body == null) {
                throw new IllegalArgumentException(String.format("Error Code '%s' does not correspond to known value", errorCode));
            }
            return TwilioResponse.create(responseCode, body, errorCode);
        }

        TwilioResponse success(int responseCode, String body) {
            return TwilioResponse.create(responseCode, body, null);
        }

    }

}
