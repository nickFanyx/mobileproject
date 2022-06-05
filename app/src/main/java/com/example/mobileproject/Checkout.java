package com.example.mobileproject;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.Canvas;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.mobileproject.Model.CartModel;
import com.example.mobileproject.Model.OrderModel;
import com.example.mobileproject.adapters.AdapterCheckout;
import com.example.mobileproject.databinding.ActivityCheckoutBinding;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;

public class Checkout extends AppCompatActivity {
    private ActivityCheckoutBinding binding;
    private ArrayList<CartModel> cartModelArrayList;
    private float totalPrice, totalPayment;
    private ActivityResultLauncher<Intent> address;
    private String currAdd="";
    private FirebaseFirestore db;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding =ActivityCheckoutBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);
        SetToolbar();
        LoadBundle();
        SetupRecyclerview();

        db=FirebaseFirestore.getInstance();
        address=registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
            @Override
            public void onActivityResult(ActivityResult result) {
                if (result !=null && result.getResultCode()== RESULT_OK){
                    if (result.getData()!=null && result.getData().getStringExtra(EditAddress.KEY_ADD)!=null){

                        binding.tvAddress.setText(result.getData().getStringExtra(EditAddress.KEY_ADD));
                        currAdd=result.getData().getStringExtra(EditAddress.KEY_ADD);
                    }else{
                        binding.tvAddress.setText("Please add your address to proceed...");
                    }
                }

            }
        });

        HandleOnClick();



    }

    private void HandleOnClick() {
        binding.tvAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Checkout.this, EditAddress.class);
                if(currAdd!=null){
                    Bundle bundle = new Bundle();
                    bundle.putString("currAdd",currAdd);
                    intent.putExtra("BUNDLE",bundle);
                }
                address.launch(intent);



            }
        });

        binding.btnPlaceOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (currAdd==""){
                    Toast.makeText(Checkout.this, "Please enter delivery address!", Toast.LENGTH_SHORT).show();
                }else{
                    for (CartModel cart : cartModelArrayList){
                        db.collection("Cart").document(cart.getItemId()).delete();
                    }
                    CollectionReference ref = db.collection("Order");

                    String orderId=ref.document().getId();
                    OrderModel order= new OrderModel(orderId,"1", totalPrice, totalPayment,currAdd,cartModelArrayList);
                    ref.document(orderId).set(order).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void unused) {

                            startActivity(new Intent(Checkout.this, PaymentSuccessful.class));
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(Checkout.this, "Failed", Toast.LENGTH_SHORT).show();
                        }
                    });

                }

            }
        });
    }

    @Override
    public void onActivityReenter(int resultCode, Intent data) {
        super.onActivityReenter(resultCode, data);
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