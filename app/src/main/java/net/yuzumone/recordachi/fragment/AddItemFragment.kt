package net.yuzumone.recordachi.fragment

import android.app.Dialog
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.support.v7.app.AlertDialog
import android.widget.EditText
import net.yuzumone.recordachi.R
import net.yuzumone.recordachi.model.Item
import net.yuzumone.recordachi.viewmodel.ItemViewModel

class AddItemFragment : DialogFragment() {

    private lateinit var editName: EditText
    private val viewModel by lazy(LazyThreadSafetyMode.NONE) {
        ViewModelProviders.of(this).get(ItemViewModel::class.java)
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val v = activity.layoutInflater.inflate(R.layout.fragment_add_item, null)
        editName = v.findViewById(R.id.editName)
        return AlertDialog.Builder(activity)
                .setTitle(getString(R.string.name))
                .setView(v)
                .setPositiveButton("OK", { _, _ ->
                    val name = editName.text.toString()
                    val item = Item(name = name, time = System.currentTimeMillis())
                    viewModel.insert(item)
                })
                .setNegativeButton("CANCEL", { _, _ ->
                    dismiss()
                })
                .create()

    }
}