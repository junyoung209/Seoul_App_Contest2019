package com.example.seoul.Single.Run;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.example.seoul.R;
import com.google.android.gms.maps.model.LatLng;

import org.json.JSONObject;
import org.w3c.dom.Text;

import java.util.ArrayList;

public class DatauploadActivity extends AppCompatActivity {

    private String userID,runTime,runDistance,runVelocity,runDate;

    private Button Upload_btn,Cancel_btn;
    private RunrecordUploadDialog runrecordUploadDialog;
    private ArrayList<LatLng> runCord=new ArrayList<>();
    private Runrecord runRecord=new Runrecord();

    private TextView runTime_View,runDistance_View,runVelocity_View,runDate_View;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dataupload);


        //뛴 결과값 가져와
        runCord= (ArrayList<LatLng>) getIntent().getSerializableExtra("runCord");
        runRecord=(Runrecord)getIntent().getSerializableExtra("runRecord");

        userID=runRecord.getUserID();
        runTime=runRecord.getRunTime();
        runDistance=runRecord.getRunDistance();
        runVelocity=runRecord.getRunVelocity();
        runDate=runRecord.getDateToday();

        runTime_View=(TextView)findViewById(R.id.upload_runTime);
        runDistance_View=(TextView)findViewById(R.id.upload_runDistance);
        runVelocity_View=(TextView)findViewById(R.id.upload_runVelocity);
        runDate_View=(TextView)findViewById(R.id.upload_runDate);


        runTime_View.setText(runTime);
        runDistance_View.setText(runDistance);
        runVelocity_View.setText(runVelocity);
        runDate_View.setText(runDate);


        runrecordUploadDialog = new RunrecordUploadDialog(this,positiveListener,negativeListener);

        Upload_btn=(Button)findViewById(R.id.upload_btn);

        Upload_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                runrecordUploadDialog.show();

            }
        });
        Cancel_btn=(Button)findViewById(R.id.cancel_btn);

        Cancel_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(), "기록을 취소했습니다.",Toast.LENGTH_SHORT).show();
                runrecordUploadDialog.dismiss();
                finish();
            }
        });


    }

    private View.OnClickListener positiveListener = new View.OnClickListener() {
        public void onClick(View v) {
            Toast.makeText(getApplicationContext(), "기록을 저장했습니다",Toast.LENGTH_SHORT).show();
            runrecordUploadDialog.dismiss();


            //데이터 업로드
            Response.Listener<String> responseListener=new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    try{
                        JSONObject jsonResponse = new JSONObject(response);
                        Log.i("log",response);
                        boolean success =jsonResponse.getBoolean("success");

                        if(success)
                        {
                            finish();
                        }
                    }
                    catch (Exception e){
                        e.printStackTrace();
                    }

                }
            };
            Log.i("log",runDate);
            UploadRequest uploadRequest=new UploadRequest(userID,runTime,runDate,responseListener);
            RequestQueue queue= Volley.newRequestQueue(DatauploadActivity.this);
            queue.add(uploadRequest);
        }
    };

    private View.OnClickListener negativeListener = new View.OnClickListener() {
        public void onClick(View v) {
            Toast.makeText(getApplicationContext(), "취소버튼이 눌렸습니다.",Toast.LENGTH_SHORT).show();
            runrecordUploadDialog.dismiss();
        }
    };


}
