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

        val btnBackToMainMenu: Button = findViewById(R.id.btn_gtn_back_menu)

        val btnEasyDifficult: Button = findViewById(R.id.btn_easy_difficult)
        val btnMediumDifficult: Button = findViewById(R.id.btn_medium_difficult)
        val btnHardDifficult: Button = findViewById(R.id.btn_hard_difficult)
        val btnInsaneDifficult: Button = findViewById(R.id.btn_insane_difficult)

        val intent = Intent(this, GuessTheNumberGameActivity::class.java)

        btnBackToMainMenu.setOnClickListener {
            finish()
        }

        // Difficult Buttons
        btnEasyDifficult.setOnClickListener {
            startGame(intent, Difficulties.EASY)
        }

        btnMediumDifficult.setOnClickListener {
            startGame(intent, Difficulties.MEDIUM)
        }

        btnHardDifficult.setOnClickListener {
            startGame(intent, Difficulties.HARD)
        }

        btnInsaneDifficult.setOnClickListener {
            startGame(intent,  Difficulties.INSANE)
        }
    }

    private fun startGame(intent: Intent, difficulty: Difficulties) {
        intent.putExtra("difficulty", difficulty.difficultyCode)
        startActivity(intent)
    }


}