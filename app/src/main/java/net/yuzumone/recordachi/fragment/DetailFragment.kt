package net.yuzumone.recordachi.fragment

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.*
import android.widget.Toast
import kotlinx.android.synthetic.main.fragment_detail.*
import net.yuzumone.recordachi.R
import net.yuzumone.recordachi.view.RecordAdapter
import net.yuzumone.recordachi.viewmodel.DetailViewModel
import java.text.DateFormat
import java.util.*

class DetailFragment : Fragment() {

    private val eventId by lazy {
        arguments.getString(ARG_ID)
    }
    private val viewModel by lazy(LazyThreadSafetyMode.NONE) {
        ViewModelProviders.of(this).get(DetailViewModel::class.java)
    }

    companion object {
        private const val ARG_ID = "id"
        fun newInstance(id: String): DetailFragment {
            return DetailFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_ID, id)
                }
            }
        }
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view =  inflater?.inflate(R.layout.fragment_detail, container, false)
        setHasOptionsMenu(true)
        activity.title = ""
        return view
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.event(eventId).observe(this, Observer {
            it?.let { event ->
                activity.title = event.name
            }
        })
        val adapter = RecordAdapter()
        recycler.adapter = adapter
        recycler.layoutManager = LinearLayoutManager(activity)
        viewModel.records(eventId).observe(this, Observer {
            adapter.update(it)
            it?.let {
                val last = DateFormat.getDateTimeInstance().format(Date(it.last().time))
                textLast.text = getString(R.string.last_format, last)
                textCount.text = getString(R.string.count_format, it.count())
                val ave = (0 until it.count() - 1)
                        .map { i -> (it[i + 1].time - it[i].time) / (1000 * 60 * 60 * 24) }
                        .average().toInt()
                textAverage.text = getString(R.string.average_format, ave)
            }
        })
    }

    override fun onCreateOptionsMenu(menu: Menu?, inflater: MenuInflater?) {
        inflater?.inflate(R.menu.menu_detail, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId) {
            R.id.menu_delete_event -> {
                viewModel.deleteEvent(eventId)
                Toast.makeText(activity, "Delete event", Toast.LENGTH_SHORT).show()
                fragmentManager.popBackStack()
            }
        }
        return super.onOptionsItemSelected(item)
    }
}