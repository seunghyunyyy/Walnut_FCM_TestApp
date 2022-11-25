package com.test.fcm_test;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.gson.JsonObject;

import java.util.logging.Logger;

public class SignInActivity extends AppCompatActivity {
    private static final String TAG = "SignInActivity";
    private static final String url = "http://192.168.50.190:8080/push/v1";

    private FirebaseAuth mAuth;
    private Boolean check;
    String Token;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        mAuth = FirebaseAuth.getInstance();
        Button signInBtn = findViewById(R.id.sign_in_btn);
        Button signUpBtn = findViewById(R.id.sign_in_register);


        signInBtn.setOnClickListener(view -> signIn());

        signUpBtn.setOnClickListener(view -> {
            Intent intent = new Intent(SignInActivity.this, SignUpActivity.class);
            startActivity(intent);
        });
    }

    private void signIn() {
        // 이메일
        EditText emailEditText = findViewById(R.id.sign_in_email);
        String email = emailEditText.getText().toString();
        // 비밀번호
        EditText passwordEditText = findViewById(R.id.sign_in_password);
        String password = passwordEditText.getText().toString();
        CheckBox autoLogin = (CheckBox) findViewById(R.id.sign_in_auto_login) ;

        if(email.equals("")|| password.equals("")) {
            Toast.makeText(SignInActivity.this, "공백입력", Toast.LENGTH_SHORT).show();
            return;
        }

        mAuth.signInWithEmailAndPassword(email,password)
                .addOnCompleteListener(SignInActivity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (!task.isSuccessful()) {
                            Log.w(TAG, "Fetching FCM registration token failed", task.getException());
                            Toast.makeText(SignInActivity.this,"로그인 오류",Toast.LENGTH_SHORT).show();
                            return;
                        }

                        Intent intent = new Intent(SignInActivity.this, HomeActivity.class);
                        Toast.makeText(SignInActivity.this,"로그인 성공",Toast.LENGTH_SHORT).show();
                        saveToken(email);
                        if (autoLogin.isChecked()) {
                                //자동 로그인이 설정되었으면 저장하기
                            SaveSharedPreference.setLoginInfo(SignInActivity.this, email, password);
                        }
                        System.out.println("###############################Login Info : " + SaveSharedPreference.getLoginInfo(getApplicationContext()) + "#######################");

                        startActivity(intent);
                        finish();

                    }

                });
    }

    public void saveToken(String email) {
        FirebaseMessaging.getInstance().getToken()
                .addOnCompleteListener(new OnCompleteListener<String>() {
                    @Override
                    public void onComplete(@NonNull Task<String> task) {
                        if (!task.isSuccessful()) {
                            Log.w(TAG, "Fetching FCM registration token failed", task.getException());
                            return;
                        }
                        // Get new FCM registration token
                        Token = task.getResult();
                        System.out.println("1        "+Token);
                        // Log
                        String msg = getString(R.string.msg_token_fmt, Token);
                        Log.d(TAG, msg);
                        if (Token != SaveSharedPreference.getString(getApplicationContext(), "token")) {
                            SaveSharedPreference.clearPreferences(getApplicationContext(), "token");
                            SaveSharedPreference.setToken(getApplicationContext(), Token);
                            System.out.println("2        "+Token);
                            System.out.println("3        "+SaveSharedPreference.getString(getApplicationContext(), "token"));
                            //조회한 토큰 값과 기기에 저장된 토큰 값이 다른 경우 토큰 값을 삭제하고 새로 저장한다.
                            //한 이메일로 여러개의 기계를 사용할 수 없음
                        }

                        Toast.makeText(SignInActivity.this,email + "\n" + Token,Toast.LENGTH_SHORT).show();

                        Thread thread = new Thread(){
                            public void run(){
                                HttpUtility.putToken(url + "/tokens", email, Token);
                            }
                        };
                        thread.start();
                    }
                });
    }
}
