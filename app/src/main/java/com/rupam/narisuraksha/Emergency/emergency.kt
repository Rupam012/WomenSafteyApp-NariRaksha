package com.rupam.narisuraksha.Emergency

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.database.*
import com.rupam.narisuraksha.databinding.ActivityEmergencyBinding

class emergency : AppCompatActivity() {

    private lateinit var binding: ActivityEmergencyBinding
    private lateinit var adapter: Adapter
    private val data = ArrayList<emergengydata>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEmergencyBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Setup RecyclerView
        binding.recycleview.layoutManager = LinearLayoutManager(this)
        adapter = Adapter(data)
        binding.recycleview.adapter = adapter

        // Load from Firebase
        val ref = FirebaseDatabase.getInstance().getReference("emergency_contacts")
        ref.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                data.clear()
                for (contactSnap in snapshot.children) {
                    val contact = contactSnap.getValue(emergengydata::class.java)
                    if (contact != null) data.add(contact)
                }
                adapter.notifyDataSetChanged()
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(this@emergency, "Failed to load", Toast.LENGTH_SHORT).show()
            }
        })

        // Back button
        binding.back.setOnClickListener { finish() }

        // Add Number button
        binding.mobilenumberadd.setOnClickListener {
            startActivity(Intent(this, mobilenumberadd::class.java))
        }
    }
}
