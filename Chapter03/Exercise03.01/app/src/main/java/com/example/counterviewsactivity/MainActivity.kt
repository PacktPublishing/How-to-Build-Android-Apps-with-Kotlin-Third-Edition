package com.example.counterviewsactivity

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        var counter = 0
        val mainView = findViewById<ConstraintLayout>(R.id.main)
        val counterValue = mainView.findViewById<TextView>(R.id.counter_value)
        val plusButton = mainView.findViewById<Button>(R.id.plus)
        val minusButton = mainView.findViewById<Button>(R.id.minus)
        plusButton.setOnClickListener {
            counter++
            counterValue.text = counter.toString()
        }
        minusButton.setOnClickListener {
            if (counter > 0) {
                counter--
                counterValue.text = counter.toString()
            }
        }
    }
}