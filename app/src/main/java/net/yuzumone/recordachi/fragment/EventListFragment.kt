package net.yuzumone.recordachi.fragment

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.fragment_event_list.*
import net.yuzumone.recordachi.R
import net.yuzumone.recordachi.model.EventCategoryRecords
import net.yuzumone.recordachi.model.Record
import net.yuzumone.recordachi.view.EventAdapter
import net.yuzumone.recordachi.view.EventAdapter.CheckClickCallback
import net.yuzumone.recordachi.view.ItemClickCallback
import net.yuzumone.recordachi.viewmodel.EventListViewModel

class EventListFragment : Fragment(), AddRecordDialogFragment.OnAddRecordListener {

    private val viewModel by lazy(LazyThreadSafetyMode.NONE) {
        ViewModelProviders.of(this).get(EventListViewModel::class.java)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_event_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        buttonAdd.setOnClickListener {
            val fragment = AddEventFragment()
            fragmentManager!!.beginTransaction().replace(R.id.container, fragment)
                    .addToBackStack(null).commit()
        }
        val adapter = EventAdapter(getCheckClickCallback())
        adapter.setItemClickCallback(object : ItemClickCallback {
            override fun onItemClick(view: View, position: Int) {
                val event = adapter.getItem(position)
                val fragment = DetailFragment.newInstance(event.id)
                fragmentManager!!.beginTransaction().replace(R.id.container, fragment)
                        .addToBackStack(null).commit()
            }
        })
        recycler.adapter = adapter
        recycler.layoutManager = LinearLayoutManager(activity)
        viewModel.allEventCategoryRecords.observe(this, Observer {
            adapter.update(it)
        })
    }

    private fun getCheckClickCallback(): CheckClickCallback {
        return object : EventAdapter.CheckClickCallback {
            override fun onCheck(event: EventCategoryRecords) {
                val dialog = AddRecordDialogFragment
                        .newInstance(this@EventListFragment, event.id)
                dialog.show(fragmentManager, "add_record")
            }
        }
    }

    override fun onRecordAdd(eventId: String) {
        val record = Record(time = System.currentTimeMillis(), eventId = eventId)
        viewModel.insertRecord(record)
    }

    override fun onResume() {
        super.onResume()
        activity?.title = getString(R.string.app_name)
    }
}