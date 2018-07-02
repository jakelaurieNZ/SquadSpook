package com.jakelaurie.squadspook.di.module

import android.arch.lifecycle.ViewModelProvider
import com.jakelaurie.squadspook.ui.viewmodel.serverdetail.PlayerListViewModelFactory
import dagger.Module
import dagger.Provides

@Module
class ServerDetailActivityModule {
    @Provides
    fun providePlayerListViewModelFactory(
            factory: PlayerListViewModelFactory): ViewModelProvider.Factory = factory
}
