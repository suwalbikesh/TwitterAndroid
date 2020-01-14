package com.example.twitter.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import com.example.twitter.R;

public class VerificationCodeActivity extends AppCompatActivity {
    ImageButton btnVerifyCodeBack;

    EditText etCode;
    Button btnVerifyCodeNext;

    String username, phoneEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verification_code);

        btnVerifyCodeBack = findViewById(R.id.btnVerifyCodeBack);
        btnVerifyCodeNext = findViewById(R.id.btnVerifyCodeNext);
        etCode = findViewById(R.id.etCode);

        etCode.setText("01578");
        Bundle bundle = getIntent().getExtras();
        if (bundle != null){
            username = bundle.getString("username");
            phoneEmail = bundle.getString("phoneEmail");
        }

//        etCode.setText(username+" "+phoneEmail);

        btnVerifyCodeNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (etCode.getText().toString().equals("01578")){
                    Intent intent = new Intent(VerificationCodeActivity.this, PasswordSetActivity.class);
                    intent.putExtra("username",username);
                    intent.putExtra("phoneEmail",phoneEmail);
                    startActivity(intent);
                }
                else {
                    etCode.setError("Code is invalid");
                }
            }
        });

        btnVerifyCodeBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

    }
}
