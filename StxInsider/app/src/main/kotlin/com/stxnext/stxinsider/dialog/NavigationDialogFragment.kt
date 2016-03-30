package com.stxnext.stxinsider.dialog

import android.app.Dialog
import android.app.DialogFragment
import android.app.FragmentManager
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.util.Log
import com.google.android.gms.maps.model.LatLng
import com.stxnext.stxinsider.R

/**
 * Created by Lukasz on 29.03.2016.
 */
class NavigationDialogFragment : DialogFragment() {

    val ARG_MESSAGE : String = "message";
    val ARG_DESTINATION: String = "destination"
    val TAG = "action_dialog"

    fun showDialog(manager: FragmentManager?, message: String?, destination: LatLng?) {
        var bundle : Bundle = Bundle();
        bundle.putString(ARG_MESSAGE, message)
        bundle.putParcelable(ARG_DESTINATION, destination)
        arguments = bundle
        show(manager, TAG)
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog? {

        var arguments : Bundle? = arguments;
        if (arguments == null)
            throw RuntimeException("There are no arguments for dialog")
        val message : String = arguments.getString(ARG_MESSAGE)
        val destination : LatLng = arguments.getParcelable(ARG_DESTINATION)
        Log.d(TAG, "Latitude: " + destination.latitude.toString())
        var builder : AlertDialog.Builder = AlertDialog.Builder(activity)
        builder.setCancelable(true)
        .setTitle(R.string.launch_navigation)
        .setMessage(message)
        .setPositiveButton(getString(R.string.launch), { dialog, which ->
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(
                    "http://maps.google.com/maps?daddr="
                            + destination.latitude + ", " + destination.longitude))
            startActivity(intent)
        })
        .setNegativeButton(getString(android.R.string.cancel).toUpperCase(), {dialog, which -> dismiss()})
        return builder.create()
    }
}
