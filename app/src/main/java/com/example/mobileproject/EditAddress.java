package com.example.mobileproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.mobileproject.databinding.ActivityEditAddressBinding;

public class EditAddress extends AppCompatActivity {
    private ActivityEditAddressBinding binding;
    private String address;
    public static final String KEY_ADD="ADDRESS";


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
                String add=binding.ETAddress.getText().toString();
                Intent intent = new Intent();
                intent.putExtra(KEY_ADD,add);
                setResult(RESULT_OK, intent);
                finish();
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