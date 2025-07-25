package com.rupam.narisuraksha

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.util.Patterns
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.firebase.auth.FirebaseAuth
import com.rupam.narisuraksha.databinding.ActivityForgetpasswordBinding

class forgetpassword : AppCompatActivity() {
    private val binding: ActivityForgetpasswordBinding by lazy {
        ActivityForgetpasswordBinding.inflate(layoutInflater)
    }
    private lateinit var auth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        auth = FirebaseAuth.getInstance()

        binding.forget.setOnClickListener {
            val email = binding.forgetemail.text.toString().trim()
            if (email.isEmpty()){
                binding.forgetemail.error = "Enter Email"
            }
            else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                binding.forgetemail.error = "Enter a valid email"
            }
            else{
                auth.sendPasswordResetEmail(email)
                    .addOnCompleteListener { task ->
                        if (task.isSuccessful){
                            Log.d("ForgetPassword", "Reset email sent.")
                            Toast.makeText(this, "Email Sent", Toast.LENGTH_SHORT).show()
                            startActivity(Intent(this, Loginpage::class.java))
                            finish()
                        }
                        else{
                            Log.e("ForgetPassword", "Reset failed: ${task.exception?.message}")
                            Toast.makeText(this, "Failed ${task.exception?.message}", Toast.LENGTH_SHORT).show()
                        }
                    }
            }
        }
    }
}