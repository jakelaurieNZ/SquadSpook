package com.jakelaurie.squadspook.data.repository

import com.jakelaurie.squadspook.data.database.PlayersDao
import com.jakelaurie.squadspook.data.network.PlayerApi
import com.jakelaurie.squadspook.model.Player
import com.jakelaurie.squadspook.model.Server
import io.reactivex.Observable
import javax.inject.Inject

class PlayerRepository @Inject constructor(private val playerApi: PlayerApi,
                                           private val playersDao: PlayersDao) {
    fun getPlayers(serverId: String): Observable<List<Player>> {
        val networkObservable = getPlayersFromApi(serverId)
        val databaseObservable = getPlayersFromDatabase(serverId)
        return Observable.concatArray(databaseObservable, networkObservable)
    }

    private fun getPlayersFromApi(serverId: String): Observable<List<Player>> {
        val parseTime = System.currentTimeMillis()
        return playerApi.getServerDetails(serverId).flatMap {
            Observable.fromArray(Player.fromArray(Server.extractPlayers(it), serverId))
        }.doOnNext {
            it.forEach {
                playersDao.insertPlayer(it)
            }

            playersDao.removeBefore(parseTime, serverId)
        }
    }

    private fun getPlayersFromDatabase(serverId: String): Observable<List<Player>> {
        return playersDao.queryPlayers(serverId).toObservable()
    }
}