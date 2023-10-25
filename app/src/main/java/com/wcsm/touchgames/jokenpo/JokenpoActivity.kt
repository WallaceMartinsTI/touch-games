package com.wcsm.touchgames.jokenpo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import com.wcsm.touchgames.R
import kotlin.random.Random

class JokenpoActivity : AppCompatActivity() {

    private lateinit var imageApp: ImageView
    private lateinit var resultField: TextView
    private lateinit var appPointsField: TextView
    private lateinit var playerPointsField: TextView
    private lateinit var btnBackToMainMenu: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_jokenpo)

        imageApp = findViewById(R.id.jkp_image_app)
        resultField = findViewById(R.id.jkp_result)
        appPointsField = findViewById(R.id.jkp_app_points_field)
        playerPointsField = findViewById(R.id.jkp_player_points_field)
        btnBackToMainMenu = findViewById(R.id.jkp_btn_back_main_screen)

        btnBackToMainMenu.setOnClickListener {
            finish()
        }
    }

    // Pontuation
    private var appPoints = 0
    private var playerPoints = 0

    fun selectStone(view: View) {
        checkWinner(JKPMoves.STONE)
    }

    fun selectPaper(view: View) {
        checkWinner(JKPMoves.PAPER)
    }

    fun selectScissors(view: View) {
        checkWinner(JKPMoves.SCISSORS)
    }

    fun restartGame(view: View) {
        imageApp.setImageResource(R.drawable.jkp_default)
        resultField.text = getString(R.string.jkp_result)
        appPoints = 0
        playerPoints = 0
        appPointsField.text = appPoints.toString()
        playerPointsField.text = playerPoints.toString()
    }

    private fun generateAppChoice(): JKPMoves {
        val options = listOf(JKPMoves.STONE, JKPMoves.PAPER, JKPMoves.SCISSORS)
        val randomNumber = Random.nextInt(3)
        val appChoice = options[randomNumber]

        when (appChoice) {
            JKPMoves.STONE -> imageApp.setImageResource(R.drawable.jkp_stone)
            JKPMoves.PAPER -> imageApp.setImageResource(R.drawable.jkp_paper)
            JKPMoves.SCISSORS -> imageApp.setImageResource(R.drawable.jkp_scissors)
        }

        return appChoice
    }

    private fun checkWinner(playerChoice: JKPMoves) {
        val appChoice = generateAppChoice()

        if (
            (appChoice == JKPMoves.STONE && playerChoice == JKPMoves.SCISSORS) ||
            (appChoice == JKPMoves.PAPER && playerChoice == JKPMoves.STONE) ||
            (appChoice == JKPMoves.SCISSORS && playerChoice == JKPMoves.PAPER)
        ) {
            appPointsField.text = (++appPoints).toString()
            resultField.text = getString(R.string.jkp_app_win)
        } else if (
            (playerChoice == JKPMoves.STONE && appChoice == JKPMoves.SCISSORS) ||
            (playerChoice == JKPMoves.PAPER && appChoice == JKPMoves.STONE) ||
            (playerChoice == JKPMoves.SCISSORS && appChoice == JKPMoves.PAPER)
        ) {
            playerPointsField.text = (++playerPoints).toString()
            resultField.text = getString(R.string.jkp_player_win)
        } else {
            resultField.text = getString(R.string.jkp_draw)
        }
    }
}