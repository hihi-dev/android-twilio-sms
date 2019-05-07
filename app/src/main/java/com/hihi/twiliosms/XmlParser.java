package com.hihi.twiliosms;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.IOException;
import java.io.Reader;

class XmlParser {

    private static final String XML_TAG_BODY = "Body";
    private static final String XML_TAG_ERROR_CODE = "Code";

    static String parseBody(Reader reader) throws XmlPullParserException, IOException {
        XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
        factory.setNamespaceAware(true);
        XmlPullParser parser = factory.newPullParser();

        parser.setInput(reader);

        int eventType = parser.getEventType();
        while (eventType != XmlPullParser.END_DOCUMENT) {
            if (XML_TAG_BODY.equals(parser.getName()) && eventType == XmlPullParser.START_TAG) {
                parser.next();
                return parser.getText();
            }
            eventType = parser.next();
        }

        return "No message returned from the Twilio server.";
    }

    static String parseError(Reader reader) throws XmlPullParserException, IOException {
        XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
        factory.setNamespaceAware(true);
        XmlPullParser parser = factory.newPullParser();

        parser.setInput(reader);

        int eventType = parser.getEventType();
        while (eventType != XmlPullParser.END_DOCUMENT) {
            if (XML_TAG_ERROR_CODE.equals(parser.getName()) && eventType == XmlPullParser.START_TAG) {
                parser.next();
                return parser.getText();
            }
            eventType = parser.next();
        }

        return "No error code supplied by the Twilio server.";
    }

}
