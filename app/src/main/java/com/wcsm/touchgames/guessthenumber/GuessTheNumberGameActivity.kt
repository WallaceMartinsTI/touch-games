package com.wcsm.touchgames.guessthenumber

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.wcsm.touchgames.R
import com.google.android.material.textfield.TextInputEditText
import android.widget.Button
import android.widget.TextView
import kotlin.random.Random

class GuessTheNumberGameActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_guess_the_number_game)

        // Get Intent Data
        val bundle = intent.extras
        val difficulty = bundle?.getString("difficulty")


        val guessInputText: TextInputEditText = findViewById(R.id.gtn_guess_input_text)

        val btnGuess: Button = findViewById(R.id.gtn_btn_guess)
        val btnNewGame: Button = findViewById(R.id.gtn_btn_new_game)
        val btnPreviousScreen: Button = findViewById(R.id.gtn_btn_previous_screen)


        val attemptsQuantityField: TextView = findViewById(R.id.attempts_quantity)

        val winTextField: TextView = findViewById(R.id.gtn_win_text)


        var randomNumber = generateRandomNumber(difficulty)

        var attempts = 0

        // Clear all states for the New Game
        btnNewGame.setOnClickListener {
            attempts = 0
            btnGuess.isEnabled = true
            winTextField.text = ""
            guessInputText.text?.clear()
            attemptsQuantityField.text = attempts.toString()
            randomNumber = generateRandomNumber(difficulty)
        }

        btnGuess.setOnClickListener {
            val userGuess = guessInputText.text.toString().toInt()
            if (userGuess == randomNumber) {
                winTextField.text = when (attempts) {
                    0 -> "Parabéns! Você acertou na PRIMEIRA TENTATIVA!!!"
                    1 -> "Parabens! Você acertou após $attempts tentativa!"
                    else -> "Parabens! Você acertou após $attempts tentativas!"
                }
                btnGuess.isEnabled = false
            } else {
                ++attempts
                attemptsQuantityField.text = attempts.toString()
                guessInputText.text?.clear()
            }
        }

        btnPreviousScreen.setOnClickListener {
            finish()
        }

        setDifficultyChoosen(difficulty)

    }
    private fun generateRandomNumber(difficulty: String?): Int {
        val max = when (difficulty) {
            "easy" -> 5
            "medium" -> 10
            "hard" -> 50
            "insane" -> 100
            else -> 0
        }
        return Random.nextInt(1, max + 1)
    }

    private fun setDifficultyChoosen(difficulty: String?) {
        val difficultyChosenField: TextView = findViewById(R.id.gtn_difficulty_chosen)

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