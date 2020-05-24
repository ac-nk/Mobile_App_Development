package com.example.recyclerview;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity implements SongsAdapter.AdapterCallback, RecycleFragment.RecycleFragmentListener, VoteFragment.VoteFragmentListener {

//    private List<Song> mSongsList;
//    private RecyclerView mRecyclerView;
//    private SongsAdapter mSongsAdapter;
    private Gson mGson;
    private SongVotes mSongVotes;

    ViewPager2 mViewPager;
    MyViewPagerAdapter mMyViewPagerAdapter;

    private int mVoteNum;
    private int mRecyclePosition;
    private static final String KEY = "SONG_VOTE";
    private static final String TAG = "MAIN_ACTIVITY";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // pull data from shared preferences
        SharedPreferences mSettings = this.getSharedPreferences("Settings", Context.MODE_PRIVATE);
        String storedDataString = mSettings.getString(KEY, "");
        Log.i(TAG, "dataString: "+storedDataString );

        mGson = new GsonBuilder().create();

        if ( storedDataString.equals("") ) {
            // IF : there is no data in shared prefs, make dummy data
            mSongVotes = new SongVotes(12);
        }
        else {
            // OTHERWISE : read string and convert it to a class object
            mSongVotes = mGson.fromJson(storedDataString, SongVotes.class);
        }

        Log.i(TAG, mGson.toJson(mSongVotes));

        mViewPager = findViewById(R.id.container);          // assign the instance of ViewPager2
        mMyViewPagerAdapter = new MyViewPagerAdapter(this);
        mViewPager.setAdapter(mMyViewPagerAdapter);         // bind the adapter to the viewpager2
        mViewPager.setUserInputEnabled(false);              // disable swiping

//        Gson gson = new GsonBuilder().create();
//        Song[] mySongs = gson.fromJson( getString(R.string.my_songs), Song[].class );
//        mSongsList = new ArrayList<>(Arrays.asList(mySongs));
//
//        mRecyclerView = findViewById(R.id.recycler_view);
//        mSongsAdapter = new SongsAdapter(mSongsList, this);
//        mSongsAdapter.notifyDataSetChanged();
//
//        RecyclerView.LayoutManager rvlManager = new LinearLayoutManager(this);
//        mRecyclerView.setLayoutManager(rvlManager);
//        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
//
//        mRecyclerView.setAdapter(mSongsAdapter);
    }
    @Override
    public void onAdapterCallback(int pos){
        mRecyclePosition = pos;
        Log.i(TAG, "" +pos);
        Log.i(TAG, "" +mSongVotes.getSong(mRecyclePosition).getPosition());

        mMyViewPagerAdapter.updateVoteFragment(mSongVotes.getSong(mRecyclePosition));
        // switch fragments
        mViewPager.setCurrentItem(1, false);
    }
    @Override
    public void onVoteFragmentAction(int num) {
        //mVoteNum = num;

        // update the master reference
        mSongVotes.setSongVotes(num, mRecyclePosition);

        // update shared preferences
        saveToSharedPreferences();
        Log.i(TAG, mGson.toJson(mSongVotes));
       // mMyViewPagerAdapter.updateSongAdapter(mSongVotes);

        // update master fragment
        mMyViewPagerAdapter.updateRecycleFragment(mSongVotes);

        // switch pages
        mViewPager.setCurrentItem(0, false);
    }
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
        private SongsAdapter mSongsAdapter;

        private int currentVote;

        public MyViewPagerAdapter(MainActivity ma) {
            super(ma);
        }

//        public void updateSongAdapter( SongVotes svs) {
//           // mSongsAdapter.updateSongVotes(svs);
//            mRecycleFragment.updateDisplay();
//        }
        public void updateVoteFragment(SongVote sv) {
            if(mVoteFragment != null) {
                mVoteFragment.updateDisplay(sv.getVoteNum());
            }
        }
        public void updateRecycleFragment(SongVotes svs) {
            if(mRecycleFragment != null) {
                mRecycleFragment.updateDataSet(svs);
                mRecycleFragment.updateDisplay();
            }
            //mRecyclerView.updateDataSet(svs);
            //mRecyclerView.updateDisplay();
        }
//        public void setDetailsString(int n) {
//            mVoteNum = n;
//                mVoteFragment.updateDisplay(mVoteNum);
//        }

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
    }
}
