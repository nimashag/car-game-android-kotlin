package com.example.carracegame

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.view.MotionEvent
import android.view.View
import java.util.ArrayList

class GameView(var c: Context, var gameTask: GameTask) : View(c) {
    // Paint object for drawing
    private var myPaint: Paint? = null

    // Game variables
    private var speed = 1
    private var time = 0
    private var score = 0
    private var myCarPosition = 0
    private val otherCars = ArrayList<HashMap<String, Any>>()


    // View dimensions
    var viewWidth = 0
    var viewHeight = 0

    init {
        myPaint = Paint()
    }

    // Function to end the game and notify the GameTask
    fun endGame() {
        gameTask.closeGame(score)
    }

    // rendering the game elements onto the canvas
    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        viewWidth = this.measuredWidth
        viewHeight = this.measuredHeight

        // Create new cars at random lanes
        if (time % 700 < 10 + speed) {
            val map = HashMap<String, Any>()
            map["lane"] = (0..2).random()
            map["startTime"] = time
            otherCars.add(map)
        }
        //Updating Time
        time = time + 10 + speed
        val carWidth = viewWidth / 5
        val carHeight = carWidth + 10

        // Draw user's car
        myPaint!!.style = Paint.Style.FILL
        val d = resources.getDrawable(R.drawable.red_car, null)

        d.setBounds(
            myCarPosition * viewWidth / 3 + viewWidth / 15 + 25,
            viewHeight - 2 - carHeight,
            myCarPosition * viewWidth / 3 + viewWidth / 15 + carWidth - 25,
            viewHeight - 2
        )
        d.draw(canvas)

        // Draw other cars and check for collisions
        myPaint!!.color = Color.GREEN
        var highScore = 0

        for (i in otherCars.indices) {
            try {
                val carX = otherCars[i]["lane"] as Int * viewWidth / 3 + viewWidth / 15
                var carY = time - otherCars[i]["startTime"] as Int
                val d2 = resources.getDrawable(R.drawable.yellow1_car, null)

                d2.setBounds(
                    carX + 25, carY - carHeight, carX + carWidth - 25, carY
                )

                d2.draw(canvas)
                // Check for collisions with user's car
                if (otherCars[i]["lane"] as Int == myCarPosition) {
                    if (carY > viewHeight - 2 - carHeight
                        && carY < viewHeight - 2
                    ) {
                        endGame()
                        return // Exit the loop and stop drawing
                    }
                }

                // Remove cars that have passed the screen and update score
                if (carY > viewHeight + carHeight) {
                    otherCars.removeAt(i)
                    score++
                    speed = 1 + Math.abs(score / 8)
                    if (score > highScore) {
                        highScore = score
                    }
                }

            } catch (e: Exception) {
                e.printStackTrace()
            }
        }

        // Draw score and speed
        myPaint!!.color = Color.WHITE
        myPaint!!.textSize = 40f
        canvas.drawText("Score : $score", 80f, 80f, myPaint!!)
        canvas.drawText("Speed : $speed", 380f, 80f, myPaint!!)
        invalidate()
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        when (event!!.action) {
            MotionEvent.ACTION_DOWN -> {
                val x1 = event.x

                // Move user's car based on touch position
                if (x1 < viewWidth / 2) {
                    if (myCarPosition > 0) {
                        myCarPosition--
                    }
                }
                if (x1 > viewWidth / 2) {
                    if (myCarPosition < 2) {
                        myCarPosition++
                    }
                }
                invalidate()
            }
            MotionEvent.ACTION_UP -> {
            }
        }
        return true
    }
}
