package com.test.fcm_test;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.regex.Pattern;

public class SignUpActivity extends AppCompatActivity {

    private static final String TAG = "SignUpActivity";
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        mAuth = FirebaseAuth.getInstance();

        Button signUpBtn = findViewById(R.id.sign_up_btn);
        signUpBtn.setOnClickListener(v -> signUp());
    }

    @Override
    public void onStart() {
        super.onStart();
        FirebaseUser currentUser = mAuth.getCurrentUser();
    }

    private void signUp() {
        Pattern pattern = Patterns.EMAIL_ADDRESS;
        String password;
        // 이메일
        EditText emailEditText = findViewById(R.id.sign_up_email);
        String email = emailEditText.getText().toString();
        // 비밀번호
        EditText passwordEditText = findViewById(R.id.sign_up_password);
        String passwordCheck1 = passwordEditText.getText().toString();

        EditText passwordCheckEditText = findViewById(R.id.sign_up_password_check);
        String passwordCheck2 = passwordCheckEditText.getText().toString();

        if (!passwordCheck1.equals(passwordCheck2)) {
            if (passwordCheck1.equals("") || passwordCheck2.equals("")){
                Toast.makeText(SignUpActivity.this, "공백 입력", Toast.LENGTH_SHORT).show();
            }
            else {
                Toast.makeText(SignUpActivity.this, "비밀번호 일치하지 않음", Toast.LENGTH_SHORT).show();
            }
            return;
        } else {
            password = passwordCheck1;
        }

        if(email.equals("")) {
            Toast.makeText(SignUpActivity.this, "공백 입력", Toast.LENGTH_SHORT).show();
            return;
        }

        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Log.d(TAG, "createUserWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            Intent intent = new Intent(SignUpActivity.this, MainActivity.class);
                            startActivity(intent);
                            Toast.makeText(SignUpActivity.this, "등록 완료", Toast.LENGTH_SHORT).show();
                            finish();
                        } else {
                            if (!pattern.matcher(email).matches()) {
                                Toast.makeText(SignUpActivity.this, "이메일 형식 에러", Toast.LENGTH_SHORT).show();
                                return;
                            }
                            else
                                Toast.makeText(SignUpActivity.this, "이메일 중복", Toast.LENGTH_SHORT).show();
                            return;
                        }
                    }
                });
    }
    Intent intent = getIntent();
}
