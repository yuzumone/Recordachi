package net.yuzumone.recordachi.fragment

import android.Manifest
import android.app.Activity
import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import android.graphics.Bitmap
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
import net.yuzumone.recordachi.extension.getBitmapFromUri
import net.yuzumone.recordachi.model.Category
import net.yuzumone.recordachi.model.Event
import net.yuzumone.recordachi.model.Record
import net.yuzumone.recordachi.viewmodel.AddEventViewModel
import pub.devrel.easypermissions.AfterPermissionGranted
import pub.devrel.easypermissions.EasyPermissions
import java.io.ByteArrayOutputStream
import java.text.SimpleDateFormat
import java.util.*

class AddEventFragment : Fragment(), DatePickerDialog.OnDateSetListener, TimePickerDialog.OnTimeSetListener {

    companion object {
        private const val READ_STORAGE = 1234
        private const val ADD_IMAGE_CODE = 5678
        private const val ARG_HEADER = "header"
    }
    private var header: Bitmap? = null
    private val calender by lazy { Calendar.getInstance() }
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
        val date = Date()
        textDate.text = SimpleDateFormat("yyyy.M.dd", Locale.US).format(date)
        textTime.text = SimpleDateFormat("HH:mm", Locale.US).format(date)
        textDate.setOnClickListener {
            val dialog = DatePickerDialogFragment.newInstance(this)
            dialog.show(fragmentManager, "date")
        }
        textTime.setOnClickListener {
            val dialog = TimePickerDialogFragment.newInstance(this)
            dialog.show(fragmentManager, "time")
        }
        imageHeader.setOnClickListener {
            selectImage()
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
        calender.set(Calendar.YEAR, year)
        calender.set(Calendar.MONTH, month)
        calender.set(Calendar.DAY_OF_MONTH, dayOfMonth)
        val monthOfYear = month + 1
        textDate.text = getString(R.string.date_format, year, monthOfYear, dayOfMonth)
    }

    override fun onTimeSet(view: TimePicker?, hourOfDay: Int, minute: Int) {
        calender.set(Calendar.HOUR_OF_DAY, hourOfDay)
        calender.set(Calendar.MINUTE, minute)
        textTime.text = getString(R.string.time_format, hourOfDay, minute)
    }

    private fun register() {
        val eventName = editEventName.text.toString()
        val categoryName = editCategory.text.toString()
        val time = calender.time.time
        if (eventName.isEmpty()) {
            Toast.makeText(activity, "Add event name", Toast.LENGTH_SHORT).show()
        } else {
            if (categoryName.isEmpty()) {
                viewModel.noneCategory.observe(this, Observer { category ->
                    val event = Event(name = eventName, categoryId = category!!.id)
                    val record = Record(time = time, eventId = event.id)
                    if (header != null) {
                        val stream = ByteArrayOutputStream()
                        header!!.compress(Bitmap.CompressFormat.PNG, 0, stream)
                        event.image = stream.toByteArray()
                    }
                    viewModel.insert(event, record)
                    fragmentManager?.popBackStack()
                })
            } else {
                val category = Category(name = categoryName)
                val event = Event(name = eventName, categoryId = category.id)
                val record = Record(time = time, eventId = event.id)
                if (header != null) {
                    val stream = ByteArrayOutputStream()
                    header!!.compress(Bitmap.CompressFormat.PNG, 0, stream)
                    event.image = stream.toByteArray()
                }
                viewModel.insert(category, event, record)
                fragmentManager?.popBackStack()
            }
        }
    }

    override fun onCreateAnimation(transit: Int, enter: Boolean, nextAnim: Int): Animation {
        return if (enter) {
            AnimationUtils.loadAnimation(activity, R.anim.slide_in_top)
        } else {
            AnimationUtils.loadAnimation(activity, R.anim.slide_out_bottom)
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>,
                                            grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this)
    }

    @AfterPermissionGranted(READ_STORAGE)
    private fun selectImage() {
        if (EasyPermissions.hasPermissions(activity!!, Manifest.permission.READ_EXTERNAL_STORAGE)) {
            val intent = Intent(Intent.ACTION_GET_CONTENT)
            intent.addCategory(Intent.CATEGORY_OPENABLE)
            intent.type = "image/*"
            startActivityForResult(intent, ADD_IMAGE_CODE)
        } else {
            EasyPermissions.requestPermissions(this, "STORAGE",
                    READ_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == ADD_IMAGE_CODE && resultCode == Activity.RESULT_OK) {
            if (data == null) return
            val uri = data.data
            header = getBitmapFromUri(activity!!, uri)
            imageHeader.setImageBitmap(header)
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        if (header != null) outState.putParcelable(ARG_HEADER, header)
        super.onSaveInstanceState(outState)
    }

    override fun onViewStateRestored(savedInstanceState: Bundle?) {
        super.onViewStateRestored(savedInstanceState)
        if (savedInstanceState != null && !savedInstanceState.isEmpty) {
            header = savedInstanceState.getParcelable(ARG_HEADER)
            imageHeader.setImageBitmap(header)
        }
    }
}