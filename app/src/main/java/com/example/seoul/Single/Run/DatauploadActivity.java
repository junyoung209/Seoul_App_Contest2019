package com.example.seoul.Single.Run;

import android.content.Context;
import android.content.ContextWrapper;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Layout;
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
import com.example.seoul.Single.Run.Runrecord;
/*import com.example.seoul.Single.Run.Runrecord;*/
import com.example.seoul.Single.Run.RunrecordUploadDialog;
import com.example.seoul.Single.Run.UploadRequest;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.gson.Gson;

import org.json.JSONObject;
import org.w3c.dom.Text;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

public class DatauploadActivity extends AppCompatActivity
        implements OnMapReadyCallback {

    private String userID,runTime,runDistance,runVelocity,runDate;

    private Button Upload_btn,Cancel_btn;
    private RunrecordUploadDialog runrecordUploadDialog;
    private ArrayList<LatLng> runCord=new ArrayList<>();
    private Runrecord runRecord=new Runrecord();


    private GoogleMap mGoogleMap = null;
    private double Lat = 0;
    private double Lng = 0;
    private Marker resultMarker = null;

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
        runDate=runRecord.getRunDate();

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

        MapFragment mapFragment = (MapFragment) getFragmentManager()
                .findFragmentById(R.id.upload_map);
        mapFragment.getMapAsync(this);
    }

    private View.OnClickListener positiveListener = new View.OnClickListener() {
        public void onClick(View v) {
            Toast.makeText(getApplicationContext(), "기록을 저장했습니다",Toast.LENGTH_SHORT).show();
            runrecordUploadDialog.dismiss();


            /*GoogleMap.SnapshotReadyCallback callback = new GoogleMap.SnapshotReadyCallback() {
                Bitmap bitmap;
                @Override
                public void onSnapshotReady(Bitmap snapshot) {
                    ContextWrapper cw = new ContextWrapper(getApplicationContext());
                    File directory = cw.getDir("imageDir", Context.MODE_PRIVATE);

                    String fileName = (myRecord.getIdx()) + ".jpg";
                    File mypath = new File(directory, fileName);
                    bitmap = snapshot;
                    try{
                        FileOutputStream out =  new FileOutputStream(mypath);

                        bitmap.compress(Bitmap.CompressFormat.JPEG,100,out);
                        Log.e("MyTag", "FileStored : " + fileName);
                        out.close();
                    }catch(FileNotFoundException e){
                        Log.e("MyTag", "FileNotFoundException : " + e.getMessage());
                    }catch (IOException e){
                        Log.e("MyTag", "IOException : " + e.getMessage());
                    }
                }
            };
            mGoogleMap.snapshot(callback);*/
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




            //arraylist를 string으로 변환
            UploadRequest uploadRequest=new UploadRequest(userID,runTime,runDistance,runVelocity,runDate,new Gson().toJson(runCord),responseListener);
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

    @Override
    public void onMapReady(GoogleMap googleMap){
        Log.d("log", "onMapReady :");


        mGoogleMap= googleMap;
        for(int i=0; i<runCord.size(); i++){
            Lat+= runCord.get(i).latitude;
            Lng+= runCord.get(i).longitude;
        }
        Lat/= runCord.size();
        Lng/= runCord.size();

        setPath(runCord);
        setLocation();
    }

    public void setLocation() {


        //디폴트 위치, Seoul
        LatLng DEFAULT_LOCATION = new LatLng(Lat, Lng);

        Toast.makeText(getApplicationContext(), DEFAULT_LOCATION.toString(),Toast.LENGTH_SHORT).show();




        CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(DEFAULT_LOCATION, 17);
        mGoogleMap.moveCamera(cameraUpdate);

    }

    public void setPath(ArrayList<LatLng> runPath){
        PolylineOptions options = new PolylineOptions();
        CircleOptions circleOptionsStart=new CircleOptions().center(runPath.get(0)).radius(0.1).strokeColor(Color.RED).strokeWidth(19);
        mGoogleMap.addCircle(circleOptionsStart);

        for(int i=0; i<runPath.size()-1; i++){
            // PolylineOptions options = new PolylineOptions().add(startLatLng).add(endLatLng).width(20).color(Color.RED).geodesic(true);
            options.add(runPath.get(i)).add(runPath.get(i+1)).width(20).color(Color.RED).geodesic(true);
            mGoogleMap.addPolyline(options);
            CircleOptions circleOptions=new CircleOptions().center(runPath.get(i+1)).radius(0.1).strokeColor(Color.RED).strokeWidth(19);
            mGoogleMap.addCircle(circleOptions);
        }


        /*CircleOptions circleOptions=new CircleOptions().center(endLatLng).radius(0.1).strokeColor(Color.RED).strokeWidth(19);
        mGoogleMap.addCircle(circleOptions);*/
    }


}
