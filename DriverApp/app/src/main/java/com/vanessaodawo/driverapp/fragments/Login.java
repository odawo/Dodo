package com.vanessaodawo.driverapp.fragments;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.vanessaodawo.driverapp.HomePage;
import com.vanessaodawo.driverapp.MainActivity;
import com.vanessaodawo.driverapp.POJO.Driver;
import com.vanessaodawo.driverapp.R;

public class Login extends Fragment {

    Button loginBtn;
    ImageButton back;
    TextView forgotPass;
    EditText email;
    EditText password;

    String TAG;

    FirebaseAuth firebaseAuth;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference usersdb;
    FirebaseUser firebaseUser;

//    private OnFragmentInteractionListener mListener;

    public Login() {
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
        View view = inflater.inflate(R.layout.fragment_login, container, false);

        loginBtn = view.findViewById(R.id.loginBtn);
        forgotPass = view.findViewById(R.id.forgotPass);
        back = view.findViewById(R.id.backIB);
        email = view.findViewById(R.id.logemail);
        password = view.findViewById(R.id.logpassword);

        firebaseAuth = FirebaseAuth.getInstance();

        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginUser();
            }
        });

        forgotPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                forgotPassMod();
            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), MainActivity.class));
            }
        });

        return view;
    }

    private void loginUser() {

        loginBtn.setVisibility(View.INVISIBLE);

        if (email == null || password == null ) {
            Toast.makeText(getActivity(), "Kindly fill all empty fields", Toast.LENGTH_SHORT).show();
        } else {
            firebaseAuth.signInWithEmailAndPassword(email.getText().toString(), password.getText().toString()).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                @Override
                public void onSuccess(AuthResult authResult) {
                    FirebaseDatabase.getInstance().getReference("DRIVER").child(firebaseAuth.getCurrentUser().getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            Driver firebaseUser = dataSnapshot.getValue(Driver.class);
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });
                    firebaseUser = firebaseAuth.getCurrentUser();
                    updateUI(firebaseUser);
                    startActivity(new Intent(getActivity(), HomePage.class));
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(getActivity(), "ERROR : " + e.getMessage(), Toast.LENGTH_SHORT).show();
                    loginBtn.setVisibility(View.VISIBLE);
                    updateUI(firebaseUser);
                }
            });
        }

    }


    @Override
    public void onStart() {
        super.onStart();
        FirebaseUser currentUser = firebaseAuth.getCurrentUser();
        updateUI(currentUser);
    }

    private void updateUI(FirebaseUser currentUser) {
        if (currentUser != null) {
            startActivity(new Intent(getActivity(), HomePage.class));
        }
    }

    private void forgotPassMod() {
        displayPopup();
    }

    private void displayPopup() {

        Dialog dialog = new Dialog(getActivity());
        dialog.setContentView(R.layout.password_popup);
        EditText emailreset = dialog.findViewById(R.id.resetpassword);
        final String em = emailreset.getText().toString().trim();
        Button send = dialog.findViewById(R.id.sendPasswordBtn);
        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (em.isEmpty()) {
                    Toast.makeText(getActivity(), "Email field is empty.", Toast.LENGTH_SHORT).show();
                } else {
                    FirebaseAuth.getInstance().sendPasswordResetEmail(em).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            Log.d(TAG, "onSuccess: sent");
                            confirmMail();
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(getActivity(), "ERROR : " + e, Toast.LENGTH_SHORT).show();
                            forgotPass.setText("");
                        }
                    });
                }

            }
        });
        dialog.show();
    }

    private void confirmMail() {
        final Dialog dialog = new Dialog(getActivity());
        dialog.setContentView(R.layout.pass_confirmation);
        Button confirm = dialog.findViewById(R.id.ok);

        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(), "redirects to user email or set option", Toast.LENGTH_SHORT);
                dialog.dismiss();
            }
        });
    }

}
