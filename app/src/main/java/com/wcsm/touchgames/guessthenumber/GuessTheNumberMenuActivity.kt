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

        val btnEasyDifficult = binding.gtnBtnEasyDifficulty
        val btnMediumDifficult = binding.gtnBtnMediumDifficulty
        val btnHardDifficult = binding.gtnBtnHardDifficulty
        val btnInsaneDifficult = binding.gtnBtnInsaneDifficulty

        val btnBackToMainMenu = binding.gtnBtnBackMainScreen

        val intent = Intent(this, GuessTheNumberGameActivity::class.java)

        btnBackToMainMenu.setOnClickListener {
            finish()
        }

        // Difficult Buttons
        btnEasyDifficult.setOnClickListener {
            startGame(intent, GTNDifficulties.EASY)
        }

        btnMediumDifficult.setOnClickListener {
            startGame(intent, GTNDifficulties.MEDIUM)
        }

        btnHardDifficult.setOnClickListener {
            startGame(intent, GTNDifficulties.HARD)
        }

        btnInsaneDifficult.setOnClickListener {
            startGame(intent,  GTNDifficulties.INSANE)
        }
    }

    private fun startGame(intent: Intent, difficulty: GTNDifficulties) {
        intent.putExtra("difficulty", difficulty.difficultyCode)
        startActivity(intent)
    }
}