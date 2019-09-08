package com.example.seoul.Group.Grouplist;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.seoul.R;


public class GrouplistFragment extends Fragment {

    String text = "";
    TextView textView;
    SwipeRefreshLayout mSwipeRefreshLayout;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View v=inflater.inflate(R.layout.fragment_grouplist, container, false);
        textView = (TextView)v.findViewById(R.id.textview12);

        mSwipeRefreshLayout = (SwipeRefreshLayout) v.findViewById(R.id.swipe_layout);
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                text+="1\n";
                textView.setText(text);
                mSwipeRefreshLayout.setRefreshing(false);
            }
        });

        // 100 줄의 텍스트를 생성합니다.
        for(int i=0; i<100; i++)
            text += i + "\n";
        textView.setText(text);
        return v;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState)
    {
        super.onActivityCreated(savedInstanceState);
    }


    @Override
    public void onStart() {
        super.onStart();
        Log.i("log","mylist:onstart");
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.i("log","mylist:onresume");
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.i("log","mylist:onpause");
    }

    @Override
    public void onStop() {
        super.onStop();
        Log.i("log","mylist:onstart");
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        Log.i("log","mylist:ondestoryview");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.i("log","mylist:ondestory");
    }

    @Override
    public void onDetach() {
        super.onDetach();
        Log.i("log","mylist:ondetach");
    }


}
