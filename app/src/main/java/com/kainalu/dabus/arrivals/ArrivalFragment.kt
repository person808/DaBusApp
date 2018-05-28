package com.kainalu.dabus.arrivals

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.view.*
import com.kainalu.dabus.Arrival
import com.kainalu.dabus.R
import com.kainalu.dabus.RecyclerViewFragment
import com.kainalu.dabus.StopViewModel
import kotlinx.android.synthetic.main.fragment_list.*

class ArrivalFragment : RecyclerViewFragment(), ArrivalAdapter.OnListFragmentInteractionListener {

    private lateinit var stopViewModel: StopViewModel
    private lateinit var stopId: String
    private val adapter = ArrivalAdapter(this)
    private lateinit var favoritesButton: MenuItem

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (arguments != null) {
            stopId = arguments!!.getString(ARG_STOP_ID)
        }

        setHasOptionsMenu(true)
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
        swipeRefreshLayout.setOnRefreshListener { updateArrivals() }
        updateArrivals()
    }

    override fun onCreateOptionsMenu(menu: Menu?, inflater: MenuInflater?) {
        inflater?.inflate(R.menu.menu_favorites, menu)
        menu?.let { favoritesButton = it.findItem(R.id.favorites_button) }
    }

    override fun onPrepareOptionsMenu(menu: Menu?) {
        super.onPrepareOptionsMenu(menu)
        // Favorites button default to un-favorited state
        if (stopViewModel.stopInFavorites(stopId)) {
            favoritesButton.icon = ContextCompat.getDrawable(context!!, R.drawable.ic_favorite_white_24dp)
            favoritesButton.title = getString(R.string.remove_favorite)
        }
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId) {
            R.id.favorites_button -> if (stopViewModel.stopInFavorites(stopId)) {
                removeStopFromFavorites()
            } else {
                addStopToFavorites()
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun addStopToFavorites() {
        stopViewModel.addFavoriteStop(stopId)
        favoritesButton.icon = ContextCompat.getDrawable(context!!, R.drawable.ic_favorite_white_24dp)
        favoritesButton.title = getString(R.string.remove_favorite)
    }

    private fun removeStopFromFavorites() {
        stopViewModel.removeFavoriteStop(stopId)
        favoritesButton.icon = ContextCompat.getDrawable(context!!, R.drawable.ic_favorite_border_white_24dp)
        favoritesButton.title = getString(R.string.add_favorite)
    }

    override fun onResume() {
        super.onResume()
        updateArrivals()
    }

    private fun updateArrivals() {
        swipeRefreshLayout.isRefreshing = true
        stopViewModel.getStopRealtimeArrivals(stopId).observe(this, Observer {
            it?.let {
                adapter.submitList(it)
                swipeRefreshLayout.isRefreshing = false
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
