package com.wcsm.touchgames

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.wcsm.touchgames.guessthenumber.GuessTheNumberMenuActivity
import com.wcsm.touchgames.jokenpo.JokenpoActivity

import com.google.gson.Gson
import com.wcsm.touchgames.databinding.ActivityMainBinding
import com.wcsm.touchgames.memorygame.MemoryGameMenuActivity

class MainActivity : AppCompatActivity() {

    private val binding by lazy {ActivityMainBinding.inflate(layoutInflater)}

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        // Open Games Buttons
        with(binding) {
            btnOpenGuessTheNumber.setOnClickListener {
                val intent = Intent(applicationContext, GuessTheNumberMenuActivity::class.java)
                startActivity(intent)
            }

            btnOpenJokenpo.setOnClickListener {
                val intent = Intent(applicationContext, JokenpoActivity::class.java)
                startActivity(intent)
            }

            btnOpenMemoryGame.setOnClickListener {
                val intent = Intent(applicationContext, MemoryGameMenuActivity::class.java)
                startActivity(intent)
            }
        }

        // JSON
        val assetManager = assets
        val inputStream = assetManager.open("words.json")
        val json = inputStream.bufferedReader().use { it.readText() }

        data class Words(
            val worldCountries: List<String>,
            val famousCitiesInTheWorld: List<String>,
            val animals: List<String>,
            val foods: List<String>,
            val movieCharacters: List<String>,
            val sports: List<String>,
            val christmasWords: List<String>,
            val professions: List<String>,
            val brandsAnCompanies: List<String>,
            val soccerTeams: List<String>,
            val celebrityNames: List<String>,
            val colors: List<String>,
            val heroes: List<String>,
            val techs: List<String>,
            val villains: List<String>,
        )

        val gson = Gson()
        val words = gson.fromJson(json, Words::class.java)

        println("=========================================================================")
        println(words.animals)
        // [Leão, Elefante, Girafa, Tigre, Cachorro, Gato, Zebra, Urso, Tubarão, Golfinho, Águia, Tartaruga, Crocodilo, Panda, Cavalo]
        // Entendido, continuar daqui!
    }


}