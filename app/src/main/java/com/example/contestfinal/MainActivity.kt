package com.example.contestfinal

import android.graphics.Color
import android.media.MediaPlayer
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    override fun onStart() {
        super.onStart()
        Log.i("LIFECYCLE", "onStart")
    }
    override fun onResume() {
        super.onResume()
        Log.i("LIFECYCLE", "onResume")
    }
    override fun onPause() {
        super.onPause()
        Log.i("LIFECYCLE", "onPause")
    }
    override fun onStop() {
        super.onStop()
        Log.i("LIFECYCLE", "onStop")
    }
    override fun onDestroy() {
        super.onDestroy()
        Log.i("LIFECYCLE", "onDestroy")
    }

    override fun onRestart() {
        super.onRestart()
        Log.i("LIFECYCLE", "onRestart")
    }
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
        Log.i("LIFECYCLE", "onCreate")

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
        Log.i("LIFECYCLE", "onSave")
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
