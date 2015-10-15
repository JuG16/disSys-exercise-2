package ch.ethz.inf.vs.a2.vsjgigerwebservices.http;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.impl.client.DefaultHttpClient;

import android.util.Log;
import ch.ethz.inf.vs.a2.http.Requester;


public class XmlClient implements SimpleHttpClient{

    private static final String LOG_TAG = "Debugging";
    private HttpRequestBase request;

    public ApacheRequester() {
        Log.d(LOG_TAG, "XmlClient()");
        this.request = null;
    }

    public ApacheRequester(HttpRequestBase request) {
        Log.d(LOG_TAG, "XmlClient(request)");
        this.request = request;
    }

    public void setRequest(HttpGet request) {
        this.request = request;
    }

    public HttpRequestBase getRequest() {
        return this.request;
    }

    @Override
    public String execute(Object request) throws NullPointerException {
        Log.d(LOG_TAG, "executeRequest()");

        // Check request
        if (request == null) {
            throw new RuntimeException("XmlClient.executeRequest: request is null");
        }

        // Send request
        Log.d(LOG_TAG, "Creating request");
        HttpClient client = new DefaultHttpClient();
        HttpResponse response = null;
        try {
            response = client.execute(request);
        } catch (ClientProtocolException e1) {
            Log.e(LOG_TAG, "ClientProtocolException");
            e1.printStackTrace();
        } catch (IOException e1) {
            Log.e(LOG_TAG, "IOException");
            e1.printStackTrace();
        }

        // Read response
        Log.d(LOG_TAG, "Receiving response");
        InputStreamReader inputStreamReader = null;
        try {
            inputStreamReader = new InputStreamReader(response.getEntity().getContent());
        } catch (IOException e) {
            Log.e(LOG_TAG, "Could not create inputStreamReader");
            e.printStackTrace();
        }
        BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
        StringBuilder builder = new StringBuilder();
        String line = null;
        try {
            line = bufferedReader.readLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
        while(line != null) {
            builder.append(line);
            try {
                line = bufferedReader.readLine();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        try {
            bufferedReader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        Log.d(LOG_TAG, "finished reading the inputStream, chars read " + builder.toString().length());
        return builder.toString();
    }

}
