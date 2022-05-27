package com.example.mobileproject;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mobileproject.databinding.ActivityShoppingCartBinding;

import org.w3c.dom.Text;

public class ShoppingCart extends AppCompatActivity {
    private TextView btnCheckout;
    private ActivityShoppingCartBinding binding;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding =ActivityShoppingCartBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        //Set toolbar
        SetToolbar();

        btnCheckout = findViewById(R.id.btnCheckout);

        binding.btnCheckout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(ShoppingCart.this, "ok", Toast.LENGTH_SHORT).show();
            }
        });



        HandleCheckOut();

    }

    private void HandleCheckOut(){
        btnCheckout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(ShoppingCart.this, "ok", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void SetToolbar() {
        binding.toolbar.toolbar.setTitle("Shopping Cart");
        setSupportActionBar(binding.toolbar.toolbar);
        binding.toolbar.toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                finish();

            }
        });
    }
}