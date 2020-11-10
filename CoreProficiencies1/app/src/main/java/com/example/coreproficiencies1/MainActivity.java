package com.example.coreproficiencies1;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    ViewPager mViewPager;
    MyViewPagerAdapter mMyViewPagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // assign the instance of ViewPager
        mViewPager = findViewById(R.id.container);

        // create an adapter for the ViewPager
        mMyViewPagerAdapter = new MyViewPagerAdapter(getSupportFragmentManager());

        // set the adapter for the ViewPager
        mViewPager.setAdapter(mMyViewPagerAdapter);
    }

    public class MyViewPagerAdapter extends FragmentPagerAdapter {

        public MyViewPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {

            // each time you swipe to a new position, a new fragment is created
            // NOTE that fragments are not created by calling the constructor directly.
            //  => https://stackoverflow.com/questions/9245408/
            return MainFragment.newInstance();
        }

        @Override
        public int getCount() {
            return 5;
        }
    }
}