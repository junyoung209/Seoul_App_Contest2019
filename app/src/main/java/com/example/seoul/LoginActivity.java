package com.example.seoul;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

public class LoginActivity extends AppCompatActivity {

    private AlertDialog dialog;
    private SharedPreferences appData;
    private String userID;
    private String userPassword;
    private CheckBox checkBox;
    private EditText idText;
    private EditText passwordText;
    private Button loginButton;
    private boolean saveLoginData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        TextView registerButton=(TextView)findViewById(R.id.registerButton);
        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent registerIntent = new Intent(LoginActivity.this,RegisterActivity.class);
                LoginActivity.this.startActivity(registerIntent);
            }
        });

        appData=getSharedPreferences("appData",MODE_PRIVATE);
        load();

        idText = (EditText) findViewById(R.id.idText);
        passwordText = (EditText) findViewById(R.id.passwordText);
        loginButton = (Button)findViewById(R.id.loginButton);
        checkBox=(CheckBox)findViewById(R.id.checkBox);

        if(saveLoginData)
        {
            idText.setText(userID);
            passwordText.setText(userPassword);
            checkBox.setChecked(saveLoginData);
        }

        loginButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                userID = idText.getText().toString();
                userPassword = passwordText.getText().toString();
                Response.Listener<String> responseLisner = new Response.Listener<String>(){
                    @Override

                    public void onResponse(String response) {
                        try{
                            JSONObject jsonResponse = new JSONObject(response);
                            boolean success = jsonResponse.getBoolean("success");

                            if(success){
//                                AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this);
//                                dialog = builder.setMessage("로그인에 성공했습니다")
//                                        .setPositiveButton("확인", null)
//                                        .create();
//                                dialog.show();
                                save();
                                Intent intent = new Intent(LoginActivity.this, SingleActivity.class);
                                intent.putExtra("userID",userID); //액티비티 전환하면서 id넘겨
                                LoginActivity.this.startActivity(intent);
                                finish();
                            }else {
                                AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this);
                                dialog = builder.setMessage("계정을 다시 확인하세요")
                                        .setNegativeButton("다시시도", null)
                                        .create();
                                dialog.show();
                            }
                        }catch (Exception e){
                            e.printStackTrace();
                        }
                    }
                };
                LoginRequest loginRequest = new LoginRequest(userID, userPassword, responseLisner);
                RequestQueue queue = Volley.newRequestQueue(LoginActivity.this);
                queue.add(loginRequest);
            }

        });


    }
    private void save() {
        // SharedPreferences 객체만으론 저장 불가능 Editor 사용
        SharedPreferences.Editor editor = appData.edit();

        // 에디터객체.put타입( 저장시킬 이름, 저장시킬 값 )
        // 저장시킬 이름이 이미 존재하면 덮어씌움
        editor.putBoolean("SAVE_LOGIN_DATA", checkBox.isChecked());
        editor.putString("ID", idText.getText().toString().trim());
        editor.putString("PWD", passwordText.getText().toString().trim());

        // apply, commit 을 안하면 변경된 내용이 저장되지 않음
        editor.apply();
    }

    // 설정값을 불러오는 함수
    private void load() {
        // SharedPreferences 객체.get타입( 저장된 이름, 기본값 )
        // 저장된 이름이 존재하지 않을 시 기본값
        saveLoginData = appData.getBoolean("SAVE_LOGIN_DATA", false);
        userID = appData.getString("ID", "");
        userPassword = appData.getString("PWD", "");
    }
}
