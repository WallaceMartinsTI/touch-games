package com.wcsm.touchgames.guessthenumber

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.wcsm.touchgames.R
import com.google.android.material.textfield.TextInputEditText
import android.widget.Button
import android.widget.TextView
import com.google.android.material.textfield.TextInputLayout
import kotlin.random.Random

class GuessTheNumberGameActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_guess_the_number_game)

        // Get Intent Data
        val bundle = intent.extras
        val difficulty = bundle?.getString("difficulty")

        val guessInputText: TextInputEditText = findViewById(R.id.gtn_guess_input_text)
        val guessTextInputLayout: TextInputLayout = findViewById(R.id.gtn_text_input_layout)

        val btnGuess: Button = findViewById(R.id.gtn_btn_guess)
        val btnNewGame: Button = findViewById(R.id.gtn_btn_new_game)
        val btnPreviousScreen: Button = findViewById(R.id.gtn_btn_previous_screen)

        val attemptsQuantityField: TextView = findViewById(R.id.attempts_quantity)
        val winTextField: TextView = findViewById(R.id.gtn_win_text)

        val maxNumber = when(difficulty) {
            "easy" -> 5
            "medium" -> 10
            "hard" -> 50
            "insane" -> 100
            else -> 0
        }

        var randomNumber = generateRandomNumber(maxNumber)
        var attempts = 0

        // Clear all states for the New Game
        btnNewGame.setOnClickListener {
            attempts = 0
            btnGuess.isEnabled = true
            winTextField.text = ""
            guessInputText.text?.clear()
            attemptsQuantityField.text = attempts.toString()
            randomNumber = generateRandomNumber(maxNumber)
        }

        btnGuess.setOnClickListener {
            val userGuessInputed = guessInputText.text.toString()

            if (userGuessInputed.isEmpty()) {
                guessTextInputLayout.error = "Este campo é obrigatório"
            } else if (userGuessInputed.toInt() !in 1..maxNumber) {
                guessTextInputLayout.error = "Número inváido (1 a $maxNumber)"
            } else {
                guessTextInputLayout.error = null

                val userGuess = userGuessInputed.toInt()
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
        }

        btnPreviousScreen.setOnClickListener {
            finish()
        }

        setDifficultyChoosen(difficulty)
    }

    private fun generateRandomNumber(max: Int): Int {
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