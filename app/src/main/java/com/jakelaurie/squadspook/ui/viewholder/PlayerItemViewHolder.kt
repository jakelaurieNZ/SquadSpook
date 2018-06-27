package com.jakelaurie.squadspook.ui.viewholder

import android.support.v7.widget.RecyclerView
import android.view.View
import com.jakelaurie.squadspook.model.Player
import kotlinx.android.synthetic.main.player_viewholder.view.*

class PlayerItemViewHolder(itemView: View?) : RecyclerView.ViewHolder(itemView) {

    fun bind(player: Player) {
        itemView.itemPlayerNameLabel.text = player.name
        itemView.itemPlayerScore.text = player.score.toString()
    }
}