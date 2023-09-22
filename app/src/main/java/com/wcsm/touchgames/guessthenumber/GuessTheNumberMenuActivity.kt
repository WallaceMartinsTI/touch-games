package com.wcsm.touchgames.guessthenumber

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.wcsm.touchgames.R

class GuessTheNumberMenuActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_guess_the_number_menu)

        val btnBackToMainMenu: Button = findViewById(R.id.btn_gtn_back_menu)
        btnBackToMainMenu.setOnClickListener {
            finish()
        }
    }
}