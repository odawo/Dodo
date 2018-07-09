package com.vanessaodawo.r_client.Fragments;

import android.content.Intent;
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
    DatabaseReference clientDb;

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
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        clientDb = firebaseDatabase.getReference("ClientInformation");

        return view;
    }

    private void registerUser() {

        if (f_name.getText() == null||l_name.getText() == null||email.getText() == null||password.getText() == null||telephone.getText() == null) {

            Toast.makeText(getActivity(), "Kindly fill all empty fields!", Toast.LENGTH_SHORT).show();

        } else {

            firebaseAuth.createUserWithEmailAndPassword(email.getText().toString().trim(), password.getText().toString().trim()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {

                    final Clients clients = new Clients();
                    clients.setC_fname(f_name.getText().toString().trim());
                    clients.setC_lname(l_name.getText().toString().trim());
                    clients.setC_email(email.getText().toString().trim());
//                       clients.setC_fname(password.getText().toString().trim());
                    clients.setC_tel(telephone.getText().toString().trim());

                    if (task.isSuccessful()) {
//                        registers new users to firebaseuser & db
                        clientDb.child(FirebaseAuth.getInstance().getCurrentUser().getUid()).setValue(clients).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                Toast.makeText(getActivity(), "Registered " + f_name.getText(), Toast.LENGTH_SHORT).show();
//                                savetoDB();
                                firebaseUser = firebaseAuth.getCurrentUser();
                                updateUI(firebaseUser);

                                startActivity(new Intent(getActivity(), MainActivity.class));
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(getActivity(), "Registration process failed" + e.getMessage() + "," + e.getCause(), Toast.LENGTH_SHORT).show();

                                updateUI(null);
                                email.setText("");
                                password.setText("");
//                                telephone.setText("");
                            }
                        });

                    } else {
                        Toast.makeText(getActivity(), "Registration process failed. Retry", Toast.LENGTH_SHORT).show();

                        updateUI(null);
                        email.setText("");
                        password.setText("");
//                        telephone.setText("");

                    }
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText( getActivity(), "Failed"+e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });

        }

    }

//    private void savetoDB() {
//
//        String fname = f_name.getText().toString().trim();
//        String lname = l_name.getText().toString().trim();
//        String eml = email.getText().toString().trim();
//        String tel = telephone.getText().toString().trim();
//
//        Clients rider_client = new Clients();
//        rider_client.setC_fname(fname);
//        rider_client.setC_lname(lname);
//        rider_client.setC_email(eml);
//        rider_client.setC_tel(tel);
//
//        clientDb.child("RIDER_CLIENT").setValue(rider_client, new DatabaseReference.CompletionListener() {
//            @Override
//            public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
//                if (databaseError == null) {
////                    Toast.makeText(getActivity(), "USER ADDED",Toast.LENGTH_SHORT).show();
//                } else {
//                    Toast.makeText(getActivity(), "ERROR!",Toast.LENGTH_SHORT).show();
//                }
//            }
//        });
//    }

    private void updateUI(FirebaseUser firebaseUser) {
        if(firebaseUser != null){
            startActivity(new Intent(getActivity(), MainActivity.class));
        }
    }

}
