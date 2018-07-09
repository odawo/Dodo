package com.vanessaodawo.driverapp.fragments;

import android.app.AlertDialog;
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
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.vanessaodawo.driverapp.HomePage;
import com.vanessaodawo.driverapp.MainActivity;
import com.vanessaodawo.driverapp.R;

public class Login extends Fragment {

    Button loginBtn;
    ImageButton back;
    TextView forgotPass;

    String TAG;

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
        Toast.makeText(getContext(), "login button clicked. ", Toast.LENGTH_SHORT).show();
        startActivity(new Intent(getActivity(), HomePage.class));
    }

    private void forgotPassMod() {
        displayPopup();
    }

    private void displayPopup() {

        final String resetPass = forgotPass.getText().toString().trim();

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Forgot Password.");
        builder.setPositiveButton("send", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                FirebaseAuth.getInstance().sendPasswordResetEmail(resetPass).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d(TAG, "onSuccess: sent");
                        Toast.makeText(getActivity(), "A password reset link has been sent to your email." , Toast.LENGTH_SHORT).show();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(getActivity(), "ERROR : " + e , Toast.LENGTH_SHORT).show();
                        forgotPass.setText("");
                    }
                });
            }

        }).setNegativeButton("exit", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                startActivity(new Intent(getActivity(), Login.class));
            }
        });

    }

}
