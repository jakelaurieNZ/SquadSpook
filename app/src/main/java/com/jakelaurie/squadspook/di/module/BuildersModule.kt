package com.jakelaurie.squadspook.di.module

import com.jakelaurie.squadspook.ui.ServerDetailActivity
import com.jakelaurie.squadspook.ui.ServerListActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class BuildersModule {

    @ContributesAndroidInjector
    abstract fun contributeServerListActivity(): ServerListActivity

    @ContributesAndroidInjector
    abstract fun contributeServerDetailActivity(): ServerDetailActivity
}