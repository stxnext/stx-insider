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
import android.widget.TextView;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {

    @Bind(R.id.activity_main_taxi_phone_no_tv) TextView taxiPhoneNoTextView;
    @Bind(R.id.activity_main_wifi_ssid_tv) TextView wifiSSIDTextView;
    @Bind(R.id.activity_main_wifi_pass_tv) TextView wifiPassTextView;

    private final String WiFiSSID = "StxXXXXXXX";
    private final String WiFiPass = "xxxxxxxxx";

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

    @OnClick(R.id.activity_main_wifi_outer_layout)
    public void connectToWifi(View v) {
        WifiConfiguration conf = new WifiConfiguration();
        conf.SSID = "\"" + WiFiSSID + "\"";
        conf.preSharedKey = "\""+ WiFiPass +"\"";

        WifiManager wifiManager = (WifiManager)this.getSystemService(Context.WIFI_SERVICE);
        //todo: check if conf exists first
        wifiManager.addNetwork(conf);


        List<WifiConfiguration> list = wifiManager.getConfiguredNetworks();
        for( WifiConfiguration i : list ) {
            if(i.SSID != null && i.SSID.equals("\"" + WiFiPass + "\"")) {
                wifiManager.disconnect();
                wifiManager.enableNetwork(i.networkId, true);
                wifiManager.reconnect();

                break;
            }
        }
    }


}
