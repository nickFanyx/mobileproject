package com.example.mobileproject;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mobileproject.Model.OrderlistActivity;
import com.example.mobileproject.adapters.OrderlistAdapter;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class ViewOrderList extends AppCompatActivity {

    FirebaseFirestore firestore;
    OrderlistAdapter viewAdapter;
    List<OrderlistActivity> viewOrderList;
    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_order_history);


        firestore = FirebaseFirestore.getInstance();
        String productName = getIntent().getStringExtra("productName");
        recyclerView = findViewById(R.id.orderlist);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        viewOrderList = new ArrayList<>();
        viewAdapter = new OrderlistAdapter(this,viewOrderList);
        recyclerView.setAdapter(viewAdapter);


        //get productName
        if(productName!= null && productName.equalsIgnoreCase("Apple")) {
            firestore.collection("Order").whereEqualTo("productName", "Apple").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                @Override
                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                    for (DocumentSnapshot documentSnapshot : task.getResult().getDocuments()) {
                        OrderlistActivity orderlistActivity = documentSnapshot.toObject(OrderlistActivity.class);
                        viewOrderList.add(orderlistActivity);
                        viewAdapter.notifyDataSetChanged();

                    }

                }
            });
        }

            //get productName
            if(productName!= null && productName.equalsIgnoreCase("Broccoli")){
                firestore.collection("Order").whereEqualTo("productName","Broccoli").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        for (DocumentSnapshot documentSnapshot:task.getResult().getDocuments()){
                            OrderlistActivity orderlistActivity = documentSnapshot.toObject(OrderlistActivity.class);
                            viewOrderList.add(orderlistActivity);
                            viewAdapter.notifyDataSetChanged();

                        }

                    }
                });
        }


    }


}
