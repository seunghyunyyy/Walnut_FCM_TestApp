package com.test.fcm_test;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import org.json.JSONException;
import org.json.JSONObject;

public class HomeActivity extends AppCompatActivity {
    final String url = "http://192.168.50.190:8080/push/v1";
    String messages;//메시지 리스트
    String msgID;//세부 메시지


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.i("save token", SaveSharedPreference.getString(getApplicationContext(), "token"));


        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_activity);

        Button messageList = (Button) findViewById(R.id.messageList);
        messageList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), MessagesListActivity.class);
                startActivity(intent);
            }
        });


        Button signOutBtn = (Button) findViewById(R.id.home_sign_out_btn);
        signOutBtn.setOnClickListener(v -> {
            SaveSharedPreference.clearPreferences1(getApplicationContext());
            System.out.println(SaveSharedPreference.getPreferences(getApplicationContext()));
            Intent intent = new Intent(getApplicationContext(), SignInActivity.class);
            Toast.makeText(HomeActivity.this,"로그아웃",Toast.LENGTH_SHORT).show();

            startActivity(intent);
            finish();
        });


    }
}
