package com.example.carracegame

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.constraintlayout.widget.ConstraintLayout

class Home : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        val introBtn: ConstraintLayout = findViewById(R.id.introBtn)
        introBtn.setOnClickListener {
            startActivity(Intent(this@Home, MainActivity::class.java)) }

    }
}