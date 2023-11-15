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

    private lateinit var rvContainer: RecyclerView

    // 16 Different Cards = 32 Pairs
    private val cards = listOf(
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
        Card(R.drawable.mg_wb_sunny_24),
        Card(R.drawable.mg_watch_24),
        Card(R.drawable.mg_watch_24),
        Card(R.drawable.mg_wc_24),
        Card(R.drawable.mg_wc_24),
        Card(R.drawable.mg_workspace_premium_24),
        Card(R.drawable.mg_workspace_premium_24),
        Card(R.drawable.mg_volume_up_24),
        Card(R.drawable.mg_volume_up_24),
        Card(R.drawable.mg_videogame_asset_24),
        Card(R.drawable.mg_videogame_asset_24),
        Card(R.drawable.mg_vaping_rooms_24),
        Card(R.drawable.mg_vaping_rooms_24)
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_memory_game_game)

        rvContainer = findViewById(R.id.mg_rv_container)
        val btnPreviousScreen: Button = findViewById(R.id.mg_btn_previous_screen)

        btnPreviousScreen.setOnClickListener {
            finish()
        }

        val endgameButton: Button = findViewById(R.id.mg_btn_endgame)
        val textView1: TextView = findViewById(R.id.mg_textView1)
        val textView2: TextView = findViewById(R.id.mg_textView2)

        var gameType: MemoryGameGameTypes? = null

        val bundle = intent.extras

        if(bundle != null) {
            when (bundle.getSerializable("gameType") as MemoryGameGameTypes) {
                MemoryGameGameTypes.SINGLEPLAYER -> {
                    gameType =  MemoryGameGameTypes.SINGLEPLAYER
                    textView1.text = "Pontos: 0"
                    textView2.text = "Tempo: 00:00"
                }
                MemoryGameGameTypes.TWOPLAYERS -> {
                    gameType = MemoryGameGameTypes.TWOPLAYERS
                    textView1.text = "1º Jogador: 0"
                    textView2.text = "2º Jogador: 0"
                }
                MemoryGameGameTypes.COUNTDOWN -> {
                    gameType = MemoryGameGameTypes.COUNTDOWN
                    textView1.text = "Pontos: 0"
                    textView2.text = "Tempo: 00:00"
                }
            }
        }

        // Original Method
        val shuffledCards = cards.shuffled().toMutableList()
        rvContainer.adapter = CardsAdapter(this, shuffledCards, gameType, textView1, textView2, endgameButton)

        // Tests
        //val treatedCards = cards.toMutableList()
        //rvContainer.adapter = CardsAdapter(this, treatedCards, gameType, textView1, textView2, endgameButton)

        rvContainer.layoutManager = GridLayoutManager(this, 4)
    }

    fun restartGame() {
        val newShuffledCards = cards.shuffled().toMutableList()
        (rvContainer.adapter as? CardsAdapter)?.apply {
            resetCards(newShuffledCards)
        }
    }
}