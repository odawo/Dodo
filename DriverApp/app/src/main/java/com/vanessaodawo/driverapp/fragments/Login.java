package com.vanessaodawo.driverapp.fragments;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.vanessaodawo.driverapp.HomePage;
import com.vanessaodawo.driverapp.MainActivity;
import com.vanessaodawo.driverapp.R;

public class Login extends Fragment {

    Button loginBtn;
    ImageButton back;
    TextView forgotPass;

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
        Toast.makeText(getContext(), "forgot password button clicked. ", Toast.LENGTH_SHORT).show();
    }

}
