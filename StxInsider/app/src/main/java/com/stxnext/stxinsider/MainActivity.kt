package com.stxnext.stxinsider

import android.Manifest
import android.animation.LayoutTransition
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v4.app.ActivityCompat
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.util.TypedValue
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import butterknife.bindView
import com.estimote.sdk.BeaconManager
import com.estimote.sdk.SystemRequirementsChecker
import com.stxnext.stxinsider.estimote.BeaconID
import com.stxnext.stxinsider.estimote.EstimoteCloudBeaconDetails
import com.stxnext.stxinsider.estimote.EstimoteCloudBeaconDetailsFactory
import com.stxnext.stxinsider.estimote.ProximityContentManager
import com.stxnext.stxinsider.inject.rest.InsiderApiService
import com.stxnext.stxinsider.model.SliderActivityType
import com.stxnext.stxinsider.util.*
import java.util.*
import javax.inject.Inject

class MainActivity : AppCompatActivity(), ActivityCompat.OnRequestPermissionsResultCallback {

    val versionTextView: TextView by bindView(R.id.activity_main_version_textview)
    val teams: View? by bindView(R.id.teams)

    init {
        R.id.imageNews onClick KOnClick(this, { v: View -> onNewsClick(v) })
        R.id.activity_main_start_tour onClick KOnClick(this, { v: View -> startTourClick(v) })
        R.id.imageViewTeams onClick KOnClick(this, { v: View -> onTeamsImageClick(v) })
        R.id.imageViewPortfolio onClick KOnClick(this, { v: View -> onPortfolioImageClick(v) })
        R.id.imageViewEvents onClick KOnClick(this, { v: View -> onEventsImageClick(v) })
        R.id.imageCompanyLocation onClick KOnClick(this, { v: View -> onCompanyLocationClick(v) })
    }

    @Inject lateinit var mInsiderApiService: InsiderApiService

    private var beaconManager: BeaconManager? = null
    private val baeconScanId: String? = null

    private var proximityContentManager: ProximityContentManager? = null

    private var location: Location? = null
    private val PERMISSIONS_REQUEST_FINE_LOCATION: Int = 1;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setTransitionAnimationsForElementsLayout()
        bindKViews()

        InsiderApp.component.inject(this)

        beaconManager = BeaconManager(this)

        supportActionBar!!.hide()

        versionTextView.text = getAppVersion(this@MainActivity)

        mInsiderApiService.getTeamsAsync({ list ->
            list.forEach { item ->
                print(item.description)
            }
        }, { })

        if (!(this hasPermission Manifest.permission.ACCESS_FINE_LOCATION))
                ActivityCompat.requestPermissions(this,
                        arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                        PERMISSIONS_REQUEST_FINE_LOCATION);
    }

    fun startLocalizationCheck() {
        if (location == null)
            location = Location(this)
        location!!.startLookingForOfficeLocation(object : Location.OnLocationListener {
            override fun onLocationDetected() {
                Log.d(TAG, "Office location detected.")
                activateTeams()
                location = null
            }
        })
    }

    fun onNewsClick(v: View) {
        val intent = Intent(this, NewsActivity::class.java)
        startActivity(intent)
    }

    fun startTourClick(v: View) {
        showSnackBar(this, "Nearable recognition started", 20)

        initializeNearables()

        if (!SystemRequirementsChecker.checkWithDefaultDialogs(this))
            Log.e(TAG, "Can't scan for beacons, some pre-conditions were not met")
        else {
            Log.d(TAG, "Starting ProximityContentManager content updates")
            proximityContentManager!!.startContentUpdates()
        }
    }

    private fun showSnackBar(context: Activity, txt: String, textSizeInSp: Int) {
        val viewGroup = (context.findViewById(android.R.id.content) as ViewGroup).getChildAt(0) as ViewGroup
        val snack = Snackbar.make(viewGroup, txt, Snackbar.LENGTH_LONG)
        snack.setAction("Close") { snack.dismiss() }
        snack.setActionTextColor(android.graphics.Color.parseColor("#FFFFFF"))
        val view = snack.view
        val tv = view.findViewById(android.support.design.R.id.snackbar_text) as TextView
        tv.setTextColor(android.graphics.Color.parseColor("#FFFFFF"))
        tv.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20f)
        snack.show()
    }

    fun onTeamsImageClick(v: View) {
        val intent = Intent(this, SliderActivity::class.java)
        intent.putExtra(SliderActivity.TYPE_TAG, SliderActivityType.TEAM)
        startActivity(intent)
    }

    fun onPortfolioImageClick(image: View) {
        val intent = Intent(this, SliderActivity::class.java)
        intent.putExtra(SliderActivity.TYPE_TAG, SliderActivityType.PORTFOLIO)
        startActivity(intent)
    }

    fun onEventsImageClick(v: View) {
        val intent = Intent(this, ItemListActivity::class.java)
        intent.putExtra("title", "Upcoming Events")
        startActivity(intent)
    }

    fun onCompanyLocationClick(v: View) {
        startActivity(Intent(applicationContext, MapActivity::class.java))
    }

    override fun onResume() {
        super.onResume()
        if (teams?.visibility != View.VISIBLE) {
            if (this hasPermission Manifest.permission.ACCESS_FINE_LOCATION)
                startLocalizationCheck()
        }
    }

    override fun onPause() {
        super.onPause()
        if (proximityContentManager != null)
            proximityContentManager!!.stopContentUpdates()
        location?.stopLookingForOfficeLocation()
    }

    override fun onDestroy() {
        super.onDestroy()
        if (proximityContentManager != null)
            proximityContentManager!!.destroy()
    }

    override fun onStop() {
        super.onStop()
        beaconManager!!.stopNearableDiscovery(baeconScanId)
        //beaconManager.disconnect();
    }

    /**
     *  # Beacon SDK initialization
     *
     *  This method registers 3 beacons with IDs taken from Estimote Cloud.
     *  Invoke this method in [onCreate].
     *
     *  ## Showcase demo
     *
     *  Steps:
     *  * Grant application bluetooth, GPS and WiFi permissions
     *  * Wait for about 1 minute (beacons broadcast in 0.1 ~ 2.0 sec intervals)
     *  * Snackbar should appear on the app's main activity
     *
     */
    private fun initializeNearables() {
        proximityContentManager = ProximityContentManager(this,
                Arrays.asList(
                        BeaconID("B9407F30-F5F8-466E-AFF9-25556B57FE6D", 52730, 32585),
                        BeaconID("B9407F30-F5F8-466E-AFF9-25556B57FE6D", 39956, 18827),
                        BeaconID("B9407F30-F5F8-466E-AFF9-25556B57FE6D", 32985, 16771)),
                EstimoteCloudBeaconDetailsFactory())

        proximityContentManager!!.setListener { content ->
            val text: String
            if (content != null) {
                val beaconDetails = content as EstimoteCloudBeaconDetails?
                val beaconColor = beaconDetails!!.beaconColor
                val beaconName = beaconDetails!!.beaconName

                Log.d(TAG, "Nearable discovered, name: " + beaconDetails.getBeaconName() + " color: " + beaconColor.text)

                val prefix = "Welcome to "
                var addition = ""
                if (beaconName.contains("mint"))
                    addition = "our automated tests display"
                else if (beaconName.contains("ice"))
                    addition = "our Augmented Reality App stand"
                else if (beaconName.contains("stxblueberry"))
                    addition = "our StxInsider App stand"
                showSnackBar(this@MainActivity, prefix + addition, 18)
                activateTeams()
            } else {
                text = "No beacons in range."
                Log.d(TAG, text)
            }
        }
    }

    /**
     * Teams are activated only when beacons are detected (when user is inside our building).
     */
    private fun activateTeams() {
        teams?.visibility = View.VISIBLE
    }

    /**
     * Sets animations when there are changes inside layout.
     */
    private fun setTransitionAnimationsForElementsLayout() {
        val elementsLayout = findViewById(R.id.elements_layout) as LinearLayout
        val layoutTransition = LayoutTransition()
        // There is a need to disable animation when view disappears because it is badly implemented.
        layoutTransition.disableTransitionType(LayoutTransition.CHANGE_DISAPPEARING)
        elementsLayout.layoutTransition = layoutTransition
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        when (requestCode) {
            PERMISSIONS_REQUEST_FINE_LOCATION -> {
                if (grantResults.size > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED)
                    startLocalizationCheck()
            }
        }

    }

    companion object {
        private val TAG = MainActivity::class.java.simpleName
    }
}

