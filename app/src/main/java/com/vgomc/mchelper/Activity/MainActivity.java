package com.vgomc.mchelper.activity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.RadioGroup;
import android.widget.SpinnerAdapter;

import com.thoughtworks.xstream.XStream;
import com.vgomc.mchelper.Entity.setting.Configuration;
import com.vgomc.mchelper.R;
import com.vgomc.mchelper.adapter.MainFragmentPagerAdapter;
import com.vgomc.mchelper.base.BaseActivity;
import com.vgomc.mchelper.fragment.SettingFragment;
import com.vgomc.mchelper.transmit.file.FileServiceProvider;
import com.vgomc.mchelper.utility.ToastUtil;

import org.holoeverywhere.app.AlertDialog;
import org.holoeverywhere.widget.ArrayAdapter;
import org.holoeverywhere.widget.EditText;
import org.holoeverywhere.widget.ViewPager;

import java.io.File;
import java.security.Policy;


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
        switch (item.getItemId()) {
            case R.id.menu_action_bar_read_from_device:
                return true;
            case R.id.menu_action_bar_read_from_file:
                showConfigurationFiles();
                return true;
            case R.id.menu_action_bar_write_to_device:
                return true;
            case R.id.menu_action_bar_write_to_file:
                showWriteToFileDialog();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void showConfigurationFiles() {
        final String[] configurationFiles = FileServiceProvider.getConfigurationFileNames();
        if (configurationFiles == null || configurationFiles.length == 0) {
            showToast(R.string.menu_action_bar_read_from_file_empty);
            return;
        }
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);

        builder.setTitle(R.string.menu_action_bar_read_from_file_choice)
                .setItems(configurationFiles, new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Configuration configuration = FileServiceProvider.readObjectFromFile(FileServiceProvider.getExternalStoragePath() + File.separator + configurationFiles[which]);
                        if (configuration == null) {
                            showToast(R.string.menu_action_bar_read_from_file_fail);
                        } else {
                            showToast(R.string.menu_action_bar_read_from_file_success);
                            Configuration.setInstance(configuration);
                            ((SettingFragment) mPagerAdapter.getItem(0)).updateData();
                        }
                    }
                }).show();
    }

    private void showWriteToFileDialog() {
        final EditText et = new EditText(mContext);

        new AlertDialog.Builder(this).setTitle(R.string.menu_action_bar_write_to_file_input)
                .setView(et)
                .setPositiveButton(R.string.dialog_confirm, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        String input = et.getText().toString();
                        if (input.equals("")) {
                            showToast(R.string.menu_action_bar_write_to_file_input_empty);
                        } else {
                            String path = FileServiceProvider.getExternalStoragePath() + File.separator + input + FileServiceProvider.SUFFIX;
                            System.out.println("path: " + path);
                            File file = new File(path);
                            if (file.exists()) {
                                showReplaceDialog(path);
                            } else {
                                if (FileServiceProvider.writeObjectToFile(Configuration.getInstance(), path)) {
                                    showToast(R.string.menu_action_bar_write_to_file_success);
                                } else {
                                    showToast(R.string.menu_action_bar_write_to_file_fail);
                                }
                            }
                        }
                    }
                }).setNegativeButton("取消", null).show();
    }

    private void showReplaceDialog(final String path) {
        new AlertDialog.Builder(mContext).setTitle(R.string.menu_action_bar_write_to_file_existed)
                .setPositiveButton(R.string.dialog_confirm, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        FileServiceProvider.writeObjectToFile(Configuration.getInstance(), path);
                        showToast(R.string.menu_action_bar_write_to_file_success);
                    }
                }).setNegativeButton(R.string.dialog_cancel, null).create().show();
    }
}
