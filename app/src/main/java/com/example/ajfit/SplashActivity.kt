package com.example.ajfit

import android.animation.ObjectAnimator
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.view.View
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import com.example.ajfit.databinding.ActivitySplashBinding

class SplashActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        val logo = findViewById<View>(R.id.splash_logo)

        // Animate the logo from the top to the center of the screen
        val translateAnimator = ObjectAnimator.ofFloat(logo, "translationY", -500f, 0f)  // Adjust -500f as per screen size
        translateAnimator.duration = 1500 // Duration of the animation
        translateAnimator.start()

        // After the animation finishes, navigate to MainActivity
        Handler().postDelayed({
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()  // Close SplashActivity so user can't go back
        }, 2000) // Set delay time to allow animation to finish
    }
}