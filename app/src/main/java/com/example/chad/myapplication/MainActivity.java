package com.example.chad.myapplication;

import android.content.Context;
import android.graphics.Color;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

/**
 *
 */
public class MainActivity extends ActionBarActivity implements SensorEventListener {

    private TextView textViewX;
    private TextView textViewY;
    private TextView textViewZ;
    private TextView textViewMoving;

    private SensorManager mSensorManager;
    private Sensor mAccelerometer;

    private float previousX;
    private float previousY;
    private float previousZ;

    private long previousTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        textViewX = (TextView) findViewById(R.id.textViewX);
        textViewY = (TextView) findViewById(R.id.textViewY);
        textViewZ = (TextView) findViewById(R.id.textViewZ);
        textViewMoving = (TextView) findViewById(R.id.textViewMoving);

        previousX = 0.0f;
        previousY = 0.0f;
        previousZ = 0.0f;

        textViewX.setText("X: ");
        textViewY.setText("Y: ");
        textViewZ.setText("Z: ");

        mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        mAccelerometer = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        mSensorManager.registerListener(this, mAccelerometer, SensorManager.SENSOR_DELAY_NORMAL);

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        Sensor mySensor = event.sensor;

        long currentTime = System.currentTimeMillis();

        if (mySensor.getType() == Sensor.TYPE_ACCELEROMETER) {

            if ((currentTime - previousTime) > 50) {
                if (Math.abs(previousX - event.values[0]) > 0.25 ||
                    Math.abs(previousY - event.values[1]) > 0.25 ||
                    Math.abs(previousZ - event.values[2]) > 0.25) {
                    textViewX.setText("X: " + event.values[0]);
                    textViewY.setText("Y: " + event.values[1]);
                    textViewZ.setText("Z: " + event.values[2]);
                    textViewMoving.setTextColor(Color.rgb(0,255,0));
                } else {
                    textViewMoving.setTextColor(Color.rgb(200,200,200));
                }

                previousTime = currentTime;
                previousX = event.values[0];
                previousY = event.values[1];
                previousZ = event.values[2];
            }

        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
}
