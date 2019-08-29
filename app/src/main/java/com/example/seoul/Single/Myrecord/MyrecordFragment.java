package com.example.seoul.Single.Myrecord;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
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


import com.example.seoul.R;
import com.example.seoul.Single.Run.RunrecordUploadDialog;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.json.JSONObject;

import java.util.ArrayList;


public class MyrecordFragment extends Fragment {


    private String userID;
    private ArrayList<Myrecord> myrecord=new ArrayList<>();
    private ProgressDialog pdialog;


    private Boolean modify_Flag=false;

    private RecyclerView rv;
    private LinearLayoutManager llm;

    private SwipeRefreshLayout refresh;
    private MyrecordAdapter adapter;
    private FloatingActionButton modify;

    private Button Delete_btn,Cancel_btn;
    private RunrecordUploadDialog runrecordUploadDialog;


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

        rv.setHasFixedSize(true);
        rv.setLayoutManager(llm);



        refresh = (SwipeRefreshLayout) view.findViewById(R.id.myrecord_SwipeLayout);

        getData(new RecordCallBack() {
            @Override
            public void onSuccess(ArrayList<Myrecord> result) {
                myrecord=result;

                adapter = new MyrecordAdapter(getActivity(), myrecord);
                rv.setAdapter(adapter);
                adapter.notifyDataSetChanged();

                pdialog.dismiss();

            }
        });
        Log.i("log",Integer.toString(myrecord.size()));


//        modify = (FloatingActionButton) view.findViewById(R.id.myrecord_Modify);
//        //+버튼 누르면 편집모드로 바뀜
//        modify.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                if (modify_Flag == true) {
//                    modify.setImageResource(R.drawable.ic_add_24dp);
//                    modify_Flag = false;
//                } else {
//                    Toast.makeText(getActivity(), "편집모드", Toast.LENGTH_SHORT).show();
//                    modify_Flag = true;
//                    modify.setImageResource(R.drawable.ic_remove_white_24dp);
//                }
//            }
//        });


        return view;
    }


    public void getData(final RecordCallBack callback)
    {

        RequestQueue queue=Volley.newRequestQueue(getActivity().getApplicationContext());
        Response.Listener<String> responseListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {

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

    public void refreshData(){
        getData(new RecordCallBack() {
            @Override
            public void onSuccess(ArrayList<Myrecord> result) {
                myrecord=result;
                adapter.notifyDataSetChanged();

            }
        });
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
        refreshData();


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
