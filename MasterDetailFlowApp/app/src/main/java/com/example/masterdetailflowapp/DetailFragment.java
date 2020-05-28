package com.example.masterdetailflowapp;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class DetailFragment extends Fragment {

    private String name;

    private DetailFragmentListener mCallback;

    private TextView mEditText;
    private Button mBackButton;

    public DetailFragment() {
        // Required empty public constructor
    }

    public static DetailFragment newInstance(){
        DetailFragment fragment = new DetailFragment();

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_detail, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // get the views
        mEditText = view.findViewById(R.id.text_edit);
        mBackButton = view.findViewById(R.id.back_button);

        mBackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // pass name back to MainActivity
                name = mEditText.getText().toString();
                mCallback.onDetailFragmentAction(name);
            }
        });
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof DetailFragmentListener) {
            mCallback = (DetailFragmentListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " (Main Activity) must implement DetailFragmentListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mCallback = null;
    }

    // fragment interface for communication, passes back user's input (name)
    public interface DetailFragmentListener {
        void onDetailFragmentAction(String n);
    }
}
