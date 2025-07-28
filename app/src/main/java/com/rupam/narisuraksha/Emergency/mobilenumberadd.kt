package com.rupam.narisuraksha.Emergency

import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.firebase.Firebase
import com.google.firebase.database.FirebaseDatabase
import com.rupam.narisuraksha.R
import com.rupam.narisuraksha.databinding.ActivityMobilenumberaddBinding

class mobilenumberadd : AppCompatActivity() {

    private val binding: ActivityMobilenumberaddBinding by lazy {
        ActivityMobilenumberaddBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.save.setOnClickListener {
            val name = binding.usercontactname.text.toString().trim()
            val number = binding.usermobilenumber.text.toString().trim()

            if(name.isEmpty() || number.isEmpty()){
                binding.usercontactname.error = "Enter Name"
                binding.usermobilenumber.error = "Enter Mobile Number"
            }
            else{
                saveToFirebase(name, number)
            }
        }
    }

    private fun saveToFirebase(name : String, number: String){
        val ref = FirebaseDatabase.getInstance().getReference("emergency_contacts")
        val id = ref.push().key!!

        val contact = emergengydata(name,number)
        ref.child(id).setValue(contact)
            .addOnSuccessListener {
                Toast.makeText(this, "Saved Successfully", Toast.LENGTH_SHORT).show()
                binding.usercontactname.text.clear()
                binding.usermobilenumber.text.clear()
            }
            .addOnFailureListener {
                Toast.makeText(this, "Failed to Save", Toast.LENGTH_SHORT).show()
            }
    }
    
}