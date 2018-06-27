package com.jakelaurie.squadspook.ui.viewholder

import android.support.v7.widget.RecyclerView
import android.view.View
import com.bumptech.glide.Glide
import com.jakelaurie.squadspook.R
import com.jakelaurie.squadspook.model.Server
import com.urizev.flags.lib.CountryFlag
import kotlinx.android.synthetic.main.server_viewholder.view.*
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.request.RequestOptions
import com.jakelaurie.squadspook.ui.UIUtils


class ServerItemViewHolder(itemView: View?) : RecyclerView.ViewHolder(itemView) {
    var server: Server? = null

    fun bind(server: Server) {
        this.server = server

        with(itemView) {
            itemServerNameLabel.text = server.name
            itemServerMapName.text = server.map
            itemServerPlayerCount.text =
                    context.getString(
                            R.string.server_map_players_format,
                            server.players,
                            server.maxPlayers
                    )

            Glide.with(context)
                    .load(CountryFlag.flag(context, server.country ?: "nz"))
                    .apply(RequestOptions()
                            .transforms(CenterCrop(), RoundedCorners(UIUtils.dpToPixels(35f))))
                    .into(itemServerIcon)
        }
    }

    fun setOnClickListener(listener: (Server?) -> Unit) {
        itemView.setOnClickListener { listener(server) }
    }
}
