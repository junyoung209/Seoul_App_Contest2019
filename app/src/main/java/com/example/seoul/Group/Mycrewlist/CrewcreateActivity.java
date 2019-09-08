package com.example.seoul.Group.Mycrewlist;


import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.example.seoul.R;
import com.example.seoul.Single.Myrecord.MyrecordDeleteRequset;
import com.example.seoul.Single.Myrecord.MyrecordResult;

import org.json.JSONObject;

import java.io.StringReader;

public class CrewcreateActivity extends AppCompatActivity {

    private EditText crewName;
    private EditText crewInfo;
    private Spinner spinner;
    private ArrayAdapter adapter;
    private Button crewcreate_btn;
    private String userID;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crewcreate);


        userID=getIntent().getStringExtra("userID");

        crewName=(EditText)findViewById(R.id.crewname_Text);
        crewInfo=(EditText)findViewById(R.id.crewinfo_Text);

        spinner=(Spinner)findViewById(R.id.regionSpinner);
        adapter= ArrayAdapter.createFromResource(this,R.array.region,android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        crewcreate_btn=(Button)findViewById(R.id.crewcreate_btn);


        crewcreate_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Response.Listener<String> responseListener=new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject=new JSONObject(response);
                            boolean success=jsonObject.getBoolean("success");
                            if(success)
                            {
                                finish();
                            }
                            else
                            {
                                Toast.makeText(getApplicationContext(),"크루명 중복입니다.",Toast.LENGTH_LONG).show();
                            }

                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                };

                CrewcreateRequest crewcreateRequest = new CrewcreateRequest(userID,crewName.getText().toString(),crewInfo.getText().toString(),spinner.getSelectedItem().toString(), responseListener);
                RequestQueue queue = Volley.newRequestQueue(CrewcreateActivity.this);
                queue.add(crewcreateRequest);

            }
        });



    }
}