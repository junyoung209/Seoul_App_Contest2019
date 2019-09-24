package com.example.seoul.Group.Grouplist;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.example.seoul.Group.GroupData;
import com.example.seoul.Group.Mycrewlist.CrewcreateActivity;
import com.example.seoul.Group.GroupdataCallBack;
import com.example.seoul.R;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;


public class GrouplistFragment extends Fragment {


    private RecyclerView surcrewlist_rv, allcrewlist_rv;
    private LinearLayoutManager llm_vertical;
    private LinearLayoutManager llm_vertical1;


    TextView mTxtDate;
    EditText editText;
    int mYear, mMonth, mDay, mHour, mMinute;


    private SwipeRefreshLayout refresh;
    Button crew_search_bnt;

    private ProgressDialog pdialog;
    private String userID,userRegion;

    private TextView location;

    private GroupSurcrewlist_RvAdapter surcrewlist_adapter;
    private GroupAllcrewlist_RvAdapter allcrewlist_adapter;


    private ArrayList<GroupData> groupSurData=new ArrayList<>();
    private ArrayList<GroupData> groupAllData=new ArrayList<>();

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @SuppressLint("WrongConstant")
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_grouplist, container, false);
        surcrewlist_rv = (RecyclerView) view.findViewById(R.id.surcrewlist_recyclerView);
        allcrewlist_rv = (RecyclerView) view.findViewById(R.id.allcrewlist_recyclerView);
        location=(TextView)view.findViewById(R.id.grouplist_userlocation);

        editText = (EditText)view.findViewById(R.id.EditTextDate);

        pdialog=new ProgressDialog(getActivity());
        pdialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        pdialog.setMessage("Loading");
        pdialog.show();


        Bundle bundle=this.getArguments();
        if(bundle!=null)
        {
            userID=bundle.getString("userID");
            userRegion=bundle.getString("userRegion");
            Log.i("log",userRegion);
        }
        location.setText(userRegion+", 서울시");

        Calendar cal = new GregorianCalendar();
        mYear = cal.get(Calendar.YEAR);
        mMonth = cal.get(Calendar.MONTH);
        mDay = cal.get(Calendar.DAY_OF_MONTH);
        mHour = cal.get(Calendar.HOUR_OF_DAY);
        mMinute = cal.get(Calendar.MINUTE);


        llm_vertical = new LinearLayoutManager(getActivity());
        llm_vertical.setOrientation(LinearLayoutManager.HORIZONTAL);
        llm_vertical1 = new LinearLayoutManager(getActivity());
        llm_vertical1.setOrientation(LinearLayoutManager.VERTICAL);



        surcrewlist_rv.setHasFixedSize(true);
        surcrewlist_rv.setLayoutManager(llm_vertical);
        allcrewlist_rv.setHasFixedSize(false);
        allcrewlist_rv.setLayoutManager(llm_vertical1);


        editText = (EditText)view.findViewById(R.id.EditTextDate);
        editText.setCursorVisible(false);
        editText.setShowSoftInputOnFocus(false);
        editText.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {

                DatePickerFragment dpf = new DatePickerFragment(editText);
                dpf.show(getFragmentManager(), "datePicker");

            }

        });


        refresh = (SwipeRefreshLayout) view.findViewById(R.id.grouplist_swipelayout);


        getSurData(new GroupdataCallBack() {
            @Override
            public void onSuccess(ArrayList<GroupData> result) {
                groupSurData=result;

                surcrewlist_adapter = new GroupSurcrewlist_RvAdapter(getActivity(), groupSurData);
                surcrewlist_rv.setAdapter(surcrewlist_adapter);
                surcrewlist_adapter.notifyDataSetChanged();


            }
        });
        getAllData(new GroupdataCallBack() {
            @Override
            public void onSuccess(ArrayList<GroupData> result) {
                groupAllData=result;

                allcrewlist_adapter = new GroupAllcrewlist_RvAdapter(getActivity(),groupAllData);
                allcrewlist_rv.setAdapter(allcrewlist_adapter);
                allcrewlist_adapter.notifyDataSetChanged();


            }
        });

        crew_search_bnt = (Button) view.findViewById(R.id.SearchButton);
        crew_search_bnt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), CrewcreateActivity.class);
                startActivity(intent);
            }
        });

        pdialog.dismiss();
        return view;
    }



    public void getSurData(final GroupdataCallBack callback)
    {

        RequestQueue queue= Volley.newRequestQueue(getActivity().getApplicationContext());
        Response.Listener<String> responseListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {


                try {

                    JSONObject jsonObject=new JSONObject(response);
                    Log.i("log",userRegion);
                    boolean success=jsonObject.getBoolean("success");
                    Log.i("log",response+"surcrew");
                    if(success)
                    {
                        groupSurData.clear();
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
                            groupSurData.add(temp);
                        }
                        callback.onSuccess(groupSurData);

                    }
                    else {

                    }
                }
                catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };


        GroupSurcrewlistRequest groupSurcrewlistRequest=new GroupSurcrewlistRequest(userID,userRegion,responseListener);
        //getActivity().getApplicationContext()
        queue.add(groupSurcrewlistRequest);


        return;
    }


    public void getAllData(final GroupdataCallBack callback) {

        RequestQueue queue= Volley.newRequestQueue(getActivity().getApplicationContext());
        Response.Listener<String> responseListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {

                    JSONObject jsonObject=new JSONObject(response);

                    boolean success=jsonObject.getBoolean("success");
                    Log.i("log",response+"allcrew");
                    if(success)
                    {
                        groupAllData.clear();
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
                            groupAllData.add(temp);
                        }
                        callback.onSuccess(groupAllData);

                    }
                    else {

                    }
                }
                catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };


        GroupAllcrewlistRequest groupAllcrewlistRequest=new GroupAllcrewlistRequest(responseListener);
        //getActivity().getApplicationContext()
        queue.add(groupAllcrewlistRequest);


        return;
    }


    public void refreshData(){
        getSurData(new GroupdataCallBack() {
            @Override
            public void onSuccess(ArrayList<GroupData> result) {
                groupSurData=result;
                if(surcrewlist_adapter!=null)
                {
                    surcrewlist_adapter.notifyDataSetChanged();
                }

            }
        });
        getAllData(new GroupdataCallBack() {
            @Override
            public void onSuccess(ArrayList<GroupData> result) {
                groupAllData=result;
                if(allcrewlist_adapter!=null)
                {
                    allcrewlist_adapter.notifyDataSetChanged();
                }

            }
        });
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onStart() {
        super.onStart();
        Log.i("log", "grouplist:onstart");
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
        Log.i("log", "grouplist:onpause");
    }

    @Override
    public void onStop() {
        super.onStop();
        Log.i("log", "grouplist:onstart");
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        Log.i("log", "grouplist:ondestoryview");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.i("log", "grouplist:ondestory");
    }

    @Override
    public void onDetach() {
        super.onDetach();
        Log.i("log", "grouplist:ondetach");
    }
}