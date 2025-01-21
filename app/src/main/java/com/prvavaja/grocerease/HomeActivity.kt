package com.prvavaja.grocerease
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.prvavaja.grocerease.databinding.ActivityHomeBinding


class HomeActivity : AppCompatActivity() {
    private lateinit var binding: ActivityHomeBinding
    lateinit var app: MyApplication

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)
        app = application as MyApplication


        val bottomNav: BottomNavigationView = findViewById(R.id.bottom_navigation)
        setupBottomNav(this, bottomNav, app.isGuest)
    }
}
