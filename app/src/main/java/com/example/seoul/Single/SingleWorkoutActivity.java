package com.example.seoul.Single;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.seoul.R;

import java.text.SimpleDateFormat;
import java.util.Date;

public class SingleWorkoutActivity extends AppCompatActivity {
    private Button mStartBtn, mStopBtn;
    private TextView mTimeTextView;
    private String userID;
    private String dateToday;


    private Thread timeThread1 = null;
    private Boolean isRunning = true;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.i("log","singleworkout:oncreate");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_workout);


        userID= getIntent().getStringExtra("userID");

        Log.i("log",userID);
        mStartBtn = (Button)findViewById(R.id.start_btn);
        mStopBtn = (Button)findViewById(R.id.stop_btn);
        mTimeTextView = (TextView)findViewById(R.id.timeView);


        mStartBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //v.setVisibility(View.GONE);
                if(timeThread1==null)
                {
                    mStartBtn.setText("PAUSE");
                    timeThread1 = new Thread(new timeThread() );
                    timeThread1.start();
                }
                else
                {
                    isRunning = !isRunning;
                    if (isRunning) {
                        mStartBtn.setText("PAUSE");
                    } else {
                        mStartBtn.setText("START");
                    }
                }


            }
        });

        mStopBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.i("log",getToDay());
                Intent intent = new Intent(SingleWorkoutActivity.this, DatauploadActivity.class);
                intent.putExtra("userID",userID);
                intent.putExtra("runTime",mTimeTextView.getText());
                intent.putExtra("dateToday",getToDay());//액티비티 전환하면서 id넘겨
                SingleWorkoutActivity.this.startActivity(intent);
                finish();
            }
        });



    }

    @SuppressLint("HandlerLeak")
    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            int sec = (msg.arg1 / 100) % 60;
            int min = (msg.arg1 / 100) / 60;
            int hour = (msg.arg1 / 100) / 360;
            //1000이 1초 1000*60 은 1분 1000*60*10은 10분 1000*60*60은 한시간

            @SuppressLint("DefaultLocale") String result = String.format("%d:%02d:%02d", hour, min, sec);
            mTimeTextView.setText(result);
        }
    };

    public class timeThread implements Runnable {
        @Override
        public void run() {
            int i = 0;

            while (true) {
                while (isRunning) { //일시정지를 누르면 멈춤
                    Message msg = new Message();
                    msg.arg1 = i++;
                    handler.sendMessage(msg);

                    try {
                        Thread.sleep(10);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                        return; // 인터럽트 받을 경우 return
                    }
                }
            }
        }
    }

    public static String getToDay(){

        SimpleDateFormat date=new SimpleDateFormat("yyyy-MM-dd");
        return date.format(new Date());
    }



}
