package com.example.mobileproject;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

public class DetailedActivity extends AppCompatActivity {

    TextView quantity;
    int totalQuantity = 1;
    int totalPrice = 0;

    ImageView detailedImg;
    TextView price, rating, description;
    Button addToCart;
    ImageView addItem, removeItem;
    Toolbar toolbar;

    ViewAllModel viewAllModel = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detailed);

        detailedImg = findViewById(R.id.detailed_img);
        toolbar = findViewById(R.id.toolbar5);
        setSupportActionBar(toolbar);

        final Object object = getIntent().getSerializableExtra("detail");
        if (object instanceof ViewAllModel) {
            viewAllModel = (ViewAllModel) object;

        }

        quantity = findViewById(R.id.quantity);

        addItem = findViewById(R.id.add_item);
        removeItem = findViewById(R.id.remove_item);

        price = findViewById(R.id.detailed_price);
        rating = findViewById(R.id.detailed_rating);
        description = findViewById(R.id.detailed_dec);

        if (viewAllModel != null) {
            Glide.with(getApplicationContext()).load(viewAllModel.getImg_url()).into(detailedImg);
            rating.setText(viewAllModel.getRating());
            description.setText(viewAllModel.getDescription());
            price.setText("Price :$" + viewAllModel.getPrice() + "/gram");

            totalPrice = viewAllModel.getPrice() * totalQuantity;

            if (viewAllModel.getType().equals("bread")) {
                price.setText("Price :$" + viewAllModel.getPrice() + "/pack");
                totalPrice = viewAllModel.getPrice() * totalQuantity;
            }

            if (viewAllModel.getType().equals("canned")) {
                price.setText("Price :$" + viewAllModel.getPrice() + "/unit");
                totalPrice = viewAllModel.getPrice() * totalQuantity;
            }

            addToCart = findViewById(R.id.add_to_cart);
            addItem.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    if (totalQuantity < 10) {
                        totalQuantity++;
                        quantity.setText(String.valueOf(totalQuantity));
                    }

                }
            });

            removeItem.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    if (totalQuantity > 1) {
                        totalQuantity--;
                        quantity.setText(String.valueOf(totalQuantity));
                    }

                }
            });

        }
    }
}