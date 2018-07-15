package com.jakelaurie.squadspook

import com.jakelaurie.squadspook.data.database.PlayersDao
import com.jakelaurie.squadspook.data.network.PlayerApi
import com.jakelaurie.squadspook.data.repository.PlayerRepository
import com.jakelaurie.squadspook.util.TestUtil
import io.reactivex.Observable
import io.reactivex.Single
import junit.framework.Assert
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.`when` as whenever
import org.mockito.MockitoAnnotations

/**
 * Unit test for [PlayerRepository]
 */
class PlayerRepositoryTest {
    //mock dao
    @Mock
    private lateinit var dataSource: PlayersDao
    @Mock
    private lateinit var playerApi: PlayerApi

    private lateinit var repository: PlayerRepository

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        repository = PlayerRepository(playerApi, dataSource)
    }

    @Test
    fun fromNetworkTest() {
        val jsonResponseCount = 76
        val count = 10
        val inputCount = 2

        val serverId = "1"
        whenever(playerApi.getServerDetails(serverId))
                .thenReturn(Observable.just(TestUtil.getPlayersResponse()))

        whenever(dataSource.queryPlayers(serverId))
                .thenReturn(Single.just(TestUtil.createPlayers(count, serverId)))

        val servers = repository.getPlayers(serverId)
                .test()
                .values()

        Assert.assertEquals(servers.size, inputCount)

        Assert.assertEquals(servers[0].size, count)
        Assert.assertEquals(servers[1].size, jsonResponseCount)
    }
}