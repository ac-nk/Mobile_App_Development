package com.example.masterdetailflowapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class MainActivity extends AppCompatActivity implements MasterFragment.MasterFragmentListener, DetailFragment.DetailFragmentListener {

    private Friends mFriends;
    private Gson mGson;

    private int mFragmentIndex;

    ViewPager2 mViewPager;
    MyViewPagerAdapter mMyViewPagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mFriends = new Friends();
        mGson = new GsonBuilder().create();

        mViewPager = findViewById(R.id.container);          // assign the instance of ViewPager2
        mMyViewPagerAdapter = new MyViewPagerAdapter(this);
        mViewPager.setAdapter(mMyViewPagerAdapter);         // bind the adapter to the viewpager2
        mViewPager.setUserInputEnabled(false);              // disable swiping

    }

    /* --------------------------------------------- */
    /*  This interface method is called when the     */
    /*  user taps on a view in the master fragment   */
    /* --------------------------------------------- */
    @Override
    public void onMasterFragmentData(int i) {
        // records which detail fragment was clicked
        mFragmentIndex = i;

        // change the position
        mViewPager.setCurrentItem(1, false);

    }


    /* --------------------------------------------- */
    /*  This interface method is called when the     */
    /*  user taps back in the detail fragment        */
    /* --------------------------------------------- */
    @Override
    public void onDetailFragmentAction(String n){
        // update master reference
        mFriends.setName(mFragmentIndex, n);
        // change name of TextView object
        mMyViewPagerAdapter.updateMasterFragment(mFriends);

        // change the position
        mViewPager.setCurrentItem(0, false);
    }

    /* --------------------------------------------- */
    /*  This class is responsible for loading        */
    /*  fragments into the ViewPager2                */
    /* --------------------------------------------- */
    private class MyViewPagerAdapter extends FragmentStateAdapter {

        private MasterFragment mMasterFragment = null;
        private DetailFragment mDetailsFragment = null;

        public MyViewPagerAdapter(MainActivity ma) {
            super(ma);
        }

        // update data and TextView
        public void updateMasterFragment(Friends fd) {
            mMasterFragment.updateData(fd);
            mMasterFragment.updateList();
        }

        @Override
        public Fragment createFragment(int position) {
            Fragment res = null;
            switch (position) {
                case 0:
                    mMasterFragment = MasterFragment.newInstance(mGson.toJson(mFriends));
                    res = mMasterFragment;
                    break;
                case 1:
                    mDetailsFragment = DetailFragment.newInstance();
                    res = mDetailsFragment;
                    break;
                default:
                    mMasterFragment = MasterFragment.newInstance(mGson.toJson(mFriends));
                    res = mMasterFragment;
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

