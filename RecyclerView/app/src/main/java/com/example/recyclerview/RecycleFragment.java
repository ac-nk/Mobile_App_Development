package com.example.recyclerview;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class RecycleFragment extends Fragment {

    private List<Song> mSongsList;
    private RecyclerView mRecyclerView;
    private SongsAdapter mSongsAdapter;
    private SongVotes mSongVotes;

    private static final String ARG_PARAM1 = "param1";

    private RecycleFragmentListener mCallback;

    public RecycleFragment() {
        // Required empty public constructor
    }

    public static RecycleFragment newInstance(String serializedObject) {
        RecycleFragment fragment = new RecycleFragment();

        // contains serialized string of song votes
        Bundle args = new Bundle();
        args.putString("SERIALIZED_OBJECT", serializedObject);
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
        return inflater.inflate(R.layout.fragment_recycle, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // changes serialized string back to SongVotes object
        Gson gson = new GsonBuilder().create();
        String serializedObject = getArguments().getString("SERIALIZED_OBJECT");
        mSongVotes = gson.fromJson(serializedObject, SongVotes.class);

        // update data set
        updateDataSet(mSongVotes);

        // sets up RecyclerView
        Song[] mySongs = gson.fromJson( getString(R.string.my_songs), Song[].class );
        mSongsList = new ArrayList<>(Arrays.asList(mySongs));

        mRecyclerView = view.findViewById(R.id.recycler_view);
        mSongsAdapter = new SongsAdapter(mSongsList, mSongVotes, getActivity());
        mSongsAdapter.notifyDataSetChanged();

        RecyclerView.LayoutManager rvlManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(rvlManager);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());

        mRecyclerView.setAdapter(mSongsAdapter);

        mCallback.onRecycleFragmentData(0);

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof RecycleFragmentListener) {
            mCallback = (RecycleFragmentListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " (MainActivity) must implement RecycleFragmentListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mCallback = null;
    }

    /* ------------------------*/
    /*   helper methods        */

    public void updateDataSet(SongVotes r) {
        mSongVotes = r;

    }

    // updates RecyclerView with new vote counts after user votes in vote fragment
    public void updateDisplay(){
        mSongsAdapter.updateSongVotes(mSongVotes);
        mSongsAdapter.notifyDataSetChanged();
    }
    /* ------------------------*/

    // fragment interface for communication, not used
    public interface RecycleFragmentListener {
        void onRecycleFragmentData(int i);
    }
}
