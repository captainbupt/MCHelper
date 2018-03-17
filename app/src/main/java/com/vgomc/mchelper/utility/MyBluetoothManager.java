package com.vgomc.mchelper.utility;

import android.content.Context;
import android.text.TextUtils;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.vgomc.mchelper.entity.system.Bluetooth;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MyBluetoothManager {

    private final static String KEY = "bluetooth";

    public static Map<String, String> getBluetoothMap(Context context) {
        String content = SP.getStringSP(context, KEY, KEY, null);
        if (TextUtils.isEmpty(content)) {
            return new HashMap<>();
        }
        return (Map<String, String>) new Gson().fromJson(content, new TypeToken<Map<String, String>>() {
        }.getRawType());
    }

    public static List<Object> getBluetoothList(Context context) {
        Map<String, String> map = getBluetoothMap(context);
        List<Object> bluetoothList = new ArrayList<>();
        for (Map.Entry<String, String> item : map.entrySet()) {
            Bluetooth bluetooth = new Bluetooth();
            bluetooth.setAddress(item.getKey());
            bluetooth.setName(item.getValue());
            bluetoothList.add(bluetooth);
        }
        return bluetoothList;
    }

    public static void addBluetooth(Context context, String address, String name) {
        Map<String, String> map = getBluetoothMap(context);
        map.put(address, name);
        SP.putStringSP(context, KEY, KEY, new Gson().toJson(map));
    }

    public static void deleteBluetooth(Context context, String address) {
        Map<String, String> map = getBluetoothMap(context);
        map.remove(address);
        SP.putStringSP(context, KEY, KEY, new Gson().toJson(map));
    }
}
