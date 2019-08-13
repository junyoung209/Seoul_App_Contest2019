package com.example.seoul.Single;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.seoul.R;


public class RunFragment extends Fragment {

    private ImageButton bt_workout;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.fragment_run,container, false);


        bt_workout = (ImageButton)view.findViewById(R.id.workout_btn);

        bt_workout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent=new Intent(getActivity(), SingleWorkoutActivity.class);
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
        Log.i("log","Run:onstart");
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.i("log","Run:onresume");
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.i("log","Run:onpause");
    }

    @Override
    public void onStop() {
        super.onStop();
        Log.i("log","Run:onstart");
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        Log.i("log","Run:ondestoryview");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.i("log","Run:ondestory");
    }

    @Override
    public void onDetach() {
        super.onDetach();
        Log.i("log","Run:ondetach");
    }
}
