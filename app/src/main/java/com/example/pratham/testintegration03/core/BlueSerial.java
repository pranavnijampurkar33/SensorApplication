package com.example.pratham.testintegration03.core;

import android.content.Context;

import app.akexorcist.bluetotohspp.library.BluetoothSPP;

public class BlueSerial {
    public static BluetoothSPP instance;
    public static String addrBuf;

    public static void setInstance(Context context) {
        instance = new BluetoothSPP(context);
    }

}
