package com.wcsm.touchgames

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatDelegate
import com.wcsm.touchgames.guessthenumber.GuessTheNumberMenuActivity
import com.wcsm.touchgames.jokenpo.JokenpoActivity

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        // Disable DARK MODE
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Open Games Buttons
        val btnOpenGuessTheNumber: Button = findViewById(R.id.btn_open_guess_the_number)
        val btnOpenJokenpo: Button = findViewById(R.id.btn_open_jokenpo)


        btnOpenGuessTheNumber.setOnClickListener {
            val intent = Intent(this, GuessTheNumberMenuActivity::class.java)
            startActivity(intent)
        }

        btnOpenJokenpo.setOnClickListener {
            val intent = Intent(this, JokenpoActivity::class.java)
            startActivity(intent)
        }

    }
}