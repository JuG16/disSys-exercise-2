package ch.ethz.inf.vs.a2.vsjgigerwebservices.sensor;

import android.util.Log;
import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.PropertyInfo;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;

import ch.ethz.inf.vs.a2.vsjgigerwebservices.skeleton.SimpleHttpClientImpl;
import ch.ethz.inf.vs.a2.vsjgigerwebservices.http.HttpRawRequest;
import ch.ethz.inf.vs.a2.vsjgigerwebservices.http.HttpRawRequestFactory;
import ch.ethz.inf.vs.a2.vsjgigerwebservices.http.RemoteServerConfiguration;


/**
 * Created by JG on 13.10.2015.
 */
public class SoapSensor extends AbstractSensor {
    private static final String LOG_TAG = "SoapSensor";
    private static final String REQUEST_URL = "http://vslab.inf.ethz.ch:8080/SunSPOTWebServices/SunSPOTWebservice";
    private static final String SOAP_NAMESPACE = "http://webservices.vslecture.vs.inf.ethz.ch/";
    private static final String SOAP_METHOD = "getSpot";
    private static final String SOAP_SPOT = "Spot3";
    private String httpRequest;


    @Override
    protected void setHttpClient() {

}

    @Override
    public double parseResponse(String response) {
        Log.d(LOG_TAG, "parseResponse()");

        return Double.valueOf(response);
    }

    @Override
    public void getTemperature() throws NullPointerException {
        Log.d(LOG_TAG, "getTemperature()");
        SimpleHttpClient soapRequest = new SoapClient();
        new AsyncWorker().execute(soapRequest);

    }
}
