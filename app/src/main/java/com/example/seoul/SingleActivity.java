package com.example.seoul;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.example.seoul.Group.GrouplistFragment;
import com.example.seoul.Single.CrewrecomFragment;
import com.example.seoul.Single.MyrecordFragment;
import com.example.seoul.Single.RunFragment;
import com.example.seoul.Single.RunguideFragment;
import com.google.android.material.tabs.TabLayout;

public class SingleActivity extends AppCompatActivity {

    private ImageButton bt_img1, bt_img2,bt_img3,bt_img4;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private String userID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.i("log","single:oncreate");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single);

        userID= getIntent().getStringExtra("userID");


        RunFragment tab1 = new RunFragment();
        RunguideFragment tab2 = new RunguideFragment();
        CrewrecomFragment tab3 = new CrewrecomFragment();
        MyrecordFragment tab4 = new MyrecordFragment();

        Bundle bundle=new Bundle();
        bundle.putString("userID",userID);
        tab1.setArguments(bundle);
        tab2.setArguments(bundle);
        tab3.setArguments(bundle);
        tab4.setArguments(bundle);

        tabLayout = (TabLayout)findViewById(R.id.tabLayout);
        tabLayout.addTab(tabLayout.newTab().setText("운동화면"));
        tabLayout.addTab(tabLayout.newTab().setText("러닝가이드"));
        tabLayout.addTab(tabLayout.newTab().setText("러닝크루 추천"));
        tabLayout.addTab(tabLayout.newTab().setText("My info"));

        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
        //Initializing ViewPager
        viewPager = (ViewPager)findViewById(R.id.viewPager);

        //Creating adapter
        TabPagerAdapter pagerAdapter = new TabPagerAdapter(getSupportFragmentManager(), tabLayout.getTabCount());
        pagerAdapter.addItem(tab1);
        pagerAdapter.addItem(tab2);
        pagerAdapter.addItem(tab3);
        pagerAdapter.addItem(tab4);

        viewPager.setAdapter(pagerAdapter);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        //Set TabSelectedListener
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        // 위젯에 대한 참조
        bt_img1 = (ImageButton)findViewById(R.id.imgbt1);
        bt_img2 = (ImageButton)findViewById(R.id.imgbt2);
        bt_img3 = (ImageButton)findViewById(R.id.imgbt3);
        bt_img4 = (ImageButton)findViewById(R.id.imgbt4);
        bt_img1.setImageResource(R.drawable.ic_person_black_24dp);



        // 탭 버튼에 대한 리스너 연결

        bt_img2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent=new Intent(SingleActivity.this,GroupActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                SingleActivity.this.startActivity(intent);
            }
        });
        bt_img3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent=new Intent(SingleActivity.this,MypageActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                SingleActivity.this.startActivity(intent);
            }
        });
        bt_img4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent=new Intent(SingleActivity.this,ApiActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                SingleActivity.this.startActivity(intent);
            }
        });




    }


}
