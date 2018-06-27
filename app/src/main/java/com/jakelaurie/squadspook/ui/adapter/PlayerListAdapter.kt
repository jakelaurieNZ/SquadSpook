package com.jakelaurie.squadspook.ui.adapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.jakelaurie.squadspook.R
import com.jakelaurie.squadspook.model.Player
import com.jakelaurie.squadspook.ui.viewholder.PlayerItemViewHolder
import javax.inject.Inject

class PlayerListAdapter @Inject constructor() : RecyclerView.Adapter<PlayerItemViewHolder>() {
    init {
        setHasStableIds(true)
    }

    var data: List<Player> = emptyList()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlayerItemViewHolder {
        return PlayerItemViewHolder(
                LayoutInflater.from(parent.context)
                        .inflate(R.layout.player_viewholder, parent, false)
        )
    }

    override fun onBindViewHolder(holder: PlayerItemViewHolder, position: Int) {
        holder.bind(data[position])
    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun getItemId(position: Int): Long {
        return data[position].id.toLongOrNull()?: super.getItemId(position)
    }
}