package com.example.twitter.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.twitter.R;

public class PolicyActivity extends AppCompatActivity {

    EditText etRegisterUsername, etRegisterPhoneEmail;
    TextView txtPolicy;
    Button btnPolicyNext;
    ImageButton btnPolicyBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_policy);

        txtPolicy = findViewById(R.id.txtPolicy);
        btnPolicyNext = findViewById(R.id.btnPolicyNext);
        btnPolicyBack = findViewById(R.id.btnPolicyBack);
        etRegisterUsername = findViewById(R.id.etRegisterUsername);
        etRegisterPhoneEmail = findViewById(R.id.etRegisterPhoneEmail);

        txtPolicy.setText(Html.fromHtml(
                " By signing up, you agree to the <font color=\"#38A1F3\">Terms of Service</font> and <font color=\"#38A1F3\">Privacy Policy</font>, " +
                        "including <font color=\"#38A1F3\">Cookie Use</font>. " +
                        "Others will be able to find you by email or phone number when provided ." +
                        " <font color=\"#38A1F3\">Privacy Options</font>\n"));

        btnPolicyNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(TextUtils.isEmpty(etRegisterUsername.getText().toString())){
                    etRegisterUsername.setError("Please enter username");
                }
                else if(TextUtils.isEmpty(etRegisterPhoneEmail.getText().toString())){
                    etRegisterPhoneEmail.setError("Please enter phone number or email");
                }
                else {
                    Intent intent = new Intent(PolicyActivity.this,VerificationCodeActivity.class);
                    intent.putExtra("username",etRegisterUsername.getText().toString());
                    intent.putExtra("phoneEmail",etRegisterPhoneEmail.getText().toString());
                    startActivity(intent);
                }

            }
        });
        btnPolicyBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });


    }
}
