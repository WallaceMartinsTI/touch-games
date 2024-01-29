package com.wcsm.touchgames.guessthenumber

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.wcsm.touchgames.R
import android.widget.Button
import android.content.Intent
import com.wcsm.touchgames.databinding.ActivityGuessTheNumberMenuBinding

class GuessTheNumberMenuActivity : AppCompatActivity() {

    private val binding by lazy {ActivityGuessTheNumberMenuBinding.inflate(layoutInflater)}

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        val intent = Intent(this, GuessTheNumberGameActivity::class.java)

        // Difficult Buttons
        with(binding) {
            gtnBtnBackMainScreen.setOnClickListener {
                finish()
            }

            gtnBtnEasyDifficulty.setOnClickListener {
                startGame(intent, GTNDifficulties.EASY)
            }

            gtnBtnMediumDifficulty.setOnClickListener {
                startGame(intent, GTNDifficulties.MEDIUM)
            }

            gtnBtnHardDifficulty.setOnClickListener {
                startGame(intent, GTNDifficulties.HARD)
            }

            gtnBtnInsaneDifficulty.setOnClickListener {
                startGame(intent,  GTNDifficulties.INSANE)
            }
        }
    }

    private fun startGame(intent: Intent, difficulty: GTNDifficulties) {
        intent.putExtra("difficulty", difficulty.difficultyCode)
        startActivity(intent)
    }
}