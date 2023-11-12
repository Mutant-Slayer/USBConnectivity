package com.example.usbconnectivity

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    private lateinit var usbBroadcastReceiver: BroadcastReceiver

    private var connected = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val textView: TextView = findViewById(R.id.tvText)

        val usbBroadcastReceiver = object : BroadcastReceiver() {

            override fun onReceive(context: Context?, intent: Intent?) {

                if (intent?.getBooleanExtra("connected", false) == true) {
                    connected = true
                    Toast.makeText(
                        this@MainActivity,
                        "USB device is connected",
                        Toast.LENGTH_LONG
                    ).show()
                } else if (intent?.getBooleanExtra("connected", false) == false && connected) {
                    Toast.makeText(
                        this@MainActivity,
                        "USB device is disconnected",
                        Toast.LENGTH_LONG
                    ).show()
                    connected = false
                }

                var variable = ""

                intent?.extras?.keySet()?.forEach {
                    variable += it
                    variable += intent.extras?.get(it)
                    variable += "\n"
                }
                textView.text = variable
            }
        }
        val filter = IntentFilter("android.hardware.usb.action.USB_STATE")
        registerReceiver(usbBroadcastReceiver, filter)
    }

    override fun onDestroy() {
        super.onDestroy()
        unregisterReceiver(usbBroadcastReceiver)
    }
}