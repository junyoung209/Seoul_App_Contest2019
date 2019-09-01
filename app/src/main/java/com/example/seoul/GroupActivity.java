package com.example.seoul;


import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.example.seoul.Group.GrouplistFragment;
import com.example.seoul.Group.MycrewlistFragment;
import com.google.android.material.tabs.TabLayout;

public class GroupActivity extends AppCompatActivity {

    private ImageButton bt_img1, bt_img2,bt_img3,bt_img4;
    private TabLayout tabLayout;
    private ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.i("log","group:create");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group);

        GrouplistFragment tab1 = new GrouplistFragment();
        MycrewlistFragment tab2 = new MycrewlistFragment();

        //Initializing the TabLayout;
        tabLayout = (TabLayout)findViewById(R.id.tabLayout);
        tabLayout.addTab(tabLayout.newTab().setText("Group List"));
        tabLayout.addTab(tabLayout.newTab().setText("My Group List"));
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
        //Initializing ViewPager
        viewPager = (ViewPager)findViewById(R.id.viewPager);

        //Creating adapter
        TabPagerAdapter pagerAdapter = new TabPagerAdapter(getSupportFragmentManager(), tabLayout.getTabCount());
        pagerAdapter.addItem(tab1);
        pagerAdapter.addItem(tab2);
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

        bt_img2.setImageResource(R.drawable.ic_group_black_24dp);


        // 탭 버튼에 대한 리스너 연결
        bt_img1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                Intent intent=new Intent(GroupActivity.this,SingleActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                GroupActivity.this.startActivity(intent);
            }
        });

        bt_img3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent=new Intent(GroupActivity.this,MypageActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                GroupActivity.this.startActivity(intent);
            }
        });
        bt_img4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent=new Intent(GroupActivity.this,ApiActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                GroupActivity.this.startActivity(intent);
            }
        });

        // 임의로 액티비티 호출 시점에 어느 프레그먼트를 프레임레이아웃에 띄울 것인지를 정함



    }

    @Override
    protected void onResume(){
        super.onResume();
        Log.i("log","group:onresume");
    }


    @Override
    protected void onStart() {
        super.onStart();
        Log.i("log","group:onstart");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.i("log","group:stop");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.i("log","group:destory");
    }


}
