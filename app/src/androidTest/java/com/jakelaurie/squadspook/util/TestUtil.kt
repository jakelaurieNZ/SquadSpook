package com.jakelaurie.squadspook.util

import com.jakelaurie.squadspook.model.Player
import com.jakelaurie.squadspook.model.Server

object TestUtil {
    fun createServer(id: String) = Server(id)

    fun createPlayer(id: String, serverId: String): Player {
        val player = Player(id)
        player.serverId = serverId
        return player
    }
}