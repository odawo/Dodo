package com.vanessaodawo.driverapp.fragments;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.vanessaodawo.driverapp.HomePage;
import com.vanessaodawo.driverapp.MainActivity;
import com.vanessaodawo.driverapp.POJO.Driver;
import com.vanessaodawo.driverapp.Profile;
import com.vanessaodawo.driverapp.R;

public class Register extends Fragment {

    Button registerBtn;
    ImageButton backBtn;
    EditText fname, lname, email, password, tel;

    FirebaseAuth firebaseAuth;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference driverRef;

    FirebaseUser firebaseUser;


    public Register() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view  = inflater.inflate(R.layout.fragment_register, container, false);

        registerBtn = view.findViewById(R.id.registerBtn);
        backBtn = view.findViewById(R.id.backBt);
        fname = view.findViewById(R.id.firstName);
        lname = view.findViewById(R.id.lastName);
        email = view.findViewById(R.id.email);
        password = view.findViewById(R.id.password);
        tel = view.findViewById(R.id.telephone);

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseDatabase = FirebaseDatabase.getInstance();
        driverRef = firebaseDatabase.getReference("DRIVER");

        registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registerUser();
            }
        });

        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), MainActivity.class));
            }
        });

        return view;
    }

    private void registerUser() {

        if (fname == null || lname == null || email == null || password == null || tel == null){
            Toast.makeText(getActivity(), "Kindly fill all empty fields", Toast.LENGTH_SHORT).show();
        } else {

            final String em = email.getText().toString().trim();
            final String pass = password.getText().toString().trim();
            final String fn = fname.getText().toString().trim();
            final String ln = lname.getText().toString().trim();
            final int tl = Integer.parseInt(tel.getText().toString().trim());

            firebaseAuth.createUserWithEmailAndPassword(em, pass).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                @Override
                public void onSuccess(AuthResult authResult) {
                    Driver driver = new Driver();
                    driver.setFname(fn);
                    driver.setLname(ln);
                    driver.setEmail(em);
                    driver.setTel(tl);

                    driverRef.child(firebaseAuth.getCurrentUser().getUid()).setValue(driver).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                Snackbar.make(getView(), fn + " registered.", Snackbar.LENGTH_SHORT).show();
                                firebaseUser = firebaseAuth.getCurrentUser();
                                updateUI(firebaseUser);
                                startActivity(new Intent(getActivity(), Profile.class));
                            }
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Snackbar.make(getView(), " ERROR : " + e.getMessage(), Snackbar.LENGTH_SHORT).show();
                            updateUI(null);
                        }
                    });
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(getActivity(), "ERROR :" + e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });

        }

    }

    private void updateUI(Object o) {
        if(firebaseUser != null){
            startActivity(new Intent(getActivity(), HomePage.class));
        }
    }
}
