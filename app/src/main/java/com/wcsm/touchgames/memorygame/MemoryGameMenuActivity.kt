package com.wcsm.touchgames.memorygame

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.wcsm.touchgames.R
import com.wcsm.touchgames.databinding.ActivityMemoryGameMenuBinding

class MemoryGameMenuActivity : AppCompatActivity() {

    private val binding by lazy {
        ActivityMemoryGameMenuBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        val intent = Intent(this, MemoryGameGameActivity::class.java)

        with(binding) {
            mgBtnBackMainScreen.setOnClickListener {
                finish()
            }

            mgBtnSingleplayer.setOnClickListener {
                startGame(intent, MemoryGameGameTypes.SINGLEPLAYER)
            }

            mgBtnTwoPlayer.setOnClickListener {
                startGame(intent, MemoryGameGameTypes.TWOPLAYERS)
            }

            mgBtnCountdown.setOnClickListener {
                startGame(intent, MemoryGameGameTypes.COUNTDOWN)
            }
        }
    }

    private fun startGame(intent: Intent, gameType: MemoryGameGameTypes) {
        intent.putExtra("gameType", gameType)
        startActivity(intent)
    }
}