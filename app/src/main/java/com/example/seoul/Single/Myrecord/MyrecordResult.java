package com.example.seoul.Single.Myrecord;

import android.graphics.Color;
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
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.PolylineOptions;

import org.json.JSONObject;

import java.util.ArrayList;

public class MyrecordResult extends AppCompatActivity
        implements OnMapReadyCallback {

    private int idx = 0;

    private Button Back_btn, Delete_btn;
    private MyrecordDeleteDialog myrecordDeleteDialog;

    private Myrecord myRecord;
    private Runrecord runRecord;
    private ArrayList<LatLng> runCord;

    private GoogleMap mGoogleMap = null;
    private double Lat = 0;
    private double Lng = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_myrecord_result);

        runCord = (ArrayList<LatLng>) getIntent().getSerializableExtra("runCord");
        runRecord = (Runrecord) getIntent().getSerializableExtra("runRecord");
        idx = getIntent().getIntExtra("idx", -1);

        myRecord = new Myrecord(idx, runRecord.getUserID(), runRecord.getRunTime(), runRecord.getRunDistance(), runRecord.getRunVelocity(), runRecord.getRunDate(), runCord);

        Log.i("log", myRecord.getRunDistance());
        Log.i("log", myRecord.getRunVelocity());


        myrecordDeleteDialog = new MyrecordDeleteDialog(this, positiveListener, negativeListener);

        Back_btn = (Button) findViewById(R.id.myrecord_back);

        Back_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(), "뒤로 가기", Toast.LENGTH_SHORT).show();
                finish();


            }
        });
        Delete_btn = (Button) findViewById(R.id.myrecord_delete);

        Delete_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                myrecordDeleteDialog.show();


            }
        });

        MapFragment mapFragment = (MapFragment) getFragmentManager()
                .findFragmentById(R.id.Result_map);
        mapFragment.getMapAsync(this);


    }

    private View.OnClickListener positiveListener = new View.OnClickListener() {
        public void onClick(View v) {
            Toast.makeText(getApplicationContext(), "기록을 삭제했습니다.", Toast.LENGTH_SHORT).show();
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
            Toast.makeText(getApplicationContext(), "취소버튼이 눌렸습니다.", Toast.LENGTH_SHORT).show();
            myrecordDeleteDialog.dismiss();
        }
    };

    @Override
    public void onMapReady(GoogleMap googleMap) {
        Log.d("log", "onMapReady :");


        mGoogleMap = googleMap;
        for (int i = 0; i < runCord.size(); i++) {
            Lat += runCord.get(i).latitude;
            Lng += runCord.get(i).longitude;
        }
        Lat /= runCord.size();
        Lng /= runCord.size();

        setPath(runCord);
        setLocation();
    }

    public void setLocation() {


        //디폴트 위치, Seoul
        LatLng DEFAULT_LOCATION = new LatLng(Lat, Lng);

        Toast.makeText(getApplicationContext(), DEFAULT_LOCATION.toString(), Toast.LENGTH_SHORT).show();


        CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(DEFAULT_LOCATION, 17);
        mGoogleMap.moveCamera(cameraUpdate);

    }

    public void setPath(ArrayList<LatLng> runPath) {
        PolylineOptions options = new PolylineOptions();
        CircleOptions circleOptionsStart = new CircleOptions().center(runPath.get(0)).radius(0.1).strokeColor(Color.RED).strokeWidth(19);
        mGoogleMap.addCircle(circleOptionsStart);

        for (int i = 0; i < runPath.size() - 1; i++) {
            // PolylineOptions options = new PolylineOptions().add(startLatLng).add(endLatLng).width(20).color(Color.RED).geodesic(true);
            options.add(runPath.get(i)).add(runPath.get(i + 1)).width(20).color(Color.RED).geodesic(true);
            mGoogleMap.addPolyline(options);
            CircleOptions circleOptions = new CircleOptions().center(runPath.get(i + 1)).radius(0.1).strokeColor(Color.RED).strokeWidth(19);
            mGoogleMap.addCircle(circleOptions);
        }


    }
}


