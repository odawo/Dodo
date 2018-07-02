package com.vanessaodawo.r_client.Fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.vanessaodawo.r_client.MainActivity;
import com.vanessaodawo.r_client.POJO.Clients;
import com.vanessaodawo.r_client.R;

public class Register extends Fragment {

    EditText f_name, l_name, email, password, telephone;
    Button registerBtn;
    ImageButton backBtn;

    FirebaseAuth firebaseAuth;
    FirebaseUser firebaseUser;
    DatabaseReference newClientRef;

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

        f_name = view.findViewById(R.id.regfirstName);
        l_name = view.findViewById(R.id.reglastName);
        email = view.findViewById(R.id.regemail);
        password = view.findViewById(R.id.regpassword);
        telephone = view.findViewById(R.id.regtelephone);
        registerBtn = view.findViewById(R.id.registerBtn);
        backBtn = view.findViewById(R.id.backBt);

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

        firebaseAuth = FirebaseAuth.getInstance();
        newClientRef = FirebaseDatabase.getInstance().getReference();

        return view;
    }

    private void registerUser() {

        String fname = f_name.getText().toString().trim();
        String lname = l_name.getText().toString().trim();
        String eml = email.getText().toString().trim();
        String pass = password.getText().toString().trim();
        String tel = telephone.getText().toString().trim();

        if (fname.isEmpty()||lname.isEmpty()||eml.isEmpty()||pass.isEmpty()||tel.isEmpty()) {

            Toast.makeText(getActivity(), "Kindly fill all empty fields!", Toast.LENGTH_SHORT).show();

        } else {

            firebaseAuth.createUserWithEmailAndPassword(eml, pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()) {
                        Toast.makeText(getActivity(), "Registration successful. Karibu " + f_name.getText(), Toast.LENGTH_SHORT).show();
                        savetoDB();
                        firebaseUser = firebaseAuth.getCurrentUser();
                        updateUI(firebaseUser);

                        startActivity(new Intent(getActivity(), MainActivity.class));

                    } else {
                        Toast.makeText(getActivity(), "Registration process failed. Retry", Toast.LENGTH_SHORT).show();

                        updateUI(null);
                        f_name.setText("");
                        l_name.setText("");
                        email.setText("");
                        password.setText("");
                        telephone.setText("");

                    }
                }
            });

        }

    }

    private void savetoDB() {

        String fname = f_name.getText().toString().trim();
        String lname = l_name.getText().toString().trim();
        String eml = email.getText().toString().trim();
        String tel = telephone.getText().toString().trim();

        Clients rider_client = new Clients();
        rider_client.setC_fname(fname);
        rider_client.setC_lname(lname);
        rider_client.setC_email(eml);
        rider_client.setC_tel(tel);

        newClientRef.child("RIDER_CLIENT").setValue(rider_client, new DatabaseReference.CompletionListener() {
            @Override
            public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
                if (databaseError == null) {
                    Toast.makeText(getActivity(), "USER ADDED",Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getActivity(), "USER NOT ADDED!",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void updateUI(FirebaseUser firebaseUser) {
        if(firebaseUser != null){
            startActivity(new Intent(getActivity(), MainActivity.class));
        }
    }

}
