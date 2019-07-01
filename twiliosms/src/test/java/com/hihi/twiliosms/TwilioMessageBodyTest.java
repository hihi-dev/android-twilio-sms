package com.hihi.twiliosms;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static com.hihi.twiliosms.TwilioMessageBody.KEY_BODY;
import static com.hihi.twiliosms.TwilioMessageBody.KEY_CALLBACK;
import static com.hihi.twiliosms.TwilioMessageBody.KEY_FROM;
import static com.hihi.twiliosms.TwilioMessageBody.KEY_MEDIA_LIST;
import static com.hihi.twiliosms.TwilioMessageBody.KEY_TO;
import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertNotNull;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

public class TwilioMessageBodyTest {

    private static final String SMS_NUMBER = "123";
    private static final String SMS_BODY = "SMS BODY";
    private static final String SMS_CALLBACK = "SMS CALLBACK";
    private static final List<String> SMS_MEDIA_LIST = new ArrayList<>();

    @Mock TwilioMessage twilioMessage;

    @Before
    public void setUp() {
        initMocks(this);
    }

    @Test
    public void givenNewTwilioMessage_whenToFromAndBodyAreProvided_thenMessageBodyContainsToFromAndBody() throws Exception {
        when(twilioMessage.getTo()).thenReturn(SMS_NUMBER);
        when(twilioMessage.getFrom()).thenReturn(SMS_NUMBER);
        when(twilioMessage.getBody()).thenReturn(SMS_BODY);

        HashMap<String, List<String>> messageBody = TwilioMessageBody.create(twilioMessage);

        assertNotNull(messageBody.get(KEY_TO));
        assertNotNull(messageBody.get(KEY_FROM));
        assertNotNull(messageBody.get(KEY_BODY));
    }

    @Test
    public void givenNewTwilioMessage_whenCallbackUrlIsProvided_thenMessageBodyContainsCallbackUrl() throws Exception {
        when(twilioMessage.getTo()).thenReturn(SMS_NUMBER);
        when(twilioMessage.getFrom()).thenReturn(SMS_NUMBER);
        when(twilioMessage.getBody()).thenReturn(SMS_BODY);
        when(twilioMessage.getCallback()).thenReturn(SMS_CALLBACK);

        HashMap<String, List<String>> messageBody = TwilioMessageBody.create(twilioMessage);

        assertNotNull(messageBody.get(KEY_CALLBACK));
    }

    @Test
    public void givenNewTwilioMessage_whenMediaIsProvided_thenMessageBodyContainsMediaParameters() throws Exception {
        SMS_MEDIA_LIST.add("Media1");
        SMS_MEDIA_LIST.add("Media2");
        SMS_MEDIA_LIST.add("Media3");

        when(twilioMessage.getTo()).thenReturn(SMS_NUMBER);
        when(twilioMessage.getFrom()).thenReturn(SMS_NUMBER);
        when(twilioMessage.getBody()).thenReturn(SMS_BODY);
        when(twilioMessage.getMedia()).thenReturn(SMS_MEDIA_LIST);

        HashMap<String, List<String>> messageBody = TwilioMessageBody.create(twilioMessage);

        assertEquals(messageBody.get(KEY_MEDIA_LIST), SMS_MEDIA_LIST);
    }

}