package ch.ethz.inf.vs.a2.vsjgigerwebservices.sensor;


import org.apache.http.client.methods.HttpGet;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.URI;
import java.net.URISyntaxException;

import ch.ethz.inf.vs.a2.vsjgigerwebservices.HttpResponseParser;
import ch.ethz.inf.vs.a2.vsjgigerwebservices.http.RemoteServerConfiguration;
import ch.ethz.inf.vs.a2.vsjgigerwebservices.http.SimpleHttpClientFactory;

/**
 * Created by Andreas on 13.10.2015.
 */
public class JsonSensor extends AbstractSensor {


    private AsyncWorker worker;
    private HttpGet requester;

    @Override
    protected void setHttpClient() {

        int port = RemoteServerConfiguration.REST_PORT;
        String host = RemoteServerConfiguration.HOST;
        String path = RemoteServerConfiguration.PATH;

        String request = "http://" + host + ":" + Integer.toString(port) + path;
        URI uri = null;
        try {
            uri = new URI(request);
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }

        requester = new HttpGet(uri);
        requester.setHeader("Accept","application/json");

        httpClient = SimpleHttpClientFactory.getInstance(SimpleHttpClientFactory.Type.LIB); // still use lib here, we want to use library and don't need to implement new stuff just because we get json back

        System.out.println("debug: Set up JsonClient");
    }


    @Override
    public double parseResponse(String response) {

        System.out.println("got json response: "+ response);
        double result = RemoteServerConfiguration.TEMPERATURE_ERROR;
        JSONObject obj = null;
        try {
            obj = new JSONObject(response);
            result = obj.getDouble("value");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return result;
    }

    @Override
    public void getTemperature() throws NullPointerException {
        worker = new AsyncWorker();
        worker.execute(requester);
    }
}
