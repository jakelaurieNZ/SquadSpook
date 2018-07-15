package com.jakelaurie.squadspook.di.module

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.jakelaurie.squadspook.annotations.OpenForTesting
import com.jakelaurie.squadspook.data.network.PlayerApi
import com.jakelaurie.squadspook.data.network.ServerApi
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Named
import javax.inject.Singleton

@OpenForTesting
@Module
class NetworkModule {
    @Provides
    @Singleton
    fun providesOkHttpClient(): OkHttpClient = OkHttpClient.Builder().build()

    @Provides
    @Singleton
    fun providesGson(): Gson = GsonBuilder().create()

    @Provides
    @Singleton
    fun providesRetrofitBuilder(okHttpClient: OkHttpClient, gson : Gson): Retrofit.Builder {
        return Retrofit.Builder()
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
    }

    @Provides
    @Singleton
    fun providesServerApi(retrofitBuilder: Retrofit.Builder,
                          @Named("serverApiUrl") serverApiUrl: String): ServerApi {
        return retrofitBuilder
                .baseUrl(serverApiUrl)
                .build()
                .create(ServerApi::class.java)
    }

    @Provides
    @Singleton
    fun providesPlayerApi(retrofitBuilder: Retrofit.Builder,
                          @Named("playerApiUrl") playerApiUrl: String): PlayerApi {
        return retrofitBuilder
                .baseUrl(playerApiUrl)
                .build()
                .create(PlayerApi::class.java)
    }
}