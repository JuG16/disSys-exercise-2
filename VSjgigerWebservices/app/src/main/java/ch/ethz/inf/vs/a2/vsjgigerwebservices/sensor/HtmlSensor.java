package ch.ethz.inf.vs.a2.vsjgigerwebservices.sensor;


import org.apache.http.client.methods.HttpGet;

import java.net.URI;
import java.net.URISyntaxException;

import ch.ethz.inf.vs.a2.vsjgigerwebservices.HttpRawRequestImpl;
import ch.ethz.inf.vs.a2.vsjgigerwebservices.HttpResponseParser;
import ch.ethz.inf.vs.a2.vsjgigerwebservices.http.RemoteServerConfiguration;
import ch.ethz.inf.vs.a2.vsjgigerwebservices.http.SimpleHttpClientFactory;

/**
 * Created by Andreas on 14.10.2015.
 */
public class HtmlSensor extends AbstractSensor{

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


        httpClient = SimpleHttpClientFactory.getInstance(SimpleHttpClientFactory.Type.LIB);

        System.out.println("debug: Set up HtmlClient");
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
}
