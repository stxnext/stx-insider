package com.stxnext.stxinsider;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.estimote.sdk.BeaconManager;
import com.estimote.sdk.SystemRequirementsChecker;
import com.estimote.sdk.cloud.model.Color;
import com.stxnext.stxinsider.estimote.BeaconID;
import com.stxnext.stxinsider.estimote.EstimoteCloudBeaconDetails;
import com.stxnext.stxinsider.estimote.EstimoteCloudBeaconDetailsFactory;
import com.stxnext.stxinsider.estimote.ProximityContentManager;
import com.stxnext.stxinsider.model.SliderActivityType;
import com.stxnext.stxinsider.receiver.WifiConnStateChangedListener;
import com.stxnext.stxinsider.util.ActivityUtil;

import org.joda.time.DateTime;
import org.joda.time.Duration;

import java.util.Arrays;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {

    private final static String TAG = MainActivity.class.getSimpleName();

    @Bind(R.id.activity_main_version_textview) TextView versionTextView;

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
        versionTextView.setText(ActivityUtil.getAppVersion(MainActivity.this, MainActivity.this));
    }

    @OnClick(R.id.activity_main_start_tour)
    public void startTourClick(ImageView v) {
        showSnackBar(MainActivity.this, "Nearable recognition started", 20);

        initializeNearables();

        if (!SystemRequirementsChecker.checkWithDefaultDialogs(this))
            Log.e(TAG, "Can't scan for beacons, some pre-conditions were not met");
        else {
            Log.d(TAG, "Starting ProximityContentManager content updates");
            proximityContentManager.startContentUpdates();
        }
    }

    private void showSnackBar(Activity context, String txt, int textSizeInSp) {
        final ViewGroup viewGroup = (ViewGroup) ((ViewGroup) context
                .findViewById(android.R.id.content)).getChildAt(0);
        final Snackbar snack = Snackbar.make(viewGroup, txt , Snackbar.LENGTH_LONG );
        snack.setAction("Close", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                snack.dismiss();
            }
        });
        snack.setActionTextColor(android.graphics.Color.parseColor("#FFFFFF"));
        View view = snack.getView();
        TextView tv = (TextView) view.findViewById(android.support.design.R.id.snackbar_text);
        tv.setTextColor(android.graphics.Color.parseColor("#FFFFFF"));
        tv.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20);
        snack.show();
    }

    @OnClick(R.id.imageViewTeams)
    public void onTeamsImageClick(ImageView v) {
        Intent intent = new Intent(MainActivity.this, SliderActivity.class);
        intent.putExtra(SliderActivity.TYPE_TAG, SliderActivityType.TEAM);
        startActivity(intent);
    }

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

    @OnClick(R.id.imageCompanyLocation)
    public void onCompanyLocationClick(ImageView v) {
        startActivity(new Intent(getApplicationContext(), MapActivity.class));
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (proximityContentManager != null)
            proximityContentManager.stopContentUpdates();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (proximityContentManager != null)
            proximityContentManager.destroy();
    }

    @Override
    protected void onStop() {
        super.onStop();
        beaconManager.stopNearableDiscovery(baeconScanId);
        //beaconManager.disconnect();
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
                    String beaconName = beaconDetails.getBeaconName();

                    Log.d(TAG, "Nearable discovered, name: " + beaconDetails.getBeaconName() + " color: " + beaconColor.text);

                    String prefix = "Welcome to ";
                    String addition = "";
                    if (beaconName.contains("mint"))
                        addition = "our automated tests display";
                    else if (beaconName.contains("ice"))
                        addition = "our Augmented Reality App stand";
                    else if (beaconName.contains("stxblueberry"))
                        addition = "our StxInsider App stand";
                    showSnackBar(MainActivity.this, prefix + addition, 18);
                } else {
                    text = "No beacons in range.";
                    Log.d(TAG, text);
                }
            }
        });
    }
}
