package com.prvavaja.grocerease

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.prvavaja.grocerease.databinding.ActivityHomeBinding

class HomeActivity : AppCompatActivity() {
    private lateinit var binding: ActivityHomeBinding //ADD THIS LINE
    private lateinit var serialization: Serialization
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater) //ADD THIS LINE
        setContentView(binding.root)

        binding.listButton.setOnClickListener{//gumb da prides do lists
            val intent = Intent(this, ListsActivity::class.java)
            startActivity(intent)
        }

        binding.mapButton.setOnClickListener{//gumb da prides do lists
            val intent = Intent(this, MapActivity::class.java)
            startActivity(intent)
        }

        binding.btnProfile.setOnClickListener{//gumb da prides do lists
            val intent = Intent(this, ProfileActivity::class.java)
            startActivity(intent)
        }
    }
}