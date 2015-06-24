package com.vgomc.mchelper.transmit.bluetooth;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.Toast;

import com.vgomc.mchelper.utility.ToastUtil;

import org.holoeverywhere.app.Activity;

import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;

/**
 * Created by weizhouh on 6/6/2015.
 */
public class BluetoothHelper {
    // Message types sent from the BluetoothHelperService Handler
    public static final int MESSAGE_STATE_CHANGE = 1;
    public static final int MESSAGE_READ = 2;
    public static final int MESSAGE_WRITE = 3;
    public static final int MESSAGE_DEVICE_NAME = 4;
    public static final int MESSAGE_TOAST = 5;

    // Key names received from the BluetoothHelperService Handler
    public static final String DEVICE_NAME = "device_name";
    public static final String TOAST = "toast";

    // Intent request codes
    private static final int REQUEST_CONNECT_DEVICE = 100001;
    private static final int REQUEST_ENABLE_BT = 100002;

    // Local Bluetooth adapter
    private static BluetoothAdapter mBluetoothAdapter;
    // Member object for the chat services
    private static BluetoothHelperService mChatService = null;
    private static Context mContext;

    public static void initBluetooth(Context context) {
        // Get local Bluetooth adapter
        if (mBluetoothAdapter == null)
            mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        mContext = context;
    }

    private static void setupChat() {
        // Initialize the BluetoothHelperService to perform bluetooth connections
        mChatService = new BluetoothHelperService(mContext, mHandler);
    }

    private static String mMessage;
    private static boolean isErrorSend = false;

    /**
     * Sends a message.
     *
     * @param message A string of text to send.
     */
    public static boolean sendMessage(String message) {
        mMessage = message;
        isErrorSend = false;
        System.out.println(message + ", " + isErrorSend);
        // If BT is not on, request that it be enabled.
        // setupChat() will then be called during onActivityResult
        if (!mBluetoothAdapter.isEnabled()) {
            Intent enableIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            ((Activity) mContext).startActivityForResult(enableIntent, REQUEST_ENABLE_BT);
            return false;
        }
        // Otherwise, setup the chat session
        if (mChatService == null) setupChat();


        // Check that we're actually connected before trying anything
        if (mChatService.getState() != BluetoothHelperService.STATE_CONNECTED) {
            scanDevice();
            return false;
        }

        // Check that there's actually something to send
        if (message.length() > 0) {
            // Get the message bytes and tell the BluetoothHelperService to write
            byte[] send = message.getBytes(Charset.forName("GBK"));
            mChatService.write(send);
        }
        return true;
    }

    public interface OnReceivedMessageListener {
        void onReceivedMessage(byte[] messageByte, int length);

        void onError();
    }

    public static void setOnReceivedMessageListener(OnReceivedMessageListener onReceivedMessageListener) {
        BluetoothHelper.mOnReceivedMessageListener = onReceivedMessageListener;
    }

    private static OnReceivedMessageListener mOnReceivedMessageListener;

    private static String mConnectedDeviceName;
    // The Handler that gets information back from the BluetoothHelperService
    private static final Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case MESSAGE_STATE_CHANGE:
                    switch (msg.arg1) {
                        case BluetoothHelperService.STATE_CONNECTED:
                            //mTitle.append(mConnectedDeviceName);
                            //ToastUtil.showToast(mContext, "State connected");
                            BluetoothHelper.sendMessage(mMessage);
                            break;
                        case BluetoothHelperService.STATE_CONNECTING:
                            //ToastUtil.showToast(mContext, "State connecting");
                            break;
                        case BluetoothHelperService.STATE_LISTEN:
                            // ToastUtil.showToast(mContext, "State listen");
                        case BluetoothHelperService.STATE_NONE:
                            System.out.println("error, " + isErrorSend);
                            if (!isErrorSend) {
                                mOnReceivedMessageListener.onError();
                                isErrorSend = true;
                            }
                            break;
                    }
                    break;
                case MESSAGE_WRITE:
                    byte[] writeBuf = (byte[]) msg.obj;
                    // construct a string from the buffer
                    String writeMessage = new String(writeBuf);
                    // ToastUtil.showToast(mContext, "Write message: " + writeMessage);
                    break;
                case MESSAGE_READ:
                    byte[] readBuf = (byte[]) msg.obj;
                    // construct a string from the valid bytes in the buffer
                    /*String readMessage = null;

                    readMessage = new String(readBuf, 0, msg.arg1);*/
                    if (mOnReceivedMessageListener != null) {
                        mOnReceivedMessageListener.onReceivedMessage(readBuf, msg.arg1);
                    } else {
                        // ToastUtil.showToast(mContext, "Read message: " + readMessage);
                    }
                    break;
                case MESSAGE_DEVICE_NAME:
                    // save the connected device's name
                    mConnectedDeviceName = msg.getData().getString(DEVICE_NAME);
                    //ToastUtil.showToast(mContext, "Connected device: " + mConnectedDeviceName);
                    break;
                case MESSAGE_TOAST:
                    //Toast.makeText(mContext, msg.getData().getString(TOAST), Toast.LENGTH_SHORT).show();
                    break;
            }
        }
    };

    public static void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case REQUEST_CONNECT_DEVICE:
                // When DeviceListActivity returns with a device to connect
                if (resultCode == android.app.Activity.RESULT_OK) {
                    System.out.println("received result");
                    // Get the device MAC address
                    String address = data.getExtras()
                            .getString(DeviceListActivity.EXTRA_DEVICE_ADDRESS);
                    // Get the BLuetoothDevice object
                    BluetoothDevice device = mBluetoothAdapter.getRemoteDevice(address);
                    // Attempt to connect to the device
                    mChatService.connect(device);
                } else {
                    if (!isErrorSend) {
                        mOnReceivedMessageListener.onError();
                        isErrorSend = true;
                    }
                }
                break;
            case REQUEST_ENABLE_BT:
                // When the request to enable Bluetooth returns
                if (resultCode == android.app.Activity.RESULT_OK) {
                    // Bluetooth is now enabled, so set up a chat session
                    setupChat();
                    sendMessage(mMessage);
                } else {
                    if (!isErrorSend) {
                        mOnReceivedMessageListener.onError();
                        isErrorSend = true;
                    }
                }
        }
    }

    public static void scanDevice() {
        // Launch the DeviceListActivity to see devices and do scan
        Intent serverIntent = new Intent(mContext, DeviceListActivity.class);
        ((Activity) mContext).startActivityForResult(serverIntent, REQUEST_CONNECT_DEVICE);
    }

}
