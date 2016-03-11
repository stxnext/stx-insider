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
    val DETECTION_PRECISION = 0.0004;
    var destinationLocation : LatLng? = null;

    val locationManager : LocationManager;
    var listener : OnLocationListener? = null;
    val locationListener = object: LocationListener  {
        override fun onLocationChanged(location: Location?) {
            Log.d(TAG, "On Location changed: " + location)
            if (isLocationDestination(location))
            {
                Log.d(TAG, "This is a destination location.")
                listener?.onLocationDetected()
                stopLookingForLocation()
            } else
                Log.d(TAG, "This is not a destination location.")

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

    fun startLookingForOfficeLocation(listener: OnLocationListener) {
        destinationLocation = OFFICE_LOCATION
        startLookingForLocation(listener)
    }

    fun stopLookingForOfficeLocation() {
        stopLookingForLocation()
    }

    private fun startLookingForLocation(listener : OnLocationListener) {
        Log.d(TAG, "startLookingForLocation")
        this.listener = listener
        locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 60 * 1000, 0f, locationListener)
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 5 * 60 * 1000, 0f, locationListener)
        val lastKnownLocation = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
        locationListener.onLocationChanged(lastKnownLocation)
    }

    private fun stopLookingForLocation() {
        Log.d(TAG, "stopLookingForLocation")
        locationManager.removeUpdates(locationListener)
    }

    interface OnLocationListener {
        fun onLocationDetected()
    }

    private fun isLocationDestination(location: Location?) : Boolean {
        if (location != null && isLocationWithinRange(location))
            return true
        else
            return false
    }

    private fun isLocationWithinRange(location: Location) : Boolean {
        if (destinationLocation != null)
        return (location.latitude > (destinationLocation!!.latitude - DETECTION_PRECISION) && location.latitude < (destinationLocation!!.latitude + DETECTION_PRECISION)
                && location.longitude > (destinationLocation!!.longitude - DETECTION_PRECISION) && location.longitude < (destinationLocation!!.longitude + DETECTION_PRECISION))
        else throw RuntimeException("Destination location not defined!");
    }

}