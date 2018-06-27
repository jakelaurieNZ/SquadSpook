package com.jakelaurie.squadspook.ui.viewmodel.serverlist

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import com.jakelaurie.squadspook.data.repository.ServerRepository
import com.jakelaurie.squadspook.model.Server
import com.jakelaurie.squadspook.model.ServersFilter
import io.reactivex.Completable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.observers.DisposableObserver
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class ServerListViewModel @Inject constructor(): ViewModel() {
    var serversResult: MutableLiveData<List<Server>> = MutableLiveData()
    var serversError: MutableLiveData<String> = MutableLiveData()
    var serversFilter: MutableLiveData<ServersFilter> = MutableLiveData()

    private var databaseObservable: DisposableObserver<List<Server>>? = null
    private var networkObservable: DisposableObserver<List<Server>>? = null
    private var filterObservable: DisposableObserver<ServersFilter>? = null

    @Inject lateinit var serverRepository: ServerRepository

    fun queryDatabase(filter: ServersFilter) {
        databaseObservable?.let{
            if(!it.isDisposed){
                it.dispose()
            }
        }

        val databaseObservable = object : DisposableObserver<List<Server>>() {
            override fun onComplete() {}

            override fun onNext(servers: List<Server>) {
                serversResult.postValue(servers)
            }

            override fun onError(e: Throwable) {
                e.printStackTrace()
                serversError.postValue(e.cause.toString())
            }
        }

        serverRepository.fromDatabase(filter)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(databaseObservable)

        this.databaseObservable = databaseObservable
    }

    fun fetchFilter() {
        val filterObservable = object: DisposableObserver<ServersFilter>() {
            override fun onComplete() {}

            override fun onNext(filter: ServersFilter) {
                serversFilter.postValue(filter)
                queryDatabase(filter)
            }

            override fun onError(e: Throwable) {
                e.printStackTrace()
                serversError.postValue(e.cause.toString())

            }
        }

        serverRepository.getFilter()
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(filterObservable)

        this.filterObservable = filterObservable
    }

    fun refresh() {
        val networkObservable = object: DisposableObserver<List<Server>>() {
            override fun onComplete() {}
            override fun onNext(t: List<Server>) {}
            override fun onError(e: Throwable) {}
        }

        serverRepository.fromNetwork()
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(networkObservable)

        this.networkObservable = networkObservable
    }

    fun setFilter(filterEmptyServers: Boolean) {
        val value = serversFilter.value
        if(value != null) {
            value.filterEmptyServers = filterEmptyServers
            setFilter(value)
        }
    }

    private fun setFilter(filter: ServersFilter) {
        Completable.fromCallable { serverRepository.setFilter(filter) }
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe()
    }

    fun disposeElements() {
        databaseObservable?.let {
            if(!it.isDisposed){
                it.dispose()
            }
        }

        networkObservable?.let {
            if(!it.isDisposed) {
                it.dispose()
            }
        }

        filterObservable?.let {
            if(!it.isDisposed) {
                it.dispose()
            }
        }
    }

}
