package com.stxnext.stxinsider;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.net.wifi.WifiConfiguration;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.common.collect.Lists;
import com.stxnext.stxinsider.adapter.TeamCategoriesFragmentPagerAdapter;
import com.stxnext.stxinsider.constant.TeamCategories;
import com.stxnext.stxinsider.fragment.TeamCategoryFragment;
import com.stxnext.stxinsider.model.TeamCategoryHeader;
import com.stxnext.stxinsider.receiver.WifiConnStateChangedListener;

import org.joda.time.DateTime;
import org.joda.time.Duration;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MapActivity extends AppCompatActivity {

    private GoogleMap map;
    private final LatLng OFFICE_LOCATION = new LatLng(52.3944957,16.8936571);


    @Bind(R.id.activity_main_taxi_phone_no_tv)
    TextView taxiPhoneNoTextView;
    @Bind(R.id.activity_main_wifi_ssid_tv) TextView wifiSSIDTextView;
    @Bind(R.id.activity_main_wifi_pass_tv) TextView wifiPassTextView;
    @Bind(R.id.activity_main_wifi_connection_progressbar)
    ProgressBar wifiProgressBar;
    private final String WiFiSSID = "StxXXXXXXX";
    private final String WiFiPass = "xxxxxxxxx";
    private static WifiConnStateChangedListener wifiStateListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);

        getSupportActionBar().setTitle("Location");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        ButterKnife.bind(this);

        wifiSSIDTextView.setText(wifiSSIDTextView.getText() + WiFiSSID);
        wifiPassTextView.setText(wifiPassTextView.getText() + WiFiPass);

        prepareMap();
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

    @OnClick(R.id.activity_main_taxi_phone_no_tv)
    public void callTaxiClick(View v) {
        Intent intent = new Intent(Intent.ACTION_CALL);
        intent.setData(Uri.parse("tel:" + taxiPhoneNoTextView.getText()));
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) { return; }
        startActivity(intent);
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
        if (list == null || list.size() == 0)
            return -1;

        for( WifiConfiguration i : list ) {
            if(i.SSID != null && i.SSID.equals("\"" + SSID + "\""))
                return i.networkId;
        }

        return -1;
    }


    @Override
    protected void onResume() {
        super.onResume();
        wifiStateListener = new WifiConnStateChangedListener() {
            @Override public void stateChanged(String ssid, boolean enabled) { wifiConnectionStateChanged(ssid, enabled); }
        };
    }

    @Override
    protected void onPause() {
        super.onPause();
        wifiStateListener = null;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home)
            finish();

        return super.onOptionsItemSelected(item);
    }

    private void prepareMap() {
        FragmentManager fm = getSupportFragmentManager();
        SupportMapFragment fragment = (SupportMapFragment) fm.findFragmentById(R.id.map_fragment);
        if (fragment != null) {
            map = fragment.getMap();
            if (map != null) {

                MarkerOptions options = new MarkerOptions();
                options.position(OFFICE_LOCATION);
                map.addMarker(options);

                map.setMapType(GoogleMap.MAP_TYPE_TERRAIN);
                map.getUiSettings().setAllGesturesEnabled(false);

                animateMap();
            }
        }
    }

    private void animateMap() {
        if (map != null) {
            LatLng cameraPosition = new LatLng(OFFICE_LOCATION.latitude + 0.02, OFFICE_LOCATION.longitude);
            map.animateCamera(CameraUpdateFactory.newLatLngZoom(cameraPosition, 12), 2000, null);
        }
    }

    public static WifiConnStateChangedListener getWifiStateListener() {
        return wifiStateListener;
    }
}
