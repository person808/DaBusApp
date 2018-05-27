package com.kainalu.dabus

import android.support.v7.recyclerview.extensions.ListAdapter
import android.support.v7.util.DiffUtil
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.list_item.view.*

class RouteAdapter(private val listener: OnListFragmentInteractionListener) :
    ListAdapter<Route, RouteAdapter.ViewHolder>(DIFF_CALLBACK) {

    interface OnListFragmentInteractionListener {
        fun onListFragmentInteraction(route: Route)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.list_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val route = getItem(position)
        val routeText =
            holder.view.context.getString(R.string.route_label, route.shortName, route.longName)
        holder.view.listItem.title = routeText
        holder.view.setOnClickListener({ listener.onListFragmentInteraction(route) })
    }

    inner class ViewHolder internal constructor(val view: View) : RecyclerView.ViewHolder(view)

    companion object {

        val DIFF_CALLBACK: DiffUtil.ItemCallback<Route> = object : DiffUtil.ItemCallback<Route>() {
            override fun areItemsTheSame(oldItem: Route, newItem: Route): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: Route, newItem: Route): Boolean {
                return oldItem == newItem
            }
        }
    }
}
