package com.prvavaja.grocerease

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import android.widget.Switch
import android.widget.TextView
import android.widget.Toast
import android.view.View

class SettingsActivity : AppCompatActivity() {

    private lateinit var switchTheme: Switch

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.settings_layout)

        // Initialize UI components
        val switchNotifications: Switch = findViewById(R.id.switch_notifications)
        switchTheme = findViewById(R.id.switch_theme)
        val systemInfoText: TextView = findViewById(R.id.system_info_text)

        // Load the saved theme preference
        val sharedPref = getSharedPreferences("AppSettings", MODE_PRIVATE)
        val isDarkMode = sharedPref.getBoolean("DARK_MODE", false)
        switchTheme.isChecked = isDarkMode

        // Theme switch listener
        switchTheme.setOnCheckedChangeListener { _, isChecked ->
            toggleDarkMode(isChecked)
        }

        // Notification switch listener
        switchNotifications.setOnCheckedChangeListener { _, isChecked ->
            Toast.makeText(this, "Notifications are " + (if (isChecked) "on" else "off"), Toast.LENGTH_SHORT).show()
        }

        // Display system information
        systemInfoText.text = getSystemInfo()
    }

    private fun toggleDarkMode(isEnabled: Boolean) {
        // Save the dark mode preference
        val editor = getSharedPreferences("AppSettings", MODE_PRIVATE).edit()
        editor.putBoolean("DARK_MODE", isEnabled)
        editor.apply()

        // Apply the theme change across the app
        AppCompatDelegate.setDefaultNightMode(
            if (isEnabled) AppCompatDelegate.MODE_NIGHT_YES else AppCompatDelegate.MODE_NIGHT_NO
        )
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
