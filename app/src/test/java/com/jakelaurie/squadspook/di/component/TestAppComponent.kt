package com.jakelaurie.squadspook.di.component

import com.jakelaurie.squadspook.SquadApplication
import com.jakelaurie.squadspook.di.module.AppModule
import com.jakelaurie.squadspook.di.module.NetworkModule
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjectionModule
import javax.inject.Named
import javax.inject.Singleton

@Singleton
@Component(
        modules = [
            AndroidInjectionModule::class,
            AppModule::class,
            NetworkModule::class
        ]
)
interface TestAppComponent {
    @Component.Builder interface Builder {
        fun build(): AppComponent

        @BindsInstance
        fun application(squadApplication: SquadApplication): Builder

        @BindsInstance
        fun serverApiUrl(@Named("serverApiUrl") serverApiUrl: String): Builder

        @BindsInstance
        fun playerApiUrl(@Named("playerApiUrl") playerApiUrl: String): Builder
    }

    fun inject(app: SquadApplication)
}