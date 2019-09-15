package com.example.seoul.Group.Grouplist;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.Nullable;
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
import android.widget.TextView;

import com.example.seoul.Group.Mycrewlist.CrewcreateActivity;
import com.example.seoul.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;


public class GrouplistFragment extends Fragment {


    private RecyclerView surcrewlist_rv, allcrewlist_rv;
    private LinearLayoutManager llm_horizontal;
    private LinearLayoutManager llm_vertical;
    private List<Integer> count1, count2;
    private int i = 0;
    TextView mTxtDate;
    int mYear, mMonth, mDay, mHour, mMinute;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    Button crew_search_bnt;

    @SuppressLint("WrongConstant")
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_grouplist, container, false);
        surcrewlist_rv = (RecyclerView) view.findViewById(R.id.surcrewlist_recyclerView);
        allcrewlist_rv = (RecyclerView) view.findViewById(R.id.allcrewlist_recyclerView);
        mTxtDate = (TextView) view.findViewById(R.id.txtdate);

        Calendar cal = new GregorianCalendar();
        mYear = cal.get(Calendar.YEAR);
        mMonth = cal.get(Calendar.MONTH);
        mDay = cal.get(Calendar.DAY_OF_MONTH);
        mHour = cal.get(Calendar.HOUR_OF_DAY);
        mMinute = cal.get(Calendar.MINUTE);

        UpdateNow();

        llm_horizontal = new LinearLayoutManager(getActivity());
        llm_horizontal.setOrientation(LinearLayoutManager.HORIZONTAL);
        llm_vertical = new LinearLayoutManager(getActivity());
        llm_vertical.setOrientation(LinearLayoutManager.VERTICAL);

        count1 = new ArrayList<>();
        count2 = new ArrayList<>();

        surcrewlist_rv.setHasFixedSize(true);
        surcrewlist_rv.setLayoutManager(llm_horizontal);
        allcrewlist_rv.setHasFixedSize(true);
        allcrewlist_rv.setLayoutManager(llm_vertical);


        mSwipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.swipe_layout);
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                i++;
                count1.add(i);
                count2.add(i);

                com.example.seoul.Group.Grouplist.GroupSurcrewlist_RvAdapter surcrewlist_adapter = new com.example.seoul.Group.Grouplist.GroupSurcrewlist_RvAdapter(getActivity(), count1, i);
                surcrewlist_rv.setAdapter(surcrewlist_adapter);
                com.example.seoul.Group.Grouplist.GroupAllcrewlist_RvAdapter allcrewlist_adapter = new com.example.seoul.Group.Grouplist.GroupAllcrewlist_RvAdapter(getActivity(), count2, i);
                allcrewlist_rv.setAdapter(allcrewlist_adapter);

                surcrewlist_adapter.notifyDataSetChanged();
                allcrewlist_adapter.notifyDataSetChanged();

                mSwipeRefreshLayout.setRefreshing(false);
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

        return view;
    }

    void UpdateNow() {
        mTxtDate.setText(String.format("%d년 %d월 %d일", mYear,
                mMonth + 1, mDay));
    }

    public void mOnClick(View v) {
        new DatePickerDialog(getActivity(), AlertDialog.THEME_HOLO_LIGHT, mDateSetListener, mYear,
                mMonth, mDay).show();
    }

    //날짜 대화상자 리스너 부분
    DatePickerDialog.OnDateSetListener mDateSetListener =
            new DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker view, int year, int monthOfYear,
                                      int dayOfMonth) {
                    // TODO Auto-generated method stub
                    //사용자가 입력한 값을 가져온뒤
                    mYear = year;
                    mMonth = monthOfYear;
                    mDay = dayOfMonth;
                    //텍스트뷰의 값을 업데이트함
                    UpdateNow();
                }
            };

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
        Log.i("log", "grouplist:onresume");
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