package com.jakelaurie.squadspook.di.module

import android.arch.lifecycle.ViewModelProvider
import android.arch.persistence.db.SupportSQLiteDatabase
import android.arch.persistence.room.OnConflictStrategy
import android.arch.persistence.room.Room
import android.arch.persistence.room.RoomDatabase
import android.content.ContentValues
import com.jakelaurie.squadspook.SquadApplication
import com.jakelaurie.squadspook.data.database.Database
import com.jakelaurie.squadspook.data.database.PlayersDao
import com.jakelaurie.squadspook.data.database.ServersDao
import com.jakelaurie.squadspook.data.preferences.Preferences
import com.jakelaurie.squadspook.ui.viewmodel.serverdetail.PlayerListViewModelFactory
import com.jakelaurie.squadspook.ui.viewmodel.serverlist.ServerListViewModelFactory
import dagger.Module
import dagger.Provides
import io.reactivex.Completable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Singleton

@Module class AppModule(val app: SquadApplication) {
    @Provides
    @Singleton
    fun provideApplication(): SquadApplication = app

    @Provides
    @Singleton
    fun provideDatabase(app: SquadApplication): Database
            = Room.databaseBuilder(app, Database::class.java, "db")
            .fallbackToDestructiveMigration()
            .addCallback(object: RoomDatabase.Callback() {

                override fun onCreate(db: SupportSQLiteDatabase) {
                    super.onCreate(db)
                    val contentValues = ContentValues()
                    contentValues.put("id", 0)
                    contentValues.put("filterEmptyServers", true)

                    Completable.fromCallable {
                        db.insert("serversFilter", OnConflictStrategy.ABORT, contentValues)
                    }
                            .subscribeOn(Schedulers.newThread())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe()
                }
            }).build()

    @Provides
    @Singleton
    fun provideServersDao(database: Database): ServersDao = database.serversDao()

    @Provides
    fun provideServerListViewModelFactory(
            factory: ServerListViewModelFactory): ViewModelProvider.Factory = factory

    @Provides
    @Singleton fun providePlayerDao(database: Database): PlayersDao
            = database.playersDao()

    @Provides fun providePlayerListViewModelFactory(
            factory: PlayerListViewModelFactory): ViewModelProvider.Factory = factory

    @Provides fun provideSharedPreferences(app: SquadApplication): Preferences
            = Preferences(app, "SquadSpookPrefs")
}

