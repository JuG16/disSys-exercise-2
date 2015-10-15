package ch.ethz.inf.vs.a2.vsjgigerwebservices;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import ch.ethz.inf.vs.a2.vsjgigerwebservices.sensor.SensorFactory;

public class MainActivity extends ActionBarActivity {

    public static final String SENSOR_TYPE = "SensorType";
    private final String LOG_TAG = "debuging";
    private Intent myIntent;
    private String sensType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        myIntent = new Intent(this, RestClient.class);


        Log.d(LOG_TAG, "blabla2");
        System.out.println("debuging test right here");
        Toast.makeText(this, Boolean.toString(0 != (-1 >> 31)), Toast.LENGTH_SHORT).show();


    }


    public void startRestClient() {
        myIntent.putExtra(this.SENSOR_TYPE, sensType);
        startActivity(myIntent);
    }

    public void onButtonClickRawHttp(View v) {
        sensType = SensorFactory.Type.RAW_HTTP.name();
        startRestClient();
        Toast.makeText(this, "button raw http clicked, senstype: " + sensType, Toast.LENGTH_SHORT).show();
    }

    public void onButtonClickLibrary(View v) {
        sensType = SensorFactory.Type.HTML.name();
        startRestClient();
        Toast.makeText(this, "button raw http clicked, senstype: " + sensType, Toast.LENGTH_SHORT).show();
    }

    public void onButtonClickJson(View v) {
        sensType = SensorFactory.Type.JSON.name();
        startRestClient();
        Toast.makeText(this, "button raw http clicked, senstype: " + sensType, Toast.LENGTH_SHORT).show();
    }
}