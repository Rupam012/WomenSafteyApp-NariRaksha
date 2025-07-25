package com.rupam.narisuraksha

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.firebase.auth.FirebaseAuth
import com.rupam.narisuraksha.databinding.ActivityRegistrationscreenBinding

class registrationscreen : AppCompatActivity() {
    private val binding: ActivityRegistrationscreenBinding by lazy {
        ActivityRegistrationscreenBinding.inflate(layoutInflater)
    }

    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        //Intialize Firebase Auth
        auth = FirebaseAuth.getInstance()

        var isPasswordVisible = false

        binding.password.setOnTouchListener { v, event ->
            val DRAWABLE_END = 2
            if (event.action == android.view.MotionEvent.ACTION_UP) {
                val drawableEnd = binding.password.compoundDrawables[DRAWABLE_END]
                if (drawableEnd != null && event.rawX >= (binding.password.right - drawableEnd.bounds.width())) {
                    // Toggle password visibility
                    isPasswordVisible = !isPasswordVisible
                    if (isPasswordVisible) {
                        binding.password.transformationMethod = android.text.method.HideReturnsTransformationMethod.getInstance()
                        binding.password.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.eye, 0)
                    } else {
                        binding.password.transformationMethod = android.text.method.PasswordTransformationMethod.getInstance()
                        binding.password.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.eye, 0)
                    }
                    binding.password.setSelection(binding.password.text.length)
                    return@setOnTouchListener true
                }
            }
            false
        }

        binding.rgntolgn.setOnClickListener {
            startActivity(Intent(this, Loginpage::class.java))
            finish()
        }

        binding.signup.setOnClickListener {
            val email = binding.email.text.toString()
            val password = binding.password.text.toString()
            val username = binding.username.text.toString()

            if (email.isEmpty() || password.isEmpty() || username.isEmpty()){
                binding.email.error = "Enter Email"
                binding.password.error = "Enter Password"
                binding.username.error = "Enter Username"
            }
            else{
                auth.createUserWithEmailAndPassword(email,password)
                    .addOnCompleteListener(this) { task ->
                        if(task.isSuccessful){
                            Toast.makeText(this, "Sign Up Successful", Toast.LENGTH_SHORT).show()
                            startActivity(Intent(this, Loginpage::class.java))
                            finish()
                        }
                        else{
                            Toast.makeText(this, "Sign Up Failed ${task.exception?.message}", Toast.LENGTH_SHORT).show()
                        }
                    }
            }
        }
    }
}