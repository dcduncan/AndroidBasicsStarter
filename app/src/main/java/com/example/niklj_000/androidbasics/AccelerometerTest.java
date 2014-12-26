package com.example.niklj_000.androidbasics;

import android.app.Activity;
import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.util.Log;
import android.view.WindowManager;
import android.widget.TextView;

/**
 * Created by niklj_000 on 12/25/2014.
 */
public class AccelerometerTest extends Activity implements SensorEventListener {
    TextView textView;
    StringBuilder builder = new StringBuilder();
    int screenRotation;

    static final int ACCELEROMETER_AXIS_SWAP[][] = {
            {1, -1, 0, 1},  //ROTATION_0
            {-1, -1, 1, 0}, //ROTATION_90
            {-1, 1, 0, 1},  //ROTATION_180
            {1, 1, 1, 0},   //ROTATION_270
    };

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        textView = new TextView(this);
        setContentView(textView);

        SensorManager manager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        if (manager.getSensorList(Sensor.TYPE_ACCELEROMETER).size() == 0) {
            textView.setText("No accelerometer installed.");
        } else {
            Sensor accelerometer = manager.getSensorList(Sensor.TYPE_ACCELEROMETER).get(0);
            if (!manager.registerListener(this, accelerometer, SensorManager.SENSOR_DELAY_GAME)) {
                textView.setText("Couldn't register sensor listener");
            }
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        WindowManager windowMgr = (WindowManager) getSystemService(Activity.WINDOW_SERVICE);
        screenRotation = windowMgr.getDefaultDisplay().getRotation();
    }

    public void onSensorChanged(SensorEvent event) {
        final int[] as = ACCELEROMETER_AXIS_SWAP[screenRotation];
        float screenX = (float) as[0] * event.values[as[2]];
        float screenY = (float) as[1] * event.values[as[3]];
        float screenZ = event.values[2];

        builder.setLength(0);
        builder.append("x: ");
        builder.append(screenX);
        builder.append("y, ");
        builder.append(screenY);
        builder.append("Z, ");
        builder.append(screenZ);
        String text = builder.toString();
        textView.setText(text);
        Log.d("AccelerometerText", text);
    }

    public void onAccuracyChanged(Sensor sensor, int accuracy) {
//        do nothing
    }
}
