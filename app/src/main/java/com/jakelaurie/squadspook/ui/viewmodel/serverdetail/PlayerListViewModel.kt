package com.jakelaurie.squadspook.ui.viewmodel.serverdetail

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import com.jakelaurie.squadspook.data.repository.PlayerRepository
import com.jakelaurie.squadspook.model.Player
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.observers.DisposableObserver
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class PlayerListViewModel @Inject constructor(
        private val playerRepository: PlayerRepository): ViewModel() {

    var playersResult: MutableLiveData<List<Player>> = MutableLiveData()
    var playersError: MutableLiveData<String> = MutableLiveData()
    lateinit var disposableObserver: DisposableObserver<List<Player>>

    fun loadPlayers(serverId: String) {
        disposableObserver = object : DisposableObserver<List<Player>>() {
            override fun onComplete() {
            }

            override fun onNext(players: List<Player>) {
                playersResult.postValue(players)
            }

            override fun onError(e: Throwable) {
                e.printStackTrace()
                playersError.postValue(e.cause.toString())
            }
        }

        playerRepository.getPlayers(serverId)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(disposableObserver)
    }

    fun disposeElements() {
        if(!disposableObserver.isDisposed) {
            disposableObserver.dispose()
        }
    }
}
