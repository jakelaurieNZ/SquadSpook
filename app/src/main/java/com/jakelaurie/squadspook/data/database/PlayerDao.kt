package com.jakelaurie.squadspook.data.database

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy
import android.arch.persistence.room.Query
import com.jakelaurie.squadspook.model.Player

import io.reactivex.Single

@Dao
interface PlayersDao {
    @Query("SELECT * FROM player WHERE serverId LIKE :serverId")
    fun queryPlayers(serverId: String): Single<List<Player>>

    @Query("SELECT * FROM player")
    fun allPlayers(): Single<List<Player>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertPlayer(player: Player)

    @Query("DELETE FROM player WHERE serverId LIKE :serverId")
    fun removeWithServerId(serverId: String)

    @Query("DELETE FROM player WHERE parseTime < :time AND serverId LIKE :serverId")
    fun removeBefore(time: Long, serverId: String)
}
