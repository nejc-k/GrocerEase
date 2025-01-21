package com.prvavaja.grocerease

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import okhttp3.*
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.IOException
import java.security.MessageDigest
import java.security.NoSuchAlgorithmException

class RegisterActivity : AppCompatActivity() {

    private lateinit var userImageView: ImageView
    private lateinit var uploadImageButton: Button
    private lateinit var usernameEditText: EditText
    private lateinit var emailEditText: EditText
    private lateinit var passwordEditText: EditText
    private lateinit var confirmPasswordEditText: EditText
    private lateinit var registerButton: Button
    private lateinit var loginLink: TextView
    private var imageUri: Uri? = null

    private lateinit var pickMedia: ActivityResultLauncher<PickVisualMediaRequest>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        userImageView = findViewById(R.id.userImage)
        usernameEditText = findViewById(R.id.usernameRegister)
        emailEditText = findViewById(R.id.emailRegister)
        passwordEditText = findViewById(R.id.passwordRegister)
        confirmPasswordEditText = findViewById(R.id.confirmPasswordRegister)
        registerButton = findViewById(R.id.btnRegister)
        loginLink = findViewById(R.id.loginLink)

        loginLink.setOnClickListener {
            val intent = Intent(this@RegisterActivity, LoginActivity::class.java)
            startActivity(intent)
        }

        pickMedia = registerForActivityResult(ActivityResultContracts.PickVisualMedia()) { uri ->
            if (uri != null) {
                Log.d("PhotoPicker", "Selected URI: $uri")
                imageUri = uri
                contentResolver.takePersistableUriPermission(uri, Intent.FLAG_GRANT_READ_URI_PERMISSION)
                userImageView.setImageURI(imageUri)
            } else {
                Log.d("PhotoPicker", "No media selected")
            }
        }

        userImageView.setOnClickListener {
            pickMedia.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
        }

        registerButton.setOnClickListener {
            registerUser()
        }
    }

    private fun registerUser() {
        val username = usernameEditText.text.toString().trim()
        val email = emailEditText.text.toString().trim()
        val password = passwordEditText.text.toString().trim()
        val confirmPassword = confirmPasswordEditText.text.toString().trim()

        // Check if all fields are filled
        if (username.isEmpty() || email.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()) {
            Log.e("RegisterActivity", "Please fill in all fields")
            return
        }

        // Validate email format
        if (!isValidEmail(email)) {
            Log.e("RegisterActivity", "Invalid email format")
            emailEditText.error = "Invalid email format" // Show error on the email field
            return
        }

        // Check if passwords match
        if (password != confirmPassword) {
            Log.e("RegisterActivity", "Passwords do not match")
            return
        }

        // Check if password meets the minimum length requirement (5 characters)
        if (password.length < 5) {
            Log.e("RegisterActivity", "Password must be at least 5 characters")
            passwordEditText.error = "Password must be at least 5 characters" // Show error on the password field
            return
        }

        // Hash the password before saving it
        val hashedPassword = hashPassword(password)

        // Save data to SharedPreferences (hashed password)
        val sharedPreferences = getSharedPreferences("user_prefs", Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putString("username", username)
        editor.putString("email", email)
        editor.putString("password", hashedPassword) // Store the hashed password
        editor.putString("profile_image", imageUri.toString())
        editor.apply()

        // Create the HTTP client and request
        val client = OkHttpClient()
        val jsonMediaType = "application/json; charset=utf-8".toMediaType()

        val jsonBody = """
            {
                "username": "$username",
                "email": "$email",
                "password": "$hashedPassword", 
                "profile_image": "${imageUri.toString()}"
            }
        """.trimIndent()

        val requestBody = jsonBody.toRequestBody(jsonMediaType)
        Log.d("-----------------------------------------------", jsonBody)
        val request = Request.Builder()
            .url("http://204.216.219.141:5000/api/user/register")
            .post(requestBody)
            .build()

        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                Log.e("RegisterActivity", "Registration failed: ${e.message}")
            }

            override fun onResponse(call: Call, response: Response) {
                if (response.isSuccessful) {
                    Log.d("RegisterActivity", "Registration successful!")
                    runOnUiThread {
                        val intent = Intent(this@RegisterActivity, LoginActivity::class.java)
                        startActivity(intent)
                        finish()
                    }
                } else {
                    Log.e("RegisterActivity", "Registration failed: ${response.message}")
                }
            }
        })
    }

    // Function to validate email format using regex
    private fun isValidEmail(email: String): Boolean {
        val emailPattern = "[a-zA-Z0-9._-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,6}"
        return email.matches(Regex(emailPattern))
    }

    // Function to hash the password
    private fun hashPassword(password: String): String {
        return try {
            val digest = MessageDigest.getInstance("SHA-256")
            val hashBytes = digest.digest(password.toByteArray(Charsets.UTF_8))
            // Convert the bytes to a hexadecimal string
            val stringBuilder = StringBuilder()
            for (byte in hashBytes) {
                stringBuilder.append(String.format("%02x", byte))
            }
            stringBuilder.toString()
        } catch (e: NoSuchAlgorithmException) {
            e.printStackTrace()
            password // Return the password itself if hashing fails (not ideal)
        }
    }
}
