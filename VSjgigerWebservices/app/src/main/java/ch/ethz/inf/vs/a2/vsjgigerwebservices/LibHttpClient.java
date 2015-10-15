package ch.ethz.inf.vs.a2.vsjgigerwebservices;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.Buffer;

import ch.ethz.inf.vs.a2.vsjgigerwebservices.http.SimpleHttpClient;


/**
 * Created by Andreas on 13.10.2015.
 */
public class LibHttpClient implements SimpleHttpClient {
    @Override
    public String execute(Object request) {


        if (!(request instanceof HttpGet)) return null;
        HttpGet getRequest = (HttpGet) request;
        HttpClient httpClient = new DefaultHttpClient();
        HttpResponse httpResponse = null;
        BufferedReader reader = null;
        String line, reply;
        try {
            httpResponse = httpClient.execute(getRequest);
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            reader = new BufferedReader(
                    new InputStreamReader(httpResponse.getEntity().getContent()));
        } catch (IOException e) {
            e.printStackTrace();
        }
        line = "";
        try {
            line = reader.readLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
        reply = line;
        try {
            while (line != null) {
                reply = reply + line;
                line = reader.readLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return reply;
    }
}
