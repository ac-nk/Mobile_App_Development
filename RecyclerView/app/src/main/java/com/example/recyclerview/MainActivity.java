package com.example.recyclerview;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class MainActivity extends AppCompatActivity implements SongsAdapter.AdapterCallback, RecycleFragment.RecycleFragmentListener, VoteFragment.VoteFragmentListener {

    private static final String KEY = "SONG_VOTE";
    private static final String TAG = "MAIN_ACTIVITY";

    ViewPager2 mViewPager;
    MyViewPagerAdapter mMyViewPagerAdapter;

    private Gson mGson;
    private SongVotes mSongVotes;
    private int mRecyclePosition;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // pull data from shared preferences
        SharedPreferences mSettings = this.getSharedPreferences("Settings", Context.MODE_PRIVATE);
        String storedDataString = mSettings.getString(KEY, "");

        mGson = new GsonBuilder().create();

        if ( storedDataString.equals("") ) {
            // IF : there is no data in shared prefs, make dummy data
            mSongVotes = new SongVotes(12);
        }
        else {
            // OTHERWISE : read string and convert it to a class object
            mSongVotes = mGson.fromJson(storedDataString, SongVotes.class);
        }

        mViewPager = findViewById(R.id.container);          // assign the instance of ViewPager2
        mMyViewPagerAdapter = new MyViewPagerAdapter(this);
        mViewPager.setAdapter(mMyViewPagerAdapter);         // bind the adapter to the viewpager2
        mViewPager.setUserInputEnabled(false);              // disable swiping

    }
    @Override
    public void onAdapterCallback(int pos){

        // gets location of song that was clicked
        mRecyclePosition = pos;

        // display vote count in vote fragment
        mMyViewPagerAdapter.updateVoteFragment(mSongVotes.getSong(mRecyclePosition));

        // switch fragments
        mViewPager.setCurrentItem(1, false);
    }

    // called by vote fragment
    @Override
    public void onVoteFragmentAction(int num) {

        // update the master reference with vote count and position/song
        mSongVotes.setSongVotes(num, mRecyclePosition);

        // update shared preferences
        saveToSharedPreferences();

        // update recycle fragment
        mMyViewPagerAdapter.updateRecycleFragment(mSongVotes);

        // switch pages
        mViewPager.setCurrentItem(0, false);
    }

    // called by recycle fragment
    @Override
    public void onRecycleFragmentData(int i) {
    }

    /* ------------------------------------------*/
    /*    HELPER FUNCTIONS                       */

    public void saveToSharedPreferences() {
        SharedPreferences mSettings = this.getSharedPreferences("Settings", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = mSettings.edit();
        editor.putString(KEY, mGson.toJson(mSongVotes));
        editor.apply();
    }

    private class MyViewPagerAdapter extends FragmentStateAdapter {

        // retain references to the fragments in the adapter
        private RecycleFragment mRecycleFragment = null;
        private VoteFragment mVoteFragment = null;

        public MyViewPagerAdapter(MainActivity ma) {
            super(ma);
        }

        @Override
        public Fragment createFragment(int position) {
            Fragment res = null;
            switch (position) {
                case 0:
                    mRecycleFragment = RecycleFragment.newInstance(mGson.toJson(mSongVotes));
                    res = mRecycleFragment;
                    break;
                case 1:
                    mVoteFragment = VoteFragment.newInstance(mSongVotes.getSong(mRecyclePosition).getVoteNum());
                    res = mVoteFragment;
                    break;
                default:
                    mRecycleFragment = RecycleFragment.newInstance(mGson.toJson(mSongVotes));
                    res = mRecycleFragment;
                    break;
            }
            return res;
        }

        @Override
        public int getItemCount() {
            return 2;       // there are only two fragments, the master and the detail
        }

        /* ------------------------------------------*/
        /*    HELPER FUNCTIONS                       */

        // display vote count on vote fragment
        public void updateVoteFragment(SongVote sv) {
            if(mVoteFragment != null) {
                mVoteFragment.updateDisplay(sv.getVoteNum());
            }
        }

        // update vote count on recycle fragment
        public void updateRecycleFragment(SongVotes svs) {
            if(mRecycleFragment != null) {
                mRecycleFragment.updateDataSet(svs);
                mRecycleFragment.updateDisplay();
            }
        }
        /* ------------------------------------------*/
    }
}
