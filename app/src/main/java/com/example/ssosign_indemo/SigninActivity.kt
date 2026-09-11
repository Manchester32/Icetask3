package com.example.ssosign_indemo

import android.content.Intent
import android.os.Bundle
import android.util.Patterns
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class SigninActivity : AppCompatActivity() {

    private lateinit var emailEditText: EditText
    private lateinit var btnSsoSignIn: Button
    private lateinit var btnGoogle: Button
    private lateinit var btnMicrosoft: Button
    private lateinit var progressBar: ProgressBar
    private lateinit var errorText: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signin)

        emailEditText = findViewById(R.id.emailEditText)
        btnSsoSignIn = findViewById(R.id.btnSsoSignIn)
        btnGoogle = findViewById(R.id.btnGoogle)
        btnMicrosoft = findViewById(R.id.btnMicrosoft)
        progressBar = findViewById(R.id.progressBar)
        errorText = findViewById(R.id.errorText)

        // ICE Task 3: validate the email before continuing with SSO.
        btnSsoSignIn.setOnClickListener {
            validateEmailAndContinue()
        }

        // ICE Task 3 requires Google and Microsoft options on the screen.
        // They give user feedback in this base demo; SSO is the working flow.
        btnGoogle.setOnClickListener {
            Toast.makeText(
                this,
                "Google sign-in option selected. Please use Continue with SSO for this demo.",
                Toast.LENGTH_SHORT
            ).show()
        }

        btnMicrosoft.setOnClickListener {
            Toast.makeText(
                this,
                "Microsoft sign-in option selected. Please use Continue with SSO for this demo.",
                Toast.LENGTH_SHORT
            ).show()
        }
    }

    private fun validateEmailAndContinue() {
        val email = emailEditText.text.toString().trim()

        errorText.visibility = View.GONE

        if (email.isEmpty()) {
            showError("Email address is required")
            emailEditText.requestFocus()
            return
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            showError("Please enter a valid email address")
            emailEditText.requestFocus()
            return
        }

        simulateSsoAuthentication(email)
    }

    private fun simulateSsoAuthentication(email: String) {
        setLoading(true)

        // Short delay so the progress indicator is visible during authentication.
        progressBar.postDelayed({
            if (isFinishing || isDestroyed) return@postDelayed

            setLoading(false)

            Toast.makeText(
                this,
                "Authentication successful",
                Toast.LENGTH_SHORT
            ).show()

            val intent = Intent(this, WelcomeActivity::class.java).apply {
                putExtra("USER_EMAIL", email)
            }

            startActivity(intent)
        }, 900)
    }

    private fun setLoading(isLoading: Boolean) {
        progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE

        if (isLoading) {
            errorText.visibility = View.GONE
        }

        btnSsoSignIn.isEnabled = !isLoading
        btnGoogle.isEnabled = !isLoading
        btnMicrosoft.isEnabled = !isLoading
        emailEditText.isEnabled = !isLoading
    }

    private fun showError(message: String) {
        errorText.text = message
        errorText.visibility = View.VISIBLE
    }
}
