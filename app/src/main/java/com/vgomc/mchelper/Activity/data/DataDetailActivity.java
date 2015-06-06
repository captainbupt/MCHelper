package com.vgomc.mchelper.activity.data;

import android.os.Bundle;

import com.vgomc.mchelper.R;
import com.vgomc.mchelper.adapter.data.DataDetailFragmentAdapter;
import com.vgomc.mchelper.base.BaseActivity;

import org.holoeverywhere.widget.ViewPager;

/**
 * Created by weizhouh on 6/6/2015.
 */
public class DataDetailActivity extends BaseActivity{
    DataDetailFragmentAdapter mFragmentAdapter;
    ViewPager mDetailFragmentViewPager;

    @Override
    protected void onCreate(Bundle arg0) {
        super.onCreate(arg0);
        setContentView(R.layout.activity_data_detail);
        mDetailFragmentViewPager = (ViewPager) findViewById(R.id.vp_activity_data_detail);
        mFragmentAdapter = new DataDetailFragmentAdapter(getSupportFragmentManager());
    }
}
