package com.wcsm.touchgames

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.wcsm.touchgames.guessthenumber.GuessTheNumberMenuActivity
import com.wcsm.touchgames.jokenpo.JokenpoActivity

import com.google.gson.Gson
import com.wcsm.touchgames.databinding.ActivityMainBinding
import com.wcsm.touchgames.hangman.HangmanMenuActivity
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
                startActivity(Intent(applicationContext, HangmanMenuActivity::class.java))
            }
        }

        // JSON
        val assetManager = assets
        val inputStream = assetManager.open("hm_words.json")
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