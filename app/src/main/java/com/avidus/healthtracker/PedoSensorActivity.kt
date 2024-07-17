package com.avidus.healthtracker

import android.content.Context
import android.content.SharedPreferences
import android.content.pm.PackageManager
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.avidus.healthtracker.databinding.ActivityPedoSensorBinding
import java.text.SimpleDateFormat
import java.util.*

class PedoSensorActivity : AppCompatActivity(), SensorEventListener {

    private lateinit var sensorManager: SensorManager
    private var sensor: Sensor? = null
    private lateinit var binding: ActivityPedoSensorBinding
    private lateinit var sharedPreferences: SharedPreferences
    private val dateFormat = SimpleDateFormat("yyyyMMdd", Locale.getDefault())

    companion object {
        private const val PERMISSION_REQUEST_ACTIVITY_RECOGNITION = 1
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityPedoSensorBinding.inflate(layoutInflater)
        setContentView(binding.root)

        sensorManager = getSystemService(Context.SENSOR_SERVICE) as SensorManager
        sharedPreferences = getSharedPreferences("PedoSensorPrefs", Context.MODE_PRIVATE)

        // Check for permission
        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACTIVITY_RECOGNITION)
            != PackageManager.PERMISSION_GRANTED) {
            // Permission is not granted, request it
            ActivityCompat.requestPermissions(
                this,
                arrayOf(android.Manifest.permission.ACTIVITY_RECOGNITION),
                PERMISSION_REQUEST_ACTIVITY_RECOGNITION
            )
        } else {
            // Permission already granted
            setupSensor()
        }

        // Check if a new day has started
        checkForNewDay()

        // Reset button click listener
        binding.reset.setOnClickListener {
            resetSteps()
        }
    }

    private fun setupSensor() {
        sensor = sensorManager.getDefaultSensor(Sensor.TYPE_STEP_COUNTER)

        if (sensor == null) {
            Toast.makeText(this, "Sensor not found", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onResume() {
        super.onResume()
        sensor?.let {
            sensorManager.registerListener(this, it, SensorManager.SENSOR_DELAY_NORMAL)
        }
    }

    override fun onPause() {
        super.onPause()
        sensor?.let {
            sensorManager.unregisterListener(this)
        }
    }

    override fun onSensorChanged(event: SensorEvent?) {
        event?.let {
            val steps = it.values[0]
            val previousTotalSteps = sharedPreferences.getFloat("previousTotalSteps", 0f)
            val currentSteps = steps - previousTotalSteps

            binding.steps.text = currentSteps.toInt().toString()
            val remain= (5000-currentSteps.toInt()).toString()
            binding.remainSteps.text="$remain Steps more to Reach Goal"
            // Update UI or perform actions with the step count
        }
    }

    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {
        // Handle sensor accuracy changes if needed
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            PERMISSION_REQUEST_ACTIVITY_RECOGNITION -> {
                if ((grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
                    // Permission granted
                    setupSensor()
                } else {
                    // Permission denied
                    Toast.makeText(this, "Permission denied to access activity recognition", Toast.LENGTH_SHORT).show()
                }
                return
            }
        }
    }

    private fun checkForNewDay() {
        val currentDate = dateFormat.format(Date())
        val savedDate = sharedPreferences.getString("lastRecordedDate", currentDate)
        if (currentDate != savedDate) {
            resetSteps()
            with(sharedPreferences.edit()) {
                putString("lastRecordedDate", currentDate)
                apply()
            }
        }
    }

    private fun resetSteps() {
        val currentStepCount = sharedPreferences.getFloat("currentStepCount", 0f)
        val previousTotalSteps = sharedPreferences.getFloat("previousTotalSteps", 0f)
        with(sharedPreferences.edit()) {
            putFloat("previousTotalSteps", currentStepCount + previousTotalSteps)
            putFloat("currentStepCount", 0f)
            apply()
        }
        binding.steps.text = "0"
        binding.remainSteps.text = "5000 Steps more to Reach Goal"
    }
}
