package com.kainalu.dabus.search

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.kainalu.dabus.R
import kotlinx.android.synthetic.main.list_item.view.*

class SearchTypesAdapter(
    private val context: Context,
    private val listener: OnListFragmentInteractionListener
) : RecyclerView.Adapter<SearchTypesAdapter.ViewHolder>() {

    interface OnListFragmentInteractionListener {
        fun onListFragmentInteraction(searchInfo: SearchInfo)
    }

    inner class ViewHolder(val view: View) : RecyclerView.ViewHolder(view)

    private val searchInfos: List<SearchInfo>
        get() {
            val list = mutableListOf<SearchInfo>()
            val titles = context.resources.getStringArray(R.array.search_types)
            val descriptions = context.resources.getStringArray(R.array.search_type_descriptions)
            for (i in SearchType.values().indices) {
                list.add(
                    SearchInfo(
                        SearchType.values()[i],
                        titles[i],
                        descriptions[i]
                    )
                )
            }
            return list
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.list_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val searchType = searchInfos[position]
        holder.view.listItem.title = searchType.title
        holder.view.listItem.subtitle = searchType.description
        holder.view.setOnClickListener {
            listener.onListFragmentInteraction(searchType)
        }
    }

    override fun getItemCount(): Int = searchInfos.size
}

enum class SearchType {
    SEARCH_STOP, SEARCH_STREET, SEARCH_ROUTE, SEARCH_DESTINATION
}

class SearchInfo(val searchType: SearchType, val title: String, val description: String)
