package com.wcsm.touchgames.memorygame


import android.os.Handler
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView.Adapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.wcsm.touchgames.R

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

    private var singleplayerPoints = 0
    private var countUpTimer: CountUpTimer? = null

    init {
        countUpTimer = object : CountUpTimer(1000) {
            override fun onTick(elapsedTime: Long) {
                updateTimerUI(elapsedTime)
            }
        }
    }

    enum class Operations {
        PLUS, MINUS
    }

    inner class CardsViewHolder(private val itemView: View) : ViewHolder(itemView) {

        private val cardDefault: ImageView = itemView.findViewById(R.id.mg_card_default)

        fun bind(card: Card) {

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
                        singleplayerPoints = checkPontuation(Operations.PLUS, singleplayerPoints)

                        selectedCard = initialCard
                        plays = 0

                        // Pontuation and Time Manipulation
                        if(gameType == MemoryGameGameTypes.SINGLEPLAYER) {
                            textView1.text = "Pontos: $singleplayerPoints"
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

                            singleplayerPoints = checkPontuation(Operations.MINUS, singleplayerPoints)

                            // Losing Points if cards don't combine
                            if(gameType == MemoryGameGameTypes.SINGLEPLAYER) {
                                textView1.text = "Pontos: ${singleplayerPoints}"
                                Log.i("MEMORY_GAME", "APÓS textView1.text: ${textView1.text}")
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
        val pointsToWin = 50
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

    private fun startCountUpTimer() {
        countUpTimer?.start()
    }

    private fun updateTimerUI(elapsedTime: Long) {
        val minutes = (elapsedTime / 1000) / 60
        val seconds = (elapsedTime / 1000) % 60
        textView2.text = String.format("%02d:%02d", minutes, seconds)
    }
}
