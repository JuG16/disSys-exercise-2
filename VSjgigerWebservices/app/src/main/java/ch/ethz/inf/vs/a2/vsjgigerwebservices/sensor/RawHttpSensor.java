package ch.ethz.inf.vs.a2.vsjgigerwebservices.sensor;

import android.util.Log;

import ch.ethz.inf.vs.a2.vsjgigerwebservices.HttpRawRequestImpl;
import ch.ethz.inf.vs.a2.vsjgigerwebservices.HttpResponseParser;
import ch.ethz.inf.vs.a2.vsjgigerwebservices.SimpleHttpClientImpl;
import ch.ethz.inf.vs.a2.vsjgigerwebservices.http.HttpRawRequest;
import ch.ethz.inf.vs.a2.vsjgigerwebservices.http.HttpRawRequestFactory;
import ch.ethz.inf.vs.a2.vsjgigerwebservices.http.RemoteServerConfiguration;
import ch.ethz.inf.vs.a2.vsjgigerwebservices.http.SimpleHttpClient;
import ch.ethz.inf.vs.a2.vsjgigerwebservices.http.SimpleHttpClientFactory;
import ch.ethz.inf.vs.a2.vsjgigerwebservices.sensor.AbstractSensor;

/**
 * Created by Andreas on 12.10.2015.
 */
public class RawHttpSensor extends AbstractSensor {


    HttpRawRequestImpl requester;
    AsyncWorker worker;

    @Override
    protected void setHttpClient() {

        int port = RemoteServerConfiguration.REST_PORT;
        String host = RemoteServerConfiguration.HOST;
        String path = RemoteServerConfiguration.PATH;

        requester = new HttpRawRequestImpl(host, port, path);
        httpClient = SimpleHttpClientFactory.getInstance(SimpleHttpClientFactory.Type.RAW);

        System.out.println("debug: Set up RawHttpClient");


    }

    @Override
    public double parseResponse(String response) {
        System.out.println(response);
        HttpResponseParser parser = new HttpResponseParser();
        return parser.parseResponse(response);

    }

    @Override
    public void getTemperature() throws NullPointerException {

        worker = new AsyncWorker();
        worker.execute(requester);

    }


    /*private String httpRequest;
    private String httpResponse;
    private HttpRawRequestImpl httpRawRequest;


    @Override
    protected void setHttpClient() {
        // Generate request
        httpRawRequest = HttpRawRequestFactory.getInstance(RemoteServerConfiguration.HOST, RemoteServerConfiguration.REST_PORT, RemoteServerConfiguration.PATH);

        httpRequest = httpRawRequest.generateRequest();

        httpClient = new SimpleHttpClientImpl();



    }

    @Override
    public double parseResponse(String response) {
        HttpResponseParser parser = new HttpResponseParser();
        return parser.parseResponse(response);

    }

    @Override
    public void getTemperature() throws NullPointerException {
        *//*if(httpClient != null && httpRequest != null && httpRequest.length() != 0) {
            httpResponse = httpClient.execute(httpRequest);
        }*//*

        AsyncWorker worker = new AsyncWorker();
        worker.execute(httpRawRequest);
    }*/
}
