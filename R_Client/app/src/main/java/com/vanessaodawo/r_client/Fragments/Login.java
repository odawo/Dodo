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
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.vanessaodawo.r_client.MainActivity;
import com.vanessaodawo.r_client.R;

public class Login extends Fragment {

    EditText email, password;
    Button loginBtn;
    ImageButton back;
    TextView forgotPass;

    FirebaseAuth firebaseAuth;
    FirebaseUser firebaseUser;

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

        email = view.findViewById(R.id.logemail);
        password = view.findViewById(R.id.logpassword);
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

        firebaseAuth = FirebaseAuth.getInstance();

        return view;
    }

    private void loginUser() {
        String log_email = email.getText().toString().trim();
        String log_pass = password.getText().toString().trim();

        if (log_email.isEmpty()||log_pass.isEmpty()) {

            Toast.makeText(getActivity(), "Kindly fill all empty spaces", Toast.LENGTH_SHORT).show();

        } else {

            firebaseAuth.signInWithEmailAndPassword(log_email, log_pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()) {
                        Toast.makeText(getActivity(), "ACCESS GRANTED", Toast.LENGTH_SHORT).show();
                        FirebaseUser user = firebaseAuth.getCurrentUser();
                        updateUI(firebaseUser);
                        startActivity(new Intent(getActivity(), MainActivity.class));
                    } else {
                        Toast.makeText(getActivity(), "AUTHENTICATION ERROR. TRY AGAIN", Toast.LENGTH_SHORT).show();
                        updateUI(null);
                    }
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
            startActivity(new Intent(getActivity(), Register.class));
        }
    }

    private void forgotPassMod() {
        Toast.makeText(getContext(), "forgot password button clicked. ", Toast.LENGTH_SHORT).show();
    }

}
