package net.yuzumone.recordachi.fragment

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.*
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.DatePicker
import android.widget.TimePicker
import android.widget.Toast
import kotlinx.android.synthetic.main.fragment_add_event.*
import net.yuzumone.recordachi.R
import net.yuzumone.recordachi.model.Category
import net.yuzumone.recordachi.model.Event
import net.yuzumone.recordachi.model.Record
import net.yuzumone.recordachi.viewmodel.AddEventViewModel
import java.text.SimpleDateFormat
import java.util.*

class AddEventFragment : Fragment(), DatePickerDialog.OnDateSetListener, TimePickerDialog.OnTimeSetListener {

    private val viewModel by lazy(LazyThreadSafetyMode.NONE) {
        ViewModelProviders.of(this).get(AddEventViewModel::class.java)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_add_event, container, false)
        setHasOptionsMenu(true)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        textDate.text = SimpleDateFormat("yyyy.M.dd", Locale.US).format(Date())
        textTime.text = SimpleDateFormat("HH:mm", Locale.US).format(Date())
        textDate.setOnClickListener {
            val dialog = DatePickerDialogFragment.newInstance(this)
            dialog.show(fragmentManager, "date")
        }
        textTime.setOnClickListener {
            val dialog = TimePickerDialogFragment.newInstance(this)
            dialog.show(fragmentManager, "time")
        }
        imageHeader.setOnClickListener {
            // TODO
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?, inflater: MenuInflater?) {
        inflater?.inflate(R.menu.menu_add_event, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId) {
            R.id.menu_add_event -> {
                register()
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onDateSet(view: DatePicker?, year: Int, month: Int, dayOfMonth: Int) {
        val monthOfYear = month + 1
        textDate.text = getString(R.string.date_format, year, monthOfYear, dayOfMonth)
    }

    override fun onTimeSet(view: TimePicker?, hourOfDay: Int, minute: Int) {
        textTime.text = getString(R.string.time_format, hourOfDay, minute)
    }

    private fun register() {
        val eventName = editEventName.text.toString()
        val categoryName = editCategory.text.toString()
        val time = SimpleDateFormat("yyyy.M.ddHH:mm", Locale.US)
                .parse(textDate.text.toString() + textTime.text.toString()).time
        if (eventName.isEmpty()) {
            Toast.makeText(activity, "Add event name", Toast.LENGTH_SHORT).show()
        } else {
            if (categoryName.isEmpty()) {
                viewModel.noneCategory.observe(this, Observer { category ->
                    val event = Event(name = eventName, categoryId = category!!.id)
                    val record = Record(time = time, eventId = event.id)
                    viewModel.insert(event, record)
                    fragmentManager?.popBackStack()
                })
            } else {
                val category = Category(name = categoryName)
                val event = Event(name = eventName, categoryId = category.id)
                val record = Record(time = time, eventId = event.id)
                viewModel.insert(category, event, record)
                fragmentManager?.popBackStack()
            }
        }
    }

    override fun onCreateAnimation(transit: Int, enter: Boolean, nextAnim: Int): Animation {
        return if (enter) {
            AnimationUtils.loadAnimation(activity, android.R.anim.slide_in_left)
        } else {
            AnimationUtils.loadAnimation(activity, android.R.anim.slide_out_right)
        }
    }
}