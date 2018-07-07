package com.jakelaurie.squadspook.db

import android.support.test.runner.AndroidJUnit4
import com.jakelaurie.squadspook.util.TestUtil
import io.reactivex.Single
import io.reactivex.rxkotlin.subscribeBy
import junit.framework.Assert
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class PlayerDaoTest: DbTest() {
    @Test
    fun insertAndRead() {
        val serverId = "1"
        val playerId = "1"
        val player = TestUtil.createPlayer(playerId, serverId)
        db.playersDao().insertPlayer(player)

        val test = SingleListTest(db.playersDao().queryPlayers(serverId))
        Assert.assertTrue(test.isComplete)
        Assert.assertEquals(test.results.size, 1)

        val value = test.results.first()
        Assert.assertEquals(value.id, playerId)
        Assert.assertEquals(value.serverId, serverId)
        Assert.assertTrue(value.name.isEmpty())
    }
}

class SingleListTest<T>(single: Single<List<T>>) {
    var error: Throwable? = null
        private set

    var results: List<T> = emptyList()
        private set

    var isComplete: Boolean = false
        private set

    init {
        single.subscribeBy(
                onError = {
                    error = it
                },

                onSuccess = {
                    isComplete = true
                    results = it
                }
        )
    }
}
