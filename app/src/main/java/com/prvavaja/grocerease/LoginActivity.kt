package com.prvavaja.grocerease

import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import okhttp3.Call
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.RequestBody.Companion.toRequestBody
import okhttp3.Request
import okhttp3.Callback
import okhttp3.Response
import org.json.JSONObject
import java.io.IOException

class LoginActivity : AppCompatActivity() {

    private lateinit var emailEditText: EditText
    private lateinit var passwordEditText: EditText
    private lateinit var loginButton: Button
    private lateinit var registerLink: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        emailEditText = findViewById(getResourceId("emailLogin"))
        passwordEditText = findViewById(getResourceId("passwordLogin"))
        loginButton = findViewById(getResourceId("btnLogin"))
        registerLink = findViewById(R.id.registerLink)

        registerLink.setOnClickListener {
            val intent = Intent(this@LoginActivity, RegisterActivity::class.java)
            startActivity(intent)
        }

        loginButton.setOnClickListener {
            loginUser()
        }
    }

    private fun loginUser() {
        val email = emailEditText.text.toString().trim()
        val password = passwordEditText.text.toString().trim()

        if (email.isEmpty() || password.isEmpty()) {
            Toast.makeText(this, "Please enter your credentials", Toast.LENGTH_SHORT).show()
            return
        }

        if (isInternetAvailable()) {
            authenticateUserWithServer(email, password)
        } else {
            authenticateUserWithSharedPrefs()
        }
    }

    private fun authenticateUserWithServer(email: String, password: String) {
        val client = OkHttpClient()
        val jsonMediaType = "application/json; charset=utf-8".toMediaType()

        val jsonBody = """
            {
                "email": "$email",
                "password": "$password"
            }
        """.trimIndent()

        val requestBody = jsonBody.toRequestBody(jsonMediaType)
        val request = Request.Builder()
            .url("http://10.0.2.2:5000/api/user/login")
            .post(requestBody)
            .build()

        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                Log.e("LoginActivity", "Server login failed: ${e.message}")
                runOnUiThread {
                    Toast.makeText(this@LoginActivity, "Server login failed", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onResponse(call: Call, response: Response) {
                if (response.isSuccessful) {
                    Log.d("LoginActivity", "Login successful with server!")

                    response.body?.let { responseBody ->
                        val responseData = responseBody.string()
                        val jsonResponse = JSONObject(responseData)
                        val token = jsonResponse.getString("token")

                        val sharedPreferences = getSharedPreferences("user_prefs", Context.MODE_PRIVATE)
                        sharedPreferences.edit().putString("auth_token", token).apply()

                        runOnUiThread {
                            val intent = Intent(this@LoginActivity, HomeActivity::class.java)
                            startActivity(intent)
                            finish()
                        }
                    }
                } else {
                    Log.e("LoginActivity", "Login failed: ${response.message}")
                    runOnUiThread {
                        Toast.makeText(this@LoginActivity, "Login failed: ${response.message}", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        })
    }

    private fun authenticateUserWithSharedPrefs() {
        val sharedPreferences = getSharedPreferences("user_prefs", Context.MODE_PRIVATE)
        val token = sharedPreferences.getString("auth_token", null)

        if (token != null) {
            Log.d("LoginActivity", "Login successful with SharedPreferences!")

            val intent = Intent(this, ProfileActivity::class.java)
            startActivity(intent)
            finish()
        } else {
            Log.e("LoginActivity", "Login failed: No valid token found in SharedPreferences")
            Toast.makeText(this, "Please login again", Toast.LENGTH_SHORT).show()
        }
    }

    private fun isInternetAvailable(): Boolean {
        val connectivityManager = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val capabilities = connectivityManager.getNetworkCapabilities(connectivityManager.activeNetwork)
        return capabilities != null && capabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
    }

    private fun getResourceId(name: String): Int {
        return resources.getIdentifier(name, "id", packageName)
    }
}
