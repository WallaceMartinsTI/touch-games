package com.wcsm.touchgames.memorygame

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
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

        val cards = listOf(
            Card(R.drawable.mg_audiotrack_24),
            Card(R.drawable.mg_audiotrack_24),

            Card(R.drawable.mg_celebration_24),
            Card(R.drawable.mg_celebration_24),
            /*Card(R.drawable.mg_star_24),
            Card(R.drawable.mg_star_24),
            Card(R.drawable.mg_star_24),
            Card(R.drawable.mg_sports_motorsports_24),

            Card(R.drawable.mg_sports_motorsports_24),
            Card(R.drawable.mg_vpn_key_24),
            Card(R.drawable.mg_vpn_key_24),
            Card(R.drawable.mg_work_24),
            Card(R.drawable.mg_work_24)*/

            //Card(R.drawable.mg_vpn_key_24),
            //Card(R.drawable.mg_work_24)

        )

        // Método Original
        //val shuffledCards = cards.shuffled().toMutableList()
        //rvContainer.adapter = CardsAdapter(shuffledCards)

        // Testes
        val treatedCards = cards.toMutableList()
        rvContainer.adapter = CardsAdapter(treatedCards)

        rvContainer.layoutManager = GridLayoutManager(this, 4)

        // Separar no LOG Temporário
        Log.i("MEMORY_GAME", "==========================================================================================================")
    }
}