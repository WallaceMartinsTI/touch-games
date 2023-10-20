package com.wcsm.touchgames.memorygame

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.wcsm.touchgames.R

class MemoryGameMenuActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_memory_game_menu)

        val btnSingleplayer: Button = findViewById(R.id.mg_btn_singleplayer)
        val btnTwoPlayers: Button = findViewById(R.id.mg_btn_2players)
        val btnCountdown: Button = findViewById(R.id.mg_btn_countdown)

        val btnBackToMainMenu: Button = findViewById(R.id.mg_btn_back_main_screen)

        val intent = Intent(this, MemoryGameGameActivity::class.java)

        btnBackToMainMenu.setOnClickListener {
            finish()
        }

        btnSingleplayer.setOnClickListener {
            startGame(intent, MemoryGameGameTypes.SINGLEPLAYER)
        }
        btnTwoPlayers.setOnClickListener {
            startGame(intent, MemoryGameGameTypes.TWOPLAYERS)
        }
        btnCountdown.setOnClickListener {
            startGame(intent, MemoryGameGameTypes.COUNTDOWN)
        }
    }

    private fun startGame(intent: Intent, gameType: MemoryGameGameTypes) {
        intent.putExtra("gameType", gameType)
        startActivity(intent)
    }
}