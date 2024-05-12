package com.example.carracegame

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.widget.Button
import android.widget.TextView

class RestartActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_restart)

        val restartButton: Button = findViewById(R.id.restartButton)
        val scoreTextView: TextView = findViewById(R.id.scoreTextView)
        val highScoreTextView: TextView = findViewById(R.id.highScoreTextView)
        val endGameButton: Button = findViewById(R.id.endGameButton)

        restartButton.setOnClickListener {
            startActivity(Intent(this@RestartActivity, MainActivity::class.java))
            finish() // Close this activity when starting MainActivity
        }

        endGameButton.setOnClickListener {
            finish() // Close this activity, effectively ending the game
        }

        val score = intent.getIntExtra("SCORE", 0)
        scoreTextView.text = "Your Score: $score"

        // Display the high score
        val sharedPreferences = getSharedPreferences("HighScore", Context.MODE_PRIVATE)
        val highScore = sharedPreferences.getInt("HIGH_SCORE", 0)
        highScoreTextView.text = "High Score: $highScore"
    }
}
