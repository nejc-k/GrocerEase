package com.prvavaja.grocerease

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import android.widget.TextView
import android.widget.Toast
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.materialswitch.MaterialSwitch

class SettingsActivity : AppCompatActivity() {

    private lateinit var switchTheme: MaterialSwitch

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.settings_layout)

        // Initialize UI components
        val switchNotifications: MaterialSwitch = findViewById(R.id.switch_notifications)
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

        val bottomNav: BottomNavigationView = findViewById(R.id.bottom_navigation)
        setupBottomNav(this, bottomNav, (application as MyApplication).isGuest)
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
}
