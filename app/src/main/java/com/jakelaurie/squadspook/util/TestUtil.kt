package com.jakelaurie.squadspook.util

import android.content.Context
import android.support.annotation.RawRes
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.JsonObject
import com.jakelaurie.squadspook.model.Player
import com.jakelaurie.squadspook.model.Server
import java.io.InputStreamReader

object TestUtil {
    /**
     * Creates one server with ID
     */
    fun createServer(id: String) = Server(id)

    /**
     * Creates 'count' number of servers with IDs from 0 to 'count'
     */
    fun createServers(count: Int): List<Server> {
        return List(count, init = {
             Server(it.toString())
        })
    }

    /**
     * Creates one player with ID and serverID specified
     */
    fun createPlayer(id: String, serverId: String): Player {
        val player = Player(id)
        player.serverId = serverId
        return player
    }

    /**
     * Wrap list as element 0 in another list
     */
    fun <T> wrapInList(items: List<T>): List<List<T>> {
        return List(1, init = { items })
    }

    /**
     * Read from res to JsonObject
     */
    fun fileToJsonObject(context: Context, @RawRes fileRes: Int): JsonObject {
        val gson = buildGson()
        val inputStream = context.resources.openRawResource(fileRes)
        val reader = InputStreamReader(inputStream)
        return gson.fromJson(reader, JsonObject::class.java)
    }

    /**
     * Until we have a better way to inject this
     * Not a huge deal because we just have basic Gson impl
     */
    private fun buildGson(): Gson {
        return GsonBuilder().create()
    }

    /**
     * Server response as JsonObject
     */
    fun getServersResponse(): JsonObject {
        val gson = buildGson()
        val jsonString = "{ \"data\": [{ \"type\": \"server\", \"id\": \"2006342\", \"attributes\": { \"name\": \"Server1\", \"ip\": \"192.168.1.1\", \"port\": 7787, \"players\": 78, \"maxPlayers\": 80, \"rank\": 1, \"location\": [-97.822, 37.751 ], \"details\": { \"map\": \"Mestia AAS v1\", \"gameMode\": \"AAS\", \"version\": \"a-11.2.463.4343\", \"secure\": 0, \"licensedServer\": true, \"numPubConn\": 78, \"numPrivConn\": 2, \"numOpenPrivConn\": 2 }, \"country\": \"US\" }, \"relationships\": { \"game\": { \"data\": { \"type\": \"game\", \"id\": \"squad\" } } } }, { \"type\": \"server\", \"id\": \"2388979\", \"attributes\": { \"name\": \"Server2\", \"ip\": \"192.168.1.2\", \"port\": 7787, \"players\": 77, \"maxPlayers\": 80, \"rank\": 2, \"location\": [-80.2401, 25.8124 ], \"details\": { \"map\": \"Gorodok AAS v2\", \"gameMode\": \"AAS\", \"version\": \"a-11.2.463.4343\", \"secure\": 0, \"licensedServer\": true, \"numPubConn\": 78, \"numPrivConn\": 2, \"numOpenPrivConn\": 2 }, \"country\": \"US\" }, \"relationships\": { \"game\": { \"data\": { \"type\": \"game\", \"id\": \"squad\" } } } }, { \"type\": \"server\", \"id\": \"1377033\", \"attributes\": { \"name\": \"Server3\", \"ip\": \"192.168.1.3\", \"port\": 7787, \"players\": 78, \"maxPlayers\": 80, \"rank\": 3, \"location\": [-0.0, 41.8745 ], \"details\": { \"map\": \"Logar Valley AAS v1\", \"gameMode\": \"AAS\", \"version\": \"a-11.2.463.4343\", \"secure\": 0, \"licensedServer\": true, \"numPubConn\": 78, \"numPrivConn\": 2, \"numOpenPrivConn\": 2 }, \"country\": \"US\" }, \"relationships\": { \"game\": { \"data\": { \"type\": \"game\", \"id\": \"squad\" } } } }, { \"type\": \"server\", \"id\": \"2319345\", \"attributes\": { \"name\": \"Server4\", \"ip\": \"192.168.1.4\", \"port\": 7787, \"players\": 78, \"maxPlayers\": 80, \"rank\": 4, \"location\": [-0.1224, 51.4964 ], \"details\": { \"map\": \"Kamdesh PAAS v1\", \"gameMode\": \"AAS\", \"version\": \"a-11.2.463.4343\", \"secure\": 0, \"licensedServer\": true, \"numPubConn\": 78, \"numPrivConn\": 2, \"numOpenPrivConn\": 2 }, \"country\": \"GB\" }, \"relationships\": { \"game\": { \"data\": { \"type\": \"game\", \"id\": \"squad\" } } } }, { \"type\": \"server\", \"id\": \"1438204\", \"attributes\": { \"name\": \"Total War | Chicago\", \"ip\": \"104.128.58.122\", \"port\": 7500, \"players\": 80, \"maxPlayers\": 80, \"rank\": 5, \"location\": [-87.6503, 41.8745 ], \"details\": { \"map\": \"Sumari AAS v2\", \"gameMode\": \"AAS\", \"version\": \"a-11.2.463.4343\", \"secure\": 0, \"licensedServer\": true, \"numPubConn\": 80, \"numPrivConn\": 0, \"numOpenPrivConn\": 0, \"serverSteamId\": \"90115739393936389\" }, \"country\": \"US\" }, \"relationships\": { \"game\": { \"data\": { \"type\": \"game\", \"id\": \"squad\" } } } }, { \"type\": \"server\", \"id\": \"1900715\", \"attributes\": { \"name\": \"Server5\", \"ip\": \"192.168.1.5\", \"port\": 7703, \"players\": 76, \"maxPlayers\": 80, \"rank\": 6, \"location\": [-96.8705, 32.8148 ], \"details\": { \"map\": \"Al Basrah Invasion v1\", \"gameMode\": \"Invasion\", \"version\": \"a-11.2.463.4343\", \"secure\": 0, \"licensedServer\": true, \"numPubConn\": 76, \"numPrivConn\": 4, \"numOpenPrivConn\": 4 }, \"country\": \"US\" }, \"relationships\": { \"game\": { \"data\": { \"type\": \"game\", \"id\": \"squad\" } } } }, { \"type\": \"server\", \"id\": \"1972911\", \"attributes\": { \"name\": \"Server6\", \"ip\": \"192.168.1.6\", \"port\": 7857, \"players\": 0, \"maxPlayers\": 80, \"rank\": 7, \"location\": [ 9.491, 51.2993 ], \"details\": { \"map\": \"Kamdesh AAS v1 INF\", \"gameMode\": \"AAS\", \"version\": \"a-11.2.463.4343\", \"secure\": 0, \"licensedServer\": true, \"numPubConn\": 79, \"numPrivConn\": 1, \"numOpenPrivConn\": 1 }, \"country\": \"DE\" }, \"relationships\": { \"game\": { \"data\": { \"type\": \"game\", \"id\": \"squad\" } } } }, { \"type\": \"server\", \"id\": \"2361808\", \"attributes\": { \"name\": \"Server7\", \"ip\": \"192.168.1.7\", \"port\": 7787, \"players\": 0, \"maxPlayers\": 80, \"rank\": 8, \"location\": [-97.822, 37.751 ], \"details\": { \"map\": \"Kamdesh AAS v1 INF\", \"gameMode\": \"AAS\", \"version\": \"a-11.2.463.4343\", \"secure\": 0, \"licensedServer\": true, \"numPubConn\": 78, \"numPrivConn\": 2, \"numOpenPrivConn\": 2 }, \"country\": \"US\" }, \"relationships\": { \"game\": { \"data\": { \"type\": \"game\", \"id\": \"squad\" } } } }, { \"type\": \"server\", \"id\": \"542530\", \"attributes\": { \"name\": \"Server8\", \"ip\": \"192.168.1.8\", \"port\": 7787, \"players\": 19, \"maxPlayers\": 80, \"rank\": 9, \"location\": [ 9.491, 51.2993 ], \"details\": { \"map\": \"Kamdesh AAS v1 INF\", \"gameMode\": \"AAS\", \"version\": \"a-11.2.463.4343\", \"secure\": 0, \"licensedServer\": true, \"numPubConn\": 79, \"numPrivConn\": 1, \"numOpenPrivConn\": 1 }, \"country\": \"DE\" }, \"relationships\": { \"game\": { \"data\": { \"type\": \"game\", \"id\": \"squad\" } } } }, { \"type\": \"server\", \"id\": \"1515775\", \"attributes\": { \"name\": \"Server9\", \"ip\": \"192.168.1.9\", \"port\": 7777, \"players\": 3, \"maxPlayers\": 80, \"rank\": 10, \"location\": [ 2.3387, 48.8582 ], \"details\": { \"map\": \"Kokan PAAS v1\", \"gameMode\": \"AAS\", \"version\": \"a-11.2.463.4343\", \"secure\": 0, \"licensedServer\": true, \"numPubConn\": 80, \"numPrivConn\": 0, \"numOpenPrivConn\": 0 }, \"country\": \"FR\" }, \"relationships\": { \"game\": { \"data\": { \"type\": \"game\", \"id\": \"squad\" } } } } ] }"
        return gson.fromJson(jsonString, JsonObject::class.java)
    }
}

/**
 * Generic exception for tests
 */
class TestException: Exception("TestException")
