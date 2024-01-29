package com.wcsm.touchgames.hangman

import android.graphics.Paint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.core.view.isVisible
import com.wcsm.touchgames.R
import com.wcsm.touchgames.databinding.ActivityHangmanGameBinding

class HangmanGameActivity : AppCompatActivity() {

    val TAG = "hangman"

    private val binding by lazy {ActivityHangmanGameBinding.inflate(layoutInflater)}

    private var lifes = 6

    private val emptyGibbet = R.drawable.hm_0
    private val gibbetWithHead = R.drawable.hm_1
    private val gibbetWithBody = R.drawable.hm_2
    private val gibbetWithOneArm = R.drawable.hm_3
    private val gibbetWithTwoArms = R.drawable.hm_4
    private val gibbetWithOneLeg = R.drawable.hm_5
    private val gibbetFull = R.drawable.hm_6

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        fillTextViews()
        underlineWord()

        val guess = binding.hmEditText.text

        with(binding) {
            hmBtnPreviousScreen.setOnClickListener {
                finish()
            }

            hmBtnEndgame.setOnClickListener {
                // Mostrar dialog com algumas informações
                lifes--
                binding.hmTime.text = "Vidas: $lifes"
                checkLives()
            }

            hmBtnCheck.setOnClickListener {
                Log.i(TAG, "guess: $guess")
                if(validateGuess(guess.toString())) {
                    Log.i(TAG, "valido: VALIDO")
                } else {
                    Log.i(TAG, "valido: INVALIDO")
                }
            }
        }
    }

    private fun validateGuess(guess: String): Boolean {
        val guessLayout = binding.hmGuessInputLayout
        guessLayout.error = null

        if(guess.isEmpty()) {
            guessLayout.error = "Dê um palpite"
            return false
        } else if (guess[0] !in 'A'..'Z') {
            guessLayout.error = "Digite uma letra \nentre (A - Z)"
            return false
        }

        return true
    }

    private fun underlineWord() {
        binding.hmWord.paintFlags = Paint.UNDERLINE_TEXT_FLAG
    }

    private fun fillTextViews() {
        binding.hmPoints.text = "Pontuação: 0"

        val bundle = intent.extras
        if(bundle != null) {
            when (bundle.getSerializable("gameType") as HangmanGameTypes) {
                HangmanGameTypes.NO_TIME -> {
                    //binding.hmTime.isVisible = false
                    binding.hmTime.text = "Vidas: $lifes"
                }
                HangmanGameTypes.WITH_TIME -> {
                    binding.hmTime.text = "Tempo: 00:00"
                }
            }
        }
    }

    private fun checkLives() {
        // Update Hangman Image State
        when(lifes){
            5 -> binding.hmImage.setImageResource(gibbetWithHead)
            4 -> binding.hmImage.setImageResource(gibbetWithBody)
            3 -> binding.hmImage.setImageResource(gibbetWithOneArm)
            2 -> binding.hmImage.setImageResource(gibbetWithTwoArms)
            1 -> binding.hmImage.setImageResource(gibbetWithOneLeg)
            0 -> binding.hmImage.setImageResource(gibbetFull)
        }

        if(lifes == 0) {
            // Game Over
        }
    }
}