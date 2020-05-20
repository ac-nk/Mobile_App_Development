package com.example.quizapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity implements MainFragment.MyFragmentInterface, ScoreFragment.MyFragmentInterfaceScore {
    private static final String TAG = "MAIN_ACTIVITY";
    String[] labels = {"main", "score" };
    String name;
    Integer score;
    MediaPlayer music;

    Button mButton;
    EditText mEditText;
    ViewPager2 mViewPager;
    MyViewPagerAdapter mMyViewPagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        music= MediaPlayer.create(MainActivity.this,R.raw.harry);
        music.start();

        mViewPager = findViewById(R.id.container);
        //mTextView = findViewById(R.id.tv_main);
        mMyViewPagerAdapter = new MyViewPagerAdapter(this);
        mButton = findViewById(R.id.btn_start);
        mButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                mEditText = findViewById(R.id.et_name);
                name = mEditText.getText().toString();

                mViewPager.setAdapter(mMyViewPagerAdapter);
                mViewPager.setUserInputEnabled(false);

            }
        });
    }
    public void onResume () {
        super.onResume();
        music.start();
    }
    public void onPause() {
        super.onPause();
        music.stop();
        music.release();
    }
    @Override
    public void onFragmentInteraction(int i, int sc) {
        score = sc;
        mViewPager.setCurrentItem(1, false);
    }
    @Override
    public void onFragmentInteractionScore(boolean b) {
        if(b == true) {
        }
    }
    public class MyViewPagerAdapter extends FragmentStateAdapter {

        public MyViewPagerAdapter(MainActivity ma) {
            super(ma);
        }

        //        @Override
//        public Fragment create(int position) {
//            return MainFragment.newInstance(position, name);
//        }
        @Override
        public Fragment createFragment(int position) {
            Fragment res = null;
            switch (position) {
                case 0:
                    res = MainFragment.newInstance(position, name);
                    break;
                case 1:
                    res = ScoreFragment.newInstance(position, score);
                    break;
                default:
                    break;
            }
            return res;
        }

        @Override
        public int getItemCount() {
            return labels.length;
        }
    }
}
