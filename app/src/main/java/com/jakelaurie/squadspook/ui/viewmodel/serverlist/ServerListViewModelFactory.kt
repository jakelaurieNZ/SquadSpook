package com.jakelaurie.squadspook.ui.viewmodel.serverlist

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import javax.inject.Inject

class ServerListViewModelFactory @Inject constructor(
        private val serverListViewModel: ServerListViewModel) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(ServerListViewModel::class.java)) {
            return serverListViewModel as T
        }

        throw IllegalArgumentException("Unknown class" + modelClass.name)
    }
}