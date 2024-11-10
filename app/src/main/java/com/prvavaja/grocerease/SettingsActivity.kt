package com.prvavaja.grocerease

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.widget.Switch
import android.widget.TextView
import android.widget.Toast
import android.view.View

class SettingsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.settings_layout)

        val switchNotifications: Switch = findViewById(R.id.switch_notifications)
        val systemInfoText: TextView = findViewById(R.id.system_info_text)

        switchNotifications.setOnCheckedChangeListener { _, isChecked ->
            Toast.makeText(this, "Notifications are " + (if (isChecked) "on" else "off"), Toast.LENGTH_SHORT).show()
        }

        // Display system information
        systemInfoText.text = getSystemInfo()
    }

    private fun getSystemInfo(): String {
        val apiLevel = android.os.Build.VERSION.SDK_INT.toString()
        val device = android.os.Build.DEVICE ?: "N/A"
        val model = android.os.Build.MODEL ?: "N/A"
        val versionName = packageManager.getPackageInfo(packageName, 0).versionName

        return "API Level: $apiLevel\nDevice: $device\nModel: $model\nApp Version: $versionName"
    }

    fun backOnClick(view: View) {
        val intent = Intent(this, HomeActivity::class.java)
        startActivity(intent)
        finish()
    }
}
