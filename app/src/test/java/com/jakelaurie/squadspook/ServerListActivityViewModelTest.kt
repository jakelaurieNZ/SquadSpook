package com.jakelaurie.squadspook

import android.arch.core.executor.testing.InstantTaskExecutorRule
import com.jakelaurie.squadspook.data.repository.ServerRepository
import com.jakelaurie.squadspook.model.ServersFilter
import com.jakelaurie.squadspook.ui.viewmodel.serverlist.ServerListViewModel
import com.jakelaurie.squadspook.util.TestException
import com.jakelaurie.squadspook.util.TestSchedulerRule
import com.jakelaurie.squadspook.util.TestUtil
import io.reactivex.Observable
import io.reactivex.rxkotlin.toObservable
import junit.framework.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.`when` as whenever
import org.mockito.MockitoAnnotations

class ServerListActivityViewModelTest {
    @Rule
    @JvmField var testSchedulerRule = TestSchedulerRule() //RxScheduler

    @Rule
    @JvmField var instantTaskExecutorRule = InstantTaskExecutorRule() //Arch scheduler

    @Mock private lateinit var mockRepository: ServerRepository

    private lateinit var serverListViewModel: ServerListViewModel

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        serverListViewModel = ServerListViewModel(mockRepository)
    }

    @Test
    fun networkNoDataTest() {
        whenever(mockRepository.fromNetwork())
                .thenReturn(TestUtil.wrapInList(TestUtil.createServers(5)).toObservable())

        serverListViewModel.refresh()

        Assert.assertNull(serverListViewModel.serversResult.value)
    }

    @Test
    fun databaseDataTest() {
        val size = 5
        val filter = ServersFilter(0)
        whenever(mockRepository.fromDatabase(filter))
                .thenReturn(TestUtil.wrapInList(TestUtil.createServers(size)).toObservable())

        serverListViewModel.queryDatabase(filter)

        Assert.assertNotNull(serverListViewModel.serversResult.value)
        Assert.assertEquals(serverListViewModel.serversResult.value?.size?: 0, size)

        Assert.assertNull(serverListViewModel.serversError.value)
        Assert.assertNull(serverListViewModel.serversFilter.value)
    }

    @Test
    fun serverErrorTest() {
        val filter = ServersFilter(0)

        whenever(mockRepository.fromDatabase(filter)).thenReturn(Observable.error(TestException()))

        serverListViewModel.queryDatabase(filter)

        Assert.assertNotNull(serverListViewModel.serversError)
    }

    @Test
    fun serversFilterTest() {
        val filter = ServersFilter(0)
        whenever(mockRepository.getFilter()).thenReturn(Observable.just(filter))
        whenever(mockRepository.fromDatabase(filter)).thenReturn(
                TestUtil.wrapInList(TestUtil.createServers(2)).toObservable())

        serverListViewModel.fetchFilter()

        Assert.assertNotNull(serverListViewModel.serversFilter)
        Assert.assertNotNull(serverListViewModel.serversResult)
        Assert.assertEquals(serverListViewModel.serversResult.value!!.size, 2)
    }
}