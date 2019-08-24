package com.example.seoul.Single.Run;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.example.seoul.R;

import org.json.JSONObject;

public class DatauploadActivity extends AppCompatActivity {

    private String userID,run_time,dateToday;

    private Button Upload_btn,Cancel_btn;
    private CustomDialog customDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dataupload);
        userID= getIntent().getStringExtra("userID");
        run_time= getIntent().getStringExtra("runTime");
        dateToday=getIntent().getStringExtra("dateToday");
        customDialog = new CustomDialog(this,positiveListener,negativeListener);

        Upload_btn=(Button)findViewById(R.id.upload_btn);

        Upload_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                customDialog.show();

            }
        });
        Cancel_btn=(Button)findViewById(R.id.cancel_btn);

        Cancel_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(), "기록을 취소하였습니다.",Toast.LENGTH_SHORT).show();
                customDialog.dismiss();
                finish();
            }
        });


    }

    private View.OnClickListener positiveListener = new View.OnClickListener() {
        public void onClick(View v) {
            Toast.makeText(getApplicationContext(), "기록을 저장했습니다",Toast.LENGTH_SHORT).show();
            customDialog.dismiss();
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
            Log.i("log",dateToday);
            UploadRequest uploadRequest=new UploadRequest(userID,run_time,dateToday,responseListener);
            RequestQueue queue= Volley.newRequestQueue(DatauploadActivity.this);
            queue.add(uploadRequest);
        }
    };

    private View.OnClickListener negativeListener = new View.OnClickListener() {
        public void onClick(View v) {
            Toast.makeText(getApplicationContext(), "취소버튼이 눌렸습니다.",Toast.LENGTH_SHORT).show();
            customDialog.dismiss();
        }
    };


}
