package com.jakelaurie.squadspook.data.database

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy
import android.arch.persistence.room.Query
import com.jakelaurie.squadspook.model.Server
import com.jakelaurie.squadspook.model.ServersFilter
import io.reactivex.Flowable
import io.reactivex.Observable
import io.reactivex.Single

@Dao
interface ServersDao {
    @Query("SELECT * FROM server WHERE players > :minPlayerCount")
    fun queryServers(minPlayerCount: Int): Flowable<List<Server>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertServer(server: Server)

    @Query("DELETE FROM server WHERE parseTime < :time")
    fun removeBefore(time: Long)

    /**
     * Filter
     */
    @Query("SELECT * from serversFilter LIMIT 1")fun queryFilter(): Flowable<ServersFilter>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun setFilter(filter: ServersFilter)
}