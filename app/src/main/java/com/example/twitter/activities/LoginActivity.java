package com.example.twitter.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.twitter.DashboardActivity;
import com.example.twitter.R;
import com.example.twitter.bill.LoginBill;
import com.example.twitter.strictmode.StrictModeClass;
import com.google.android.material.textfield.TextInputEditText;

public class LoginActivity extends AppCompatActivity {

    EditText etLoginUsername;
    TextInputEditText etLoginPassword;
    Button btnLogin;
    TextView txtSignUp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        etLoginUsername = findViewById(R.id.etLoginUsername);
        etLoginPassword = findViewById(R.id.etLoginPassword);
        txtSignUp = findViewById(R.id.txtSignUp);
        btnLogin = findViewById(R.id.btnLogin);

        txtSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, PolicyActivity.class);
                startActivity(intent);
            }
        });

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty(etLoginUsername.getText().toString())){
                    etLoginUsername.setError("username is empty");
                }
                else if (TextUtils.isEmpty(etLoginPassword.getText().toString())){
                    etLoginPassword.setError("Password is empty");
                }
                else {
                    login();
                }
            }
        });
    }

    private void login() {
        String username = etLoginUsername.getText().toString();
        String password = etLoginPassword.getText().toString();

        LoginBill loginBLL = new LoginBill();

        StrictModeClass.StrictMode();
        if (loginBLL.checkUser(username, password)) {
            Intent intent = new Intent(LoginActivity.this, DashboardActivity.class);
            startActivity(intent);
            finish();
        } else {
            Toast.makeText(this, "Either username or password is incorrect", Toast.LENGTH_SHORT).show();
            etLoginUsername.requestFocus();
        }
    }
}
