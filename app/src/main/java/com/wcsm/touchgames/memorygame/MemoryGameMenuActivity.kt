package com.wcsm.touchgames.memorygame

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.wcsm.touchgames.R
import com.wcsm.touchgames.guessthenumber.GuessTheNumberGameActivity

class MemoryGameMenuActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_memory_game_menu)

        val btnEasyDifficult: Button = findViewById(R.id.mg_btn_easy_difficult)
        val btnMediumDifficult: Button = findViewById(R.id.mg_btn_medium_difficult)
        val btnHardDifficult: Button = findViewById(R.id.mg_btn_hard_difficult)

        val btnBackToMainMenu: Button = findViewById(R.id.mg_btn_back_main_screen)

        val intent = Intent(this, MemoryGameGameActivity::class.java)

        btnBackToMainMenu.setOnClickListener {
            finish()
        }

        btnEasyDifficult.setOnClickListener {
            startActivity(intent)
        }
    }
}