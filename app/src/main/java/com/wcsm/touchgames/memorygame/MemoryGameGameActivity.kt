package com.wcsm.touchgames.memorygame

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import androidx.recyclerview.widget.RecyclerView
import com.wcsm.touchgames.R

class MemoryGameGameActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_memory_game_game)

        val rvContainer: RecyclerView = findViewById(R.id.mg_rv_container)
        val btnNewGame: Button = findViewById(R.id.mg_btn_new_game)
        val btnPreviousScreen: Button = findViewById(R.id.mg_btn_previous_screen)
    }
}