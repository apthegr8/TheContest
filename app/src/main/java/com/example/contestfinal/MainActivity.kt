package com.example.contestfinal

import android.graphics.Color
import android.media.MediaPlayer
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    companion object {
        const val KEY_COUNT = "count"
    }

    private lateinit var textView: TextView
    private lateinit var scoreButton: Button
    private lateinit var stealButton: Button
    private lateinit var resetButton: Button
    private lateinit var counter: Counter
    private lateinit var mediaPlayer: MediaPlayer

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        textView = findViewById(R.id.tv_view)
        scoreButton = findViewById(R.id.btn_score)
        stealButton = findViewById(R.id.btn_steal)
        resetButton = findViewById(R.id.btn_reset)

        mediaPlayer = MediaPlayer.create(this, R.raw.sound)
        counter = Counter(textView, mediaPlayer)

        if (savedInstanceState != null) {
            counter.restoreState(savedInstanceState)
        }

        scoreButton.setOnClickListener {
            counter.increment()
        }

        stealButton.setOnClickListener {
            counter.decrement()
        }

        resetButton.setOnClickListener {
            counter.reset()
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        counter.saveState(outState)
    }

    class Counter(private val textView: TextView, private val mediaPlayer: MediaPlayer) {
        private var count = 0

        fun increment() {
            if (count < 15) {
                count++
                updateDisplay()
                if (count == 15) {
                    playSound()
                }
            }
        }

        fun decrement() {
            if (count != 0) {
                count--
            }
            updateDisplay()
        }

        fun reset() {
            count = 0
            updateDisplay()
        }

        private fun updateDisplay() {
            textView.text = count.toString()

            // Update text color based on count value
            when {
                count >= 10 -> textView.setTextColor(Color.GREEN)
                count in 5..9 -> textView.setTextColor(Color.BLUE)
                else -> textView.setTextColor(Color.BLACK) // Default color
            }
        }

        fun saveState(outState: Bundle) {
            outState.putInt(KEY_COUNT, count)
        }

        fun restoreState(savedInstanceState: Bundle) {
            count = savedInstanceState.getInt(KEY_COUNT, 0)
            updateDisplay()
        }

        private fun playSound() {
            mediaPlayer.start()
        }
    }
}
