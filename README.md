# android-twilio-sms
A simple Android library for Twilio Programmable SMS https://www.twilio.com/sms

Use with maven: ```implementation 'com.github.hihi-dev:twiliosms:0.1.1'```

# How to use
Create an instance of the Twilio library in your application, providing application context and String values of your TwilioSID and TwilioAuth tokens.

```Twilio twilio = Twilio.create(Context context, String sid, String token);```

Create an SMS message, providing The recipient's number, the senders number, the message body and a callback URL.
The callback URL is an optional field and be left null if you do not wish to utilise this functionality.

```TwilioMessage message = TwilioMessage.create(String to, String from, String body, String callback);```

Twilio SMS also allows media URLs, provide a list of String URL's to add media to the SMS.

```TwilioMessage message = TwilioMessage.create(String to, String from, String body, List<String> media, String callback);```

Finally, send the SMS providing your TwilioMessage. A Callable TwilioResponse will be returned containing the success state, respose code, and any relevant error message.

```twilio.send(TwilioMessage message);```
