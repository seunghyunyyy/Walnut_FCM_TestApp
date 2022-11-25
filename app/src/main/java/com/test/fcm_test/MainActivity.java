package com.test.fcm_test;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.google.firebase.messaging.FirebaseMessaging;

import java.util.Map;
import java.util.Objects;


public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";//fcm
    private static final String url = "http://192.168.50.190:8080/push/v1";
    private static String Token;
    SignInActivity token;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Intent intent;
        if (!Objects.requireNonNull(SaveSharedPreference.getLoginInfo(this).get("email")).isEmpty()) {
            intent = new Intent(MainActivity.this, HomeActivity.class);
            //저장된 로그인 정보 불러오기
            Map<String, String> loginInfo = SaveSharedPreference.getLoginInfo(this);
            String email    = loginInfo.get("email");
            String password = loginInfo.get("password");
            SaveSharedPreference.setLoginInfo(this, email, password);
            System.out.println(email + " " + password);
            FirebaseMessaging.getInstance().getToken()
                    .addOnCompleteListener(task -> {
                        if (!task.isSuccessful()) {
                            Log.w("", "Fetching FCM registration token failed", task.getException());
                            return;
                        }
                        Token = task.getResult();
                        String msg = getString(R.string.msg_token_fmt, Token);
                        Log.d("", msg);
                        if (Token != SaveSharedPreference.getString(getApplicationContext(), "token")) {
                            SaveSharedPreference.clearPreferences(getApplicationContext(), "token");
                            SaveSharedPreference.setToken(getApplicationContext(), Token);
                            //조회한 토큰 값과 기기에 저장된 토큰 값이 다른 경우 토큰 값을 삭제하고 새로 저장한다.
                            //한 이메일로 여러개의 기계를 사용할 수 없음
                        }
                        System.out.println("Auto Login Token ---------------------------"+ Token);
                    });
        }
        else {
            intent = new Intent(MainActivity.this, SignInActivity.class);
        }
        startActivity(intent);
        finish();
    }

}