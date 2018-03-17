/*
 * Copyright (C) 2009 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.vgomc.mchelper.transmit.bluetooth;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.vgomc.mchelper.R;
import com.vgomc.mchelper.adapter.system.MyBluetoothAdapter;
import com.vgomc.mchelper.entity.system.Bluetooth;
import com.vgomc.mchelper.utility.MyBluetoothManager;

import java.util.Map;
import java.util.Set;

/**
 * This Activity appears as a dialog. It lists any paired devices and devices
 * detected in the area after discovery. When a device is chosen by the user,
 * the MAC address of the device is sent back to the parent Activity in the
 * result Intent.
 */
public class DeviceListActivity extends Activity {
    // Debugging
    private static final String TAG = "DeviceListActivity";
    private static final boolean D = true;

    // Return Intent extra
    public static String EXTRA_DEVICE_ADDRESS = "device_address";

    // Member fields
    private BluetoothAdapter mBtAdapter;
    private MyBluetoothAdapter mPairedDevicesArrayAdapter;
    private MyBluetoothAdapter mNewDevicesArrayAdapter;
    private Map<String, String> mBluetoothMap;
    private TextView mNonePairedTextView;
    private TextView mNoneFoundTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mBluetoothMap = MyBluetoothManager.getBluetoothMap(this);

        // Setup the window
        requestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS);
        setContentView(R.layout.device_list);

        // Set result CANCELED incase the user backs out
        setResult(Activity.RESULT_CANCELED);

        // Initialize the button to perform device discovery
        final Button scanButton = (Button) findViewById(R.id.button_scan);
        scanButton.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                doDiscovery();
                v.setEnabled(false);
            }
        });

        mNonePairedTextView = findViewById(R.id.none_paired);
        mNoneFoundTextView = findViewById(R.id.none_found);

        // Initialize array adapters. One for already paired devices and
        // one for newly discovered devices
        mPairedDevicesArrayAdapter = new MyBluetoothAdapter(this, mDeviceClickListener);
        mNewDevicesArrayAdapter = new MyBluetoothAdapter(this, mDeviceClickListener);

        // Find and set up the ListView for paired devices
        ListView pairedListView = (ListView) findViewById(R.id.paired_devices);
        pairedListView.setAdapter(mPairedDevicesArrayAdapter);

        // Find and set up the ListView for newly discovered devices
        ListView newDevicesListView = (ListView) findViewById(R.id.new_devices);
        newDevicesListView.setAdapter(mNewDevicesArrayAdapter);

        // Register for broadcasts when a device is discovered
        IntentFilter filter = new IntentFilter(BluetoothDevice.ACTION_FOUND);
        this.registerReceiver(mReceiver, filter);

        // Register for broadcasts when discovery has finished
        filter = new IntentFilter(BluetoothAdapter.ACTION_DISCOVERY_FINISHED);
        this.registerReceiver(mReceiver, filter);

        // Get the local Bluetooth adapter
        mBtAdapter = BluetoothAdapter.getDefaultAdapter();

        // Get a set of currently paired devices
        Set<BluetoothDevice> pairedDevices = mBtAdapter.getBondedDevices();

        // If there are paired devices, add each one to the ArrayAdapter
        if (pairedDevices.size() > 0) {
            for (BluetoothDevice device : pairedDevices) {
                Bluetooth bluetooth = new Bluetooth();
                bluetooth.setAddress(device.getAddress());
                String name = device.getName();
                if (mBluetoothMap.containsKey(device.getAddress())) {
                    name = mBluetoothMap.get(device.getAddress());
                }
                bluetooth.setName(name);
                mPairedDevicesArrayAdapter.addItem(bluetooth);
            }
            mNonePairedTextView.setVisibility(View.INVISIBLE);
        } else {
            mNonePairedTextView.setVisibility(View.VISIBLE);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        // Make sure we're not doing discovery anymore
        if (mBtAdapter != null) {
            mBtAdapter.cancelDiscovery();
        }

        // Unregister broadcast listeners
        this.unregisterReceiver(mReceiver);
    }

    /**
     * Start device discover with the BluetoothAdapter
     */
    private void doDiscovery() {

        if (D) Log.d(TAG, "doDiscovery()");
        mBluetoothMap = MyBluetoothManager.getBluetoothMap(this);

        mNoneFoundTextView.setVisibility(View.INVISIBLE);

        // Indicate scanning in the title
        setProgressBarIndeterminateVisibility(true);
        setTitle(R.string.scanning);

        // If we're already discovering, stop it
        if (mBtAdapter.isDiscovering()) {
            mBtAdapter.cancelDiscovery();
        }

        // Request discover from BluetoothAdapter
        mBtAdapter.startDiscovery();
    }

    // The on-click listener for all devices in the ListViews
    private OnClickListener mDeviceClickListener = new OnClickListener() {
        @Override
        public void onClick(View view) {
            mBtAdapter.cancelDiscovery();

            Bluetooth bluetooth = (Bluetooth) view.getTag();

            MyBluetoothManager.addBluetooth(DeviceListActivity.this, bluetooth.getAddress(), bluetooth.getName());

            // Create the result Intent and include the MAC address
            Intent intent = new Intent();
            intent.putExtra(EXTRA_DEVICE_ADDRESS, bluetooth.getAddress());

            // Set result and finish this Activity
            setResult(Activity.RESULT_OK, intent);
            finish();
        }
    };

    // The BroadcastReceiver that listens for discovered devices and
    // changes the title when discovery is finished
    private final BroadcastReceiver mReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();

            // When discovery finds a device
            if (BluetoothDevice.ACTION_FOUND.equals(action)) {
                // Get the BluetoothDevice object from the Intent
                BluetoothDevice device = intent
                        .getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
                // If it's already paired, skip it, because it's been listed
                // already
                if (device.getBondState() != BluetoothDevice.BOND_BONDED) {
                    Bluetooth bluetooth = new Bluetooth();
                    bluetooth.setAddress(device.getAddress());
                    String name = device.getName();
                    if (mBluetoothMap.containsKey(device.getAddress())) {
                        name = mBluetoothMap.get(device.getAddress());
                    }
                    bluetooth.setName(name);
                    mNewDevicesArrayAdapter.addItem(bluetooth);
                    mNoneFoundTextView.setVisibility(View.INVISIBLE);
                }
                // When discovery is finished, change the Activity title
            } else if (BluetoothAdapter.ACTION_DISCOVERY_FINISHED.equals(action)) {
                setProgressBarIndeterminateVisibility(false);
                setTitle(R.string.select_device);
                if (mNewDevicesArrayAdapter.getCount() == 0) {
                    mNoneFoundTextView.setVisibility(View.VISIBLE);
                }
                findViewById(R.id.button_scan).setEnabled(true);
            }
        }
    };

}
