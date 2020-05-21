package com.example.masterdetailflowapp;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class DetailFragment extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String TAG = "MAIN_FRAGMENT";

    private String mDetailsString;
    private String name;

    private DetailFragmentListener mCallback;

    private TextView mEditText;
    private Button mBackButton;
    private Friends mFriends;

    public DetailFragment() {
        // Required empty public constructor
    }

    public static DetailFragment newInstance(){//String ds) {
        DetailFragment fragment = new DetailFragment();
//        Bundle args = new Bundle();
//        args.putString(ARG_PARAM1, ds);
//        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        if (getArguments() != null) {
//            mDetailsString = getArguments().getString(ARG_PARAM1);
//        }
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

        // configure display & animation
        //updateDisplay(mDetailsString);

        mBackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                name = mEditText.getText().toString();
//                if (getArguments() != null) {
//                    mDetailsString = getArguments().getString(ARG_PARAM1);
//                }
//                Log.i(TAG, "text user inputted: " +name);
//                Log.i(TAG, "textview location: " + mDetailsString);
                mCallback.onDetailFragmentAction(name);//mDetailsString, name);
            }
        });
    }

    /* ------------------------------------*/
    /*   custom helper method              */
    /*   this is called by MainActivity    */

//    public void updateDisplay(String s){
//        mEditText.setText( s );
//    }


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

    public interface DetailFragmentListener {
        void onDetailFragmentAction(String n);//String pos, String n);
    }
}
