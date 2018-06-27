package com.jakelaurie.squadspook.ui.adapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.jakelaurie.squadspook.R
import com.jakelaurie.squadspook.model.Server
import com.jakelaurie.squadspook.ui.viewholder.ServerItemViewHolder
import javax.inject.Inject

class ServerListAdapter @Inject constructor() : RecyclerView.Adapter<ServerItemViewHolder>(){
    init {
        setHasStableIds(true)
    }

    var data: List<Server> = emptyList()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    var clickListener: (Server?) -> Unit = {}

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ServerItemViewHolder {
        return ServerItemViewHolder(
                LayoutInflater.from(parent.context)
                        .inflate(R.layout.server_viewholder, parent, false)
        )
    }

    override fun onBindViewHolder(holder: ServerItemViewHolder, position: Int) {
        holder.bind(data[position])
        holder.setOnClickListener(clickListener)
    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun getItemId(position: Int): Long {
        return data[position].id.toLongOrNull()?: super.getItemId(position)
    }
}