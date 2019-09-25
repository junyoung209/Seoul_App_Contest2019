package com.example.seoul.Group.Mycrewlist;


import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.example.seoul.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class CrewmainActivity extends AppCompatActivity {

    private Button join_btn;
    private boolean flag=true;
    private  FloatingActionButton fab;

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crewmain);

        join_btn = (Button) findViewById(R.id.JoinButton);
        fab = (FloatingActionButton) findViewById(R.id.fab);

        join_btn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                if(flag==true) {
                    flag=false;
                    join_btn.setBackgroundColor(Color.RED);
                    join_btn.setText("가입완료");
                    fab.show();

                }
                else {
                    flag=true;
                    join_btn.setBackgroundColor(Color.LTGRAY);
                    join_btn.setText("가입 및 참가신청");
                    fab.hide();
                }
            }
        });

        fab.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CrewmainActivity.this, CrewcreateActivity.class);
                startActivity(intent);
            }
        });
    }
}