package com.stxnext.stxinsider.dialog

import android.app.Dialog
import android.app.DialogFragment
import android.app.FragmentManager
import android.os.Bundle
import android.support.v7.app.AlertDialog

/**
 * Created by Lukasz on 15.03.2016.
 */
class InformationDialogFragment : DialogFragment() {

    val ARG_MESSAGE : String = "message";
    val TAG = "information_dialog"

    fun showDialog(manager: FragmentManager?, message: String?) {
        var bundle : Bundle = Bundle();
        bundle.putString(ARG_MESSAGE, message)
        arguments = bundle
        show(manager, TAG)
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog? {
        var arguments : Bundle? = arguments;
        if (arguments == null)
            throw RuntimeException("There are no arguments for dialog")
        val message : String = arguments.getString(ARG_MESSAGE)
        var builder : AlertDialog.Builder = AlertDialog.Builder(activity)
        builder.setCancelable(true)
        builder.setMessage(message)
        builder.setPositiveButton(android.R.string.ok, {dialog, which -> dismiss()})
        return builder.create()
    }
}

/**
 *  Shows DialogFragment
 *
 *  fragmentManager showDialog "comment-body"
 */
infix fun FragmentManager.showDialog(txt: String) {
    InformationDialogFragment().showDialog(this, txt)
}