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
            val bmiIntent = Intent(this, BMIActivity::class.java)
            startActivity(bmiIntent)
        }

        val pedoSensor= binding.imagePedoMeter
        pedoSensor.setOnClickListener {
        val pedoMeter= Intent(this, PedoSensorActivity::class.java)
            startActivity(pedoMeter)
        }

        val healthTips= binding.imageTips
        healthTips.setOnClickListener {
            val healthTips= Intent(this, HeathTipsActivity::class.java)
            startActivity(healthTips)
        }

        val yoga= binding.imageYoga
        yoga.setOnClickListener {
            val yoga= Intent(this, YogaActivity::class.java)
            startActivity(yoga)
        }
    }
}