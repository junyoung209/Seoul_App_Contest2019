package com.example.seoul.Single.Myrecord;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.example.seoul.R;
import com.example.seoul.Single.Run.Runrecord;
import com.google.android.gms.maps.model.LatLng;

import org.json.JSONObject;

import java.util.ArrayList;

public class MyrecordResult extends AppCompatActivity {

    private int idx=0;

    private Button Back_btn,Delete_btn;
    private MyrecordDeleteDialog myrecordDeleteDialog;

    private Myrecord myRecord;
    private Runrecord runRecord;
    private ArrayList<LatLng> runCord;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_myrecord_result);

        runCord= (ArrayList<LatLng>) getIntent().getSerializableExtra("runCord");
        runRecord=(Runrecord)getIntent().getSerializableExtra("runRecord");
        idx=getIntent().getIntExtra("idx",-1);

        myRecord=new Myrecord(idx,runRecord.getUserID(),runRecord.getRunTime(),runRecord.getRunDistance(),runRecord.getRunVelocity(),runRecord.getRunDate(),runCord);

        Log.i("log",myRecord.getRunDistance());
        Log.i("log",myRecord.getRunVelocity());




        myrecordDeleteDialog = new MyrecordDeleteDialog(this,positiveListener,negativeListener);

        Back_btn=(Button)findViewById(R.id.myrecord_back);

        Back_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(), "뒤로 가기",Toast.LENGTH_SHORT).show();
                finish();


            }
        });
        Delete_btn=(Button)findViewById(R.id.myrecord_delete);

        Delete_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                myrecordDeleteDialog.show();


            }
        });




    }

    private View.OnClickListener positiveListener = new View.OnClickListener() {
        public void onClick(View v) {
            Toast.makeText(getApplicationContext(), "기록을 삭제했습니다.",Toast.LENGTH_SHORT).show();
            Response.Listener<String> responseListener = new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {

                    try {
                        JSONObject jsonResponse = new JSONObject(response);
                        boolean success = jsonResponse.getBoolean("success");

                        if (success) {
                            myrecordDeleteDialog.dismiss();
                            finish();
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                }
            };
            MyrecordDeleteRequset myrecordDeleteRequset = new MyrecordDeleteRequset(Integer.toString(idx), responseListener);
            RequestQueue queue = Volley.newRequestQueue(MyrecordResult.this);
            queue.add(myrecordDeleteRequset);


        }
    };

    private View.OnClickListener negativeListener = new View.OnClickListener() {
        public void onClick(View v) {
            Toast.makeText(getApplicationContext(), "취소버튼이 눌렸습니다.",Toast.LENGTH_SHORT).show();
            myrecordDeleteDialog.dismiss();
        }
    };


}
