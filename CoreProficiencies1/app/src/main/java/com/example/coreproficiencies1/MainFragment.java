package com.example.coreproficiencies1;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class MainFragment extends Fragment implements View.OnClickListener {

    Button mButtonMint;
    Button mButtonCoffee;
    Button mButtonVanilla;

    String vote;

    public MainFragment() {
        // Required empty public constructor
    }

    public static MainFragment newInstance() {
        MainFragment fragment = new MainFragment();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_main, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        // assign callbacks, etc
        super.onViewCreated(view, savedInstanceState);

        mButtonMint = view.findViewById(R.id.button_mint);
        mButtonCoffee = view.findViewById(R.id.button_coffee);
        mButtonVanilla = view.findViewById(R.id.button_vanilla);

        mButtonMint.setOnClickListener(this);
        mButtonCoffee.setOnClickListener(this);
        mButtonVanilla.setOnClickListener(this);
    }
    @Override
    public void onClick(View v) {
        int id = v.getId();

        switch(id) {
            case R.id.button_mint:
                vote = "mint";
                break;
            case R.id.button_coffee:
                vote = "coffee";
                break;
            case R.id.button_vanilla:
                vote = "vanilla";
                break;
            default:
                break;
        }

        Toast.makeText(getContext(),vote, Toast.LENGTH_SHORT).show();
    }
}
