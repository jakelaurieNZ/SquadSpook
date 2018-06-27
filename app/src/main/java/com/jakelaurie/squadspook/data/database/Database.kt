package com.jakelaurie.squadspook.data.database

import android.arch.persistence.room.Database
import android.arch.persistence.room.RoomDatabase
import com.jakelaurie.squadspook.model.Player
import com.jakelaurie.squadspook.model.Server
import com.jakelaurie.squadspook.model.ServersFilter

@Database(entities = arrayOf(Server::class, Player::class, ServersFilter::class), version = 7)
abstract class Database : RoomDatabase() {
    abstract fun serversDao(): ServersDao
    abstract fun playersDao(): PlayersDao
}