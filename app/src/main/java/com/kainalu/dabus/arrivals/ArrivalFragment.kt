package com.kainalu.dabus.arrivals

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.kainalu.dabus.Arrival
import com.kainalu.dabus.R
import com.kainalu.dabus.RecyclerViewFragment
import com.kainalu.dabus.StopViewModel
import kotlinx.android.synthetic.main.fragment_list.*

class ArrivalFragment : RecyclerViewFragment(), ArrivalAdapter.OnListFragmentInteractionListener {

    private lateinit var stopViewModel: StopViewModel
    private lateinit var stopId: String
    private val adapter = ArrivalAdapter(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (arguments != null) {
            stopId = arguments!!.getString(ARG_STOP_ID)
        }

        activity?.title = getString(R.string.stop, stopId)
        stopViewModel = ViewModelProviders.of(this)[StopViewModel::class.java]
    }

    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        list.adapter = adapter
        updateArrivals()
    }

    override fun onResume() {
        super.onResume()
        updateArrivals()
    }

    private fun updateArrivals() {
        stopViewModel.getStopRealtimeArrivals(stopId).observe(this, Observer {
            it?.let {
                adapter.submitList(it)
            }
        })
    }

    override fun onListFragmentInteraction(arrival: Arrival) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    companion object {
        private const val ARG_STOP_ID = "stopId"

        fun newInstance(stopId: String): ArrivalFragment {
            val fragment = ArrivalFragment()
            val args = Bundle()
            args.putString(ARG_STOP_ID, stopId)
            fragment.arguments = args
            return fragment
        }
    }
}
