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

    val OFFICE_LOCATION = LatLng(52.3946831, 16.8940677)
    val DETECTION_PRECISION = 0.001;

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

    fun startLookingForLocation(listener : OnLocationListener) {
        Log.d(TAG, "startLookingForLocation")
        this.listener = listener
        locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 60 * 1000, 0f, locationListener)
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 5 * 60 * 1000, 0f, locationListener)
        val lastKnownLocation = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
        locationListener.onLocationChanged(lastKnownLocation)
    }

    fun stopLookingForLocation() {
        Log.d(TAG, "stopLookingForLocation")
        locationManager.removeUpdates(locationListener)
    }

    interface OnLocationListener {
        fun onOfficeLocationDetected()
    }

    private fun isLocationOffice(location: Location?) : Boolean {
        if (location != null && isLocationWithinRange(location))
            return true
        else
            return false
    }

    private fun isLocationWithinRange(location: Location) : Boolean {
        return (location.latitude > (OFFICE_LOCATION.latitude - DETECTION_PRECISION) && location.latitude < (OFFICE_LOCATION.latitude + DETECTION_PRECISION)
                && location.longitude > (OFFICE_LOCATION.longitude - DETECTION_PRECISION) && location.longitude < (OFFICE_LOCATION.longitude + DETECTION_PRECISION))
    }

}