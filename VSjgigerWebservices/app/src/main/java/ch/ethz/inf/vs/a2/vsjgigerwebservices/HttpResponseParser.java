package ch.ethz.inf.vs.a2.vsjgigerwebservices;

import android.util.Log;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import ch.ethz.inf.vs.a2.vsjgigerwebservices.http.RemoteServerConfiguration;
import ch.ethz.inf.vs.a2.vsjgigerwebservices.sensor.ResponseParser;

/**
 * Created by Andreas on 12.10.2015.
 */
public class HttpResponseParser implements ResponseParser {

    private static final String REGULAR_EXPRESSION = "<span class=\"getterValue\">(\\S+)</span>";
    private static final String LOG_TAG = "Response Parser";

    @Override
    public double parseResponse(String response) {
        Pattern pattern = Pattern.compile(REGULAR_EXPRESSION);
        Matcher matcher = pattern.matcher(response);

        if (matcher.find()){
            String res = matcher.group(1);
            return Double.parseDouble(res);
        } else {
            Log.d(LOG_TAG, "did not get a right response to parse");
            return RemoteServerConfiguration.TEMPERATURE_ERROR;
        }

    }
}
