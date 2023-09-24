package com.wcsm.touchgames

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.wcsm.touchgames.guessthenumber.GuessTheNumberMenuActivity

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val btnOpenGuessTheNumber: Button = findViewById(R.id.btn_open_guess_the_number)

        btnOpenGuessTheNumber.setOnClickListener {
            val intent = Intent(this, GuessTheNumberMenuActivity::class.java)

            startActivity(intent)
            println("Rodou!")
        }

    }
}