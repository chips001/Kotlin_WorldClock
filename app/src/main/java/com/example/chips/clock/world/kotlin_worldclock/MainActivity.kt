package com.example.chips.clock.world.kotlin_worldclock

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ListView
import android.widget.TextView
import java.util.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val timeZoneDefault = TimeZone.getDefault()
        val timeZoneTextView = findViewById<TextView>(R.id.time_zone_text_view)
        timeZoneTextView.text = timeZoneDefault.displayName

        val addButton = findViewById<Button>(R.id.add_button)
        addButton.setOnClickListener {
            val intent = Intent(this, TimeZoneSelectActivity::class.java)
            startActivityForResult(intent, 1)
        }

        showWorldClocks()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == 1 && resultCode == Activity.RESULT_OK && data != null) {
            val timeZone = data.getStringExtra("time_zone")
            val preference = getSharedPreferences("preference", Context.MODE_PRIVATE)
            val timeZones = preference.getStringSet("time_zone", mutableSetOf())
            timeZones.add(timeZone)
            preference.edit().putStringSet("time_zone", timeZones).apply()
            showWorldClocks()
        }
    }

    private fun showWorldClocks() {
        val preference = getSharedPreferences("preference", Context.MODE_PRIVATE)
        val timeZones = preference.getStringSet("time_zone", setOf())
        val clockListView = findViewById<ListView>(R.id.clock_list_view)
        clockListView.adapter = TimeZoneSelectActivity.TimeZoneAdapter(this, timeZones.toTypedArray())
    }
}
