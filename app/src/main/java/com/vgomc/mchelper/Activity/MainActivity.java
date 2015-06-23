package com.vgomc.mchelper.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.ViewConfiguration;
import android.widget.RadioGroup;
import android.widget.SpinnerAdapter;

import com.thoughtworks.xstream.XStream;
import com.vgomc.mchelper.Entity.setting.Configuration;
import com.vgomc.mchelper.R;
import com.vgomc.mchelper.adapter.MainFragmentPagerAdapter;
import com.vgomc.mchelper.base.BaseActivity;
import com.vgomc.mchelper.fragment.SettingFragment;
import com.vgomc.mchelper.transmit.bluetooth.BluetoothHelper;
import com.vgomc.mchelper.transmit.file.FileServiceProvider;
import com.vgomc.mchelper.utility.ToastUtil;

import org.holoeverywhere.app.AlertDialog;
import org.holoeverywhere.widget.ArrayAdapter;
import org.holoeverywhere.widget.EditText;
import org.holoeverywhere.widget.ViewPager;

import java.io.File;
import java.lang.reflect.Field;
import java.security.Policy;


public class MainActivity extends BaseActivity {

    MainFragmentPagerAdapter mPagerAdapter;

    ViewPager mViewPager;
    RadioGroup mRadioGroup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        try {
            ViewConfiguration config = ViewConfiguration.get(this);
            Field menuKeyField = ViewConfiguration.class.getDeclaredField("sHasPermanentMenuKey");
            if(menuKeyField != null) {
                menuKeyField.setAccessible(true);
                menuKeyField.setBoolean(config, false);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mPagerAdapter = new MainFragmentPagerAdapter(getSupportFragmentManager(), mContext);

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.activity_main_view_pager);
        mViewPager.setAdapter(mPagerAdapter);
        mViewPager.setOffscreenPageLimit(3);
        mViewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i2) {

            }

            @Override
            public void onPageSelected(int i) {
                mRadioGroup.check(R.id.activity_main_radio_button_1 + i);
            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });

        mRadioGroup = (RadioGroup) findViewById(R.id.activity_main_radio_group);
        mRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.activity_main_radio_button_1:
                        mViewPager.setCurrentItem(0);
                        break;
                    case R.id.activity_main_radio_button_2:
                        mViewPager.setCurrentItem(1);
                        break;
                    case R.id.activity_main_radio_button_3:
                        mViewPager.setCurrentItem(2);
                        break;
                    case R.id.activity_main_radio_button_4:
                        mViewPager.setCurrentItem(3);
                        break;
                }
            }
        });

        mRadioGroup.check(R.id.activity_main_radio_button_1);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_action_bar, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        SettingFragment settingFragment = (SettingFragment) mPagerAdapter.getItem(0);
        switch (item.getItemId()) {
            case R.id.menu_action_bar_read_from_device:
                settingFragment.readSettingFromDevice();
                return true;
            case R.id.menu_action_bar_read_from_file:
                settingFragment.readSettingFromFile();
                return true;
            case R.id.menu_action_bar_write_to_device:
                settingFragment.writeSettingToDevice();
                return true;
            case R.id.menu_action_bar_write_to_file:
                settingFragment.writeSettingToFile();
                return true;
            case R.id.menu_action_test:
                showTextDialog();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }


    private void showTextDialog() {
        final EditText et = new EditText(mContext);

        new AlertDialog.Builder(this).setTitle("测试内容")
                .setView(et)
                .setPositiveButton(R.string.dialog_confirm, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        String input = et.getText().toString();
                        if (input.equals("")) {
                            showToast(R.string.menu_action_bar_write_to_file_input_empty);
                        } else {
                            BluetoothHelper.sendMessage(input);
                        }
                    }
                }).setNegativeButton("取消", null).show();
    }
}
