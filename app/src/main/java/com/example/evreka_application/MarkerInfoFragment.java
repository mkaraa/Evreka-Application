package com.example.evreka_application;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;


public class MarkerInfoFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;


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
        return inflater.inflate(R.layout.fragment_marker_info, container, false);
    }

    @Override
    public void onStart() {
        super.onStart();
        Log.d("OnStart", "fragmentA onStart");
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.d("OnResume", "fragmentA onresume");
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.d("onPause", "fragmentA onpause");
    }

    @Override
    public void onStop() {
        super.onStop();
        Log.d("onStop", "fragmentA onstop");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d("onDestroy", "fragmentA ondestroy ");
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        Log.d("OnDestroyView", "fragmentA ondestroyview");
    }

    @Override
    public void onDetach() {
        super.onDetach();
        Log.d("OnDetach", "fragmentA ondetach");
    }
}