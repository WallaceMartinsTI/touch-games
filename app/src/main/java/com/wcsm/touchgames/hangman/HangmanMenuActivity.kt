package com.wcsm.touchgames.hangman

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.wcsm.touchgames.R
import com.wcsm.touchgames.databinding.ActivityHangmanMenuBinding

class HangmanMenuActivity : AppCompatActivity() {

    private val binding by lazy {ActivityHangmanMenuBinding.inflate(layoutInflater)}

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        val intent = Intent(this, HangmanGameActivity::class.java)

        with(binding) {
            hmBtnBackMainScreen.setOnClickListener {
                finish()
            }

            hmBtnModeNoTime.setOnClickListener {
                startGame(intent, HangmanGameTypes.NO_TIME)
            }

            hmBtnModeTime.setOnClickListener {
                startGame(intent, HangmanGameTypes.WITH_TIME)
            }
        }
    }

    private fun startGame(intent: Intent, gameType: HangmanGameTypes) {
        intent.putExtra("gameType", gameType)
        startActivity(intent)
    }
}