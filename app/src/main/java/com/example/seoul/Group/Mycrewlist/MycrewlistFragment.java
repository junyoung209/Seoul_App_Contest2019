package com.example.seoul.Group.Mycrewlist;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.example.seoul.Group.GroupdataCallBack;
import com.example.seoul.Group.GroupData;
import com.example.seoul.R;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


public class MycrewlistFragment extends Fragment {


    private RecyclerView crewlist_rv, schedule_rv;
    private LinearLayoutManager llm_vertical, llm_horizontal;

    private Mycrewlist_RvAdapter crewlist_adapter;
    private Mycrewschedule_RvAdapter schedule_adapter;

    private List<Integer> count1, count2;
    private int i = 0;
    private SwipeRefreshLayout refresh;
    private Button crew_create_text;

    private ProgressDialog pdialog;
    private String userID;
    private String crewName;
    private String crewRegion;
    private String crewHost;
    private String crewInfo;

    private ArrayList<GroupData> groupData=new ArrayList<>();


    @SuppressLint("WrongConstant")
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.fragment_mylist, container,false);
        pdialog=new ProgressDialog(getActivity());
        pdialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        pdialog.setMessage("Loading");
        pdialog.show();


        crewlist_rv = (RecyclerView) view.findViewById(R.id.crewlist_recyclerView);
        schedule_rv = (RecyclerView) view.findViewById(R.id.schedule_recyclerView);
        llm_vertical = new LinearLayoutManager(getActivity());
        llm_vertical.setOrientation(LinearLayoutManager.VERTICAL);
        llm_horizontal = new LinearLayoutManager(getActivity());
        llm_horizontal.setOrientation(LinearLayoutManager.HORIZONTAL);

        Bundle bundle=this.getArguments();
        if(bundle!=null)
        {
            userID=bundle.getString("userID");
        }
        Log.i("log",userID+"crewlist");


        count2 = new ArrayList<>();

        crewlist_rv.setHasFixedSize(true);
        crewlist_rv.setLayoutManager(llm_horizontal);
        schedule_rv.setHasFixedSize(true);
        schedule_rv.setLayoutManager(llm_vertical);

        refresh = (SwipeRefreshLayout) view.findViewById(R.id.mylist_swipe_layout);

        getData(new GroupdataCallBack() {
            @Override
            public void onSuccess(ArrayList<GroupData> result) {
                groupData=result;

                crewlist_adapter = new Mycrewlist_RvAdapter(getActivity(), groupData);
                crewlist_rv.setAdapter(crewlist_adapter);
                crewlist_adapter.notifyDataSetChanged();

                pdialog.dismiss();

            }
        });


        refresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                i ++;

                count2.add(i);


                schedule_adapter = new Mycrewschedule_RvAdapter(getActivity(), count2, i);
                schedule_rv.setAdapter(schedule_adapter);

                schedule_adapter.notifyDataSetChanged();

                refresh.setRefreshing(false);
            }
        });


        crew_create_text = (Button) view.findViewById(R.id.crew_create);
        crew_create_text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), CrewcreateActivity.class);
                intent.putExtra("userID",userID);
                startActivity(intent);
            }
        });




//        FloatingActionButton fab = (FloatingActionButton) view.findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener(){
//            @Override
//            public void onClick(View view) {
//                Intent intent = new Intent(getActivity(), CrewcreateActivity.class);
//                startActivity(intent);
//            }
//        });
        return view;
    }

    public void getData(final GroupdataCallBack callback)
    {

        RequestQueue queue= Volley.newRequestQueue(getActivity().getApplicationContext());
        Response.Listener<String> responseListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {


                try {

                    JSONObject jsonObject=new JSONObject(response);

                    boolean success=jsonObject.getBoolean("success");
                    Log.i("log",response);
                    if(success)
                    {
                        groupData.clear();
                        int row=jsonObject.getInt("row");

                        for(int i=0;i<row;i++)
                        {
                            String crewName;
                            String crewRegion;
                            String crewHost;
                            String crewInfo;

                            JSONObject json_temp=jsonObject.getJSONObject(Integer.toString(i));
                            crewName=json_temp.getString("crewName");
                            crewRegion=json_temp.getString("crewRegion");
                            crewHost=json_temp.getString("crewHost");
                            crewInfo=json_temp.getString("crewInfo");


                            GroupData temp=new GroupData(crewName,crewRegion,crewHost,crewInfo);
                            groupData.add(temp);
                        }
                        callback.onSuccess(groupData);

                    }
                    else {

                    }
                }
                catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };

        MycrewlistRequest mycrewlistRequest=new MycrewlistRequest(userID,responseListener);
        //getActivity().getApplicationContext()
        queue.add(mycrewlistRequest);


        return;
    }

    public void refreshData(){
        getData(new GroupdataCallBack() {
            @Override
            public void onSuccess(ArrayList<GroupData> result) {
                groupData=result;
                if(crewlist_adapter!=null)
                {
                    crewlist_adapter.notifyDataSetChanged();
                }
            }
        });
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState)
    {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onStart() {
        super.onStart();
        refreshData();
        Log.i("log","grouplist:onstart");
    }

    @Override
    public void onResume() {
        super.onResume();
        refresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {

                refreshData();
                refresh.setRefreshing(false);
            }
        });
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