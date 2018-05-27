package com.kainalu.dabus.search


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.kainalu.dabus.R
import com.kainalu.dabus.RecyclerViewFragment
import kotlinx.android.synthetic.main.fragment_list.*


class SearchTypesFragment : RecyclerViewFragment(),
    SearchTypesAdapter.OnListFragmentInteractionListener {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        list.adapter = SearchTypesAdapter(context!!, this)
    }

    override fun onListFragmentInteraction(searchInfo: SearchInfo) {
        activity?.run {
            supportFragmentManager
                .beginTransaction()
                .replace(R.id.placeholder, SearchFragment.newInstance(searchInfo.searchType))
                .addToBackStack(null)
                .commit()
        }
    }
}
