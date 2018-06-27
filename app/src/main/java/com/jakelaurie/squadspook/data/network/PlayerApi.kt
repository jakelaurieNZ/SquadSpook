package com.jakelaurie.squadspook.data.network

import com.google.gson.JsonObject
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Path

interface PlayerApi {
    @GET("servers/{serverId}")
    fun getServerDetails(
            @Path("serverId") serverId: String
    ): Observable<JsonObject>
}