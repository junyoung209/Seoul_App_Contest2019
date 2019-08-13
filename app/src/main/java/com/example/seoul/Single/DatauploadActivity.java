package com.example.seoul.Single;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.example.seoul.R;
import com.example.seoul.RegisterActivity;
import com.example.seoul.RegisterRequest;

import org.json.JSONObject;

public class DatauploadActivity extends AppCompatActivity {

    private String userID,run_time;

    private Button Upload_btn,Cancel_btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dataupload);
        userID= getIntent().getStringExtra("userID");
        run_time= getIntent().getStringExtra("runTime");


        Upload_btn=(Button)findViewById(R.id.upload_btn);

        Upload_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

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
                UploadRequest uploadRequest=new UploadRequest(userID,run_time,responseListener);
                RequestQueue queue= Volley.newRequestQueue(DatauploadActivity.this);
                queue.add(uploadRequest);
            }
        });
        Cancel_btn=(Button)findViewById(R.id.cancel_btn);

        Cancel_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });


    }
}
