package com.vanessaodawo.r_client.Fragments;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.vanessaodawo.r_client.Payment;
import com.vanessaodawo.r_client.R;

public class AddCard extends Fragment {

    EditText cardNo, monthYr, code, postCode;
    ImageButton backBtn, nextButton;
    TextView terms;

    public AddCard() {
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
        View view = inflater.inflate(R.layout.fragment_add_card, container, false);

        cardNo = view.findViewById(R.id.credCard);
        monthYr = view.findViewById(R.id.monthYr);
        code = view.findViewById(R.id.code);
        postCode = view.findViewById(R.id.postCode);
        backBtn = view.findViewById(R.id.backBt);
        nextButton = view.findViewById(R.id.nextPayment);
        terms = view.findViewById(R.id.terms);

        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), Payment.class));
            }
        });

        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(), "will continue with trip payment", Toast.LENGTH_SHORT).show();
            }
        });

        terms.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(), "will continue to terms of payment link", Toast.LENGTH_SHORT).show();
            }
        });

        return view;
    }

}
