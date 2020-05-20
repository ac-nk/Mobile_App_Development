package com.example.quizapp;

import android.content.Context;
import android.content.SharedPreferences;
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

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class MainFragment extends Fragment implements View.OnClickListener {

    private static final String TAG = "MAIN_FRAGMENT";
    private SharedPreferences.Editor mEditor;
    TextView mTextViewName;
    TextView mTextViewQuestion;
    TextView mTextViewScore;
    TextView mTextViewAnswer;
    Button mButtonFalse;
    Button mButtonTrue;
    Question mQuestion;
    int countCorrect = 0;
    String dataName = "null name";
    MyFragmentInterface mCallback;

    public MainFragment() {
        //constructor
    }

    public static MainFragment newInstance(int pos, String name) {
        MainFragment fragment = new MainFragment();

        Bundle args = new Bundle();
        args.putInt("key_pos", pos);
        args.putString("key_name", name);
        fragment.setArguments(args);
        Log.i(TAG, args.getString("key_name"));

        return fragment;
    }
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mCallback = (MyFragmentInterface) context;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mCallback = null;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_main, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mButtonFalse    = view.findViewById(R.id.btn_false);
        mButtonTrue     = view.findViewById(R.id.btn_true);
        mTextViewName       = view.findViewById(R.id.tv_name);
        mTextViewQuestion       = view.findViewById(R.id.tv_question);
        mTextViewScore       = view.findViewById(R.id.tv_score);
        mTextViewAnswer     = view.findViewById(R.id.tv_answer);

        if(getArguments() != null){
            dataName = getArguments().getString("key_name");
        }
        mTextViewName.setText("Hello " + dataName + "!");
        SharedPreferences mySharedPrefs = getActivity().getSharedPreferences("Storage", Context.MODE_PRIVATE);
        mEditor = mySharedPrefs.edit();
        countCorrect = mySharedPrefs.getInt(dataName, 0);

        mButtonFalse.setOnClickListener(this);
        mButtonTrue.setOnClickListener(this);

        Gson gson = new GsonBuilder().create();
        mQuestion = gson.fromJson( getString(R.string.question_bank), Question.class );

    }
    @Override
    public void onClick(View v) {
        String question = mTextViewQuestion.getText().toString();
        int quest_num = Integer.parseInt(question.substring(0, 2));
        String ans = mQuestion.getAnswer(quest_num);
        Log.i(TAG, ans);

        int id = v.getId();
        switch(id) {
            case R.id.btn_false:
                if (ans.equals("false")) {
                    Log.i(TAG, "in false case");
                    countCorrect++;
                    mTextViewAnswer.setText("correct!");
                    mEditor.putInt(dataName, countCorrect);
                    mEditor.apply();
                }
                else {
                    mTextViewAnswer.setText("incorrect :(");
                }
                break;
            case R.id.btn_true:
                if (ans.equals("true")) {
                    Log.i(TAG, "in true case");
                    countCorrect++;
                    mTextViewAnswer.setText("correct!");
                    mEditor.putInt(dataName, countCorrect);
                    mEditor.apply();
                }
                else {
                    mTextViewAnswer.setText("incorrect :(");
                }
                break;
            default:
                break;
        }
        String nextq = mQuestion.getNextQuestion();
        Log.i(TAG, nextq);
        Log.i(TAG, "correct num "+countCorrect);
        if(nextq.equals("end")) {
            mCallback.onFragmentInteraction(1, countCorrect);
            Log.i(TAG, "end of stack");
        }
        mTextViewQuestion.setText(nextq);
        mTextViewScore.setText("score: " + countCorrect);

    }
    public interface MyFragmentInterface {
        void onFragmentInteraction(int i, int sc);
    }

}