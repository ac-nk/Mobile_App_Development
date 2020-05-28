package com.example.masterdetailflowapp;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class MasterFragment extends Fragment implements View.OnClickListener {

    private static final String ARG_PARAM1 = "SERIALIZED_FRIENDS";

    private TextView mTextViewFirst;
    private TextView mTextViewSecond;
    private TextView mTextViewThird;

    private Gson mGson;
    private Friends mFriends;

    private MasterFragmentListener mCallback;

    public MasterFragment() {
        // Required empty public constructor
    }

    public static MasterFragment newInstance(String serializedFriends) {
        MasterFragment fragment = new MasterFragment();

        // inputs Friends info
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, serializedFriends);
        fragment.setArguments(args);

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
        return inflater.inflate(R.layout.fragment_master, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mTextViewFirst = view.findViewById(R.id.v_first);
        mTextViewSecond = view.findViewById(R.id.v_second);
        mTextViewThird = view.findViewById(R.id.v_third);

        mTextViewFirst.setOnClickListener(this);
        mTextViewSecond.setOnClickListener(this);
        mTextViewThird.setOnClickListener(this);

        String serializedFriends = getArguments().getString(ARG_PARAM1);

        mGson = new GsonBuilder().create();
        Friends fd = mGson.fromJson(serializedFriends, Friends.class);

        // update master fragment
        updateData(fd);
        updateList();

    }

    // update master reference
    public void updateData(Friends fd){
        mFriends = fd;
    }

    // update TextView
    public void updateList(){
        mTextViewFirst.setText("1) " + mFriends.getName(0));
        mTextViewSecond.setText("2) " + mFriends.getName(1));
        mTextViewThird.setText("3) " + mFriends.getName(2));
    }

    @Override
    public void onClick(View v) {
        int itemNo = -1;

        switch (v.getId()) {
            case R.id.v_first:
                itemNo = 0;
                break;
            case R.id.v_second:
                itemNo = 1;
                break;
            case R.id.v_third:
                itemNo = 2;
                break;
            default:
                break;
        }

        // passes back location of click
        mCallback.onMasterFragmentData(itemNo);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof MasterFragmentListener) {
            mCallback = (MasterFragmentListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " (MainActivity) must implement MasterFragmentListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mCallback = null;
    }

    // fragment interface for communication, passes back location of the click
    public interface MasterFragmentListener {
        void onMasterFragmentData(int i);
    }
}
