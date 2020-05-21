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

    private static final String TAG = "LOGTAG";

    private String name;
    private Friends mFriends;
    private int mFragmentIndex;

    ViewPager2 mViewPager;
    MyViewPagerAdapter mMyViewPagerAdapter;
    private MasterFragment mMasterFragment = null;
    private DetailFragment mDetailsFragment = null;
    private Gson mGson;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //SharedPreferences mSettings = this.getSharedPreferences("Settings", Context.MODE_PRIVATE);
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

//        String res = null;
//        switch (i) {
//            case 0:
//                    res = "top";
//                break;
//            case 1:
//                    res = "middle";
//                break;
//            case 2:
//                    res = "bottom";
//                break;
//            default:
//                break;
//        }

        // tell my adapter what the string should be
        //mMyViewPagerAdapter.setDetailsString(res);
        mFragmentIndex = i;
        // change the position
        mViewPager.setCurrentItem(1, false);

    }


    /* --------------------------------------------- */
    /*  This interface method is called when the     */
    /*  user taps back in the detail fragment        */
    /* --------------------------------------------- */
    @Override
    public void onDetailFragmentAction(String n){//String pos, String n) {
        //change name of TextView object
        mFriends.setName(mFragmentIndex, n);
        mMyViewPagerAdapter.updateMasterFragment(mFriends);
//        mMasterFragment.updateData(mFriends);
//        mMasterFragment.updateList();
       // mMasterFragment.updateName(pos, n);
//        name = n;
//        switch(pos){
//            case "top":
//                mMasterFragment.updateName("top", name);
//                break;
//            case "middle":
//                mMasterFragment.updateName("middle", name);
//                break;
//            case "bottom":
//                mMasterFragment.updateName("bottom", name);
//                break;
//            default:
//                break;
//        }
        // change the position
        mViewPager.setCurrentItem(0, false);
    }



    /* --------------------------------------------- */
    /*  This class is responsible for loading        */
    /*  fragments into the ViewPager2                */
    /* --------------------------------------------- */

    private class MyViewPagerAdapter extends FragmentStateAdapter {

        //private String mDetailsString = null;

        public MyViewPagerAdapter(MainActivity ma) {
            super(ma);
        }

//        public void setDetailsString(String s) {
//            mDetailsString = s;
//            if (mDetailsFragment != null) {
//                mDetailsFragment.updateDisplay(mDetailsString);
//            }
//        }
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
                    //mDetailsFragment = DetailFragment.newInstance(mDetailsString);
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

