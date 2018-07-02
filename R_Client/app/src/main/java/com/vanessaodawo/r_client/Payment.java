package com.vanessaodawo.r_client;

import android.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.vanessaodawo.r_client.Fragments.AddCard;
import com.vanessaodawo.r_client.Fragments.SecurityMeasure;

public class Payment extends AppCompatActivity {

    TextView cred_debit, sec_measure;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);

        cred_debit = findViewById(R.id.credCard);
        sec_measure = findViewById(R.id.secMeasure);

        cred_debit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadFragment(new AddCard());
            }
        });

        sec_measure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadFragment(new SecurityMeasure());
//                Toast.makeText(Payment.this, "sec measure clicked.", Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void loadFragment(AddCard addCard) {
        android.support.v4.app.FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.payFrame, new AddCard());
        ft.commit();
    }

    private void loadFragment(SecurityMeasure securityMeasure) {
        android.support.v4.app.FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.payFrame, new SecurityMeasure());
        ft.commit();
    }

}
