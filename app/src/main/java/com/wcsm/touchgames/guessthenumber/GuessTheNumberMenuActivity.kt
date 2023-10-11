package com.wcsm.touchgames.guessthenumber

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.wcsm.touchgames.R
import android.widget.Button
import android.content.Intent

class GuessTheNumberMenuActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_guess_the_number_menu)

        val btnEasyDifficult: Button = findViewById(R.id.gtn_btn_easy_difficult)
        val btnMediumDifficult: Button = findViewById(R.id.gtn_btn_medium_difficult)
        val btnHardDifficult: Button = findViewById(R.id.gtn_btn_hard_difficult)
        val btnInsaneDifficult: Button = findViewById(R.id.gtn_btn_insane_difficult)

        val btnBackToMainMenu: Button = findViewById(R.id.gtn_btn_back_main_screen)

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