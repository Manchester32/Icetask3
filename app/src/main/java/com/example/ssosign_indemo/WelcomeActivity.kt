package com.example.ssosign_indemo

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class WelcomeActivity : AppCompatActivity() {

    private lateinit var emailText: TextView
    private lateinit var btnSignOut: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_welcome)

        emailText = findViewById(R.id.emailText)
        btnSignOut = findViewById(R.id.btnSignOut)

        val email =
            intent.getStringExtra("USER_EMAIL")

        emailText.text =
            email ?: "User signed in"

        btnSignOut.setOnClickListener {

            val intent = Intent(
                this,
                SigninActivity::class.java
            )

            intent.flags =
                Intent.FLAG_ACTIVITY_NEW_TASK or
                        Intent.FLAG_ACTIVITY_CLEAR_TASK

            startActivity(intent)
            finish()
        }
    }
}