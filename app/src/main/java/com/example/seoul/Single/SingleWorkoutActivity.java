package com.example.seoul.Single;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.seoul.R;

public class SingleWorkoutActivity extends AppCompatActivity {
    private Button mStartBtn, mPauseBtn, mStopBtn;
    private TextView mTimeTextView;

    //private String userID;

    private Thread timeThread1 = null;
    private Boolean isRunning = true;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.i("log","singleworkout:oncreate");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_workout);

        //userID= getIntent().getStringExtra("userID");
        // System.out.println(userID);

        mStartBtn = (Button)findViewById(R.id.start_btn);
        mStopBtn = (Button)findViewById(R.id.stop_btn);
        mPauseBtn = (Button)findViewById(R.id.pause_btn);

        mTimeTextView = (TextView)findViewById(R.id.timeView);

        //mTimeTextView.setText("00:00:00:00");

        mStartBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //v.setVisibility(View.GONE);

                timeThread1 = new Thread(new timeThread() );
                timeThread1.start();

            }
        });

        mStopBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        mPauseBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isRunning = !isRunning;
                if (isRunning) {
                    mPauseBtn.setText("PAUSE");
                } else {
                    mPauseBtn.setText("START");
                }
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

    @Override
    protected void onResume(){
        super.onResume();
        Log.i("log","workout:onresume");
    }


    @Override
    protected void onStart() {
        super.onStart();
        Log.i("log","workout:onstart");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.i("log","workout:stop");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.i("log","workout:destory");
    }
}
