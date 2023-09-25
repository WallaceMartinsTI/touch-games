package com.wcsm.touchgames.guessthenumber

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.wcsm.touchgames.R
import com.google.android.material.textfield.TextInputEditText
import android.widget.Button
import android.widget.TextView

class GuessTheNumberGameActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_guess_the_number_game)

        // Get Intent Data
        val bundle = intent.extras
        val difficulty = bundle?.getString("difficulty")


        val guessInputText: TextInputEditText = findViewById(R.id.guess_input_text)
        val btnGuess: Button = findViewById(R.id.btn_guess)
        val btnPreviousScreen: Button = findViewById(R.id.btn_previous_screen)


        val attemptsQuantity: TextView = findViewById(R.id.attempts_quantity)

        val guess = guessInputText.text

        btnGuess.setOnClickListener {

        }

        btnPreviousScreen.setOnClickListener {
            finish()
        }

        setDifficultyChoosen(difficulty)
    }

    private fun setDifficultyChoosen(difficulty: String?) {
        val difficultyChosenField: TextView = findViewById(R.id.difficulty_chosen)

        if (difficulty != null) {
            difficultyChosenField.text = when (difficulty) {
                "easy" -> "FÁCIL (1 a 5)"
                "medium" -> "MÉDIO (1 a 10)"
                "hard" -> "DIFÍCIL (1 a 50)"
                "insane" -> "INSANO (1 a 100)"
                else -> difficultyChosenField.text
            }
        }
    }
}