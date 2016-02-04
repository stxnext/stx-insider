package com.stxnext.stxinsider;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.net.wifi.WifiConfiguration;
import android.net.wifi.WifiManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ShareCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.stxnext.stxinsider.receiver.WifiConnStateChangedListener;

import org.joda.time.DateTime;
import org.joda.time.Duration;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {

    @Bind(R.id.activity_main_taxi_phone_no_tv) TextView taxiPhoneNoTextView;
    @Bind(R.id.activity_main_wifi_ssid_tv) TextView wifiSSIDTextView;
    @Bind(R.id.activity_main_wifi_pass_tv) TextView wifiPassTextView;
    @Bind(R.id.activity_main_wifi_connection_progressbar) ProgressBar wifiProgressBar;

    private final String WiFiSSID = "StxXXXXXXX";
    private final String WiFiPass = "xxxxxxxxx";

    private static WifiConnStateChangedListener wifiStateListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);
        wifiSSIDTextView.setText(wifiSSIDTextView.getText() + WiFiSSID);
        wifiPassTextView.setText(wifiPassTextView.getText() + WiFiPass);
    }

    @OnClick(R.id.imageViewTeams)
    public void onTeamsImageClick(ImageView v) {
        startActivity(new Intent(MainActivity.this, SliderActivity.class));
    }

    @OnClick(R.id.activity_main_visit_us_ll)
    public void onVisitUsClick(View v) {
        startActivity(new Intent(getApplicationContext(), MapActivity.class));
    }

    @OnClick(R.id.activity_main_taxi_phone_no_tv)
    public void callTaxiClick(View v) {
        Intent intent = new Intent(Intent.ACTION_CALL);
        intent.setData(Uri.parse("tel:" + taxiPhoneNoTextView.getText()));
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) { return; }
        startActivity(intent);
    }

    @Override
    protected void onResume() {
        super.onResume();
        wifiStateListener = new WifiConnStateChangedListener() {
            @Override public void stateChanged(String ssid, boolean enabled) { wifiConnectionStateChanged(ssid, enabled); }
        };
    }

    DateTime wifiInitStarted = null;
    private void wifiConnectionStateChanged(String ssid, boolean enabled) {

        if (wifiInitStarted != null && enabled == true) {
            long diffInMillis = DateTime.now().getMillis() - wifiInitStarted.getMillis();
            long seconds = Duration.millis(diffInMillis).getStandardSeconds();
            if (seconds > 0) {
                wifiInitStarted = null;
                //Toast.makeText(this, "MainActivity: Wifi connected: " + ssid, Toast.LENGTH_SHORT).show();
                wifiProgressBar.setVisibility(View.GONE);
            }
        }
    }

    @OnClick(R.id.activity_main_wifi_outer_layout)
    public void connectToWifi(View v) {
        wifiInitStarted = DateTime.now();
        wifiProgressBar.setVisibility(View.VISIBLE);

        WifiConfiguration conf = new WifiConfiguration();
        conf.SSID = "\"" + WiFiSSID + "\"";
        conf.preSharedKey = "\""+ WiFiPass +"\"";

        WifiManager wifiManager = (WifiManager)this.getSystemService(Context.WIFI_SERVICE);
        if (!wifiManager.isWifiEnabled())
            wifiManager.setWifiEnabled(true);

        int netId = getNetworkId(wifiManager, WiFiSSID);
        if (netId != -1)
            wifiManager.removeNetwork(netId);
        wifiManager.addNetwork(conf);

        netId = getNetworkId(wifiManager, WiFiSSID);
        wifiManager.disconnect();
        wifiManager.enableNetwork(netId, true);
        wifiManager.reconnect();
    }

    private int getNetworkId(WifiManager wifiManager, String SSID) {
        List<WifiConfiguration> list = wifiManager.getConfiguredNetworks();
        for( WifiConfiguration i : list ) {
            if(i.SSID != null && i.SSID.equals("\"" + SSID + "\""))
                return i.networkId;
        }

        return -1;
    }

    public static WifiConnStateChangedListener getWifiStateListener() {
        return wifiStateListener;
    }
}
