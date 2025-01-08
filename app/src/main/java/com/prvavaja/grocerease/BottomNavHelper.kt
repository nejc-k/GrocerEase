package com.prvavaja.grocerease

import android.app.Activity
import android.content.Intent
import com.google.android.material.bottomnavigation.BottomNavigationView

fun setupBottomNav(activity: Activity, bottomNav: BottomNavigationView, is_guest: Boolean) {
    when (activity::class.java) {
        ListsActivity::class.java -> bottomNav.selectedItemId = R.id.list
        MapActivity::class.java -> bottomNav.selectedItemId = R.id.map
        SettingsActivity::class.java -> bottomNav.selectedItemId = R.id.settings
    }

    if (is_guest){
        bottomNav.menu.removeItem(R.id.profile)
    }

    bottomNav.setOnItemSelectedListener { item ->
        when (item.itemId) {
            R.id.list -> {
                if (activity !is ListsActivity) {
                    activity.startActivity(Intent(activity, ListsActivity::class.java))
                }
                true
            }
            R.id.map -> {
                if (activity !is MapActivity) {
                    activity.startActivity(Intent(activity, MapActivity::class.java))
                }
                true
            }
            R.id.settings -> {
                if (activity !is SettingsActivity) {
                    activity.startActivity(Intent(activity, SettingsActivity::class.java))
                }
                true
            }
            else -> false
        }
    }
}
