package com.rupam.narisuraksha

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.firebase.auth.FirebaseAuth
import com.rupam.narisuraksha.databinding.ActivityFirstPageBinding

class FirstPage : AppCompatActivity() {
    private val binding: ActivityFirstPageBinding by lazy{
        ActivityFirstPageBinding.inflate(layoutInflater)
    }

    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        auth = FirebaseAuth.getInstance()

        val currentuser = auth.currentUser
        if(currentuser != null){
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }

        installSplashScreen()
        Thread.sleep(3000)
        setContentView(binding.root)

        binding.getstarted.setOnClickListener {
            startActivity(Intent(this, Loginpage::class.java))
            finish()
        }
    }
}