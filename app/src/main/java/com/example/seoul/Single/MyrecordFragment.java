package com.example.seoul.Single;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.seoul.R;


public class MyrecordFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        int resId = R.layout.fragment_myrecord;
        return inflater.inflate(resId, null);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState)
    {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onStart() {
        super.onStart();
        Log.i("log","Myrecord:onstart");
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.i("log","Myrecord:onresume");
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.i("log","Myrecord:onpause");
    }

    @Override
    public void onStop() {
        super.onStop();
        Log.i("log","Myrecord:onstart");
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        Log.i("log","Myrecord:ondestoryview");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.i("log","Myrecord:ondestory");
    }

    @Override
    public void onDetach() {
        super.onDetach();
        Log.i("log","Myrecord:ondetach");
    }
}
