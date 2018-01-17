package net.yuzumone.recordachi.fragment

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.support.v4.app.Fragment
import android.support.v7.app.AlertDialog

class AddRecordDialogFragment : DialogFragment() {

    private lateinit var listener: OnAddRecordListener
    private val eventId by lazy {
        arguments.getString(ARG_ID)
    }

    companion object {
        private const val ARG_ID = "id"
        fun newInstance(targetFragment: Fragment, eventId: String): AddRecordDialogFragment {
            return AddRecordDialogFragment().apply {
                setTargetFragment(targetFragment, 0)
                arguments = Bundle().apply {
                    putString(ARG_ID, eventId)
                }
            }
        }
    }

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        try {
            listener = targetFragment as OnAddRecordListener
        } catch (e: ClassCastException) {
            throw ClassCastException("Don't implement Listener.")
        }
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        this.isCancelable = false
        return AlertDialog.Builder(activity)
                .setMessage("Add Record now?")
                .setPositiveButton("OK") { _, _ ->
                    listener.onRecordAdd(eventId)
                }
                .setNegativeButton("CANCEL") { _, _ ->
                }
                .create()
    }

    interface OnAddRecordListener {
        fun onRecordAdd(eventId: String)
    }
}