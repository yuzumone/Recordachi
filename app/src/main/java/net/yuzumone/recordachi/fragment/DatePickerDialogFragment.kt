package net.yuzumone.recordachi.fragment

import android.app.DatePickerDialog
import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.support.v4.app.Fragment
import java.util.*

class DatePickerDialogFragment : DialogFragment() {

    private lateinit var listener: DatePickerDialog.OnDateSetListener

    companion object {
        fun newInstance(fragment: Fragment) : DatePickerDialogFragment {
            return DatePickerDialogFragment().apply {
                setTargetFragment(fragment, 0)
            }
        }
    }

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        try {
            listener = targetFragment as DatePickerDialog.OnDateSetListener
        } catch (e: ClassCastException) {
            throw ClassCastException("Don't implement Listener.")
        }
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val c = Calendar.getInstance()
        val year = c.get(Calendar.YEAR)
        val month = c.get(Calendar.MONTH) + 1
        val day = c.get(Calendar.DAY_OF_MONTH)
        return DatePickerDialog(activity, listener, year, month, day)
    }
}