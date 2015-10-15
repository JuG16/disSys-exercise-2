package ch.ethz.inf.vs.a2.vsjgigerwebservices;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.widget.TextView;
import android.net.NetworkInfo;
import android.widget.Toast;

import ch.ethz.inf.vs.a2.vsjgigerwebservices.http.RemoteServerConfiguration;
import ch.ethz.inf.vs.a2.vsjgigerwebservices.sensor.Sensor;
import ch.ethz.inf.vs.a2.vsjgigerwebservices.sensor.SensorFactory;
import ch.ethz.inf.vs.a2.vsjgigerwebservices.sensor.SensorListener;

/**
 * Created by Andreas on 13.10.2015.
 */
public class RestClient extends ActionBarActivity implements SensorListener {

    private final static String LOG_TAG = "RestClient";
    private TextView textView;
    private Sensor sensor;
    private String sensorType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rest_client);
        textView = (TextView) findViewById(R.id.rest_client_textview);
        textView.setText(R.string.looking_for_temperature);



        ConnectivityManager connectivityManager =
                (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        Intent intent = getIntent();
        sensorType = intent.getStringExtra(MainActivity.SENSOR_TYPE);

        setTitle(sensorType + " Request Sensor");

        if (networkInfo != null && networkInfo.isConnected()) {

            // device is online
            Toast.makeText(this,sensorType, Toast.LENGTH_SHORT).show(); // at this point i'd be pretty happy

            sensor = SensorFactory.getInstance(SensorFactory.Type.valueOf(sensorType));
            if(sensor != null) {
                sensor.registerListener(this); // hope it works and sensor is not null
                sensor.getTemperature(); // probable that in here somewhere there is an error

                Toast.makeText(this,"it's nicea", Toast.LENGTH_SHORT).show(); // at this point i'd be pretty happy

            } else
                Toast.makeText(this,"well shit..", Toast.LENGTH_SHORT).show(); // something failed
        } else {
            // device is offline
            Toast.makeText(this, R.string.no_internet, Toast.LENGTH_SHORT).show();
            finish();
        }
    }


    @Override
    public void onReceiveDouble(double value) {
        if (value == RemoteServerConfiguration.TEMPERATURE_ERROR)
            textView.setText(R.string.general_temperature_error_message);
        textView.setText(generateText() + Double.toString(value));
    }

    public String generateText(){
        Resources res = getResources();
        String text1 = String.format(res.getString(R.string.got_temperature));
        String text2 = String.format(res.getString(R.string.temperature_value_placement));
        return  text1+ sensorType + text2;
    }


    @Override
    public void onReceiveString(String message) {
        textView.setText("onRecieveString, mit message: "+message);
    }
}
