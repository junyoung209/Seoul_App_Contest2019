package com.example.seoul.Group;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.example.seoul.CrewcreateActivity;
import com.example.seoul.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;


public class GrouplistFragment extends Fragment {

    private RecyclerView rv;
    private LinearLayoutManager llm;
    private List<Integer> count;
    private int i = 0;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.fragment_grouplist, container,false);
        rv = (RecyclerView) view.findViewById(R.id.recyclerView);
        llm = new LinearLayoutManager(getActivity());
        llm.setOrientation(LinearLayoutManager.HORIZONTAL);
        count = new ArrayList<>();

        rv.setHasFixedSize(true);
        rv.setLayoutManager(llm);

        FloatingActionButton fab = (FloatingActionButton) view.findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), CrewcreateActivity.class);
                startActivity(intent);
            }
        });
        return view;
    }
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.main_btn_add : {
                i ++;
                count.add(i);
                RvAdapter adapter = new RvAdapter(getActivity(), count, i);
                rv.setAdapter(adapter);
                Log.d("Count", count + "");
                adapter.notifyDataSetChanged();
                break;
            }
        }
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState)
    {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onStart() {
        super.onStart();
        Log.i("log","grouplist:onstart");
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.i("log","grouplist:onresume");
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.i("log","grouplist:onpause");
    }

    @Override
    public void onStop() {
        super.onStop();
        Log.i("log","grouplist:onstart");
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        Log.i("log","grouplist:ondestoryview");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.i("log","grouplist:ondestory");
    }

    @Override
    public void onDetach() {
        super.onDetach();
        Log.i("log","grouplist:ondetach");
    }
}
