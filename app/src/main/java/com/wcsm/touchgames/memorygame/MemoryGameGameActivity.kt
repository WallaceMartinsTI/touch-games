package com.wcsm.touchgames.memorygame

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.wcsm.touchgames.R
import com.wcsm.touchgames.databinding.ActivityMemoryGameGameBinding

class MemoryGameGameActivity : AppCompatActivity() {

    private val binding by lazy {
        ActivityMemoryGameGameBinding.inflate(layoutInflater)
    }

    private lateinit var rvContainer: RecyclerView

    // 16 Different Cards = 32 Pairs
    private val MGCards = listOf(
        MGCard(R.drawable.mg_audiotrack_24),
        MGCard(R.drawable.mg_audiotrack_24),
        MGCard(R.drawable.mg_celebration_24),
        MGCard(R.drawable.mg_celebration_24),
        MGCard(R.drawable.mg_star_24),
        MGCard(R.drawable.mg_star_24),
        MGCard(R.drawable.mg_sports_motorsports_24),
        MGCard(R.drawable.mg_sports_motorsports_24),
        MGCard(R.drawable.mg_vpn_key_24),
        MGCard(R.drawable.mg_vpn_key_24),
        MGCard(R.drawable.mg_work_24),
        MGCard(R.drawable.mg_work_24),
        MGCard(R.drawable.mg_sports_martial_arts_24),
        MGCard(R.drawable.mg_sports_martial_arts_24),
        MGCard(R.drawable.mg_sports_esports_24),
        MGCard(R.drawable.mg_sports_esports_24),
        MGCard(R.drawable.mg_spoke_24),
        MGCard(R.drawable.mg_spoke_24),
        MGCard(R.drawable.mg_wb_sunny_24),
        MGCard(R.drawable.mg_wb_sunny_24),
        MGCard(R.drawable.mg_watch_24),
        MGCard(R.drawable.mg_watch_24),
        MGCard(R.drawable.mg_wc_24),
        MGCard(R.drawable.mg_wc_24),
        MGCard(R.drawable.mg_workspace_premium_24),
        MGCard(R.drawable.mg_workspace_premium_24),
        MGCard(R.drawable.mg_volume_up_24),
        MGCard(R.drawable.mg_volume_up_24),
        MGCard(R.drawable.mg_videogame_asset_24),
        MGCard(R.drawable.mg_videogame_asset_24),
        MGCard(R.drawable.mg_vaping_rooms_24),
        MGCard(R.drawable.mg_vaping_rooms_24)
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        rvContainer = binding.mgRvContainer
        val btnPreviousScreen = binding.mgBtnPreviousScreen

        btnPreviousScreen.setOnClickListener {
            finish()
        }

        val endgameButton = binding.mgBtnEndgame
        val textView1 = binding.mgTextView1
        val textView2 = binding.mgTextView2

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
        val shuffledCards = MGCards.shuffled().toMutableList()
        rvContainer.adapter = CardsAdapter(this, shuffledCards, gameType, textView1, textView2, endgameButton)

        // Tests
        //val treatedCards = cards.toMutableList()
        //rvContainer.adapter = CardsAdapter(this, treatedCards, gameType, textView1, textView2, endgameButton)

        rvContainer.layoutManager = GridLayoutManager(this, 4)
    }

    fun restartGame() {
        val newShuffledCards = MGCards.shuffled().toMutableList()
        (rvContainer.adapter as? CardsAdapter)?.apply {
            resetCards(newShuffledCards)
        }
    }
}