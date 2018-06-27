package com.jakelaurie.squadspook.di.component

import com.jakelaurie.squadspook.SquadApplication
import com.jakelaurie.squadspook.di.module.AppModule
import com.jakelaurie.squadspook.di.module.BuildersModule
import com.jakelaurie.squadspook.di.module.NetworkModule
import dagger.Component
import dagger.android.AndroidInjectionModule
import javax.inject.Singleton

@Singleton
@Component(
        modules = arrayOf(
                AndroidInjectionModule::class,
                BuildersModule::class,
                AppModule::class,
                NetworkModule::class
        )
)
interface AppComponent {
    fun inject(app: SquadApplication)
}
