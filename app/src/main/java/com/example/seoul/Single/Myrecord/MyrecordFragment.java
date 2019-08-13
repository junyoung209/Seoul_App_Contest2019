package com.example.seoul.Single.Myrecord;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.example.seoul.R;

import org.json.JSONObject;

import java.util.ArrayList;


public class MyrecordFragment extends Fragment {

    private String userID;
    private ArrayList<Myrecord> myrecord=new ArrayList<>();
    private String a="";
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.fragment_myrecord, container,false);

        TextView textView=(TextView)view.findViewById(R.id.myrecordView);

        textView.setText(a);





        return view;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i("log","myrecord:oncreate");
        a="";
        Log.i("log",Integer.toString(myrecord.size()));
        for(int i=0;i<myrecord.size();i++)
        {
            a=a+" idx = "+Integer.toString(myrecord.get(i).getIdx());
            a=a+" RunTime = "+myrecord.get(i).getRunTime();
            a=a+" Date = "+myrecord.get(i).getDate()+"\n";

        }

    }

    @Override
    public void onAttach(Context context) {

        super.onAttach(context);

        Log.i("log","myrecord:onattach");
        Bundle bundle=this.getArguments();
        if(bundle!=null)
        {
            userID=bundle.getString("userID");
        }

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
        RequestQueue queue=Volley.newRequestQueue(getActivity().getApplicationContext());
        //getActivity().getApplicationContext()
        queue.add(recordRequest);




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
