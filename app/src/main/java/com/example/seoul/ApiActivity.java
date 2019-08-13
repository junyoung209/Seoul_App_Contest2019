package com.example.seoul;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;

public class ApiActivity extends AppCompatActivity {

    private ImageButton bt_img1, bt_img2,bt_img3,bt_img4;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.i("log","api:oncreate");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_api);

//        final String userID= getIntent().getStringExtra("userID");
//        System.out.println(userID);

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
                ApiActivity.this.startActivity(intent);
            }
        });
        bt_img2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent=new Intent(ApiActivity.this,GroupActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                ApiActivity.this.startActivity(intent);
            }
        });
        bt_img3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent=new Intent(ApiActivity.this,MypageActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                ApiActivity.this.startActivity(intent);
            }
        });


        // 임의로 액티비티 호출 시점에 어느 프레그먼트를 프레임레이아웃에 띄울 것인지를 정함



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
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.i("log","api:stop");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.i("log","api:destory");
    }

}