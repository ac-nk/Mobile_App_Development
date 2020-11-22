package com.example.coreproficiencies1;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class MainFragment extends Fragment implements View.OnClickListener {

    Button mButtonMint;
    Button mButtonCoffee;
    Button mButtonVanilla;
    TextView mTextMint;
    TextView mTextVanilla;
    TextView mTextCoffee;
    private RequestQueue mRequestQueue;

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
        mTextMint = view.findViewById(R.id.mint_result);
        mTextVanilla = view.findViewById(R.id.vanilla_result);
        mTextCoffee = view.findViewById(R.id.coffee_result);
        mRequestQueue = Volley.newRequestQueue(getActivity());

        mButtonMint.setOnClickListener(this);
        mButtonCoffee.setOnClickListener(this);
        mButtonVanilla.setOnClickListener(this);


    }
    @Override
    public void onClick(View v) {
        int id = v.getId();

        switch(id) {
            case R.id.button_mint:
                vote = "0";
                break;
            case R.id.button_coffee:
                vote = "1";
                break;
            case R.id.button_vanilla:
                vote = "2";
                break;
            default:
                break;
        }
//        Toast.makeText(getContext(),vote, Toast.LENGTH_SHORT).show();
        fetchResponse();
    }

    private void fetchResponse() {
        String url = "https://achue-mobileweb.sites.tjhsst.edu/voting";
        url+= "?vote=" + vote;
        JsonObjectRequest req = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            String mint = "Mint votes: " + response.getInt("mint");
                            String vanilla = "Vanilla votes: " + response.getInt("vanilla");
                            String coffee = "Coffee votes: " + response.getInt("coffee");

                            mTextMint.setText(mint);
                            mTextVanilla.setText(vanilla);
                            mTextCoffee.setText(coffee);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getContext(),
                        error.toString(),
                        Toast.LENGTH_LONG).show();
            }
        });
        /* Add your Requests to the RequestQueue to execute */
        mRequestQueue.add(req);
    }
}
