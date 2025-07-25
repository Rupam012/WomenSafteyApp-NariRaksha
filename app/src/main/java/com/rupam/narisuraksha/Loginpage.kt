package com.rupam.narisuraksha

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.firebase.auth.FirebaseAuth
import com.rupam.narisuraksha.databinding.ActivityLoginpageBinding
import kotlinx.coroutines.MainScope

class Loginpage : AppCompatActivity() {
    private val binding: ActivityLoginpageBinding by lazy {
        ActivityLoginpageBinding.inflate(layoutInflater)
    }
    private lateinit var auth: FirebaseAuth


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        //Intialize Firebase
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

        binding.signupbtnlp.setOnClickListener {
            startActivity(Intent(this, registrationscreen::class.java))
            finish()
        }
        binding.forgetpass.setOnClickListener {
            startActivity(Intent(this, forgetpassword::class.java))
        }

        binding.loginbtn.setOnClickListener {
            val email = binding.email.text.toString()
            val password = binding.password.text.toString()

            if (email.isEmpty() || password.isEmpty()){
                binding.email.error = "Enter Email"
                binding.password.error = "Enter password"
            }else{
                auth.signInWithEmailAndPassword(email,password)
                    .addOnCompleteListener(this) { task ->
                        if(task.isSuccessful){
                            Toast.makeText(this, "Login Successful", Toast.LENGTH_SHORT).show()
                            val user = auth.currentUser
                            startActivity(Intent(this, MainActivity::class.java))
                            finish()
                        }
                        else{
                            Toast.makeText(this, "Login Failed ${task.exception?.message}", Toast.LENGTH_SHORT).show()
                        }
                    }
            }
        }
    }

    override fun onStart() {
        super.onStart()

        //checking the user is already login or not
        val currentuser = auth.currentUser
        if (currentuser != null){
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }
    }
}