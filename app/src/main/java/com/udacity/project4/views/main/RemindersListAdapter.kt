package com.udacity.project4.views.main

import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.udacity.project4.R
import com.udacity.project4.ReminderDataItem
import com.udacity.project4.ReminderDescriptionActivity
import com.udacity.project4.Utils.Notifications


class RemindersListAdapter: RecyclerView.Adapter<ConstraintLayoutViewHolder>() {
    var data : List<ReminderDataItem> = listOf()

        set(value) {
            field = value
            notifyDataSetChanged()
        }
    override fun getItemCount() = data.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ConstraintLayoutViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val view = layoutInflater.inflate(R.layout.it_reminder, parent, false)

        return ConstraintLayoutViewHolder(view)
    }

    override fun onBindViewHolder(holder: ConstraintLayoutViewHolder, position: Int) {
        val item = data[position]
        holder.reminderTitle.text = item.title
        holder.reminderDescription.text = item.description
        holder.reminderLocation.text = item.location

        holder.reminderLayout.setOnClickListener {
            println("dasda")
            val myIntent = Intent(it.context, ReminderDescriptionActivity::class.java)
            val bundle = Bundle()
            bundle.putSerializable("locationReminder", item)
            myIntent.putExtras(bundle)
            val showStatusPendingIntent: PendingIntent = PendingIntent.getActivity(
                it.context,
                1,
                myIntent,
                PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT
            )
            val pendingIntent: PendingIntent = PendingIntent.getActivity(it.context, 1, Intent(), PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT )
            Notifications(it.context , pendingIntent , showStatusPendingIntent)
                .getNotification(it.context.getSystemService(Context.NOTIFICATION_SERVICE)!! , item.title!! , item!!.description!!)
        }
    }




}


class ConstraintLayoutViewHolder(val itemView: View): RecyclerView.ViewHolder(itemView){
    var reminderLayout: CardView = itemView.findViewById(R.id.reminderCardView)
    var reminderTitle: TextView = itemView.findViewById(R.id.reminder_title)
    var reminderDescription: TextView = itemView.findViewById(R.id.reminder_description)
    var reminderLocation: TextView = itemView.findViewById(R.id.reminder_location)
}