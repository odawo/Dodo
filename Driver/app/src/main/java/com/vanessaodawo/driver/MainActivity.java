package com.vanessaodawo.driver;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    Button gSign, fSign, emailSignUp;
    TextView tvLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mainactivity);

        gSign = findViewById(R.id.gSignUp);
        fSign = findViewById(R.id.fSignUp);
        emailSignUp = findViewById(R.id.emailSignUp);
        tvLogin = findViewById(R.id.tvLogin);

        gSign.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                googleSignUpBtn();
            }
        });

        fSign.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                facebookSignUpBtn();
            }
        });


        emailSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, Register.class));
            }
        });


        tvLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, Login.class));
            }
        });

    }

    private void googleSignUpBtn() {
    }

    private void facebookSignUpBtn() {
    }
}
