package com.jakelaurie.squadspook.data.network

import com.google.gson.JsonObject
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Query

interface ServerApi {
    @GET("servers/")
    fun getServers(
            @Query("version") version: String,
            @Query("sort") sort: String,
            @Query("fields[server]") fields: String,
            @Query("filter[game]") game: String
    ): Observable<JsonObject>
}