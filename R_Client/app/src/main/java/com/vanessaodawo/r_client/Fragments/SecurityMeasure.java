package com.vanessaodawo.r_client.Fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.vanessaodawo.r_client.R;


public class SecurityMeasure extends Fragment {

    ImageButton pin;
    TextView choice;
    boolean clicked = false;

    public SecurityMeasure() {
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
        View view = inflater.inflate(R.layout.fragment_security_measure, container, false);
        pin = view.findViewById(R.id.pin);
        choice = view.findViewById(R.id.clickChoice);

        pin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clicked = true;
                pinSet();
                Toast.makeText(getActivity(), "Enables a user to set a pin that will be prompted before any payment is made", Toast.LENGTH_SHORT).show();
            }
        });

        return view;
    }

    private void pinSet() {
        if (clicked) {
            choice.setText("on");
        } else {
            choice.setText("off");
        }
    }

}
