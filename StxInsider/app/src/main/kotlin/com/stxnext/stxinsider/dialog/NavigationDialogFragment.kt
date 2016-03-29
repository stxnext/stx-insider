package com.stxnext.stxinsider.dialog

import android.app.Dialog
import android.app.DialogFragment
import android.app.FragmentManager
import android.os.Bundle
import android.support.v7.app.AlertDialog
import com.stxnext.stxinsider.R

/**
 * Created by Lukasz on 29.03.2016.
 */
class NavigationDialogFragment : DialogFragment() {

    val ARG_MESSAGE : String = "message";
    val ARG_DESTINATION: String = "actionName"
    val TAG = "action_dialog"

    fun showDialog(manager: FragmentManager?, message: String?, destination: String?) {
        var bundle : Bundle = Bundle();
        bundle.putString(ARG_MESSAGE, message)
        bundle.putString(ARG_DESTINATION, destination)
        arguments = bundle
        show(manager, TAG)
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog? {

        var arguments : Bundle? = arguments;
        if (arguments == null)
            throw RuntimeException("There are no arguments for dialog")
        val message : String = arguments.getString(ARG_MESSAGE)
        val destination : String = arguments.getString(ARG_DESTINATION)
        var builder : AlertDialog.Builder = AlertDialog.Builder(activity)
        builder.setCancelable(true)
        builder.setMessage(message)
        builder.setPositiveButton(getString(R.string.launch), { dialog, which -> dismiss()})
        .setNeutralButton(getString(android.R.string.cancel).toUpperCase(), {dialog, which -> dismiss()})
        return builder.create()
    }
}
