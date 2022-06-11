package com.example.mobileproject;

import androidx.appcompat.app.AppCompatActivity;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

public class activity_light extends AppCompatActivity implements SensorEventListener {

    TextView textView;
    ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_light);

        imageView = findViewById(R.id.image_light);

        SensorManager sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);

        if (sensorManager != null) {
            Sensor lightSensor = sensorManager.getDefaultSensor(Sensor.TYPE_LIGHT);

            if (lightSensor != null) {
                sensorManager.registerListener(this, lightSensor, SensorManager.SENSOR_DELAY_NORMAL);}

            } else {
                Toast.makeText(this, "Sensor not", Toast.LENGTH_SHORT).show();
            }


        }


        @Override
        public void onSensorChanged (SensorEvent event) {

            if (event.values[0]>40) {
                imageView.setImageResource(R.drawable.discount);

            }else{
                imageView.setImageResource(R.drawable.shake);
            }
        }
        @Override
        public void onAccuracyChanged (Sensor sensor,int i){

        }
    }
