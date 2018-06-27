package com.jakelaurie.squadspook

import android.app.Activity
import android.app.Application
import com.jakelaurie.squadspook.di.component.DaggerAppComponent
import com.jakelaurie.squadspook.di.module.AppModule
import com.jakelaurie.squadspook.di.module.NetworkModule
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasActivityInjector
import javax.inject.Inject

class SquadApplication: Application(), HasActivityInjector {

    @Inject lateinit var activityInjector: DispatchingAndroidInjector<Activity>

    override fun onCreate() {
        super.onCreate()

        DaggerAppComponent.builder()
                .appModule(AppModule(this))
                .networkModule(NetworkModule(BuildConfig.SERVER_URL, BuildConfig.PLAYERS_URL))
                .build()
                .inject(this)
    }

    override fun activityInjector(): AndroidInjector<Activity> = activityInjector
}