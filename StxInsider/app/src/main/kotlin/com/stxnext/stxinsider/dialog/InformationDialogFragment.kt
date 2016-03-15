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

    fun showDialog(manager: FragmentManager?, message: String?) {
        super.show(manager, tag)
        var dialogFragment : InformationDialogFragment = InformationDialogFragment();

        var bundle : Bundle = Bundle();
        bundle.putSerializable(ARG_MESSAGE, message)
        dialogFragment.arguments = bundle
        dialogFragment.show(fragmentManager, "information_dialog")
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog? {
        var arguments : Bundle? = arguments;
        if (arguments == null)
            throw RuntimeException("There are no arguments for dialog")
        val message : String = arguments.getString(ARG_MESSAGE)
        var builder : AlertDialog.Builder = AlertDialog.Builder(activity)
        builder.setCancelable(true)
        builder.setMessage(message)
        builder.setNeutralButton(android.R.string.ok, {dialog, which -> dismiss()})
        return builder.create()
    }

    companion object {
        val ARG_MESSAGE : String = "message";
    }
}