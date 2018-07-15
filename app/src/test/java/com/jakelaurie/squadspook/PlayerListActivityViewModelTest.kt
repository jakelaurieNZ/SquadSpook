package com.jakelaurie.squadspook

import android.arch.core.executor.testing.InstantTaskExecutorRule
import com.jakelaurie.squadspook.data.repository.PlayerRepository
import com.jakelaurie.squadspook.ui.viewmodel.serverdetail.PlayerListViewModel
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
import org.mockito.Mockito
import org.mockito.MockitoAnnotations

class PlayerListActivityViewModelTest {
    @Rule
    @JvmField
    var testSchedulerRule = TestSchedulerRule() //RxScheduler

    @Rule
    @JvmField
    var instantTaskExecutorRule = InstantTaskExecutorRule() //Arch scheduler

    @Mock
    private lateinit var mockRepository: PlayerRepository

    private lateinit var playerListViewModel: PlayerListViewModel

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        playerListViewModel = PlayerListViewModel(mockRepository)
    }

    @Test
    fun networkNoDataTest() {
        Assert.assertNull(playerListViewModel.playersResult.value)
    }

    @Test
    fun databaseDataTest() {
        val size = 5
        val serverId = "1"

        Mockito.`when`(mockRepository.getPlayers(serverId))
                .thenReturn(TestUtil.wrapInList(TestUtil.createPlayers(size, serverId)).toObservable())

        playerListViewModel.loadPlayers(serverId)

        Assert.assertNotNull(playerListViewModel.playersResult.value)
        Assert.assertEquals(playerListViewModel.playersResult.value?.size ?: 0, size)

        Assert.assertNull(playerListViewModel.playersError.value)
    }

    @Test
    fun errorTest() {
        val serverId = "1"

        Mockito.`when`(mockRepository.getPlayers(serverId)).thenReturn(Observable.error(TestException()))

        playerListViewModel.loadPlayers(serverId)

        Assert.assertNotNull(playerListViewModel.playersError)
    }
}