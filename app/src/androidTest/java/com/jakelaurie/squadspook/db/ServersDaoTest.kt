package com.jakelaurie.squadspook.db

import android.support.test.runner.AndroidJUnit4
import com.jakelaurie.squadspook.model.Server
import com.jakelaurie.squadspook.util.TestUtil
import io.reactivex.subscribers.TestSubscriber
import junit.framework.Assert
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class ServersDaoTest: DbTest() {
    @Test
    fun insertAndRead() {
        val serverId = "1"
        val server = TestUtil.createServer(serverId)
        db.serversDao().insertServer(server)

        val testSubscriber = TestSubscriber<List<Server>>()
        db.serversDao()
                .queryServer(serverId)
                .subscribe(testSubscriber)

        testSubscriber.awaitCount(1)
        testSubscriber.assertSubscribed()
        testSubscriber.assertNoErrors()

        testSubscriber.assertValueCount(1) //One list

        val servers = testSubscriber.values().first()

        Assert.assertEquals(servers.size, 1)

        servers.first().run {
            assert(id == serverId)
            assert(name.isNotEmpty())
        }
    }
}