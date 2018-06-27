package com.jakelaurie.squadspook.ui.viewmodel.serverdetail

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import javax.inject.Inject;

class PlayerListViewModelFactory @Inject constructor(
        private val playerListViewModel: PlayerListViewModel) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(PlayerListViewModel::class.java)) {
            return playerListViewModel as T
        }

        throw IllegalArgumentException("Unknown class" + modelClass.name)
    }
}
