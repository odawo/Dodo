package com.vanessaodawo.driverapp;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.vanessaodawo.driverapp.fragments.Login;
import com.vanessaodawo.driverapp.fragments.Register;

public class MainActivity extends AppCompatActivity {

    Button gSign, fSign, emailSignUp;
    TextView tvLogin, lbl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        gSign = findViewById(R.id.gSignUp);
        fSign = findViewById(R.id.fSignUp);
        emailSignUp = findViewById(R.id.emailSignUp);
        tvLogin = findViewById(R.id.tvLogin);
        lbl = findViewById(R.id.lbl);

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
//                startActivity(new Intent(MainActivity.this, Register.class));
                gSign.setVisibility(View.INVISIBLE);
                fSign.setVisibility(View.INVISIBLE);
                emailSignUp.setVisibility(View.INVISIBLE);
                lbl.setVisibility(View.INVISIBLE);
                tvLogin.setVisibility(View.INVISIBLE);
                loadFragment(new Register());
            }
        });


        tvLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                startActivity(new Intent(MainActivity.this, Login.class));
                gSign.setVisibility(View.INVISIBLE);
                fSign.setVisibility(View.INVISIBLE);
                emailSignUp.setVisibility(View.INVISIBLE);
                lbl.setVisibility(View.INVISIBLE);
                tvLogin.setVisibility(View.INVISIBLE);
                loadFragment(new Login());
            }
        });
    }

    private void loadFragment(Register register) {
        android.support.v4.app.FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.frameLayout, new Register());
        ft.commit();
    }

    private void loadFragment(Login login) {
        android.support.v4.app.FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.frameLayout, new Login());
        ft.commit();
    }


    private void googleSignUpBtn() {
    }

    private void facebookSignUpBtn() {
    }
}
