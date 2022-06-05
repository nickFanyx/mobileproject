package com.example.mobileproject;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.Canvas;
import android.os.Bundle;
import android.view.View;

import com.example.mobileproject.Model.CartModel;
import com.example.mobileproject.adapters.AdapterCheckout;
import com.example.mobileproject.databinding.ActivityCheckoutBinding;

import java.util.ArrayList;

public class Checkout extends AppCompatActivity {
    private ActivityCheckoutBinding binding;
    private ArrayList<CartModel> cartModelArrayList;
    private float totalPrice, totalPayment;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding =ActivityCheckoutBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);
        SetToolbar();
        LoadBundle();
        SetupRecyclerview();

    }

    private void SetupRecyclerview() {
        AdapterCheckout adapter = new AdapterCheckout(this, cartModelArrayList);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        binding.recyclerViewOrders.setLayoutManager(layoutManager);
        binding.recyclerViewOrders.setHasFixedSize(true);
        binding.recyclerViewOrders.setItemViewCacheSize(20);
        binding.recyclerViewOrders.setDrawingCacheEnabled(true);
        binding.recyclerViewOrders.setDrawingCacheQuality(View.DRAWING_CACHE_QUALITY_HIGH);
        binding.recyclerViewOrders.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL) {
            @Override
            public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
                // Do not draw the divider
            }
        });

        binding.recyclerViewOrders.setAdapter(adapter);
    }


    private void LoadBundle() {
        Intent intent = getIntent();
        Bundle bundle = intent.getBundleExtra("BUNDLE");
        cartModelArrayList = bundle.getParcelableArrayList("cartList");
        totalPrice = bundle.getFloat("totalPrice");
        binding.tvSubtotal.setText("RM "+ String.format("%.2f", totalPrice));
        totalPayment=totalPrice+8;
        binding.tvTotalPayment.setText("RM "+ String.format("%.2f", totalPayment));
        binding.tvTotalPaymentBottom.setText("RM "+ String.format("%.2f", totalPayment));

    }

    private void SetToolbar() {
        binding.toolbar.toolbar.setTitle("Checkout");
        binding.toolbar.toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // back button pressed
                finish();
            }

        });
    }
}