package com.example.carracegame

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout

// MainActivity class implementing GameTask interface
class MainActivity : AppCompatActivity(),GameTask {
    // Declaring variables
    lateinit var rootLayout :LinearLayout
    lateinit var startBtn :Button
    lateinit var mGameView : GameView
    lateinit var score: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Initializing UI elements
        startBtn = findViewById(R.id.startBtn)
        rootLayout = findViewById(R.id.rootLayout)
        score = findViewById(R.id.score)
        mGameView = GameView(this,this)

        // Setting onClickListener for startBtn
        startBtn.setOnClickListener {
            // Setting background resource for GameView
            mGameView.setBackgroundResource(R.drawable.road_0)
            // Adding GameView to rootLayout
            rootLayout.addView(mGameView)
            // Hiding startBtn and score TextView
            startBtn.visibility = View.GONE
            score.visibility = View.GONE
        }
    }

    // Function to save high score in SharedPreferences
    private fun saveHighScore(score: Int) {
        val sharedPreferences = getSharedPreferences("HighScore", Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        val highScore = sharedPreferences.getInt("HIGH_SCORE", 0)
        // Checking if the current score is higher than the saved high score
        if (score > highScore) {
            editor.putInt("HIGH_SCORE", score)
            editor.apply()
        }
    }

    // Function called when the game is closed
    override fun closeGame(mScore: Int) {
        // Saving high score
        saveHighScore(mScore)
        // Starting RestartActivity with the final score
        val intent = Intent(this@MainActivity, RestartActivity::class.java)
        intent.putExtra("SCORE", mScore)
        startActivity(intent)
        finish()
    }

}