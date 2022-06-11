package com.example.mobileproject.fragment;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.mobileproject.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link fragment_about#newInstance} factory method to
 * create an instance of this fragment.
 */
public class fragment_about extends Fragment {

    ImageView facebookIC,instagramIC;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public fragment_about() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment fragment_about.
     */
    // TODO: Rename and change types and number of parameters
    public static fragment_about newInstance(String param1, String param2) {
        fragment_about fragment = new fragment_about();
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
        View root =  inflater.inflate(R.layout.fragment_about, container, false);

        facebookIC = root.findViewById(R.id.facebook_icon);
        instagramIC = root.findViewById(R.id.instagram_icon);

        facebookIC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String sAppLink = "fb://page/237564710351658";
                String sPackage = "com.facebook.katana";
                String sWebLink = "https://www.facebook.com/12H1rDeveloper";
                
                openLink(sAppLink,sPackage,sWebLink);
            }
        });

        instagramIC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String sAppLink = "https://www.instagram.com/androidcoding_";
                String sPackage = "com.instagram.android";

                openLink(sAppLink,sPackage,sAppLink);
            }
        });



        return root;
    }

    private void openLink(String sAppLink, String sPackage, String sWebLink) {

        try {
            Uri uri = Uri.parse(sAppLink);

            Intent intent = new Intent(Intent.ACTION_VIEW);

            intent.setData(uri);

            intent.setPackage(sPackage);

            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

            startActivity(intent);
        }catch (ActivityNotFoundException activityNotFoundException){
            Uri uri = Uri.parse(sWebLink);

            Intent intent = new Intent(Intent.ACTION_VIEW);

            intent.setData(uri);

            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

            startActivity(intent);
        }
    }
}