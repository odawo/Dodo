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
import android.widget.Toast;

import com.vanessaodawo.driverapp.MainActivity;
import com.vanessaodawo.driverapp.Profile;
import com.vanessaodawo.driverapp.R;

public class Register extends Fragment {

    Button registerBtn;
    ImageButton backBtn;

//    private OnFragmentInteractionListener mListener;

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
        Toast.makeText(getActivity(), "User Registration Button clicked", Toast.LENGTH_SHORT).show();
        startActivity(new Intent(getActivity(), Profile.class));
    }
}
