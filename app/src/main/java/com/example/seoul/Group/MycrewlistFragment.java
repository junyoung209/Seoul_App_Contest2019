package com.example.seoul.Group;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.seoul.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;


public class MycrewlistFragment extends Fragment {


    private RecyclerView crewlist_rv, schedule_rv;
    private LinearLayoutManager llm_vertical, llm_horizontal;
    private List<Integer> count1, count2;
    private int i = 0;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    TextView crew_create_text;

    @SuppressLint("WrongConstant")
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.fragment_mylist, container,false);
        crewlist_rv = (RecyclerView) view.findViewById(R.id.crewlist_recyclerView);
        schedule_rv = (RecyclerView) view.findViewById(R.id.schedule_recyclerView);
        llm_vertical = new LinearLayoutManager(getActivity());
        llm_vertical.setOrientation(LinearLayoutManager.VERTICAL);
        llm_horizontal = new LinearLayoutManager(getActivity());
        llm_horizontal.setOrientation(LinearLayoutManager.HORIZONTAL);

        count1 = new ArrayList<>();
        count2 = new ArrayList<>();

        crewlist_rv.setHasFixedSize(true);
        crewlist_rv.setLayoutManager(llm_horizontal);
        schedule_rv.setHasFixedSize(true);
        schedule_rv.setLayoutManager(llm_vertical);


        mSwipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.swipe_layout);
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                i ++;
                count1.add(i);
                count2.add(i);

                Mycrewlist_RvAdapter crewlist_adapter = new Mycrewlist_RvAdapter(getActivity(), count1, i);
                crewlist_rv.setAdapter(crewlist_adapter);
                Mycrewschedule_RvAdapter schedule_adapter = new Mycrewschedule_RvAdapter(getActivity(), count2, i);
                schedule_rv.setAdapter(schedule_adapter);

                crewlist_adapter.notifyDataSetChanged();
                schedule_adapter.notifyDataSetChanged();

                mSwipeRefreshLayout.setRefreshing(false);
            }
        });
        crew_create_text = (TextView) view.findViewById(R.id.crew_create);
        crew_create_text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), CrewcreateActivity.class);
                startActivity(intent);
            }
        });

        FloatingActionButton fab = (FloatingActionButton) view.findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), com.example.seoul.Group.CrewcreateActivity.class);
                startActivity(intent);
            }
        });
        return view;
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