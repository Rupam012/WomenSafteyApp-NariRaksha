package com.rupam.narisuraksha

import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.core.app.ActivityCompat
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.rupam.narisuraksha.databinding.ActivitySosMapsBinding

class sosMaps : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap
    private lateinit var binding: ActivitySosMapsBinding

    private lateinit var fusedLocationProviderClient: FusedLocationProviderClient

    private val Call_req_code = 1
    private val location_req_code = 2


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivitySosMapsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this)

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)

        //Auto SOS action
        requestCallPremession()
        sendUserLocationToPoliceWhatsapp()

        binding.cancelButton.setOnClickListener {
            Toast.makeText(this, "SOS Cancelled", Toast.LENGTH_SHORT).show()
            finish()
        }
    }


    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap

        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED){
            fusedLocationProviderClient.lastLocation.addOnSuccessListener { location ->
                if (location != null){
                    val userLocation = LatLng(location.latitude, location.longitude)
                    mMap.addMarker(MarkerOptions().position(userLocation).title("Your Location"))
                    mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(userLocation, 16f))
                }
            }
        }
    }

    private fun requestCallPremession(){
        if(ActivityCompat.checkSelfPermission(this, android.Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this, arrayOf(android.Manifest.permission.CALL_PHONE), Call_req_code)
        }
        else{
            makePoliceCall()
        }
    }

    private fun makePoliceCall(){
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

    private fun sendUserLocationToPoliceWhatsapp() {
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION), location_req_code)
            return
        }

        val locationRequest = com.google.android.gms.location.LocationRequest.create().apply {
            priority = com.google.android.gms.location.LocationRequest.PRIORITY_HIGH_ACCURACY
            interval = 1000
            fastestInterval = 500
            numUpdates = 1
        }

        val locationCallback = object : com.google.android.gms.location.LocationCallback() {
            override fun onLocationResult(locationResult: com.google.android.gms.location.LocationResult) {
                val location = locationResult.lastLocation
                if (location != null) {
                    val latitude = location.latitude
                    val longitude = location.longitude

                    val message = "I need help. My location is: https://maps.google.com/?q=$latitude,$longitude"
                    val encodedMessage = Uri.encode(message)

                    val url = "https://api.whatsapp.com/send?phone=918372084241&text=$encodedMessage"
                    val i = Intent(Intent.ACTION_VIEW)
                    i.data = Uri.parse(url)

                    Toast.makeText(applicationContext, "Sending location to WhatsApp", Toast.LENGTH_SHORT).show()
                    startActivity(i)
                } else {
                    Toast.makeText(applicationContext, "Unable to get location", Toast.LENGTH_SHORT).show()
                }
            }
        }

        fusedLocationProviderClient.requestLocationUpdates(locationRequest, locationCallback, null)
    }


    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResult: IntArray){
        super.onRequestPermissionsResult(requestCode, permissions, grantResult)

        when(requestCode){
            Call_req_code ->
                if (grantResult.isNotEmpty() && grantResult[0] == PackageManager.PERMISSION_GRANTED){
                    makePoliceCall()
                }
                else{
                    Toast.makeText(this, "Call permission Denied", Toast.LENGTH_SHORT).show()
                }
            location_req_code ->
                if (grantResult.isNotEmpty() && grantResult[0] == PackageManager.PERMISSION_GRANTED){
                    sendUserLocationToPoliceWhatsapp()
                }
                else{
                    Toast.makeText(this, "Location Permissioin is Denied", Toast.LENGTH_SHORT).show()
                }
        }
    }
}