package com.kainalu.dabus

import android.support.v7.recyclerview.extensions.ListAdapter
import android.support.v7.util.DiffUtil
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.list_item.view.*


class StopAdapter(private val listener: OnListFragmentInteractionListener) :
    ListAdapter<Stop, StopAdapter.ViewHolder>(DIFF_CALLBACK) {

    interface OnListFragmentInteractionListener {
        fun onListFragmentInteraction(stop: Stop)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.list_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val stop = getItem(position)
        val stopText = holder.view.context.getString(R.string.stop_label, stop.id, stop.name)
        holder.view.listItem.title = stopText
        holder.view.setOnClickListener({ listener.onListFragmentInteraction(stop) })
    }

    inner class ViewHolder internal constructor(val view: View) : RecyclerView.ViewHolder(view)

    companion object {

        val DIFF_CALLBACK: DiffUtil.ItemCallback<Stop> = object : DiffUtil.ItemCallback<Stop>() {
            override fun areItemsTheSame(oldItem: Stop, newItem: Stop): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: Stop, newItem: Stop): Boolean {
                return oldItem == newItem
            }
        }
    }
}
