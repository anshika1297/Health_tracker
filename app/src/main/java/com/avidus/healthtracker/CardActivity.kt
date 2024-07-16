package com.avidus.healthtracker

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.avidus.healthtracker.databinding.ActivityCardBinding

class CardActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        val binding= ActivityCardBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val bMIbutton = binding.imageBMI
        bMIbutton.setOnClickListener {
            val intent = Intent(this, BMIActivity::class.java)
            startActivity(intent)
        }

        val pedoSensor= binding.imagePedoMeter
        pedoSensor.setOnClickListener {
            val intent = Intent(this, pedoMeterActivity::class.java)
            startActivity(intent)
        }
    }
}