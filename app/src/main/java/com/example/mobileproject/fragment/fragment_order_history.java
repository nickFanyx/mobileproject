package com.example.mobileproject.fragment;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.example.mobileproject.Model.OrderModel;
import com.example.mobileproject.Model.OrderlistModel;
import com.example.mobileproject.NavCategoryModel;
import com.example.mobileproject.R;
import com.example.mobileproject.adapters.NavCategoryAdapter;
import com.example.mobileproject.adapters.OrderAdapter;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link fragment_order_history#newInstance} factory method to
 * create an instance of this fragment.
 */
public class fragment_order_history extends Fragment {

    FirebaseFirestore db;

    RecyclerView recyclerView;
    List<OrderModel> orderlistModel;
    OrderAdapter orderAdapter;






    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;


    public fragment_order_history() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment fragment_order_history.
     */
    // TODO: Rename and change types and number of parameters
    public static fragment_order_history newInstance(String param1, String param2) {
        fragment_order_history fragment = new fragment_order_history();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_order_history, container, false);





        db = FirebaseFirestore.getInstance();
        recyclerView = root.findViewById(R.id.orderlist);

        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(),RecyclerView.VERTICAL,false));
        orderlistModel = new ArrayList<>();
        orderAdapter = new OrderAdapter(getActivity(),orderlistModel);
        recyclerView.setAdapter(orderAdapter);
        db.collection("Order")

                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {

                                OrderModel orderlistModels = document.toObject(OrderModel.class);
                                orderlistModel.add(orderlistModels);
                                orderAdapter.notifyDataSetChanged();
                            }
                        } else {

                            Toast.makeText(getActivity(),"Error" + task.getException(), Toast.LENGTH_SHORT);
                        }
                    }
                });
















        return root;
    }


}