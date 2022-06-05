package com.example.mobileproject;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.graphics.drawable.Drawable;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class ShakeActivity extends AppCompatActivity {

    private TextView textView;
    private ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textView = findViewById(R.id.textView);
        imageView = findViewById(R.id.imageView);

        SensorManager sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        Sensor sensorShake = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        SensorEventListener sensorEventListener = new SensorEventListener() {
            @Override
            public void onSensorChanged(SensorEvent sensorEvent) {
                if (sensorEvent!=null){
                    float x_accl = sensorEvent.values[0];
                    float y_accl = sensorEvent.values[1];
                    float z_accl = sensorEvent.values[2];

                    float floatSum = Math.abs(x_accl) + Math.abs(y_accl) + Math.abs(z_accl);


//                    x_accl > 2 ||
//                            x_accl < -2 ||
//                            y_accl > 12 ||
//                            y_accl < -12 ||
                    //                           z_accl > 2 ||
                    //                           z_accl < -2

                    if (floatSum > 14){
                        textView.setText("Congratulation !!!");
                        imageView.setImageResource(R.drawable.voucher);
                        Toast.makeText(ShakeActivity.this,"Can Redeem At Home Page...",Toast.LENGTH_LONG)
                                .show();

                    }
                    else {
                        textView.setText("Shake To WIN Voucher !!!");
                        imageView.setImageResource(R.drawable.shake);
                    }
                }
            }

            @Override
            public void onAccuracyChanged(Sensor sensor, int i) {

            }
        };


        sensorManager.registerListener(sensorEventListener,sensorShake,SensorManager.SENSOR_DELAY_NORMAL);

    }
}