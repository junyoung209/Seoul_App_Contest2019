package com.example.seoul;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.material.snackbar.Snackbar;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

public class ApiActivity extends AppCompatActivity
        implements OnMapReadyCallback, ActivityCompat.OnRequestPermissionsResultCallback {

    private ImageButton bt_img1, bt_img2,bt_img3,bt_img4;
    private GoogleMap mGoogleMap = null;
    private Marker currentMarker = null;


    private static final int GPS_ENABLE_REQUEST_CODE = 2001;
    private static final int UPDATE_INTERVAL_MS = 100;  // 0.1초
    private static final int FASTEST_UPDATE_INTERVAL_MS = 50; // 0.05초

    private static final int PERMISSIONS_REQUEST_CODE = 100;
    boolean needRequest = false;


    // 앱을 실행하기 위해 필요한 퍼미션을 정의합니다.
    String[] REQUIRED_PERMISSIONS = {Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION};  // 외부 저장소


    Location mCurrentLocatiion;
    LatLng currentPosition;

    private FusedLocationProviderClient mFusedLocationClient;
    private LocationRequest locationRequest;
    private Location location;
    private boolean aedState = false;
    private String userID,userRegion;
    private View mLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.i("log","api:oncreate");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_api);

        mLayout = findViewById(R.id.layout_api);
        userID= getIntent().getStringExtra("userID");
        userRegion=getIntent().getStringExtra("userRegion");
        // 위젯에 대한 참조
        bt_img1 = (ImageButton)findViewById(R.id.imgbt1);
        bt_img2 = (ImageButton)findViewById(R.id.imgbt2);
        bt_img3 = (ImageButton)findViewById(R.id.imgbt3);
        bt_img4 = (ImageButton)findViewById(R.id.imgbt4);
        bt_img4.setImageResource(R.drawable.ic_local_hospital_black_24dp);

        // 탭 버튼에 대한 리스너 연결
        bt_img1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent=new Intent(ApiActivity.this,SingleActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                intent.putExtra("userID",userID);
                intent.putExtra("userRegion",userRegion);
                ApiActivity.this.startActivity(intent);
            }
        });
        bt_img2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent=new Intent(ApiActivity.this,GroupActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                intent.putExtra("userID",userID);
                intent.putExtra("userRegion",userRegion);
                ApiActivity.this.startActivity(intent);
            }
        });
        bt_img3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent=new Intent(ApiActivity.this,MypageActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                intent.putExtra("userID",userID);
                intent.putExtra("userRegion",userRegion);
                ApiActivity.this.startActivity(intent);
            }
        });


        // 임의로 액티비티 호출 시점에 어느 프레그먼트를 프레임레이아웃에 띄울 것인지를 정함

        if (ActivityCompat.checkSelfPermission(ApiActivity.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(ApiActivity.this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }


        locationRequest = new LocationRequest()
                .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY)
                .setInterval(UPDATE_INTERVAL_MS)
                .setFastestInterval(FASTEST_UPDATE_INTERVAL_MS);


        LocationSettingsRequest.Builder builder =
                new LocationSettingsRequest.Builder();

        builder.addLocationRequest(locationRequest);


        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);

        MapFragment mapFragment = (MapFragment) getFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    LocationCallback locationCallback = new LocationCallback() {
        @Override
        public void onLocationResult(LocationResult locationResult) {
            super.onLocationResult(locationResult);

            List<Location> locationList = locationResult.getLocations();

            if (locationList.size() > 0) {
                location = locationList.get(locationList.size() - 1);
                //location = locationList.get(0);

                currentPosition
                        = new LatLng(location.getLatitude(), location.getLongitude());


              /*  String markerTitle = getCurrentAddress(currentPosition);
                String markerSnippet = "위도:" + String.valueOf(location.getLatitude())
                        + " 경도:" + String.valueOf(location.getLongitude());

                Log.d(TAG, "onLocationResult : " + markerSnippet);
*/

                //현재 위치에 마커 생성하고 이동
                String strUrl;
                if(location != null ) {
                    strUrl = "http://apis.data.go.kr/B552657/AEDInfoInqireService/getAedLcinfoInqire?ServiceKey=oREgEE4%2F3E2I7Ivu5C6%2Fzk4%2BgJY2EXKFKhX%2B4b2uY%2BsuuatwBPcYNb7nnkaunyfEwyuLkRVat%2FHg5mT%2Fap9W2w%3D%3D&WGS84_LON=" +  String.valueOf(location.getLongitude()) + "&WGS84_LAT=" +  String.valueOf(location.getLatitude()) + "&pageNo=1&numOfRows=10";
                    new DownloadWebpageTask().execute(strUrl);
                    Log.d("LOG", strUrl);
                }

                mCurrentLocatiion = location;
            }

        }

    };

    private void startLocationUpdates() {

        if (!checkLocationServicesStatus()) {

            Log.d("LOG", "startLocationUpdates : call showDialogForLocationServiceSetting");
            showDialogForLocationServiceSetting();
        }else {

            int hasFineLocationPermission = ContextCompat.checkSelfPermission(this,
                    Manifest.permission.ACCESS_FINE_LOCATION);
            int hasCoarseLocationPermission = ContextCompat.checkSelfPermission(this,
                    Manifest.permission.ACCESS_COARSE_LOCATION);



            if (hasFineLocationPermission != PackageManager.PERMISSION_GRANTED ||
                    hasCoarseLocationPermission != PackageManager.PERMISSION_GRANTED   ) {

                Log.d("LOG", "startLocationUpdates : 퍼미션 안가지고 있음");
                return;
            }


            Log.d("LOG", "startLocationUpdates : call mFusedLocationClient.requestLocationUpdates");

            mFusedLocationClient.requestLocationUpdates(locationRequest, locationCallback, Looper.myLooper());

            if (checkPermission())
                mGoogleMap.setMyLocationEnabled(true);

        }

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {

        Log.d("log", "onMapReady :");

        mGoogleMap = googleMap;


        //런타임 퍼미션 요청 대화상자나 GPS 활성 요청 대화상자 보이기전에
        //지도의 초기위치를 서울로 이동
        setDefaultLocation();



        //런타임 퍼미션 처리
        // 1. 위치 퍼미션을 가지고 있는지 체크합니다.
        int hasFineLocationPermission = ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION);
        int hasCoarseLocationPermission = ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_COARSE_LOCATION);



        if (hasFineLocationPermission == PackageManager.PERMISSION_GRANTED &&
                hasCoarseLocationPermission == PackageManager.PERMISSION_GRANTED   ) {

            // 2. 이미 퍼미션을 가지고 있다면
            // ( 안드로이드 6.0 이하 버전은 런타임 퍼미션이 필요없기 때문에 이미 허용된 걸로 인식합니다.)


            startLocationUpdates(); // 3. 위치 업데이트 시작


        }else {  //2. 퍼미션 요청을 허용한 적이 없다면 퍼미션 요청이 필요합니다. 2가지 경우(3-1, 4-1)가 있습니다.

            // 3-1. 사용자가 퍼미션 거부를 한 적이 있는 경우에는
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, REQUIRED_PERMISSIONS[0])) {

                // 3-2. 요청을 진행하기 전에 사용자가에게 퍼미션이 필요한 이유를 설명해줄 필요가 있습니다.
                Snackbar.make(mLayout, "이 앱을 실행하려면 위치 접근 권한이 필요합니다.",
                        Snackbar.LENGTH_INDEFINITE).setAction("확인", new View.OnClickListener() {

                    @Override
                    public void onClick(View view) {

                        // 3-3. 사용자게에 퍼미션 요청을 합니다. 요청 결과는 onRequestPermissionResult에서 수신됩니다.
                        ActivityCompat.requestPermissions( ApiActivity.this, REQUIRED_PERMISSIONS,
                                PERMISSIONS_REQUEST_CODE);
                    }
                }).show();


            } else {
                // 4-1. 사용자가 퍼미션 거부를 한 적이 없는 경우에는 퍼미션 요청을 바로 합니다.
                // 요청 결과는 onRequestPermissionResult에서 수신됩니다.
                ActivityCompat.requestPermissions( this, REQUIRED_PERMISSIONS,
                        PERMISSIONS_REQUEST_CODE);
            }

        }



        mGoogleMap.getUiSettings().setMyLocationButtonEnabled(true);
        mGoogleMap.animateCamera(CameraUpdateFactory.zoomTo(15));
        mGoogleMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {

            @Override
            public void onMapClick(LatLng latLng) {

            }
        });



    }


    @Override
    protected void onResume(){
        super.onResume();
        Log.i("log","api:onresume");
    }


    @Override
    protected void onStart() {
        super.onStart();
        Log.i("log","api:onstart");

        if (checkPermission()) {

            Log.d("log", "onStart : call mFusedLocationClient.requestLocationUpdates");
            mFusedLocationClient.requestLocationUpdates(locationRequest, locationCallback, null);


            if (mGoogleMap!=null)
                mGoogleMap.setMyLocationEnabled(true);

        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.i("log","api:stop");

        if (mFusedLocationClient != null) {

            Log.d("log", "onStop : call stopLocationUpdates");
            mFusedLocationClient.removeLocationUpdates(locationCallback);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.i("log","api:destory");
    }

    public boolean checkLocationServicesStatus() {
        LocationManager locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);

        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)
                || locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
    }

    public void setDefaultLocation() {


        //디폴트 위치, Seoul
        LatLng DEFAULT_LOCATION = new LatLng(37.56, 126.97);
        String markerTitle = "위치정보 가져올 수 없음";
        String markerSnippet = "위치 퍼미션과 GPS 활성 여부 확인하세요";


        if (currentMarker != null) currentMarker.remove();

        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.position(DEFAULT_LOCATION);
        markerOptions.title(markerTitle);
        markerOptions.snippet(markerSnippet);
        markerOptions.draggable(true);
        markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED));
        currentMarker = mGoogleMap.addMarker(markerOptions);

        CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(DEFAULT_LOCATION, 15);
        mGoogleMap.moveCamera(cameraUpdate);

    }

    //여기부터는 런타임 퍼미션 처리을 위한 메소드들
    private boolean checkPermission() {

        int hasFineLocationPermission = ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION);
        int hasCoarseLocationPermission = ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_COARSE_LOCATION);



        if (hasFineLocationPermission == PackageManager.PERMISSION_GRANTED &&
                hasCoarseLocationPermission == PackageManager.PERMISSION_GRANTED   ) {
            return true;
        }

        return false;

    }


      /* ActivityCompat.requestPermissions를 사용한 퍼미션 요청의 결과를 리턴받는 메소드입니다.
     */
    @Override
    public void onRequestPermissionsResult(int permsRequestCode,
                                           @NonNull String[] permissions,
                                           @NonNull int[] grandResults) {

        if ( permsRequestCode == PERMISSIONS_REQUEST_CODE && grandResults.length == REQUIRED_PERMISSIONS.length) {

            // 요청 코드가 PERMISSIONS_REQUEST_CODE 이고, 요청한 퍼미션 개수만큼 수신되었다면

            boolean check_result = true;


            // 모든 퍼미션을 허용했는지 체크합니다.

            for (int result : grandResults) {
                if (result != PackageManager.PERMISSION_GRANTED) {
                    check_result = false;
                    break;
                }
            }


            if ( check_result ) {

                // 퍼미션을 허용했다면 위치 업데이트를 시작합니다.
                startLocationUpdates();
            }
            else {
                // 거부한 퍼미션이 있다면 앱을 사용할 수 없는 이유를 설명해주고 앱을 종료합니다.2 가지 경우가 있습니다.

                if (ActivityCompat.shouldShowRequestPermissionRationale(this, REQUIRED_PERMISSIONS[0])
                        || ActivityCompat.shouldShowRequestPermissionRationale(this, REQUIRED_PERMISSIONS[1])) {


                    // 사용자가 거부만 선택한 경우에는 앱을 다시 실행하여 허용을 선택하면 앱을 사용할 수 있습니다.
                    Snackbar.make(mLayout, "퍼미션이 거부되었습니다. 앱을 다시 실행하여 퍼미션을 허용해주세요. ",
                            Snackbar.LENGTH_INDEFINITE).setAction("확인", new View.OnClickListener() {

                        @Override
                        public void onClick(View view) {

                            finish();
                        }
                    }).show();

                }else {


                    // "다시 묻지 않음"을 사용자가 체크하고 거부를 선택한 경우에는 설정(앱 정보)에서 퍼미션을 허용해야 앱을 사용할 수 있습니다.
                    Snackbar.make(mLayout, "퍼미션이 거부되었습니다. 설정(앱 정보)에서 퍼미션을 허용해야 합니다. ",
                            Snackbar.LENGTH_INDEFINITE).setAction("확인", new View.OnClickListener() {

                        @Override
                        public void onClick(View view) {

                            finish();
                        }
                    }).show();
                }
            }

        }
    }

    //여기부터는 GPS 활성화를 위한 메소드들
    private void showDialogForLocationServiceSetting() {

        AlertDialog.Builder builder = new AlertDialog.Builder(ApiActivity.this);
        builder.setTitle("위치 서비스 비활성화");
        builder.setMessage("앱을 사용하기 위해서는 위치 서비스가 필요합니다.\n"
                + "위치 설정을 수정하실래요?");
        builder.setCancelable(true);
        builder.setPositiveButton("설정", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int id) {
                Intent callGPSSettingIntent
                        = new Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                startActivityForResult(callGPSSettingIntent, GPS_ENABLE_REQUEST_CODE);
            }
        });
        builder.setNegativeButton("취소", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int id) {
                dialog.cancel();
            }
        });
        builder.create().show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {

            case GPS_ENABLE_REQUEST_CODE:

                //사용자가 GPS 활성 시켰는지 검사
                if (checkLocationServicesStatus()) {
                    if (checkLocationServicesStatus()) {

                        Log.d("log", "onActivityResult : GPS 활성화 되있음");


                        needRequest = true;

                        return;
                    }
                }

                break;
        }
    }

    private class DownloadWebpageTask extends AsyncTask<String, Void, String> {

        @Override
        protected  String doInBackground(String... urls){
            try{
                return (String) downloadUrl((String) urls[0]);
            }catch (IOException e){
                return "다운로드 실패";
            }
        }
        protected void onPostExecute(String result)
        {

            displayhupos(result);


        }
        private String downloadUrl(String myurl) throws IOException{
            HttpURLConnection conn = null;
            try{
                URL url = new URL(myurl);
                conn = (HttpURLConnection)url.openConnection();
                BufferedInputStream buf = new BufferedInputStream(conn.getInputStream());
                BufferedReader bufreader = new BufferedReader(new InputStreamReader(buf,"utf-8"));
                String line = null;
                String page = "";
                while((line = bufreader.readLine())!= null)
                    page += line;

                return page;
            }finally {
                conn.disconnect();
            }
        }
        private void displayhupos(String result){
            boolean initem = false, inbuildAddress = false, inbuildPlace = false, inclerkTel = false;
            boolean incnt = false, indistance = false, inmanager = false, inmanagerTel = false, inmfg = false;
            boolean inmodel = false, inorg = false, inrnum = false, inwgs84Lat = false, inwgs84Lon = false;
            boolean inzipcode1 = false, inzipcode2 = false;


            String buildAddress = null, buildPlace = null, clerkTel = null, cnt = null, distance = null;
            String manager = null, managerTel = null, mfg = null, model = null, org = null, rnum = null, wgs84Lat = null;
            String wgs84Lon = null, zipcode1 = null, zipcode2 = null;

            try{
                XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
                factory.setNamespaceAware(true);
                XmlPullParser xpp = factory.newPullParser();

                xpp.setInput(new StringReader(result));
                int eventType = xpp.getEventType();
                while(eventType != XmlPullParser.END_DOCUMENT){
                    if(eventType == XmlPullParser.START_DOCUMENT){
                        ;
                    }else if(eventType == XmlPullParser.START_TAG){
                        String tag_name = xpp.getName();
                        if(tag_name.equals("buildAddress"))
                            inbuildAddress = true;
                        if(tag_name.equals("buildPlace"))
                            inbuildPlace = true;
                        if(tag_name.equals("clerkTel"))
                            inclerkTel = true;
                        if(tag_name.equals("cnt"))
                            incnt = true;
                        if(tag_name.equals("distance"))
                            indistance = true;
                        if(tag_name.equals("manager"))
                            inmanager = true;
                        if(tag_name.equals("managerTel"))
                            inmanagerTel = true;
                        if(tag_name.equals("mfg"))
                            inmfg = true;
                        if(tag_name.equals("model"))
                            inmodel = true;
                        if(tag_name.equals("org"))
                            inorg = true;
                        if(tag_name.equals("rnum"))
                            inrnum = true;
                        if(tag_name.equals("wgs84Lat"))
                            inwgs84Lat = true;
                        if(tag_name.equals("wgs84Lon"))
                            inwgs84Lon = true;
                        if(tag_name.equals("zipcode1"))
                            inzipcode1 = true;
                        if(tag_name.equals("zipcode2"))
                            inzipcode2 = true;
                    }else if(eventType == XmlPullParser.TEXT){
                        if(inbuildAddress){ //isTitle이 true일 때 태그의 내용을 저장.
                            buildAddress = xpp.getText();
                            inbuildAddress = false;
                        }
                        if(inbuildPlace){ //isAddress이 true일 때 태그의 내용을 저장.
                            buildPlace = xpp.getText();
                            inbuildPlace = false;
                        }
                        if(inclerkTel){ //isMapx이 true일 때 태그의 내용을 저장.
                            clerkTel = xpp.getText();
                            inclerkTel = false;
                        }
                        if(incnt){ //isMapy이 true일 때 태그의 내용을 저장.
                            cnt = xpp.getText();
                            incnt = false;
                        }
                        if(inmanager){ //isMapy이 true일 때 태그의 내용을 저장.
                            manager = xpp.getText();
                            inmanager = false;
                        }
                        if(inmanagerTel){ //isMapy이 true일 때 태그의 내용을 저장.
                            managerTel = xpp.getText();
                            inmanagerTel = false;
                        }
                        if(inmfg){ //isMapy이 true일 때 태그의 내용을 저장.
                            mfg = xpp.getText();
                            inmfg = false;
                        }
                        if(inmodel){ //isMapy이 true일 때 태그의 내용을 저장.
                            model = xpp.getText();
                            inmodel = false;
                        }
                        if(inorg){ //isMapy이 true일 때 태그의 내용을 저장.
                            org = xpp.getText();
                            inorg = false;
                        }
                        if(inrnum){ //isMapy이 true일 때 태그의 내용을 저장.
                            rnum = xpp.getText();
                            inrnum = false;
                        }
                        if(inwgs84Lat) { //isMapy이 true일 때 태그의 내용을 저장.
                            wgs84Lat = xpp.getText();
                            inwgs84Lat = false;
                        }
                        if(inwgs84Lon) { //isMapy이 true일 때 태그의 내용을 저장.
                            wgs84Lon = xpp.getText();
                            inwgs84Lon = false;
                        }
                        if(inzipcode1){ //isMapy이 true일 때 태그의 내용을 저장.
                            zipcode1 = xpp.getText();
                            inzipcode1 = false;
                        }
                        if(inzipcode2){ //isMapy이 true일 때 태그의 내용을 저장.
                            zipcode2 = xpp.getText();
                            inzipcode2 = false;

                            displayMap(wgs84Lat, wgs84Lon, buildPlace, buildAddress);
                        }
                    }else if(eventType == XmlPullParser.END_TAG){
                        ;
                    }
                    eventType = xpp.next();
                }
            } catch (Exception e){
                ;
            }
        }
        private void displayMap(String wgs84Lat, String wgs84Lon, String buildPlace, String buildAddress){
            double latitude = Double.parseDouble(wgs84Lat);
            double longitude = Double.parseDouble(wgs84Lon);

            final LatLng LOC = new LatLng(latitude, longitude);

            MarkerOptions mk = new MarkerOptions();
            mk.position(LOC);
            mk.title(buildPlace);
            mk.snippet(buildAddress);
            mk.draggable(true);

            mGoogleMap.addMarker(mk);
            /*CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLng(LOC);
            mGoogleMap.moveCamera(cameraUpdate);*/

        }


    }

}
