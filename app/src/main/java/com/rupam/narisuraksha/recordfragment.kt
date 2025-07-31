package com.rupam.narisuraksha

import android.Manifest
import android.content.pm.PackageManager
import android.media.MediaRecorder
import android.net.Uri
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.google.firebase.storage.FirebaseStorage
import com.rupam.narisuraksha.databinding.ActivityRecordfragmentBinding
import java.io.File
import java.io.IOException

class recordfragment : AppCompatActivity() {

    private val binding: ActivityRecordfragmentBinding by lazy {
        ActivityRecordfragmentBinding.inflate(layoutInflater)
    }

    private val reqPerCode = 1000
    private var mediaRecorder: MediaRecorder? = null
    private var outputFilePath: String = ""
    private var isRecording = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        // Check permissions on start
        if (!checkPermissions()) {
            requestPermissions()
        }

        // Toggle record button
        binding.recordbtn.setOnClickListener {
            if (checkPermissions()) {
                if (!isRecording) {
                    startRecording()
                } else {
                    stopRecording()
                }
            } else {
                requestPermissions()
            }
        }

        // Back button
        binding.backbutton.setOnClickListener {
            finish()
        }
    }

    private fun checkPermissions(): Boolean {
        val recordPermission = ContextCompat.checkSelfPermission(this, Manifest.permission.RECORD_AUDIO)
        return recordPermission == PackageManager.PERMISSION_GRANTED
    }

    private fun requestPermissions() {
        ActivityCompat.requestPermissions(
            this,
            arrayOf(Manifest.permission.RECORD_AUDIO),
            reqPerCode
        )
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == reqPerCode && grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            Toast.makeText(this, "Permission Granted", Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(this, "Permission Denied", Toast.LENGTH_SHORT).show()
        }
    }

    private fun startRecording() {
        outputFilePath = "${externalCacheDir?.absolutePath}/audiorecord.3gp"

        mediaRecorder = MediaRecorder().apply {
            setAudioSource(MediaRecorder.AudioSource.MIC)
            setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP)
            setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB)
            setOutputFile(outputFilePath)

            try {
                prepare()
                start()
                isRecording = true
                Toast.makeText(this@recordfragment, "Recording Started", Toast.LENGTH_SHORT).show()
                binding.wave.setImageResource(R.drawable.voiceimg) // Show waveform
                binding.recordbtn.setImageResource(R.drawable.recordimg) // Replace with stop icon if available
            } catch (e: IOException) {
                e.printStackTrace()
                Toast.makeText(this@recordfragment, "Recording failed", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun stopRecording() {
        try {
            mediaRecorder?.apply {
                stop()
                release()
            }
        } catch (e: Exception) {
            e.printStackTrace()
            Toast.makeText(this, "Error stopping recording", Toast.LENGTH_SHORT).show()
        }

        mediaRecorder = null
        isRecording = false
        binding.wave.setImageResource(R.drawable.voiceimg)
        binding.recordbtn.setImageResource(R.drawable.recordimg)

        // ✅ Upload to Firebase Storage
        uploadRecordingToFirebase()
    }

    private fun uploadRecordingToFirebase() {
        if (outputFilePath.isEmpty() || !File(outputFilePath).exists()) {
            Toast.makeText(this, "Recording file not found", Toast.LENGTH_SHORT).show()
            return
        }

        val fileUri = Uri.fromFile(File(outputFilePath))
        val storageRef = FirebaseStorage.getInstance().reference
        val audioRef = storageRef.child("recordings/${System.currentTimeMillis()}.3gp")

        audioRef.putFile(fileUri)
            .addOnSuccessListener {
                audioRef.downloadUrl.addOnSuccessListener { uri ->
                    Toast.makeText(this, "Uploaded!\nURL:\n$uri", Toast.LENGTH_LONG).show()
                }
            }
            .addOnFailureListener {
                Toast.makeText(this, "Upload failed: ${it.message}", Toast.LENGTH_SHORT).show()
            }
    }

    override fun onDestroy() {
        super.onDestroy()
        mediaRecorder?.release()
        mediaRecorder = null
    }
}
