package com.wcsm.touchgames.memorygame

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.wcsm.touchgames.R

class MemoryGameGameActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_memory_game_game)

        val rvContainer: RecyclerView = findViewById(R.id.mg_rv_container)
        val btnNewGame: Button = findViewById(R.id.mg_btn_new_game)
        val btnPreviousScreen: Button = findViewById(R.id.mg_btn_previous_screen)

        btnPreviousScreen.setOnClickListener {
            finish()
        }

        val textView1: TextView = findViewById(R.id.mg_textView1)
        val textView2: TextView = findViewById(R.id.mg_textView2)

        var gameType: MemoryGameGameTypes? = null

        val bundle = intent.extras

        if(bundle != null) {
            //val gameTypeReceived = bundle.getSerializable("gameType") as MemoryGameGameTypes

            when (bundle.getSerializable("gameType") as MemoryGameGameTypes) {
                MemoryGameGameTypes.SINGLEPLAYER -> {
                    gameType =  MemoryGameGameTypes.SINGLEPLAYER
                    textView1.text = "Pontos: 0"
                    textView2.text = "Tempo: 00:00"
                }
                MemoryGameGameTypes.TWOPLAYERS -> {
                    gameType = MemoryGameGameTypes.TWOPLAYERS
                }
                MemoryGameGameTypes.COUNTDOWN -> {
                    gameType = MemoryGameGameTypes.COUNTDOWN
                }
            }
        }

        // 10 Different Cards = 20 Pairs
        val cards = listOf(
            Card(R.drawable.mg_audiotrack_24),
            Card(R.drawable.mg_audiotrack_24),

            Card(R.drawable.mg_celebration_24),
            Card(R.drawable.mg_celebration_24),

            Card(R.drawable.mg_star_24),
            Card(R.drawable.mg_star_24),

            Card(R.drawable.mg_sports_motorsports_24),
            Card(R.drawable.mg_sports_motorsports_24),

            Card(R.drawable.mg_vpn_key_24),
            Card(R.drawable.mg_vpn_key_24),

            Card(R.drawable.mg_work_24),
            Card(R.drawable.mg_work_24),

            Card(R.drawable.mg_sports_martial_arts_24),
            Card(R.drawable.mg_sports_martial_arts_24),

            Card(R.drawable.mg_sports_esports_24),
            Card(R.drawable.mg_sports_esports_24),

            Card(R.drawable.mg_spoke_24),
            Card(R.drawable.mg_spoke_24),

            Card(R.drawable.mg_wb_sunny_24),
            Card(R.drawable.mg_wb_sunny_24)
        )

        // Método Original
        //val shuffledCards = cards.shuffled().toMutableList()
        //rvContainer.adapter = CardsAdapter(shuffledCards)

        // Tests
        val treatedCards = cards.toMutableList()
        rvContainer.adapter = CardsAdapter(treatedCards, gameType, textView1, textView2)

        rvContainer.layoutManager = GridLayoutManager(this, 4)

        // Separar no LOG Temporário
        Log.i("MEMORY_GAME", "==========================================================================================================")
    }
}