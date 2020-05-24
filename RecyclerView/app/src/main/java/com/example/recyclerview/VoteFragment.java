package com.example.recyclerview;

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

public class VoteFragment extends Fragment {

    private TextView mTextViewVoteNum;
    private Button mVoteButton;
    private Button mBackButton;

    private static final String ARG_PARAM1 = "VOTE_NUM";
    private static final String TAG = "MAIN_FRAGMENT_VOTE";
    private int mVoteCount;

    private VoteFragmentListener mCallback;

    public VoteFragment() {
        // Required empty public constructor
    }

    public static VoteFragment newInstance(int num) {

        // contains vote count when fragment created
        VoteFragment fragment = new VoteFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_PARAM1, num);
        fragment.setArguments(args);

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            mVoteCount = getArguments().getInt(ARG_PARAM1);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_vote, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // get the views
        mTextViewVoteNum = view.findViewById(R.id.tv_voteNum);
        mBackButton = view.findViewById(R.id.btn_back);
        mVoteButton = view.findViewById(R.id.btn_vote);

        // display vote count
        updateDisplay(mVoteCount);
        // update master reference with vote number in TextView
        String text = mTextViewVoteNum.getText().toString();
        mVoteCount = Integer.parseInt(text.substring(17));

        mVoteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // update master reference with vote number in TextView
                String text = mTextViewVoteNum.getText().toString();
                mVoteCount = Integer.parseInt(text.substring(17));

                mVoteCount++;
                // display vote count
                updateDisplay(mVoteCount);
            }
        });

        mBackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // update master reference with vote number in TextView
                String text = mTextViewVoteNum.getText().toString();
                mVoteCount = Integer.parseInt(text.substring(17));

                // makes sure to pass data to MainActivity with vote count of THIS fragment
                mCallback.onVoteFragmentAction(mVoteCount);
            }
        });
    }

    /* ------------------------------------------*/
    /*    HELPER FUNCTIONS                       */

    public void updateDisplay(int num){
        mTextViewVoteNum.setText( "number of votes: " + Integer.toString(num));
    }
    /* ------------------------------------------*/

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof VoteFragmentListener) {
            mCallback = (VoteFragmentListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " (Main Activity) must implement VoteFragmentListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mCallback = null;
    }

    // fragment interface for communication, vote count is passed as parameter
    public interface VoteFragmentListener {
        void onVoteFragmentAction(int num);
    }
}
