package com.wcsm.touchgames.memorygame

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView.Adapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.wcsm.touchgames.R

class CardsAdapter(
    private val list: List<Card>
) : Adapter<CardsAdapter.CardsViewHolder>() {

    inner class CardsViewHolder(val itemView: View) : ViewHolder(itemView) {

        val cardDefault: ImageView = itemView.findViewById(R.id.mg_card_default)

        fun bind(card: Card) {
            //imageApp.setImageResource(R.drawable.jkp_paper)
            cardDefault.setImageResource(card.imageSrc)
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
    }

    override fun getItemCount(): Int {
        return list.size
    }
}