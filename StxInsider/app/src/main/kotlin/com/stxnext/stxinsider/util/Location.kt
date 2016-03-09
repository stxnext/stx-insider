package com.stxnext.stxinsider.util

import android.content.Context
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.Bundle
import android.util.Log
import com.google.android.gms.maps.model.LatLng

/**
 * Created by Łukasz Ciupa on 08.03.2016.
 */

class Location(context: Context) {

    val OFFICE_LOCATION = LatLng(52.3944957, 16.8936571)

    val locationManager : LocationManager;
    var listener : OnLocationListener? = null;
    val locationListener = object: LocationListener  {
        override fun onLocationChanged(location: Location?) {
            Log.d(TAG, "On Location changed: " + location)
            if (isLocationOffice(location))
            {
                Log.d(TAG, "This is an office location.")
                listener?.onOfficeLocationDetected()
                stopLookingForLocation()
            } else
                Log.d(TAG, "This is not an office location.")

        }
        override fun onStatusChanged(p0: String?, p1: Int, p2: Bundle?) {
            throw UnsupportedOperationException()
        }
        override fun onProviderEnabled(p0: String?) {
            throw UnsupportedOperationException()
        }
        override fun onProviderDisabled(p0: String?) {
            throw UnsupportedOperationException()
        }
    };
    companion object {
        private val TAG = Location::class.java.simpleName
    }

    init {
        locationManager = context.getSystemService(Context.LOCATION_SERVICE) as LocationManager;
    }

    public fun startLookingForLocation(listener : OnLocationListener) {
        Log.d(TAG, "startLookingForLocation")
        this.listener = listener
        locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 60 * 1000, 0f, locationListener)
    }

    public fun stopLookingForLocation() {
        Log.d(TAG, "stopLookingForLocation")
        locationManager.removeUpdates(locationListener)
    }

    public interface OnLocationListener {
        fun onOfficeLocationDetected()
    }

    private fun isLocationOffice(location: Location?) : Boolean {
        if (location != null && location.latitude > (OFFICE_LOCATION.latitude - 0.1) && location.latitude < (OFFICE_LOCATION.latitude + 0.1)
        && location.longitude > (OFFICE_LOCATION.longitude - 0.1) && location.longitude < (OFFICE_LOCATION.longitude + 0.1))
            return true
        else
            return false
    }

}