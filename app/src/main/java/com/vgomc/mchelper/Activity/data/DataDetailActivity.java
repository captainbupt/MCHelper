package com.vgomc.mchelper.activity.data;

import android.os.Bundle;
import android.support.v4.view.ViewPager;

import com.vgomc.mchelper.R;
import com.vgomc.mchelper.adapter.data.DataDetailFragmentAdapter;
import com.vgomc.mchelper.base.BaseActivity;
import com.vgomc.mchelper.view.data.CurrentDataView;


/**
 * Created by weizhouh on 6/6/2015.
 */
public class DataDetailActivity extends BaseActivity {

    public static final String KEY_DATA = "data";
    public static final String KEY_POSITION = "position";

    DataDetailFragmentAdapter mFragmentAdapter;
    ViewPager mDetailFragmentViewPager;

    @Override
    protected void onCreate(Bundle arg0) {
        super.onCreate(arg0);
        setContentView(R.layout.activity_data_detail);
        mDetailFragmentViewPager = (ViewPager) findViewById(R.id.vp_activity_data_detail);
        mFragmentAdapter = new DataDetailFragmentAdapter(getSupportFragmentManager());
        mFragmentAdapter.setList(CurrentDataView.mVariableDataLists);
        mDetailFragmentViewPager.setAdapter(mFragmentAdapter);
        mDetailFragmentViewPager.setCurrentItem(mReceivedIntent.getIntExtra(KEY_POSITION, 0));
    }
}
