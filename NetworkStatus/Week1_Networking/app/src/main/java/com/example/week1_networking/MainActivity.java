package com.example.week1_networking;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothManager;
import android.bluetooth.BluetoothProfile;
import android.content.Context;
import android.content.DialogInterface;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkCapabilities;
import android.net.NetworkInfo;
import android.net.TrafficStats;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.View;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.HttpURLConnection;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.URL;
import java.util.Enumeration;

public class MainActivity extends AppCompatActivity {
    //Managers
    ConnectivityManager cm;
    WifiManager wifiManager;
    TelephonyManager telephonyManager;
    BluetoothAdapter bluetoothAdapter;

    //For Network Traffic
    private Handler trafficHandler = new Handler();
    Long startDownloadTraffic;
    Long startUploadTraffic;

    //Views and Switches
    TextView upSpeed, downSpeed, netStatus, wifiStatus;
    Switch mobileData, wifi, USBTethering, BluetoothTethering;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //TextViews for Displaying Status
        upSpeed = findViewById(R.id.UpSpeed);
        downSpeed = findViewById(R.id.DownSpeed);
        netStatus = findViewById(R.id.netstatus);
        wifiStatus = findViewById(R.id.wifiStatus);

        //Switches
        mobileData = findViewById(R.id.mobileData);
        wifi = findViewById(R.id.wifi);
        BluetoothTethering = findViewById(R.id.bluetooth);
        USBTethering = findViewById(R.id.usb);

        //Adapters and Managers
        bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        wifiManager = (WifiManager) getApplicationContext().getSystemService(Context.WIFI_SERVICE);
        telephonyManager = (TelephonyManager) getApplicationContext().getSystemService(Context.TELEPHONY_SERVICE);

        cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = cm.getActiveNetworkInfo();


        boolean c = false;
        if (networkInfo != null) {
            c = networkInfo.isConnected();
        }

        if (c){
            Log.i("C", "Connected");
        }

        //Initialize
        startDownloadTraffic = TrafficStats.getTotalRxBytes();
        startUploadTraffic = TrafficStats.getTotalTxBytes();

        //Alert if RX or TX TrafficStats are not supported
        if (startDownloadTraffic == TrafficStats.UNSUPPORTED || startUploadTraffic == TrafficStats.UNSUPPORTED) {
            AlertDialog.Builder alert = new AlertDialog.Builder(this);
            alert.setTitle("Uh Oh!");
            alert.setMessage("Your device does not support traffic stat monitoring.");
            alert.show();
        } else {
            trafficHandler.postDelayed(trafficRunnable, 1000);
        }


    }


    //Runnable to keep checking every second
    private final Runnable trafficRunnable = new Runnable() {
        public void run() {
            startUploadTraffic = TrafficStats.getTotalTxBytes();
            startDownloadTraffic = TrafficStats.getTotalRxBytes();

            long rxBytes = TrafficStats.getTotalRxBytes() - startDownloadTraffic;
            Double rxKBytes = (rxBytes/1000.0);
            downSpeed.setText(Double.toString(rxKBytes) + "KB/sec");

            long txBytes = TrafficStats.getTotalTxBytes() - startUploadTraffic;
            Double txKBytes = (txBytes/1000.0);
            upSpeed.setText(Double.toString(txKBytes)  + "KB/sec");

            trafficHandler.postDelayed(trafficRunnable, 1000);
            Check();
        }
    };


    //Check network speed in use and status of Wifi, Mobile Data, Bluetooth, and USB Tethering
    public void Check(){
        boolean isWifiConn = false;
        boolean isMobileConn = false;
        for (Network network : cm.getAllNetworks()) {
            NetworkInfo networkInfo = cm.getNetworkInfo(network);
            if (networkInfo.getType() == ConnectivityManager.TYPE_WIFI) {
                isWifiConn |= networkInfo.isConnected();
            }
            if (networkInfo.getType() == ConnectivityManager.TYPE_MOBILE) {
                isMobileConn |= networkInfo.isConnected();
            }
        }

        mobileData.setChecked(isMobileConn);
        wifi.setChecked((wifiManager.getWifiState()==WifiManager.WIFI_STATE_ENABLED)? true: false);

        if (isWifiConn){
            wifiStatus.setText("Connected");
        } else {
            wifiStatus.setText("Not Connected");
        }
        BluetoothTethering.setChecked(bluetoothAdapter.isEnabled());
        USBTethering.setChecked(isTetheringActive());
    }

    //Switch Statement for all Switches in Main Activity
    public void Switches(View view){

        switch (view.getId()){
            case R.id.wifi: {
                if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q){
                    Popup("Non-system apps can no longer change WIFI state from API level 29-(Android Q). \n Please change it manually",
                            "Permission Unavailable");
                } else {
                    Log.i("WifiToggle", "Triggered");
                    if (wifiManager.getWifiState()==WifiManager.WIFI_STATE_ENABLED){
                        wifiManager.setWifiEnabled(false);
                    } else {
                        wifiManager.setWifiEnabled(true);
                    }

                } break;
            }

            case R.id.mobileData: {
                Popup("The App needs to be a system app to change Mobile Data state","Permission Unavailable");
                break;
            }

            case R.id.bluetooth: {
                if (bluetoothAdapter.isEnabled()){
                    bluetoothAdapter.disable();
                } else {
                    bluetoothAdapter.enable();
                }
                break;
            }

            case R.id.usb: {
                Popup("The App needs to be a system app to change Tethering state", "Permission Unavailable");
                break;
            }

            default: Log.i("Switch", "ID not found");
        }
    }

    //Checks if USB Tethering is active
    private static boolean isTetheringActive(){
        try{
            for(Enumeration<NetworkInterface> en = NetworkInterface.getNetworkInterfaces(); en.hasMoreElements();){
                NetworkInterface intf=en.nextElement();
                for(Enumeration<InetAddress> enumIpAddr = intf.getInetAddresses(); enumIpAddr.hasMoreElements();){
                    InetAddress inetAddress=enumIpAddr.nextElement();
                    if(!intf.isLoopback()){
                        if(intf.getName().contains("rndis")){
                            return true;
                        }
                    }
                }
            }
        }catch(Exception e){e.printStackTrace();}
        return false;
    }

    //Alert Dialogue (with Neutral Button)
    public void Popup(String message, String title) {

        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        builder.setMessage(message)
                .setTitle(title)
                .setNeutralButton("Okay", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //Does Nothing!!
                    }
                });
        builder.create().show();
    }

}

