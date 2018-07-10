package com.jakelaurie.squadspook

import com.jakelaurie.squadspook.data.database.ServersDao
import com.jakelaurie.squadspook.data.network.ServerApi
import com.jakelaurie.squadspook.data.repository.ServerRepository
import com.jakelaurie.squadspook.util.TestUtil
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.`when` as whenever
import org.mockito.MockitoAnnotations
import io.reactivex.Observable
import junit.framework.Assert

/**
 * Unit test for [ServerRepository]
 */
class ServerRepositoryTest {
    //mock dao
    @Mock private lateinit var dataSource: ServersDao
    @Mock private lateinit var serverApi: ServerApi

    private lateinit var repository: ServerRepository

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        repository = ServerRepository(serverApi, dataSource)
    }

    @Test
    fun fromNetworkTest() {
        val count = 10
        whenever(serverApi.getServers(
                        "^0.1.0",
                        "rank",
                        "rank,name,players,maxPlayers,ip,port,country,location,details",
                        "squad"
                )
        ).thenReturn(Observable.just(TestUtil.getServersResponse()))

        val servers = repository
                .fromNetwork()
                .test()
                .values()

        Assert.assertEquals(servers.size, count)
    }
}