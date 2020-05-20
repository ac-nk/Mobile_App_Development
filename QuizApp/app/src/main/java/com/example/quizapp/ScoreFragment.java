package com.example.quizapp;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class ScoreFragment extends Fragment {

    private static final String TAG = "MAIN_FRAGMENT";
    TextView mTextViewScore;
    Integer dataScore;
    MyFragmentInterfaceScore mCallback;

    public ScoreFragment() {
        //constructor
    }

    public static ScoreFragment newInstance(int pos, int sc) {
        ScoreFragment fragment = new ScoreFragment();

        Bundle args = new Bundle();
        args.putInt("key_pos", pos);
        args.putInt("key_score", sc);
        fragment.setArguments(args);
        Log.i(TAG, "" + args.getInt("key_score"));

        return fragment;
    }
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mCallback = (MyFragmentInterfaceScore) context;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mCallback = null;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_score, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mTextViewScore       = view.findViewById(R.id.tv_score);


        if(getArguments() != null){
            dataScore = getArguments().getInt("key_score");
        }
        mTextViewScore.setText("your score: " + dataScore + "!");

    }

    public interface MyFragmentInterfaceScore {
        void onFragmentInteractionScore(boolean b);
    }

}
