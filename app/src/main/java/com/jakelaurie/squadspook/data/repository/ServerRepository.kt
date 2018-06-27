package com.jakelaurie.squadspook.data.repository

import com.jakelaurie.squadspook.data.database.ServersDao
import com.jakelaurie.squadspook.data.network.ServerApi
import com.jakelaurie.squadspook.model.*
import io.reactivex.Observable
import javax.inject.Inject

class ServerRepository @Inject constructor(private val serverApi: ServerApi,
                                           private val serversDao: ServersDao) {

    fun fromNetwork(): Observable<List<Server>> {
        return getServersFromApi() //TODO: change result into some server result wrapper
    }

    fun fromDatabase(filter: ServersFilter): Observable<List<Server>> {
        return serversDao.queryServers(if (filter.filterEmptyServers) 0 else -1).toObservable()
    }

    private fun getServersFromApi(): Observable<List<Server>> {
        val parseTime = System.currentTimeMillis()
        return serverApi.getServers(
                "^0.1.0",
                "rank",
                "rank,name,players,maxPlayers,ip,port,country,location,details",
                "squad")
                .flatMap {
                    Observable.fromArray(Server.fromArray(it))
                }.doOnNext {
                    it.forEach {
                        serversDao.insertServer(it)
                    }
                } .doOnNext {
                    //Delete servers we didn't see in the response
                    serversDao.removeBefore(parseTime)
                }
    }

    /**
     * Filter
     */
    fun getFilter(): Observable<ServersFilter> {
        return serversDao.queryFilter().toObservable()
    }

    fun setFilter(filter: ServersFilter) {
        serversDao.setFilter(filter)
    }
}