package com.stxnext.stxinsider.dialog

import android.Manifest
import android.app.Dialog
import android.app.DialogFragment
import android.app.FragmentManager
import android.content.DialogInterface
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Color
import android.net.Uri
import android.os.Bundle
import android.provider.Settings
import android.support.v4.app.ActivityCompat
import android.support.v4.content.ContextCompat
import android.support.v7.app.AlertDialog
import com.stxnext.stxinsider.MainActivity
import com.stxnext.stxinsider.R

/**
 * Created by Lukasz on 14.04.2016.
 */

class LocationDialogFragment : DialogFragment() {

    val ARG_TITLE: String = "title"
    val ARG_MESSAGE : String = "message";
    val ARG_PHONE_NUMBER: String = "destination"
    val TAG = "action_dialog"

    fun showDialog(manager: FragmentManager?, title: String?, message: String?) {
        var bundle : Bundle = Bundle();
        bundle.putString(ARG_TITLE, title)
        bundle.putString(ARG_MESSAGE, message)
        arguments = bundle
        show(manager, TAG)
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog? {

        var arguments : Bundle? = arguments;
        if (arguments == null)
            throw RuntimeException("There are no arguments for a dialog")
        val message : String = arguments.getString(ARG_MESSAGE)
        val title : String = arguments.getString(ARG_TITLE)
        var builder : AlertDialog.Builder = AlertDialog.Builder(activity)
        builder.setCancelable(true)
                .setTitle(title)
                .setMessage(message)
                .setPositiveButton(getString(R.string.show_settings), { dialog, which ->
                    val intent = Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                    activity.startActivityForResult(intent, MainActivity.REQUEST_ENABLE_LOCATION);
                    dismiss()
                })
                .setNegativeButton(getString(android.R.string.cancel).toUpperCase(), {dialog, which -> dismiss()})
        val dialog = builder.create()
        dialog.setOnShowListener({dialogInterface : DialogInterface ->
            val dialog = dialogInterface as AlertDialog
            val positiveButton = dialog.getButton(DialogInterface.BUTTON_POSITIVE)
            positiveButton.setTextColor(ContextCompat.getColor(activity, R.color.colorAccent))
            val negativeButton = dialog.getButton(DialogInterface.BUTTON_NEGATIVE)
            negativeButton.setTextColor(Color.BLACK)
            positiveButton.invalidate()
            negativeButton.invalidate()
        })
        return dialog
    }

    override fun onPause() {
        super.onPause()
        dismiss()
    }
}

