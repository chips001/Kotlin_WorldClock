package com.example.chips.clock.world.kotlin_worldclock

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ListView
import android.widget.TextClock
import android.widget.TextView
import java.util.*

class TimeZoneSelectActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_time_zone_select)

        setResult(Activity.RESULT_CANCELED)
        val clockList = findViewById<ListView>(R.id.clock_list_view)
        val adapter = TimeZoneAdapter(this)
        clockList.adapter = adapter
        clockList.setOnItemClickListener { _, _, position, _ ->
            val timeZone = adapter.getItem(position)
            val result = Intent()
            result.putExtra("time_zone", timeZone)
            setResult(Activity.RESULT_OK, result)
            finish()
        }
    }

    class TimeZoneAdapter(private val context: Context,
                          private val timeZones: Array<String> = TimeZone.getAvailableIDs()
    ): BaseAdapter() {

        private val inflater = LayoutInflater.from(context)

        private fun createView(parent: ViewGroup?) : View {
            val view = inflater.inflate(R.layout.list_time_zone_row, parent, false)
            view.tag = ViewHolder(view)
            return view
        }

        override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
            val view = convertView ?: createView(parent)
            val timeZoneId = getItem(position)
            val timeZone = TimeZone.getTimeZone(timeZoneId)

            val viewHolder = view.tag as ViewHolder
            @SuppressLint("SetTextI18n")
            viewHolder.timeZoneTextView.text = "${timeZone.displayName}(${timeZone.id})"
            viewHolder.textClock.timeZone = timeZone.id
            return view
        }

        override fun getItem(position: Int): String {
            return timeZones[position]
        }

        override fun getItemId(position: Int): Long {
            return position.toLong()
        }

        override fun getCount(): Int {
            return timeZones.size
        }

        private class ViewHolder(view: View) {
            val timeZoneTextView = view.findViewById<TextView>(R.id.time_zone_text_view)
            val textClock = view.findViewById<TextClock>(R.id.text_clock)
        }

    }
}
