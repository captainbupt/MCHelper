package com.vgomc.mchelper.activity;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.ViewConfiguration;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.RadioGroup;

import com.vgomc.mchelper.R;
import com.vgomc.mchelper.adapter.MainFragmentPagerAdapter;
import com.vgomc.mchelper.base.BaseActivity;
import com.vgomc.mchelper.fragment.SettingFragment;
import com.vgomc.mchelper.fragment.SystemFragment;
import com.vgomc.mchelper.transmit.bluetooth.BluetoothHelper;

import java.lang.reflect.Field;


public class MainActivity extends BaseActivity {

    MainFragmentPagerAdapter mPagerAdapter;

    ViewPager mViewPager;
    RadioGroup mRadioGroup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        try {
            ViewConfiguration config = ViewConfiguration.get(this);
            Field menuKeyField = ViewConfiguration.class.getDeclaredField("sHasPermanentMenuKey");
            if (menuKeyField != null) {
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


        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == 1) {
            boolean isAllGranted = true;

            // 判断是否所有的权限都已经授予了
            for (int grant : grantResults) {
                if (grant != PackageManager.PERMISSION_GRANTED) {
                    isAllGranted = false;
                    break;
                }
            }

            if (!isAllGranted) {
                // 弹出对话框告诉用户需要权限的原因, 并引导用户去应用权限管理中手动打开权限按钮
                openAppDetails();
            }
        }
    }

    private void openAppDetails() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("需要访问“外部存储器”，请到 “应用信息 -> 权限” 中授予！");
        builder.setPositiveButton("去手动授权", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = new Intent();
                intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                intent.addCategory(Intent.CATEGORY_DEFAULT);
                intent.setData(Uri.parse("package:" + getPackageName()));
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
                intent.addFlags(Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS);
                startActivity(intent);
            }
        });
        builder.setNegativeButton("取消", null);
        builder.show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_action_bar, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        SettingFragment settingFragment = (SettingFragment) mPagerAdapter.getItem(2);
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

    @Override
    protected void onResume() {
        super.onResume();
        if (mPagerAdapter != null) {
            ((SystemFragment) mPagerAdapter.getItem(3)).updateDate();
        }
    }
}
