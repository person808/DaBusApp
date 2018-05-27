package com.kainalu.dabus.search

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.os.Bundle
import android.support.v7.widget.SearchView
import android.view.*
import android.view.inputmethod.InputMethodManager
import com.kainalu.dabus.*
import com.kainalu.dabus.search.SearchType.*
import kotlinx.android.synthetic.main.fragment_list.*
import java.util.*


class SearchFragment : RecyclerViewFragment(), SearchView.OnQueryTextListener,
    StopAdapter.OnListFragmentInteractionListener,
    RouteAdapter.OnListFragmentInteractionListener {

    private lateinit var searchType: SearchType
    private lateinit var stopViewModel: StopViewModel
    private lateinit var routeViewModel: RouteViewModel
    private var stopList: List<Stop>? = null
    private val stopAdapter: StopAdapter = StopAdapter(this)
    private var routeList: List<Route>? = null
    private val routeAdapter: RouteAdapter = RouteAdapter(this)
    private var imm: InputMethodManager? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (arguments != null) {
            searchType = arguments!!.getSerializable(ARG_SEARCH_TYPE) as SearchType
        }

        setHasOptionsMenu(true)
        stopViewModel = ViewModelProviders.of(this).get(StopViewModel::class.java)
        routeViewModel = ViewModelProviders.of(this).get(RouteViewModel::class.java)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        swipeRefreshLayout.isEnabled = false
        when (searchType) {
            SEARCH_STOP, SEARCH_STREET -> {
                list.adapter = stopAdapter
                swipeRefreshLayout.isRefreshing = true
                stopViewModel.getStopData(false).observe(this, Observer { stops ->
                    if (stops != null) {
                        stopList = stops
                        stopAdapter.submitList(stops)
                        swipeRefreshLayout.isRefreshing = false
                    }
                })
            }
            SEARCH_ROUTE, SEARCH_DESTINATION -> {
                list.adapter = routeAdapter
                swipeRefreshLayout.isRefreshing = true
                routeViewModel.getRouteData(false).observe(this, Observer { routes ->
                    if (routes != null) {
                        routeList = routes
                        routeAdapter.submitList(routeList)
                        swipeRefreshLayout.isRefreshing = false
                    }
                })
            }
        }
        super.onViewCreated(view, savedInstanceState)
    }

    override fun onCreateOptionsMenu(menu: Menu?, inflater: MenuInflater?) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater!!.inflate(R.menu.search_menu, menu)
        // Get the SearchView and set the searchable configuration
        val searchView = menu!!.findItem(R.id.app_bar_search).actionView as SearchView
        searchView.setOnQueryTextListener(this)
        searchView.setIconifiedByDefault(false)
        searchView.maxWidth = Integer.MAX_VALUE
        // Force keyboard to show
        searchView.requestFocus()
        imm = context!!.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm!!.toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.HIDE_IMPLICIT_ONLY)
    }

    override fun onPause() {
        super.onPause()
        hideKeyboard()
    }

    override fun onQueryTextChange(newText: String): Boolean {
        when (searchType) {
            SEARCH_STOP, SEARCH_STREET -> {
                stopList?.let {
                    val filteredStopList = filterStops(stopList!!, newText, searchType)
                    stopAdapter.submitList(filteredStopList)
                }
            }
            SEARCH_ROUTE, SEARCH_DESTINATION -> {
                stopList?.let {
                    val filteredRouteList = filterRoutes(routeList!!, newText, searchType)
                    routeAdapter.submitList(filteredRouteList)
                }
            }
        }
        list.scrollToPosition(0)
        return true
    }

    override fun onQueryTextSubmit(query: String) = true

    private fun hideKeyboard() {
        val v = activity!!.currentFocus
        if (v != null) {
            imm!!.hideSoftInputFromWindow(v.windowToken, 0)
        }
    }

    override fun onListFragmentInteraction(stop: Stop) {
        hideKeyboard()
    }

    override fun onListFragmentInteraction(route: Route) {
        hideKeyboard()
    }

    companion object {

        private const val ARG_SEARCH_TYPE = "searchType"

        fun newInstance(searchType: SearchType): SearchFragment {
            val fragment = SearchFragment()
            val args = Bundle()
            args.putSerializable(ARG_SEARCH_TYPE, searchType)
            fragment.arguments = args
            return fragment
        }

        private fun filterStops(stops: List<Stop>,
                                query: String,
                                queryType: SearchType): List<Stop> {
            val lowerCaseQuery = query.toLowerCase()

            val filteredStopList = ArrayList<Stop>()
            for (stop in stops) {
                val text = if (queryType == SEARCH_STOP) {
                    stop.id.toLowerCase()
                } else {
                    stop.name.toLowerCase()
                }
                if (text.contains(lowerCaseQuery)) {
                    filteredStopList.add(stop)
                }
            }
            return filteredStopList
        }

        private fun filterRoutes(routes: List<Route>,
                                 query: String,
                                 queryType: SearchType): List<Route> {
            val lowerCaseQuery = query.toLowerCase()

            val filteredRouteList = ArrayList<Route>()
            for (route in routes) {
                val text = if (queryType == SEARCH_ROUTE) {
                    route.shortName.toLowerCase()
                } else {
                    route.longName.toLowerCase()
                }
                if (text.contains(lowerCaseQuery)) {
                    filteredRouteList.add(route)
                }
            }
            return filteredRouteList
        }
    }
}
