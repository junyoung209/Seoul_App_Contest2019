package com.example.seoul;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;

public class MypageActivity extends AppCompatActivity {



    private ImageButton bt_img1, bt_img2,bt_img3,bt_img4;
    private String userID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.i("log","mypage:oncreate");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mypage);

        userID= getIntent().getStringExtra("userID");
        // 위젯에 대한 참조
        bt_img1 = (ImageButton)findViewById(R.id.imgbt1);
        bt_img2 = (ImageButton)findViewById(R.id.imgbt2);
        bt_img3 = (ImageButton)findViewById(R.id.imgbt3);
        bt_img4 = (ImageButton)findViewById(R.id.imgbt4);
        bt_img3.setImageResource(R.drawable.ic_format_align_left_black_24dp);

        // 탭 버튼에 대한 리스너 연결
        bt_img1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent=new Intent(MypageActivity.this,SingleActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);

                intent.putExtra("userID",userID);
                MypageActivity.this.startActivity(intent);
            }
        });
        bt_img2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent=new Intent(MypageActivity.this,GroupActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                intent.putExtra("userID",userID);
                MypageActivity.this.startActivity(intent);
            }
        });

        bt_img4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent=new Intent(MypageActivity.this,ApiActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                intent.putExtra("userID",userID);
                MypageActivity.this.startActivity(intent);
            }
        });

        // 임의로 액티비티 호출 시점에 어느 프레그먼트를 프레임레이아웃에 띄울 것인지를 정함



    }
    @Override
    protected void onResume(){
        super.onResume();
        Log.i("log","mypage:onresume");
    }


    @Override
    protected void onStart() {
        super.onStart();
        Log.i("log","mypage:onstart");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.i("log","mypage:stop");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.i("log","mypage:destory");
    }
}
