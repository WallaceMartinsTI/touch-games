package com.wcsm.touchgames.memorygame

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView.Adapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.wcsm.touchgames.R

class CardsAdapter(
    private val list: MutableList<Card>,
) : Adapter<CardsAdapter.CardsViewHolder>() {

    private val initialCard = Card(0)
    private var plays = 0
    private var cardsMatched = false

    private var selectedCard = initialCard;
    private var previousItemView: View? = null

    inner class CardsViewHolder(private val itemView: View, val parentView: ViewGroup) : ViewHolder(itemView) {

        private val cardDefault: ImageView = itemView.findViewById(R.id.mg_card_default)

        fun bind(card: Card, pos: Int) {

            cardDefault.setImageResource(R.drawable.mg_square_24) // código para mostrar os quadrados (carta pra baixo)
            //cardDefault.setImageResource(card.imageSrc) // código para mostrar os cards
            //Log.i("MEMORY_GAME", "listReceived: $initialList")

            itemView.setOnClickListener {
                Log.i("MEMORY_GAME", "$itemView")
                Log.i("MEMORY_GAME", "$card")
                Log.i("MEMORY_GAME", "$pos")
                cardDefault.setImageResource(card.imageSrc)

                if(card ==  selectedCard) {
                    Log.i("MEMORY_GAME", "${card.imageSrc} JÁ ESTÁ NO ARRAY")

                    // PreviousItem
                    previousItemView!!.visibility = View.INVISIBLE
                    previousItemView!!.isEnabled = false

                    // Actual Item
                    itemView.visibility = View.INVISIBLE
                    itemView.isEnabled = false

                    previousItemView = null

                    cardsMatched = true
                } else {
                    previousItemView = itemView
                    selectedCard = card
                    Log.i("MEMORY_GAME", "${card.imageSrc} NÃO ESTAVA NO ARRAY E FOI ADICIONADO")
                }

                plays++

                if(plays == 2 && cardsMatched == true) {
                    // SOMAR PONTOS ou GANHAR TEMPO
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
        holder.bind(card, position)
    }

    override fun getItemCount(): Int {
        return list.size
    }

}