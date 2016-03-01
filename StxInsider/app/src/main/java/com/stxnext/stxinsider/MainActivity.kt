package com.stxnext.stxinsider

import android.app.Activity
import android.content.Intent
import android.location.LocationManager
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.util.TypedValue
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.estimote.sdk.BeaconManager
import com.estimote.sdk.SystemRequirementsChecker
import com.stxnext.stxinsider.estimote.BeaconID
import com.stxnext.stxinsider.estimote.EstimoteCloudBeaconDetails
import com.stxnext.stxinsider.estimote.EstimoteCloudBeaconDetailsFactory
import com.stxnext.stxinsider.estimote.ProximityContentManager
import com.stxnext.stxinsider.model.SliderActivityType
import com.stxnext.stxinsider.util.*
import butterknife.bindView
import com.stxnext.stxinsider.inject.rest.InsiderApiService
import com.stxnext.stxinsider.model.SliderItem
import retrofit.Response
import retrofit.Retrofit
import java.util.*
import javax.inject.Inject
import javax.inject.Named

class MainActivity : AppCompatActivity() {

    val versionTextView: TextView by bindView(R.id.activity_main_version_textview)

    @Inject lateinit var mInsiderApiService: InsiderApiService

    private var beaconManager: BeaconManager? = null
    private val baeconScanId: String? = null

    private var proximityContentManager: ProximityContentManager? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        InsiderApp.component.inject(this)

        beaconManager = BeaconManager(this)

        supportActionBar!!.hide()

        versionTextView.text = getAppVersion(this@MainActivity)
        bindOnClicks()

        mInsiderApiService.getTeamsAsync({ list ->
            list.forEach {  item -> print(item.description)
        } }, { } )
    }

    private fun bindOnClicks() {
        findViewById(R.id.activity_main_start_tour).setOnClickListener { v: View -> startTourClick(v) }
        findViewById(R.id.imageViewTeams).setOnClickListener { v: View -> onTeamsImageClick(v) }
        findViewById(R.id.imageViewPortfolio).setOnClickListener { v: View -> onPortfolioImageClick(v) }
        findViewById(R.id.imageViewEvents).setOnClickListener { v: View -> onEventsImageClick(v) }
        findViewById(R.id.imageCompanyLocation).setOnClickListener { v: View -> onCompanyLocationClick(v) }
    }

    fun startTourClick(v: View) {
        showSnackBar(this@MainActivity, "Nearable recognition started", 20)

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
        val intent = Intent(this@MainActivity, SliderActivity::class.java)
        intent.putExtra(SliderActivity.TYPE_TAG, SliderActivityType.TEAM)
        startActivity(intent)
    }

    fun onPortfolioImageClick(image: View) {
        val intent = Intent(this@MainActivity, SliderActivity::class.java)
        intent.putExtra(SliderActivity.TYPE_TAG, SliderActivityType.PORTFOLIO)
        startActivity(intent)
    }

    fun onEventsImageClick(v: View) {
        val intent = Intent(this@MainActivity, ItemListActivity::class.java)
        intent.putExtra("title", "Upcoming Events")
        startActivity(intent)
    }

    fun onCompanyLocationClick(v: View) {
        startActivity(Intent(applicationContext, MapActivity::class.java))
    }

    override fun onPause() {
        super.onPause()
        if (proximityContentManager != null)
            proximityContentManager!!.stopContentUpdates()
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
            } else {
                text = "No beacons in range."
                Log.d(TAG, text)
            }
        }
    }

    companion object {
        private val TAG = MainActivity::class.java.simpleName
    }
}
