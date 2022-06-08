package com.example.mobileproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.example.mobileproject.databinding.ActivityEditAddressBinding;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class EditAddress extends AppCompatActivity {
    private ActivityEditAddressBinding binding;
    private String address;
    public static final String KEY_ADD = "ADDRESS";
    FusedLocationProviderClient fusedLocationProviderClient;
    private static final String TAG = "MyActivity";
    private LocationRequest locationRequest;
    private LocationCallback locationCallback;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityEditAddressBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);
        SetToolbar();
        loadBundle();

        binding.btnSaveAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String add = binding.ETAddress.getText().toString();
                Intent intent = new Intent();
                intent.putExtra(KEY_ADD, add);
                setResult(RESULT_OK, intent);
                finish();
            }
        });

        //define fused
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);

        locationRequest = LocationRequest.create();
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        locationRequest.setInterval(20 * 1000);

        binding.btnSaveCurr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    getLocation();
            }
        });

    }


    private void getLocation() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            ActivityCompat.requestPermissions(EditAddress.this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 44);
            return;
        }
        fusedLocationProviderClient.getLastLocation().addOnCompleteListener(new OnCompleteListener<Location>() {
            @Override
            public void onComplete(@NonNull Task<Location> task) {
                Location location = task.getResult();
                Log.i("location :", "harelah1");
                if (location != null) {
                    Log.i("location :", "harelah2");
                    try {
                        Log.i("location :", "harelah3");
                        Geocoder geocoder = new Geocoder(EditAddress.this, Locale.getDefault());
                        List<Address> addresses = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 1);
                        String add = addresses.get(0).getAddressLine(0);
                        Log.i("location :", " harelah4" + add);
                        Intent intent = new Intent();
                        intent.putExtra(KEY_ADD, add);
                        setResult(RESULT_OK, intent);
                        finish();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                } else {
                    Log.i("location :", "harelah1 location takde jir");
                }

            }
        });


    }

    private void loadBundle() {
        Intent intent = getIntent();
        if(intent.getBundleExtra("BUNDLE")!=null){
            Bundle bundle = intent.getBundleExtra("BUNDLE");
            if(bundle.getString("currAdd")!=null){
                binding.ETAddress.setText(bundle.getString("currAdd"));
            }
        }

    }


    private void SetToolbar() {
        binding.toolbar.toolbar.setTitle("Add Address");
        binding.toolbar.toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // back button pressed
                finish();
            }

        });
    }


}