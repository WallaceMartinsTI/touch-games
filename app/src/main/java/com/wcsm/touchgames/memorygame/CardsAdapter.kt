package com.wcsm.touchgames.memorygame


import android.os.CountDownTimer
import android.os.Handler
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView.Adapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.wcsm.touchgames.R

enum class PlayerTurn{
    PLAYER1, PLAYER2
}

class CardsAdapter(
    private val list: MutableList<Card>,
    private val gameType: MemoryGameGameTypes?,
    private var textView1: TextView,
    private var textView2: TextView
) : Adapter<CardsAdapter.CardsViewHolder>() {
    private val initialCard = Card(0)
    private var plays = 0
    private var cardsMatched = false

    private val handler = Handler()

    private var selectedCard = initialCard;
    private var selectedCardImageView: ImageView? = null

    private var previousItemView: View? = null

    // Singleplayer Variables
    private var singleplayerPoints = 0
    private var countUpTimer: CountUpTimer? = null

    // Two Players Variables
    private var firstPlayerPoints = 0
    private var secondPlayerPoints = 0
    private var turn = PlayerTurn.PLAYER1

    // Countdown Variables
    private var countdownPoints = 0
    private var countDownTimer: CountDownTimer? = null
    private var timeLeftInMillis: Long = 60000
    private var timeGainedInMillis: Long = 15000

    init {
        countUpTimer = object : CountUpTimer(1000) {
            override fun onTick(elapsedTime: Long) {
                updateTimerUI(elapsedTime)
            }
        }

        if(gameType == MemoryGameGameTypes.TWOPLAYERS) {
            textView1.text = "1º Jogador: $firstPlayerPoints"
            textView2.text = "2º Jogador: $secondPlayerPoints"
        } else if(gameType == MemoryGameGameTypes.COUNTDOWN) {
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

        private var playerTurnColor = ContextCompat.getColor(itemView.context, R.color.mg_player_turn)
        private var playerDefaultColor = ContextCompat.getColor(itemView.context, R.color.black)

        fun bind(card: Card) {
            if(gameType == MemoryGameGameTypes.TWOPLAYERS) {
                textView1.setTextColor(playerTurnColor)
            }

            cardDefault.setImageResource(R.drawable.mg_card_back) // código para mostrar os quadrados (carta pra baixo)
            //cardDefault.setImageResource(card.imageSrc) // código para mostrar os cards

            itemView.setOnClickListener {
                // Cards Manipulation
                Log.i("MEMORY_GAME", "====================================================================")
                cardDefault.setImageResource(card.imageSrc)

                //Log.i("MEMORY_GAME", "card: $card")
                //Log.i("MEMORY_GAME", "selectedCard: $selectedCard")
                //Log.i("MEMORY_GAME", "plays: $plays")

                if(card == selectedCard) {
                    Log.i("MEMORY_GAME", "${card.imageSrc} JÁ ESTÁ NO ARRAY")

                    // Wait 1 second before turn cards invisible
                    handler.postDelayed({
                        // PreviousItem
                        previousItemView!!.visibility = View.INVISIBLE
                        previousItemView!!.isEnabled = false

                        // Actual Item
                        itemView.visibility = View.INVISIBLE
                        itemView.isEnabled = false

                        previousItemView = null
                        cardsMatched = true

                        Log.i("MEMORY_GAME", "COMBINOU AS CARTAS")

                        selectedCard = initialCard
                        plays = 0

                        // Pontuation and Time Manipulation
                        if(gameType == MemoryGameGameTypes.SINGLEPLAYER) {
                            singleplayerPoints = checkPontuation(Operations.PLUS, singleplayerPoints)
                            textView1.text = "Pontos: $singleplayerPoints"
                        } else if(gameType == MemoryGameGameTypes.TWOPLAYERS) {
                            if(turn == PlayerTurn.PLAYER1) {
                                firstPlayerPoints = checkPontuation(Operations.PLUS, firstPlayerPoints)
                                textView1.text = "1º Jogador: $firstPlayerPoints"
                            } else if(turn == PlayerTurn.PLAYER2) {
                                secondPlayerPoints = checkPontuation(Operations.PLUS, secondPlayerPoints)
                                textView2.text = "2º Jogador: $secondPlayerPoints"
                            }
                        } else if(gameType == MemoryGameGameTypes.COUNTDOWN) {
                            countDownTimer?.cancel()
                            timeLeftInMillis += timeGainedInMillis
                            startCountdownCounter()
                            Log.i("MEMORY_GAME", "Tempo ganho: $timeGainedInMillis, Tempo restante: $timeLeftInMillis")
                            updateCountdownUI()

                            countdownPoints = checkPontuation(Operations.PLUS, countdownPoints)
                            textView1.text = "Pontos: $countdownPoints"
                        }
                        itemView.isEnabled = true
                    }, 1000)

                } else {
                    Log.i("MEMORY_GAME", "${card.imageSrc} NÃO ESTAVA NO ARRAY E FOI ADICIONADO")

                    selectedCard = card

                    if(plays == 0) {
                        selectedCardImageView = cardDefault
                        previousItemView = itemView
                        previousItemView!!.isEnabled = false
                    } else {
                        previousItemView!!.isEnabled = true
                    }

                    if(plays == 1 && !cardsMatched) {
                        Log.i("MEMORY_GAME", "JOGOU 2X E NAO COMBINOU")
                        plays = 0

                        handler.postDelayed({
                            cardDefault.setImageResource(R.drawable.mg_card_back)
                            selectedCardImageView?.setImageResource(R.drawable.mg_card_back)

                            previousItemView = null

                            // Losing Points if cards don't combine
                            if(gameType == MemoryGameGameTypes.SINGLEPLAYER) {
                                singleplayerPoints = checkPontuation(Operations.MINUS, singleplayerPoints)
                                textView1.text = "Pontos: ${singleplayerPoints}"
                            } else if(gameType == MemoryGameGameTypes.TWOPLAYERS) {
                                if(turn == PlayerTurn.PLAYER1) {
                                    firstPlayerPoints = checkPontuation(Operations.MINUS, firstPlayerPoints)
                                    textView1.text = "1º Jogador: $firstPlayerPoints"
                                    turn = PlayerTurn.PLAYER2
                                    changeTurnColors(turn, playerTurnColor, playerDefaultColor)
                                } else if(turn == PlayerTurn.PLAYER2) {
                                    secondPlayerPoints = checkPontuation(Operations.MINUS, secondPlayerPoints)
                                    textView2.text = "2º Jogador: $secondPlayerPoints"
                                    turn = PlayerTurn.PLAYER1
                                    changeTurnColors(turn, playerTurnColor, playerDefaultColor)
                                }
                            } else if(gameType == MemoryGameGameTypes.COUNTDOWN) {
                                // SE ERRAR ACONTECE AQUI:
                            }

                            itemView.isEnabled = true
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

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CardsViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val itemView = layoutInflater.inflate(R.layout.mg_cards, parent, false)
        return CardsViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: CardsViewHolder, position: Int) {
        val card = list[position]
        holder.bind(card)
        
        if(gameType == MemoryGameGameTypes.SINGLEPLAYER) {
            startCountUpTimer()
        }
    }

    override fun getItemCount(): Int {
        return list.size
    }

    private fun checkPontuation(operation: Operations, actualPoints: Int) : Int {
        val pointsToWin = 20
        val pointsToLose = 5
        var result = actualPoints

        if(operation == Operations.PLUS) {
            result = actualPoints + pointsToWin
        } else if(operation == Operations.MINUS) {
            if(result != 0) {
                result  = actualPoints - pointsToLose
            }
        }
        return result
    }

    fun changeTurnColors(newTurn: PlayerTurn, turnColor: Int, defaultColor: Int) {
        if(newTurn == PlayerTurn.PLAYER1) {
            textView2.setTextColor(defaultColor)
            textView1.setTextColor(turnColor)
        } else if(newTurn == PlayerTurn.PLAYER2) {
            textView1.setTextColor(defaultColor)
            textView2.setTextColor(turnColor)
        }
    }

    // Two Players Functions
    private fun startCountUpTimer() {
        countUpTimer?.start()
    }

    private fun updateTimerUI(elapsedTime: Long) {
        val minutes = (elapsedTime / 1000) / 60
        val seconds = (elapsedTime / 1000) % 60
        textView2.text = String.format("%02d:%02d", minutes, seconds)
    }

    // Countdown Functions
    private fun startCountdownCounter() {
        countDownTimer = object : CountDownTimer(timeLeftInMillis, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                timeLeftInMillis = millisUntilFinished
                updateCountdownUI()
            }

            override fun onFinish() {
                // QUANDO ZERAR O CONTADOR
            }
        }.start()
    }

    private fun updateCountdownUI() {
        val minutes = (timeLeftInMillis / 1000) / 60
        val seconds = (timeLeftInMillis / 1000) % 60
        textView2.text = String.format("%02d:%02d", minutes, seconds)
    }



}
