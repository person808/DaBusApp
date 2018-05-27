package com.kainalu.dabus

import android.os.Bundle
import android.os.Parcelable
import android.support.v4.app.Fragment
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import kotlinx.android.synthetic.main.fragment_list.view.*


/**
 * A simple [Fragment] subclass to display a RecyclerView.
 * RecyclerView must have an id of "list."
 */

abstract class RecyclerViewFragment : Fragment() {

    private lateinit var recyclerView: RecyclerView
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        if (savedInstanceState != null) {
            val recyclerViewState = savedInstanceState.getParcelable<Parcelable>(RECYCLERVIEW_STATE)
            recyclerView.layoutManager.onRestoreInstanceState(recyclerViewState)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        recyclerView = view.list
        recyclerView.layoutManager = LinearLayoutManager(view.context)
        recyclerView.addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.VERTICAL))
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putParcelable(RECYCLERVIEW_STATE, recyclerView.layoutManager.onSaveInstanceState())
    }

    companion object {
        private const val RECYCLERVIEW_STATE = "recyclerViewState"
    }
}
