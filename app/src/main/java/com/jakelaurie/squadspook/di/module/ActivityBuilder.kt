package com.jakelaurie.squadspook.di.module

import com.jakelaurie.squadspook.ui.ServerDetailActivity
import com.jakelaurie.squadspook.ui.ServerListActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ActivityBuilder {
    @ContributesAndroidInjector(modules = [ServerListActivityModule::class])
    abstract fun contributeServerListActivity(): ServerListActivity

    @ContributesAndroidInjector(modules = [ServerListActivityModule::class])
    abstract fun contributeServerDetailActivity(): ServerDetailActivity
}