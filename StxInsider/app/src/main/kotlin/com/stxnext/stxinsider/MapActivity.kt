package com.stxnext.stxinsider

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.net.wifi.WifiConfiguration
import android.net.wifi.WifiManager
import android.os.Bundle
import android.support.design.widget.FloatingActionButton
import android.support.v4.app.ActivityCompat
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.view.MenuItem
import android.view.View
import android.view.Window
import android.widget.LinearLayout
import android.widget.ProgressBar
import android.widget.TextView
import butterknife.bindView
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.stxnext.stxinsider.receiver.WifiConnStateChangedListener
import com.stxnext.stxinsider.util.getNetworkId
import org.joda.time.DateTime
import org.joda.time.Duration

class MapActivity : AppCompatActivity() {

    val OFFICE_LOCATION = LatLng(52.3946831, 16.8940677)
    val WiFiSSID = "StxXXXXXXX"
    val WiFiPass = "xxxxxxxxx"

    internal val taxiPhoneNoTextView: TextView  by bindView(R.id.activity_main_taxi_phone_no_tv)
    internal val wifiSSIDTextView: TextView by bindView(R.id.activity_main_wifi_ssid_tv)
    internal val wifiPassTextView: TextView by bindView(R.id.activity_main_wifi_pass_tv)
    internal val wifiProgressBar: ProgressBar  by bindView(R.id.activity_main_wifi_connection_progressbar)
    internal val mainWifiOutLayout: LinearLayout  by bindView(R.id.activity_main_wifi_outer_layout)
    internal val address : View by bindView(R.id.address);
    internal val mToolbar: Toolbar by bindView(R.id.toolbar)
    internal val taxiFab: FloatingActionButton by bindView(R.id.fab)

    private val PERMISSIONS_REQUEST_CALL_PHONE: Int = 1;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_map)
        setSupportActionBar(mToolbar)

        supportActionBar!!.title = "Location"
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)

        wifiSSIDTextView.text = wifiSSIDTextView.text.toString() + WiFiSSID
        wifiPassTextView.text = wifiPassTextView.text.toString() + WiFiPass

        prepareMap()
        taxiPhoneNoTextView.setOnClickListener { v:View -> callTaxiClick() }
        mainWifiOutLayout.setOnClickListener { v:View -> connectToWifi() }
        address.setOnClickListener { v:View -> navigate() }
        taxiFab.setOnClickListener { v:View -> callTaxiClick()}
    }

    private fun navigate() {
        val intent = Intent(android.content.Intent.ACTION_VIEW, Uri.parse(
                "http://maps.google.com/maps?daddr="
                        + OFFICE_LOCATION.latitude + ", " + OFFICE_LOCATION.longitude))
        startActivity(intent)
    }

    internal var wifiInitStarted: DateTime? = null
    private fun wifiConnectionStateChanged(enabled: Boolean) {

        if (wifiInitStarted != null && enabled == true) {
            val diffInMillis = DateTime.now().millis - wifiInitStarted!!.millis
            val seconds = Duration.millis(diffInMillis).standardSeconds
            if (seconds > 0) {
                wifiInitStarted = null
                //Toast.makeText(this, "MainActivity: Wifi connected: " + ssid, Toast.LENGTH_SHORT).show();
                wifiProgressBar.visibility = View.GONE
            }
        }
    }

    fun callTaxiClick() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED)
            ActivityCompat.requestPermissions(this,
                    arrayOf(Manifest.permission.CALL_PHONE),
                    PERMISSIONS_REQUEST_CALL_PHONE);
        else {
            makePhoneCall(taxiPhoneNoTextView.text.toString())
        }
    }

    private fun makePhoneCall(phoneNumber: String) {
        val intent = Intent(Intent.ACTION_CALL)
        intent.data = Uri.parse("tel:" + phoneNumber)
        startActivity(intent)
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        when (requestCode) {
            PERMISSIONS_REQUEST_CALL_PHONE -> {
                if (grantResults.size > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    makePhoneCall(taxiPhoneNoTextView.text.toString())
                }
            }
        }

    }

    fun connectToWifi() {
        wifiInitStarted = DateTime.now()
        wifiProgressBar.visibility = View.VISIBLE

        val conf = WifiConfiguration()
        conf.SSID = "\"" + WiFiSSID + "\""
        conf.preSharedKey = "\"" + WiFiPass + "\""

        val wifiManager = this.getSystemService(Context.WIFI_SERVICE) as WifiManager
        if (!wifiManager.isWifiEnabled)
            wifiManager.isWifiEnabled = true

        var netId = wifiManager.getNetworkId(wifiManager, WiFiSSID)
        if (netId != -1)
            wifiManager.removeNetwork(netId)
        wifiManager.addNetwork(conf)

        netId = wifiManager.getNetworkId(wifiManager, WiFiSSID)
        wifiManager.disconnect()
        wifiManager.enableNetwork(netId, true)
        wifiManager.reconnect()
    }

    override fun onResume() {
        super.onResume()
        wifiStateListener = object : WifiConnStateChangedListener {
            override fun stateChanged(ssid: String, enabled: Boolean) {
                wifiConnectionStateChanged(enabled)
            }
        }
    }

    override fun onPause() {
        super.onPause()
        wifiStateListener = null
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item.itemId
        if (id == android.R.id.home)
            finish()

        return super.onOptionsItemSelected(item)
    }

    private fun prepareMap() {
        val fm = supportFragmentManager
        val fragment = fm.findFragmentById(R.id.map_fragment) as SupportMapFragment
        fragment.getMapAsync { map: GoogleMap ->
            val options = MarkerOptions()
            options.position(OFFICE_LOCATION)
            map.addMarker(options)

            map.mapType = GoogleMap.MAP_TYPE_TERRAIN

            animateMap(map)
        }
    }

    private fun animateMap(map: GoogleMap) {
        val cameraPosition = LatLng(OFFICE_LOCATION.latitude + 0.001, OFFICE_LOCATION.longitude)
        map.animateCamera(CameraUpdateFactory.newLatLngZoom(cameraPosition, 14f), 2000, null)
    }

    companion object WifiStateListener {
        var wifiStateListener: WifiConnStateChangedListener? = null
            private set
    }
}
