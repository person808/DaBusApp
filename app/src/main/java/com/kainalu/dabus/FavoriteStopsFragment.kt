package com.kainalu.dabus

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.fragment_list.*

class FavoriteStopsFragment : RecyclerViewFragment(), StopAdapter.OnListFragmentInteractionListener {

    private lateinit var stopViewModel: StopViewModel
    private val adapter = StopAdapter(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        stopViewModel = ViewModelProviders.of(activity!!)[StopViewModel::class.java]
    }

    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        list.adapter = adapter
        stopViewModel.getFavoriteStops().observe(this, Observer {
            it?.let {
                adapter.submitList(it)
            }
        })
    }

    override fun onListFragmentInteraction(stop: Stop) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}