package com.avidus.healthtracker

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.avidus.healthtracker.databinding.ActivityBmiactivityBinding
import com.avidus.healthtracker.databinding.ActivityCardBinding

class BMIActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        val binding = ActivityBmiactivityBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val height = binding.editHeight
        val weight = binding.editWeight
        val calculateButton = binding.calculateButton
        val resultTextView = binding.ResultTextView
        val resultArea = binding.ResultArea

        calculateButton.setOnClickListener {
            val heightText = height.text.toString()
            val weightText = weight.text.toString()

            if (heightText.isNotEmpty() && weightText.isNotEmpty()) {
                val heightValue = heightText.toFloatOrNull()
                val weightValue = weightText.toFloatOrNull()

                if (heightValue != null && weightValue != null && heightValue > 0) {
                    // Assuming height is entered in centimeters
                    val heightInMeters = heightValue / 100
                    val bmi = weightValue / (heightInMeters * heightInMeters)
                    resultTextView.text = "%.2f".format(bmi)
                    if (bmi < 18.5) {
                        resultArea.text = "Underweight"
                    } else if (bmi >= 18.5 && bmi < 24.9) {
                        resultArea.text = "Normal"
                        }
                    else if(bmi >= 25 && bmi < 29.9){
                        resultArea.text = "Overweight"
                    }
                    else{
                        resultArea.text = "Obese"
                    }
                } else {
                    resultArea.text = "Please enter valid height and weight values"
                }
            } else {
                resultArea.text = "Please fill out both height and weight"
            }
        }
    }
}
