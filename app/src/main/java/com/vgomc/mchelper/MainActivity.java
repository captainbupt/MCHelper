package com.vgomc.mchelper;

import java.util.Locale;

import android.support.v4.app.FragmentActivity;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.FragmentPagerAdapter;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.vgomc.mchelper.adapter.MainFragmentPagerAdapter;
import com.vgomc.mchelper.factory.MainFragmentFactory;


public class MainActivity extends FragmentActivity {

    MainFragmentPagerAdapter mPagerAdapter;

    ViewPager mViewPager;
    RadioGroup mRadioGroup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mPagerAdapter = new MainFragmentPagerAdapter(getSupportFragmentManager(), MainActivity.this, new MainFragmentFactory(this));

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
