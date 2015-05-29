package com.vgomc.mchelper.activity;

import android.os.Bundle;
import android.widget.RadioGroup;

import com.vgomc.mchelper.R;
import com.vgomc.mchelper.adapter.MainFragmentPagerAdapter;
import com.vgomc.mchelper.base.BaseActivity;

import org.holoeverywhere.widget.ViewPager;


public class MainActivity extends BaseActivity {

    MainFragmentPagerAdapter mPagerAdapter;

    ViewPager mViewPager;
    RadioGroup mRadioGroup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mPagerAdapter = new MainFragmentPagerAdapter(getSupportFragmentManager(), mContext);

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.activity_main_view_pager);
        mViewPager.setAdapter(mPagerAdapter);
        mViewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i2) {

            }

            @Override
            public void onPageSelected(int i) {
                mRadioGroup.check(R.id.activity_main_radio_button_1+i);
            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });

        mRadioGroup = (RadioGroup) findViewById(R.id.activity_main_radio_group);
        mRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                mViewPager.setCurrentItem(checkedId-R.id.activity_main_radio_button_1);
            }
        });

        mRadioGroup.check(R.id.activity_main_radio_button_1);

    }

}
