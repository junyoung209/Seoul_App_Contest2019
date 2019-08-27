package com.example.seoul.Single.Myrecord;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
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

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;


import com.example.seoul.Group.RvAdapter;
import com.example.seoul.R;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


public class MyrecordFragment extends Fragment {


    private String userID;
    private ArrayList<Myrecord> myrecord=new ArrayList<>();
    private String a="";
    private ProgressDialog pdialog;
    private TextView textView;

    private RecyclerView rv;
    private LinearLayoutManager llm;
    private List<Integer> count;
    private int i = 0;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private MyRecordAdapter adapter;


    @SuppressLint("WrongConstant")
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.fragment_myrecord, container,false);
        pdialog=new ProgressDialog(getActivity());
        pdialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        pdialog.setMessage("Loading");
        pdialog.show();

        Bundle bundle=this.getArguments();
        if(bundle!=null)
        {
            userID=bundle.getString("userID");
            Log.i("log",userID);
        }

        rv = (RecyclerView) view.findViewById(R.id.myrecord_RecycleView);
        llm = new LinearLayoutManager(getActivity());
        llm.setOrientation(LinearLayoutManager.VERTICAL);

        count = new ArrayList<>();

        rv.setHasFixedSize(true);
        rv.setLayoutManager(llm);



        mSwipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.myrecord_SwipeLayout);

        getData(new VolleyCallBack() {
            @Override
            public void onSuccess(ArrayList<Myrecord> result) {
                myrecord=result;

                adapter = new MyRecordAdapter(getActivity(), myrecord);
                rv.setAdapter(adapter);
                adapter.notifyDataSetChanged();

                pdialog.dismiss();
                Log.i("log",Integer.toString(myrecord.size()));
            }
        });
        Log.i("log",Integer.toString(myrecord.size()));








        return view;
    }


    public void getData(final VolleyCallBack callback)
    {

        RequestQueue queue=Volley.newRequestQueue(getActivity().getApplicationContext());
        Response.Listener<String> responseListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    Log.i("log",response);
                    JSONObject jsonObject=new JSONObject(response);
                    boolean success=jsonObject.getBoolean("success");
                    if(success)
                    {
                        myrecord.clear();
                        int row=jsonObject.getInt("row");
                        int total=jsonObject.getInt("total");


                        for(int i=0,j=0;i<row;i++)
                        {
                            Myrecord temp=new Myrecord(jsonObject.getInt(Integer.toString(j++)),jsonObject.getString(Integer.toString(j++)),jsonObject.getString(Integer.toString(j++)));
                            myrecord.add(temp);
                        }
                        callback.onSuccess(myrecord);

                    }
                    else {

                    }
                }
                catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };

        RecordRequest recordRequest=new RecordRequest(userID,responseListener);
        //getActivity().getApplicationContext()
        queue.add(recordRequest);


        return;
    }



    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i("log","myrecord:oncreate");



    }

    @Override
    public void onAttach(Context context) {

        super.onAttach(context);

        Log.i("log","myrecord:onattach");





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


        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {

                getData(new VolleyCallBack() {
                    @Override
                    public void onSuccess(ArrayList<Myrecord> result) {
                        myrecord=result;
                        adapter.notifyDataSetChanged();

                        Log.i("log",Integer.toString(myrecord.size()));
                    }
                });

                mSwipeRefreshLayout.setRefreshing(false);
            }
        });
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
