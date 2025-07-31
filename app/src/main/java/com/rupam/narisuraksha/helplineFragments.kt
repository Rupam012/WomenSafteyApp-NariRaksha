package com.rupam.narisuraksha

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.rupam.narisuraksha.databinding.ActivityHelplineFragmentsBinding

class helplineFragments : AppCompatActivity() {

    private val call_req_code = 1
    private val binding: ActivityHelplineFragmentsBinding by lazy {
        ActivityHelplineFragmentsBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.support.setOnClickListener {
            requestCallPremession()
        }
        binding.techhelp.setOnClickListener {
            requestCallPremession()
        }
        binding.nationalhelp.setOnClickListener {
            requestCallPremession()
        }
        binding.backButton.setOnClickListener {
            Toast.makeText(this, "Redirecting to previous screen", Toast.LENGTH_SHORT).show()
            finish()
        }
    }

    private fun requestCallPremession(){
        if(ActivityCompat.checkSelfPermission(this, android.Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.CALL_PHONE), call_req_code)
        }
        else{
            makecall()
        }
    }
    private fun makecall(){
        val intent = Intent(Intent.ACTION_CALL)
        intent.data = Uri.parse("tel:8372084241")
        try {
            startActivity(intent)
        }catch (
            e: Exception
        ){
            Toast.makeText(this, "Something went wrong", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onRequestPermissionsResult(requestcode: Int, permissions: Array<out String>, grantResult: IntArray){
        super.onRequestPermissionsResult(requestcode, permissions, grantResult)
        when(requestcode){
            call_req_code ->
                if (grantResult.isNotEmpty() && grantResult[0] == PackageManager.PERMISSION_GRANTED){
                    makecall()
                }else{
                    Toast.makeText(this, "Call Permission Denied", Toast.LENGTH_SHORT).show()
                }
        }
    }
}