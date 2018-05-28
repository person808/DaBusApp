package com.kainalu.dabus.arrivals

import android.content.Context
import android.support.v7.recyclerview.extensions.ListAdapter
import android.support.v7.util.DiffUtil
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.kainalu.dabus.Arrival
import com.kainalu.dabus.R
import kotlinx.android.synthetic.main.list_item.view.*
import org.threeten.bp.Instant
import org.threeten.bp.LocalTime
import org.threeten.bp.ZoneId
import org.threeten.bp.ZonedDateTime
import org.threeten.bp.format.DateTimeFormatter
import org.threeten.bp.temporal.ChronoUnit


class ArrivalAdapter(private val listener: OnListFragmentInteractionListener) :
    ListAdapter<Arrival, ArrivalAdapter.ViewHolder>(DIFF_CALLBACK) {

    interface OnListFragmentInteractionListener {
        fun onListFragmentInteraction(arrival: Arrival)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.list_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val arrival = getItem(position)
        val routeIdText = holder.view.context
            .getString(R.string.route_headsign, arrival.route, arrival.headsign)
        holder.view.listItem.title = routeIdText
        holder.view.listItem.subtitle = getArrivalStatusString(holder.view.context, arrival)

        holder.view.setOnClickListener({ listener.onListFragmentInteraction(arrival) })
    }

    private fun getArrivalStatusString(context: Context, arrival: Arrival): String {
        val vehicleStr = if (arrival.vehicle == "???") {
            context.getString(R.string.no_gps)
        } else {
            context.getString(R.string.bus_vehicle, arrival.vehicle)
        }

        val minutesLeft = stopTimeDifference(arrival.stopTime).toInt()
        val statusStr = when {
            arrival.canceled == 1 -> context.getString(R.string.canceled)
            minutesLeft in 1..59 -> context.resources
                .getQuantityString(R.plurals.minutes_left, minutesLeft, minutesLeft)
            minutesLeft == 0 -> context.getString(R.string.arriving)
            minutesLeft == -1 -> context.getString(R.string.arrived)
            minutesLeft < -1 -> context.getString(R.string.departed)
            else -> ""
        }

        return if (statusStr.isEmpty()) {
            context.getString(R.string.arrival_time, vehicleStr, arrival.stopTime)
        } else {
            context.getString(R.string.arrival_time_status, vehicleStr, arrival.stopTime, statusStr)
        }
    }

    private fun stopTimeDifference(string: String?): Long {
        val formatter = DateTimeFormatter.ofPattern("h:mm a")
        val parsedTime = LocalTime.parse(string, formatter)
        val arrivalTime = Instant.now().atZone(ZoneId.systemDefault()).with(parsedTime)
        val presentTime = ZonedDateTime.now()
        return ChronoUnit.MINUTES.between(presentTime, arrivalTime)
    }

    inner class ViewHolder internal constructor(val view: View) : RecyclerView.ViewHolder(view)

    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<Arrival>() {
            override fun areItemsTheSame(oldItem: Arrival, newItem: Arrival): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: Arrival, newItem: Arrival): Boolean {
                return oldItem == newItem
            }
        }
    }
}
