package ch.ethz.inf.vs.a2.vsjgigerwebservices;

import android.util.Log;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.entity.StringEntity;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;
import java.io.IOException;
import java.io.StringReader;
import java.io.UnsupportedEncodingException;
import ch.ethz.inf.vs.a2.vsjgigerwebservices.sensor.AbstractSensor;

/**
 * Created by JG on 13.10.2015.
 */
public class XmlSensor extends AbstractSensor{
    private HttpRequestBase request;
    private static final String LOG_TAG = "XmlSensor";
    private static final String REQUEST_URL = "http://vslab.inf.ethz.ch:8080/SunSPOTWebServices/SunSPOTWebservice";
    private static final String REQUEST_XML = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><S:Envelope xmlns:S=\"http://schemas.xmlsoap.org/soap/envelope/\">" +
            "<S:Header/>" +
            "<S:Body>" +
            "<ns2:getSpot xmlns:ns2=\"http://webservices.vslecture.vs.inf.ethz.ch/\">" +
            "<id>Spot3</id>" +
            "</ns2:getSpot>" +
            "</S::Body>" +
            "</S:Envelope>";

    @Override
    public double parseResponse(String response) {
        Log.d(LOG_TAG, "parseResponse()");

        double temperature = -273.15;

        // Parse XML
        XmlPullParserFactory parserFactory = null;
        try {
            parserFactory = XmlPullParserFactory.newInstance();
            parserFactory.setNamespaceAware(true);

            XmlPullParser parser = parserFactory.newPullParser();
            parser.setInput( new StringReader(response) );


            // Iterate over XML to find temperature
            int eventType = parser.getEventType();
            boolean foundTemperature = false;
            while (eventType != XmlPullParser.END_DOCUMENT && !foundTemperature) {
                String tagname = parser.getName();
                switch (eventType) {
                    case XmlPullParser.START_TAG:
                        if (tagname.equalsIgnoreCase("temperature")) {
                            Log.d(LOG_TAG, "found temperature in XML");
                            foundTemperature = true;
                            parser.next();
                            temperature = Double.valueOf(parser.getText());
                        }
                        break;

                    default:
                        break;
                }

                eventType = parser.next();
            }

            if (!foundTemperature) {
                Log.e(LOG_TAG, "No temperature TAG found in XML");
            }
        } catch (XmlPullParserException e1) {
            Log.e(LOG_TAG, "Could not parse XML file: XmlPullParserException");
            e1.printStackTrace();
        } catch (IOException e) {
            Log.e(LOG_TAG, "Could not parse XML file: IOException");
            e.printStackTrace();
        }
        return temperature;
    }

    @Override
    public void getTemperature() throws NullPointerException {
        Log.d(LOG_TAG, "getTemperature()");

        HttpPost request = new HttpPost(REQUEST_URL);
        request.setHeader("Content-Type", "text/xml");

        // Add XML to post
        StringEntity se = null;
        try {
            se = new StringEntity(REQUEST_XML);
        } catch (UnsupportedEncodingException e) {
            Log.e(LOG_TAG, "Could not generate StringEntity");
            e.printStackTrace();
        }
        se.setContentType("text/xml");
        request.setEntity(se);
        SimpleHttpClient xmlClient = new XmlClient(request);
        // Create AsyncTask to execute the POST request
        new AsyncWorker().execute(xmlClient);
    }

    @Override
    protected void setHttpClient() {

    }
}
