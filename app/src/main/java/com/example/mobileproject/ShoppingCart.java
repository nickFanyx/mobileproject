package com.example.mobileproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.paging.PagedList;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.Canvas;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mobileproject.Model.CartModel;
import com.example.mobileproject.adapters.CartAdapter;
import com.example.mobileproject.databinding.ActivityShoppingCartBinding;
import com.firebase.ui.firestore.paging.FirestorePagingAdapter;
import com.firebase.ui.firestore.paging.FirestorePagingOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class ShoppingCart extends AppCompatActivity implements CartAdapter.OnListItemClick {

    private ActivityShoppingCartBinding binding;
    private float totalPrice = 0;
    private ArrayList<CartModel> cartModelArrayList;
    private ArrayList<CartModel> selectedCart;

    private String userId;
    private FirebaseFirestore firebaseFirestore;
    private static final String TAG = "MyActivity";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityShoppingCartBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        selectedCart = new ArrayList<>();
        //Set toolbar

        SetToolbar();
        binding.cartShimmer.startShimmer();
        cartModelArrayList = new ArrayList<>();
        firebaseFirestore = FirebaseFirestore.getInstance();
        FetchDataFromDatabase();
        HandleOnClick();

    }

    private void HandleOnClick() {
        binding.btnCheckout.setOnClickListener(v -> {
            Intent intent = new Intent(ShoppingCart.this, Checkout.class);
            Bundle bundle = new Bundle();
            bundle.putParcelableArrayList("cartList", selectedCart);
            bundle.putFloat("totalPrice", totalPrice);
            intent.putExtra("BUNDLE", bundle);
            startActivity(intent);
            Log.i(TAG, "hare");
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


    private void FetchDataFromDatabase() {
        firebaseFirestore.collection("Cart").whereEqualTo("userId", "1").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                try {
                    if (task.isSuccessful()) {
                        binding.cartShimmer.stopShimmer();
                        binding.cartShimmer.setVisibility(View.GONE);
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            Log.i(TAG, "Error: " + document.getId());
                            CartModel cart = new CartModel(document.getId(), document.get("productId").toString(), document.get("productName").toString(), document.get("userId").toString(), Integer.parseInt(document.get("orderQty").toString()), Float.parseFloat(document.get("price").toString()), document.get("imageurl").toString());
                            //CartModel cart = document.toObject(CartModel.class);
                            cartModelArrayList.add(cart);
                        }
                        SetupRecyclerview();
                    }
                } catch (Exception e) {
                    binding.cartShimmer.stopShimmer();
                    binding.cartShimmer.setVisibility(View.GONE);
                    binding.layEmptyCart.setVisibility(View.VISIBLE);
                    Toast.makeText(ShoppingCart.this, "Nothing added yet " + e, Toast.LENGTH_LONG).show();
                    Log.i(TAG, "Error: " + e);
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                binding.cartShimmer.stopShimmer();
                binding.cartShimmer.setVisibility(View.GONE);
                binding.layEmptyCart.setVisibility(View.VISIBLE);
                Toast.makeText(ShoppingCart.this, "Please check your internet connection...", Toast.LENGTH_LONG).show();
            }
        });
    }

    private void SetupRecyclerview() {
        CartAdapter adapter = new CartAdapter(this, cartModelArrayList, this, binding.cartShimmer);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        binding.recyclerView.setLayoutManager(layoutManager);
        binding.recyclerView.setHasFixedSize(true);
        binding.recyclerView.setItemViewCacheSize(20);
        binding.recyclerView.setDrawingCacheEnabled(true);
        binding.recyclerView.setDrawingCacheQuality(View.DRAWING_CACHE_QUALITY_HIGH);
        binding.recyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL) {
            @Override
            public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
                // Do not draw the divider
            }
        });

        binding.recyclerView.setAdapter(adapter);

    }

    @Override
    public void onItemClick(int position, DocumentSnapshot documentSnapshot) {

    }

    private void SetTotalPrice(float price) {
        totalPrice += price;
        //Log.i(TAG, "Error: "+totalPrice);
        binding.tvTotalPrice.setText("RM" + String.format("%.2f", totalPrice));

    }

    @Override
    public void onCheckClick(int qty, float price, CartModel cartModel) {
        SetTotalPrice(price * qty);
        if (!selectedCart.contains(cartModel)) {
            selectedCart.add(cartModel);
            selectedCart.remove(cartModel);
            selectedCart.add(cartModel);
        }


        if (!selectedCart.isEmpty()) {
            binding.btnCheckout.setEnabled(true);
            binding.btnCheckout.setAlpha(1f);
        }
    }

    @Override
    public void onDeselectClick(int qty, float price, CartModel cartModel) {
        SetTotalPrice(-1 * price * qty);
        if (selectedCart.contains(cartModel)) {
            selectedCart.remove(cartModel);
        }
        if (selectedCart.isEmpty()) {
            binding.btnCheckout.setEnabled(false);
            binding.btnCheckout.setAlpha(0.8f);
        }


    }

    @Override
    public void onAddSelected(int qty, float price, CartModel cartModel) {

        SetTotalPrice(-1 * price * (qty - 1));
        if (selectedCart.contains(cartModel)) {
            selectedCart.remove(cartModel);
            selectedCart.add(cartModel);
            SetTotalPrice(price * qty);
        }


    }

    @Override
    public void onMinusSelected(int qty, float price, CartModel cartModel) {
        SetTotalPrice(-1 * price * (qty + 1));
        if (selectedCart.contains(cartModel)) {
            selectedCart.remove(cartModel);
            selectedCart.add(cartModel);
            SetTotalPrice(price * qty);
        }

    }

    @Override
    public void onRemoveItem(int qty, float price, CartModel cartModel) {
        SetTotalPrice(-1 * price * qty);
        if (selectedCart.contains(cartModel)) {
            selectedCart.remove(cartModel);
        }
        if (selectedCart.isEmpty()) {
            binding.btnCheckout.setEnabled(false);
            binding.btnCheckout.setAlpha(0.8f);
        }

    }

    @Override
    public void resetTotalPrice(float tempPrice) {
        totalPrice -= tempPrice;
        binding.tvTotalPrice.setText("RM" + String.format("%.2f", totalPrice));
    }


}