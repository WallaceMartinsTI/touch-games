package com.wcsm.touchgames.memorygame


import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.os.CountDownTimer
import android.os.Handler
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.Adapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.wcsm.touchgames.MainActivity
import com.wcsm.touchgames.R

enum class PlayerTurn {
    PLAYER1, PLAYER2
}

class CardsAdapter(
    private val context: Context,
    private val list: MutableList<Card>,
    private val gameType: MemoryGameGameTypes?,
    private var textView1: TextView,
    private var textView2: TextView,
    private val endgameButton: Button
) : Adapter<CardsAdapter.CardsViewHolder>() {
    private val initialCard = Card(0)
    private var plays = 0
    private var cardsMatched = false

    private val handler = Handler()

    private var selectedCard = initialCard;
    private var selectedCardImageView: ImageView? = null

    private var previousItemView: View? = null

    private var clicksCounter = 0

    // Singleplayer Variables
    private var singleplayerPoints = 0
    private var countUpTimer: CountUpTimer? = null
    private var singleplayerCombinations = 0
    private var timeElapsed: Long = 0

    // Two Players Variables
    private var firstPlayerPoints = 0
    private var secondPlayerPoints = 0
    private var turn = PlayerTurn.PLAYER1
    private var firstPlayerCombinations = 0
    private var secondPlayerCombinations = 0

    // Countdown Variables
    private var countdownPoints = 0
    private var countDownTimer: CountDownTimer? = null
    private var countdownCombinations = 0
    private var timeLeftInMillis: Long = 60000
    private var timeGainedInMillis: Long = 15000
    private var timeLeft: Long = 0

    init {
        countUpTimer = object : CountUpTimer(1000) {
            override fun onTick(elapsedTime: Long) {
                timeElapsed = elapsedTime
                updateTimerUI(elapsedTime)
            }
        }

        if (gameType == MemoryGameGameTypes.TWOPLAYERS) {
            textView1.text = "1º Jogador: $firstPlayerPoints"
            textView2.text = "2º Jogador: $secondPlayerPoints"
        } else if (gameType == MemoryGameGameTypes.COUNTDOWN) {
            textView1.text = "Pontos: $countdownPoints"
            textView2.text = "Tempo: 00:00"
            startCountdownCounter()
        }
    }

    enum class Operations {
        PLUS, MINUS
    }


    inner class CardsViewHolder(private val itemView: View) : ViewHolder(itemView) {
        private val cardDefault: ImageView = itemView.findViewById(R.id.mg_card_default)

        private var playerTurnColor =
            ContextCompat.getColor(itemView.context, R.color.mg_player_turn)
        private var playerDefaultColor = ContextCompat.getColor(itemView.context, R.color.black)

        fun bind(card: Card) {
            if (gameType == MemoryGameGameTypes.TWOPLAYERS) {
                textView1.setTextColor(playerTurnColor)
            }

            cardDefault.setImageResource(R.drawable.mg_card_back) // original methods (show cards hidden)
            //cardDefault.setImageResource(card.imageSrc) // code to show cards

            itemView.setOnClickListener {
                clicksCounter++

                if (clicksCounter <= 2) {
                    // Cards Manipulation
                    cardDefault.setImageResource(card.imageSrc)

                    if (card == selectedCard) {
                        // Wait 1 second before turn cards invisible
                        handler.postDelayed({
                            card.isMatched = true
                            selectedCard.isMatched = true

                            // Check if all cards are matched and reset if necessary
                            checkCardsMatchedAndReset()

                            // PreviousItem
                            previousItemView!!.visibility = View.INVISIBLE
                            previousItemView!!.isEnabled = false

                            // Actual Item
                            itemView.visibility = View.INVISIBLE
                            itemView.isEnabled = false

                            previousItemView = null
                            cardsMatched = true

                            selectedCard = initialCard
                            plays = 0

                            // Pontuation and Time Manipulation
                            if (gameType == MemoryGameGameTypes.SINGLEPLAYER) {
                                singleplayerPoints =
                                    checkPontuation(Operations.PLUS, singleplayerPoints)
                                textView1.text = "Pontos: $singleplayerPoints"
                                singleplayerCombinations++
                            } else if (gameType == MemoryGameGameTypes.TWOPLAYERS) {
                                if (turn == PlayerTurn.PLAYER1) {
                                    firstPlayerPoints =
                                        checkPontuation(Operations.PLUS, firstPlayerPoints)
                                    textView1.text = "1º Jogador: $firstPlayerPoints"
                                    firstPlayerCombinations++
                                } else if (turn == PlayerTurn.PLAYER2) {
                                    secondPlayerPoints =
                                        checkPontuation(Operations.PLUS, secondPlayerPoints)
                                    textView2.text = "2º Jogador: $secondPlayerPoints"
                                    secondPlayerCombinations++
                                }
                            } else if (gameType == MemoryGameGameTypes.COUNTDOWN) {
                                countDownTimer?.cancel()
                                timeLeftInMillis += timeGainedInMillis
                                startCountdownCounter()
                                updateCountdownUI()
                                countdownCombinations++
                                countdownPoints = checkPontuation(Operations.PLUS, countdownPoints)
                                textView1.text = "Pontos: $countdownPoints"
                            }
                            itemView.isEnabled = true
                            clicksCounter = 0
                        }, 1000)
                    } else {
                        selectedCard = card

                        if (plays == 0) {
                            selectedCardImageView = cardDefault
                            previousItemView = itemView
                            previousItemView!!.isEnabled = false
                        } else {
                            previousItemView!!.isEnabled = true
                        }

                        if (plays == 1 && !cardsMatched) {
                            plays = 0
                            handler.postDelayed({
                                cardDefault.setImageResource(R.drawable.mg_card_back)
                                selectedCardImageView?.setImageResource(R.drawable.mg_card_back)

                                previousItemView = null

                                // Losing Points if cards don't combine
                                if (gameType == MemoryGameGameTypes.SINGLEPLAYER) {
                                    singleplayerPoints =
                                        checkPontuation(Operations.MINUS, singleplayerPoints)
                                    textView1.text = "Pontos: ${singleplayerPoints}"
                                } else if (gameType == MemoryGameGameTypes.TWOPLAYERS) {
                                    if (turn == PlayerTurn.PLAYER1) {
                                        firstPlayerPoints =
                                            checkPontuation(Operations.MINUS, firstPlayerPoints)
                                        textView1.text = "1º Jogador: $firstPlayerPoints"
                                        turn = PlayerTurn.PLAYER2
                                        changeTurnColors(turn, playerTurnColor, playerDefaultColor)
                                    } else if (turn == PlayerTurn.PLAYER2) {
                                        secondPlayerPoints =
                                            checkPontuation(Operations.MINUS, secondPlayerPoints)
                                        textView2.text = "2º Jogador: $secondPlayerPoints"
                                        turn = PlayerTurn.PLAYER1
                                        changeTurnColors(turn, playerTurnColor, playerDefaultColor)
                                    }
                                } else if (gameType == MemoryGameGameTypes.COUNTDOWN) {
                                    countdownPoints =
                                        checkPontuation(Operations.MINUS, countdownPoints)
                                    textView1.text = "Pontos: ${countdownPoints}"
                                }

                                itemView.isEnabled = true
                                clicksCounter = 0
                            }, 1000)

                            selectedCard = initialCard
                        } else {
                            plays++
                        }
                        cardsMatched = false
                    }
                    itemView.isEnabled = false
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CardsViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val itemView = layoutInflater.inflate(R.layout.mg_cards, parent, false)
        return CardsViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: CardsViewHolder, position: Int) {
        val card = list[position]
        holder.bind(card)

        if (gameType == MemoryGameGameTypes.SINGLEPLAYER) {
            startCountUpTimer()
        }

        endgameButton.setOnClickListener {
            showEndgameDialog()
        }
    }

    override fun getItemCount(): Int {
        return list.size
    }

    private fun checkPontuation(operation: Operations, actualPoints: Int): Int {
        val pointsToWin = 20
        val pointsToLose = 5
        var result = actualPoints

        if (operation == Operations.PLUS) {
            result = actualPoints + pointsToWin
        } else if (operation == Operations.MINUS) {
            if (result != 0) {
                result = actualPoints - pointsToLose
            }
        }
        return result
    }

    fun changeTurnColors(newTurn: PlayerTurn, turnColor: Int, defaultColor: Int) {
        if (newTurn == PlayerTurn.PLAYER1) {
            textView2.setTextColor(defaultColor)
            textView1.setTextColor(turnColor)
        } else if (newTurn == PlayerTurn.PLAYER2) {
            textView1.setTextColor(defaultColor)
            textView2.setTextColor(turnColor)
        }
    }

    private fun startCountUpTimer() {
        countUpTimer?.start()
    }

    private fun updateTimerUI(elapsedTime: Long) {
        val minutes = (elapsedTime / 1000) / 60
        val seconds = (elapsedTime / 1000) % 60
        val formattedTime = String.format("%02d:%02d", minutes, seconds)
        textView2.text = "Tempo: $formattedTime"
    }

    private fun startCountdownCounter() {
        countDownTimer = object : CountDownTimer(timeLeftInMillis, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                timeLeftInMillis = millisUntilFinished
                timeLeft = timeLeftInMillis
                updateCountdownUI()
            }

            override fun onFinish() {
                // When counter finish
                val message =
                    "ESTATÍSTICAS\nCombinações: ${countdownCombinations}\nPontos feitos: $countdownPoints"
                endgameDialog(context, "Seu tempo ACABOU!", message)
            }
        }.start()
    }

    private fun updateCountdownUI() {
        val minutes = (timeLeftInMillis / 1000) / 60
        val seconds = (timeLeftInMillis / 1000) % 60
        val formattedTime = String.format("%02d:%02d", minutes, seconds)
        textView2.text = "Tempo: $formattedTime"
    }

    private fun endgameDialog(context: Context, title: String, message: String) {
        AlertDialog.Builder(context)
            .setTitle(title)
            .setMessage(message)
            .setPositiveButton("Novo Jogo") { dialog, _ ->
                // Code to be executed when the "Novo Jogo" button is clicked
                val intent = Intent(context, MemoryGameMenuActivity::class.java)
                context.startActivity(intent)

                dialog.dismiss()
            }
            .setNegativeButton("Menu Inicial") { dialog, _ ->
                // Code to be executed when the "Menu Inicial" button is clicked
                val intent = Intent(context, MainActivity::class.java)
                context.startActivity(intent)

                dialog.dismiss()
            }
            .show()
    }

    private fun showEndgameDialog() {
        var message = ""
        when (gameType) {
            MemoryGameGameTypes.SINGLEPLAYER -> {
                countUpTimer?.stop()
                val minutes = (timeElapsed / 1000) / 60
                val seconds = (timeElapsed / 1000) % 60
                val finalTimeElapsed = String.format("%02d:%02d", minutes, seconds)
                message =
                    "ESTATÍSTICAS\nCombinações: ${singleplayerCombinations}\nPontos feitos: $singleplayerPoints\nDuração: $finalTimeElapsed"
            }

            MemoryGameGameTypes.TWOPLAYERS -> {
                message =
                    "ESTATÍSTICAS\n=> Jogador 1 <=\nCombinações: ${firstPlayerCombinations}\nPontos: $firstPlayerPoints\n\n" +
                            "=> Jogador 2 <=\nCombinações: $secondPlayerCombinations\nPontos: $secondPlayerPoints"
            }

            MemoryGameGameTypes.COUNTDOWN -> {
                countDownTimer?.cancel()
                val minutes = (timeLeft / 1000) / 60
                val seconds = (timeLeft / 1000) % 60
                val finalTimeLeft = String.format("%02d:%02d", minutes, seconds)
                message =
                    "ESTATÍSTICAS\nCombinações: ${countdownCombinations}\nPontos feitos: $countdownPoints\nTempo restante: $finalTimeLeft"
            }

            else -> {
                message = "HOUVE ALGUM ERRO"
            }
        }
        endgameDialog(context, "Fim de Jogo!", message)
    }

    private fun checkAllCardsMatched(): Boolean {
        for(card in list) {
            if(!card.isMatched) {
                return false
            }
        }
        return true
    }

    private fun checkCardsMatchedAndReset() {
        if(checkAllCardsMatched()) {
            showEndgameDialog()
        }
    }
}
