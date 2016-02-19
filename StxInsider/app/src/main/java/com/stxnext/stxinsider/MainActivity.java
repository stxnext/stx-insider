package com.stxnext.stxinsider;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.net.wifi.WifiConfiguration;
import android.net.wifi.WifiManager;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ShareCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.estimote.sdk.BeaconManager;
import com.estimote.sdk.Nearable;
import com.estimote.sdk.SystemRequirementsChecker;
import com.estimote.sdk.cloud.model.Color;
import com.stxnext.stxinsider.estimote.BeaconID;
import com.stxnext.stxinsider.estimote.EstimoteCloudBeaconDetails;
import com.stxnext.stxinsider.estimote.EstimoteCloudBeaconDetailsFactory;
import com.stxnext.stxinsider.estimote.ProximityContentManager;
import com.stxnext.stxinsider.model.SliderActivityType;
import com.stxnext.stxinsider.receiver.WifiConnStateChangedListener;

import org.joda.time.DateTime;
import org.joda.time.Duration;

import java.util.Arrays;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {

    private final static String TAG = MainActivity.class.getSimpleName();

    @Bind(R.id.activity_main_taxi_phone_no_tv) TextView taxiPhoneNoTextView;
    @Bind(R.id.activity_main_wifi_ssid_tv) TextView wifiSSIDTextView;
    @Bind(R.id.activity_main_wifi_pass_tv) TextView wifiPassTextView;
    @Bind(R.id.activity_main_wifi_connection_progressbar) ProgressBar wifiProgressBar;

    private final String WiFiSSID = "StxXXXXXXX";
    private final String WiFiPass = "xxxxxxxxx";

    private static WifiConnStateChangedListener wifiStateListener;
    private BeaconManager beaconManager;
    private String baeconScanId;

    private ProximityContentManager proximityContentManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        beaconManager = new BeaconManager(this);

        getSupportActionBar().hide();

        ButterKnife.bind(this);
        wifiSSIDTextView.setText(wifiSSIDTextView.getText() + WiFiSSID);
        wifiPassTextView.setText(wifiPassTextView.getText() + WiFiPass);
    }

    @OnClick(R.id.activity_main_start_tour)
    public void startTourClick(ImageView v) {
        showSnackBar(MainActivity.this, "Nearable recognition started");

        initializeNearables();

        if (!SystemRequirementsChecker.checkWithDefaultDialogs(this))
            Log.e(TAG, "Can't scan for beacons, some pre-conditions were not met");
        else {
            Log.d(TAG, "Starting ProximityContentManager content updates");
            proximityContentManager.startContentUpdates();
        }
    }

    private void showSnackBar(Activity context, String txt) {
        final ViewGroup viewGroup = (ViewGroup) ((ViewGroup) context
                .findViewById(android.R.id.content)).getChildAt(0);
        final Snackbar snack = Snackbar.make(viewGroup, txt , Snackbar.LENGTH_LONG );
        snack.setAction("Close", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                snack.dismiss();
            }
        });
        View view = snack.getView();
        TextView tv = (TextView) view.findViewById(android.support.design.R.id.snackbar_text);
        tv.setTextColor(android.graphics.Color.parseColor("#FFFFFF"));
        snack.show();
    }

//    @OnClick(R.id.imageViewTeams)
//    public void onTeamsImageClick(ImageView v) {
//        startActivity(new Intent(MainActivity.this, SliderActivity.class));
//    }

    @OnClick(R.id.imageViewPortfolio)
    public void onPortfolioImageClick(ImageView image) {
        Intent intent = new Intent(MainActivity.this, SliderActivity.class);
        intent.putExtra(SliderActivity.TYPE_TAG, SliderActivityType.PORTFOLIO);
        startActivity(intent);
    }

    @OnClick(R.id.imageViewEvents)
    public void onEventsImageClick(ImageView v) {
        Intent intent = new Intent(MainActivity.this, ItemListActivity.class);
        intent.putExtra("title", "Upcoming Events");
        startActivity(intent);
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

    @Override
    protected void onPause() {
        super.onPause();
        wifiStateListener = null;
        if (proximityContentManager != null)
            proximityContentManager.stopContentUpdates();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        proximityContentManager.destroy();
    }

    @Override
    protected void onStop() {
        super.onStop();
        beaconManager.stopNearableDiscovery(baeconScanId);
        //beaconManager.disconnect();
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
        if (list == null || list.size() == 0)
            return -1;

        for( WifiConfiguration i : list ) {
            if(i.SSID != null && i.SSID.equals("\"" + SSID + "\""))
                return i.networkId;
        }

        return -1;
    }

    private void initializeNearables() {
        proximityContentManager = new ProximityContentManager(this,
                Arrays.asList(
                        new BeaconID("B9407F30-F5F8-466E-AFF9-25556B57FE6D", 52730, 32585),
                        new BeaconID("B9407F30-F5F8-466E-AFF9-25556B57FE6D", 39956, 18827),
                        new BeaconID("B9407F30-F5F8-466E-AFF9-25556B57FE6D", 32985, 16771)),
                new EstimoteCloudBeaconDetailsFactory());

        proximityContentManager.setListener(new ProximityContentManager.Listener() {
            @Override
            public void onContentChanged(Object content) {
                String text;
                if (content != null) {
                    EstimoteCloudBeaconDetails beaconDetails = (EstimoteCloudBeaconDetails) content;
                    Color beaconColor = beaconDetails.getBeaconColor();

                    Log.d(TAG, "Nearable discovered: name: " + beaconDetails.getBeaconName() + " color: " + beaconColor.text);

                    showSnackBar(MainActivity.this, "Nearable discovered: name: " + beaconDetails.getBeaconName() + " color: " + beaconColor.text);
                    final ViewGroup viewGroup = (ViewGroup) ((ViewGroup) MainActivity.this
                            .findViewById(android.R.id.content)).getChildAt(0);
                    final Snackbar snack = Snackbar.make(viewGroup, "Nearable discovered: name: " + beaconDetails.getBeaconName() + " color: " + beaconColor.text, Snackbar.LENGTH_LONG);
                    snack.setAction("Close", new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            snack.dismiss();
                        }
                    });
                    View view = snack.getView();
                    TextView tv = (TextView) view.findViewById(android.support.design.R.id.snackbar_text);
                    tv.setTextColor(android.graphics.Color.parseColor("#FFFFFF"));
                    snack.show();
                } else {
                    text = "No beacons in range.";
                    Log.d(TAG, text);
                }
            }
        });
    }

    public static WifiConnStateChangedListener getWifiStateListener() {
        return wifiStateListener;
    }
}
