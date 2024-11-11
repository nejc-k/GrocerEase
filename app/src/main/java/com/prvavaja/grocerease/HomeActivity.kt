package com.prvavaja.grocerease

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.prvavaja.grocerease.databinding.ActivityHomeBinding

class HomeActivity : AppCompatActivity() {
    private lateinit var binding: ActivityHomeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val isGuest = intent.getBooleanExtra("isGuest", false)

        if (isGuest) {
            binding.btnProfile.visibility = View.GONE
            binding.btnBack.visibility = View.VISIBLE
        } else {
            binding.btnProfile.visibility = View.VISIBLE
            binding.btnBack.visibility = View.GONE
        }

        binding.listButton.setOnClickListener {
            val intent = Intent(this, ListsActivity::class.java)
            startActivity(intent)
        }

        binding.mapButton.setOnClickListener {
            val intent = Intent(this, MapActivity::class.java)
            startActivity(intent)
        }

        binding.btnProfile.setOnClickListener {
            val intent = Intent(this, ProfileActivity::class.java)
            startActivity(intent)
        }

        binding.btnBack.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }

        binding.settingsButton.setOnClickListener {
            val intent = Intent(this, SettingsActivity::class.java)
            startActivity(intent)
        }
    }
}
