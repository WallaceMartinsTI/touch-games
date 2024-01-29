package com.wcsm.touchgames

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.wcsm.touchgames.guessthenumber.GuessTheNumberMenuActivity
import com.wcsm.touchgames.jokenpo.JokenpoActivity
import com.wcsm.touchgames.databinding.ActivityMainBinding
import com.wcsm.touchgames.hangman.HangmanGameActivity
import com.wcsm.touchgames.memorygame.MemoryGameMenuActivity

class MainActivity : AppCompatActivity() {

    private val binding by lazy {ActivityMainBinding.inflate(layoutInflater)}

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        // Open Games Buttons
        with(binding) {
            btnOpenGuessTheNumber.setOnClickListener {
                startActivity(Intent(applicationContext, GuessTheNumberMenuActivity::class.java))
            }

            btnOpenJokenpo.setOnClickListener {
                startActivity(Intent(applicationContext, JokenpoActivity::class.java))
            }

            btnOpenMemoryGame.setOnClickListener {
                startActivity(Intent(applicationContext, MemoryGameMenuActivity::class.java))
            }

            btnOpenHangman.setOnClickListener {
                startActivity(Intent(applicationContext, HangmanGameActivity::class.java))
            }
        }
    }


}