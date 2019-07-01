package com.hihi.twiliosms;

import android.app.Activity;
import android.content.Context;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Callable;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertFalse;
import static junit.framework.TestCase.assertTrue;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

public class TwilioTest {

    private static final String ACCOUNT_SID = "ACCOUNT_SID";
    private static final String ACCOUNT_AUTH = "ACCOUNT_AUTH";

    private static final String SMS_SUCCESS_MESSAGE = "SMS_SUCCESS_MESSAGE";
    private static final String ERROR_CODE_NO_MATCH = "01";
    private static final String ERROR_CODE_PERMISSION_DENIED = "2003";
    private static final String ERROR_PERMISSION_DENIED = "Permission Denied";
    private static final String ERROR_CODE_INVALID_BODY = "21602";
    private static final String ERROR_INVALID_BODY = "Message body is required.";

    private static final String SMS_NUMBER = "123";
    private static final String SMS_BODY = "SMS BODY";

    @Mock TwilioMessage twilioMessage;
    @Mock Callable<TwilioResponse> twilioResponseCallable;
    @Mock TwilioApi twilioApi;
    @Mock ErrorMapSupplier supplier;

    private static Context context = new Activity();
    private Twilio twilio;
    private Map<String, String> errors = new HashMap<>();

    @Before
    public void setUp() {
        initMocks(this);
        createErrorMap();
        twilio = Twilio.create(context, ACCOUNT_SID, ACCOUNT_AUTH);
    }

    @Test
    public void givenApiRequest_whenSmsIsValid_thenSuccessResponseWith200ResponseCodeIsReturned() throws Exception {
        when(twilioMessage.getTo()).thenReturn(SMS_NUMBER);
        when(twilioMessage.getFrom()).thenReturn(SMS_NUMBER);
        when(twilioMessage.getBody()).thenReturn(SMS_BODY);

        TwilioResponse response = new TwilioResponse.Factory(supplier).success(200, SMS_SUCCESS_MESSAGE);
        when(twilioResponseCallable.call()).thenReturn(response);
        when(twilioApi.call(anyString(), anyString(), anyString())).thenReturn(twilioResponseCallable);

        twilio.send(twilioMessage);

        assertTrue(twilioResponseCallable.call().isSuccessful());
        assertEquals(200, twilioResponseCallable.call().getResponseCode());
    }

    @Test
    public void givenApiRequest_whenSmsIsValid_thenSuccessResponseWithMessageBodyIsReturned() throws Exception {
        when(twilioMessage.getTo()).thenReturn(SMS_NUMBER);
        when(twilioMessage.getFrom()).thenReturn(SMS_NUMBER);
        when(twilioMessage.getBody()).thenReturn(SMS_BODY);

        TwilioResponse response = new TwilioResponse.Factory(supplier).success(200, SMS_SUCCESS_MESSAGE);
        when(twilioResponseCallable.call()).thenReturn(response);
        when(twilioApi.call(anyString(), anyString(), anyString())).thenReturn(twilioResponseCallable);

        twilio.send(twilioMessage);

        assertTrue(twilioResponseCallable.call().isSuccessful());
        assertEquals(SMS_SUCCESS_MESSAGE, twilioResponseCallable.call().getMessage());
    }

    @Test
    public void givenApiRequest_whenBodyInvalid_thenErrorResponseWithPermissionDeniedErrorMessageIsReturned() throws Exception {
        when(twilioMessage.getTo()).thenReturn(SMS_NUMBER);
        when(twilioMessage.getFrom()).thenReturn(SMS_NUMBER);
        when(twilioMessage.getBody()).thenReturn("");

        when(supplier.get()).thenReturn(errors);
        TwilioResponse response = new TwilioResponse.Factory(supplier).error(401, ERROR_CODE_INVALID_BODY);
        when(twilioResponseCallable.call()).thenReturn(response);
        when(twilioApi.call(anyString(), anyString(), anyString())).thenReturn(twilioResponseCallable);

        twilio.send(twilioMessage);

        assertFalse(twilioResponseCallable.call().isSuccessful());
        assertEquals(ERROR_INVALID_BODY, twilioResponseCallable.call().getMessage());
    }

    @Test
    public void givenApiRequest_whenAuthInvalid_thenErrorResponseWithPermissionDeniedErrorMessageIsReturned() throws Exception {
        when(twilioMessage.getTo()).thenReturn(SMS_NUMBER);
        when(twilioMessage.getFrom()).thenReturn(SMS_NUMBER);
        when(twilioMessage.getBody()).thenReturn(SMS_BODY);

        when(supplier.get()).thenReturn(errors);
        TwilioResponse response = new TwilioResponse.Factory(supplier).error(401, ERROR_CODE_PERMISSION_DENIED);
        when(twilioResponseCallable.call()).thenReturn(response);
        when(twilioApi.call(anyString(), anyString(), anyString())).thenReturn(twilioResponseCallable);

        twilio.send(twilioMessage);

        assertFalse(twilioResponseCallable.call().isSuccessful());
        assertEquals(ERROR_PERMISSION_DENIED, twilioResponseCallable.call().getMessage());
    }

    @Test(expected= IllegalArgumentException.class)
    public void givenApiRequest_whenErrorReturnedWithNoMatchingErrorBody_thenIllegalArgumentExceptionIsThrown() throws Exception {
        when(twilioMessage.getTo()).thenReturn(SMS_NUMBER);
        when(twilioMessage.getFrom()).thenReturn(SMS_NUMBER);
        when(twilioMessage.getBody()).thenReturn(SMS_BODY);

        when(supplier.get()).thenReturn(errors);
        TwilioResponse response = new TwilioResponse.Factory(supplier).error(401, ERROR_CODE_NO_MATCH);
        when(twilioResponseCallable.call()).thenReturn(response);
        when(twilioApi.call(anyString(), anyString(), anyString())).thenReturn(twilioResponseCallable);

        twilio.send(twilioMessage);
    }

    private void createErrorMap() {
        errors.put(ERROR_CODE_PERMISSION_DENIED, ERROR_PERMISSION_DENIED);
        errors.put(ERROR_CODE_INVALID_BODY, ERROR_INVALID_BODY);
    }

}