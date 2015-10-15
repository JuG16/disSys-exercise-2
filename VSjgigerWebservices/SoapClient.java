package ch.ethz.inf.vs.a2.vsjgigerwebservices.http;

import java.io.IOException;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.PropertyInfo;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;
import org.xmlpull.v1.XmlPullParserException;

import android.util.Log;
import ch.ethz.inf.vs.a2.http.RemoteServerConfiguration;
import ch.ethz.inf.vs.a2.http.Requester;


public class SoapClient implements SimpleHttpClient {

    private static final String LOG_TAG = "SoapClient";
    public SoapClient(){}

    @Override
    public String execute(Object request) throws NullPointerException {
        SoapObject soapObj = new SoapObject();
        soapObj = new SoapObject(SoapSensor.SOAP_NAMESPACE, SoapSensor.SOAP_METHOD);

        PropertyInfo property = new PropertyInfo();
        {
            property.setName("id");
            property.type = PropertyInfo.STRING_CLASS;
            property.setValue(SoapSensor.SOAP_SPOT);
        }
        soapObj.addProperty(property);

        SoapSerializationEnvelope soapEnvelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
        soapEnvelope.setOutputSoapObject(soapObj);
        soapEnvelope.implicitTypes = true;

        String temperature = null;
        try {
            HttpTransportSE httpTransport = new HttpTransportSE(SoapSensor.REQUEST_URL);
            httpTransport.debug = true;

            httpTransport.call(SoapSensor.REQUEST_URL, soapEnvelope);

            // Get response
            SoapObject response;
            response = (SoapObject) soapEnvelope.getResponse();

            // Parse temperature from response
            temperature = response.getPropertyAsString("temperature");

        } catch (IOException e) {
            Log.e(LOG_TAG, "Could not get temperature: IOException");
            e.printStackTrace();
        } catch (XmlPullParserException e) {
            Log.e(LOG_TAG, "Could not get temperature: XmlPullParserException");
            e.printStackTrace();
        }

        if (temperature == null) {
            Log.e(LOG_TAG, "Could not get temperature.");
        }

        return temperature;
    }

}
