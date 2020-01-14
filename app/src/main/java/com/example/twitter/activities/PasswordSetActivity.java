package com.example.twitter.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;

import com.example.twitter.R;
import com.google.android.material.textfield.TextInputEditText;

public class PasswordSetActivity extends AppCompatActivity {
    Button btnPasswordNext;
    TextInputEditText etRegisterPassword;
    String username, phoneEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_password_set);

        etRegisterPassword = findViewById(R.id.etRegisterPassword);
        btnPasswordNext = findViewById(R.id.btnPasswordNext);

        Bundle bundle = getIntent().getExtras();
        if (bundle != null){
            username = bundle.getString("username");
            phoneEmail = bundle.getString("phoneEmail");
        }

//        etRegisterPassword.setText(username + " " + phoneEmail);
        btnPasswordNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty(etRegisterPassword.getText().toString())){
                    etRegisterPassword.setError("Please enter password");
                }
                else {
                    Intent intent = new Intent(PasswordSetActivity.this, ProfileImageActivity.class);
                    intent.putExtra("username",username);
                    intent.putExtra("phoneEmail",phoneEmail);
                    intent.putExtra("password",etRegisterPassword.getText().toString());

                    startActivity(intent);
                }
            }
        });
    }
}
