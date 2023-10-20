package com.wcsm.touchgames.memorygame


import android.os.Handler
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
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

    enum class Operations {
        PLUS, MINUS
    }

    inner class CardsViewHolder(private val itemView: View, val parentView: ViewGroup) : ViewHolder(itemView) {

        private val cardDefault: ImageView = itemView.findViewById(R.id.mg_card_default)

        //private var textView1: TextView = parentView.findViewById(R.id.mg_textView1)

        //var textView2: TextView = itemView.findViewById(R.id.mg_textView2)

        fun bind(card: Card) {
            //Log.i("MEMORY_GAME", "textView1: $textView1")

            cardDefault.setImageResource(R.drawable.mg_card_back) // código para mostrar os quadrados (carta pra baixo)

            //cardDefault.setImageResource(card.imageSrc) // código para mostrar os cards
            //Log.i("MEMORY_GAME", "listReceived: $initialList")



            itemView.setOnClickListener {
                // Cards Manipulation
                Log.i("MEMORY_GAME", "====================================================================")
                cardDefault.setImageResource(card.imageSrc)

                Log.i("MEMORY_GAME", "card: $card")
                Log.i("MEMORY_GAME", "selectedCard: $selectedCard")
                Log.i("MEMORY_GAME", "plays: $plays")

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

                        //singleplayerPoints += 50
                        singleplayerPoints = checkPontuation(Operations.PLUS, singleplayerPoints)
                        Log.i("MEMORY_GAME", "SOMOU PONTOS: $singleplayerPoints")

                        selectedCard = initialCard
                        plays = 0

                        // Pontuation and Time Manipulation
                        if(gameType == MemoryGameGameTypes.SINGLEPLAYER) {
                            Log.i("MEMORY_GAME", "Entrou no IF gameType = SINGLEPLAYER")
                            Log.i("MEMORY_GAME", "gameType: $gameType")
                            Log.i("MEMORY_GAME", "textView1: $textView1")
                            Log.i("MEMORY_GAME", "textView1.text: ${textView1.text}")
                            textView1.text = "Pontos: $singleplayerPoints"
                            Log.i("MEMORY_GAME", "APÓS textView1.text: ${textView1.text}")
                        }

                    }, 1000)

                } else {
                    Log.i("MEMORY_GAME", "${card.imageSrc} NÃO ESTAVA NO ARRAY E FOI ADICIONADO")

                    previousItemView = itemView
                    selectedCard = card

                    if(plays == 0) {
                        selectedCardImageView = cardDefault
                    }

                    if(plays == 1 && !cardsMatched) {
                        Log.i("MEMORY_GAME", "JOGOU 2X E NAO COMBINOU")
                        plays = 0

                        handler.postDelayed({
                            cardDefault.setImageResource(R.drawable.mg_card_back)
                            selectedCardImageView?.setImageResource(R.drawable.mg_card_back)

                            previousItemView = null

                            /*singleplayerPoints = if (singleplayerPoints != 0) {
                                singleplayerPoints - 50
                            } else {
                                0
                            }*/

                            singleplayerPoints = checkPontuation(Operations.MINUS, singleplayerPoints)
                            // fazer perda de pontos
                            // Pontuation and Time Manipulation
                            if(gameType == MemoryGameGameTypes.SINGLEPLAYER) {
                                Log.i("MEMORY_GAME", "Entrou no IF gameType = SINGLEPLAYER")
                                Log.i("MEMORY_GAME", "gameType: $gameType")
                                Log.i("MEMORY_GAME", "textView1: $textView1")
                                Log.i("MEMORY_GAME", "textView1.text: ${textView1.text}")
                                //textView1.text = "Pontos: $singleplayerPoints"
                                textView1.text = "Pontos: ${singleplayerPoints}"
                                Log.i("MEMORY_GAME", "APÓS textView1.text: ${textView1.text}")
                            }

                        }, 1000)

                        selectedCard = initialCard
                    } else {
                        plays++
                    }
                    cardsMatched = false
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CardsViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val itemView = layoutInflater.inflate(R.layout.mg_cards, parent, false)
        return CardsViewHolder(itemView, parent)
    }

    override fun onBindViewHolder(holder: CardsViewHolder, position: Int) {
        val card = list[position]
        holder.bind(card)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    fun checkPontuation(operation: Operations, actualPoints: Int) : Int {
        val points = 50
        var result = actualPoints

        if(operation == Operations.PLUS) {
            result = actualPoints + points
        } else if(operation == Operations.MINUS) {
            if(result != 0) {
                result  = actualPoints - points
            }
        }
        return result
    }

}