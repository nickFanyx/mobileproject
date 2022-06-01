package com.example.mobileproject.fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.mobileproject.HomeCategory;
import com.example.mobileproject.PopularModel;
import com.example.mobileproject.R;
import com.example.mobileproject.adapters.HomeAdapter;
import com.example.mobileproject.adapters.PopularAdapters;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

///**
 //* A simple {@link Fragment} subclass.
 //* Use the {@link fragment_home#newInstance} factory method to
 //* create an instance of this fragment.
 //*/
public class fragment_home extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
   // private static final String ARG_PARAM1 = "param1";
    //private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    //private String mParam1;
    //private String mParam2;

    //public fragment_home() {
        // Required empty public constructor
    //}

   // /**
    // * Use this factory method to create a new instance of
    // * this fragment using the provided parameters.
    // *
    // * @param param1 Parameter 1.
    // * @param param2 Parameter 2.
    // * @return A new instance of fragment fragment_category.
     //*/
    // TODO: Rename and change types and number of parameters
  //  public static fragment_home newInstance(String param1, String param2) {
   //     fragment_home fragment = new fragment_home();
   //     Bundle args = new Bundle();
   //     args.putString(ARG_PARAM1, param1);
   //     args.putString(ARG_PARAM2, param2);
   //     fragment.setArguments(args);
   //     return fragment;
   // }

   // @Override
   // public void onCreate(Bundle savedInstanceState) {
       // super.onCreate(savedInstanceState);
      //  if (getArguments() != null) {
     //       mParam1 = getArguments().getString(ARG_PARAM1);
     //       mParam2 = getArguments().getString(ARG_PARAM2);
      //  }
   // }

    RecyclerView popularRec,homeCatRec;
    FirebaseFirestore db;

    //Popular Items
    List<PopularModel> popularModelList;
    PopularAdapters popularAdapters;

    //Home Category
    List<HomeCategory> categoryList;
    HomeAdapter homeAdapter;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_home, container, false);
        db = FirebaseFirestore.getInstance();

        popularRec = root.findViewById(R.id.pop_rec);
        homeCatRec = root.findViewById(R.id.explore_rec);

        //Popular Items
        popularRec.setLayoutManager(new LinearLayoutManager(getActivity(),RecyclerView.HORIZONTAL,false));
        popularModelList = new ArrayList<>();
        popularAdapters = new PopularAdapters(getActivity(),popularModelList);
        popularRec.setAdapter(popularAdapters);

        db.collection("PopularProducts")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {

                                PopularModel popularModel = document.toObject(PopularModel.class);
                                popularModelList.add(popularModel);
                                popularAdapters.notifyDataSetChanged();
                            }
                        } else {

                            Toast.makeText(getActivity(),"Error" + task.getException(), Toast.LENGTH_SHORT);
                        }
                    }
                });

        //Home Category
        homeCatRec.setLayoutManager(new LinearLayoutManager(getActivity(),RecyclerView.HORIZONTAL,false));
        categoryList = new ArrayList<>();
        homeAdapter = new HomeAdapter(getActivity(),categoryList);
        homeCatRec.setAdapter(homeAdapter);

        db.collection("HomeCategory")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {

                                HomeCategory homeCategory = document.toObject(HomeCategory.class);
                                categoryList.add(homeCategory);
                                homeAdapter.notifyDataSetChanged();
                            }
                        } else {

                            Toast.makeText(getActivity(),"Error" + task.getException(), Toast.LENGTH_SHORT);
                        }
                    }
                });

        return root;
    }
}