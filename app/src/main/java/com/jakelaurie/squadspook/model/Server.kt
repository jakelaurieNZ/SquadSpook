package com.jakelaurie.squadspook.model

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import com.google.gson.JsonArray
import com.google.gson.JsonObject
import java.io.Serializable

@Entity(tableName = "server")
class Server(@PrimaryKey val id: String) : Serializable {
    var country: String? = id
    var ip: String = ""
    var players: Int = 0
    var maxPlayers: Int = 0
    var name: String = ""
    var gameMode: String = ""
    var map: String = ""
    var licensedServer: Boolean = false
    var numOpenPrivConn: Int = 0
    var numPrivConn: Int = 0
    var numPubConn: Int = 0
    var parseTime: Long = 0

    companion object {
        private fun fromJson(json: JsonObject): Server? {
            val id = json.string("id") ?: return null
            val obj = Server(id)
            val jsonAttributes = json.safeObj("attributes")
            val jsonDetails = jsonAttributes.safeObj("details")
            obj.country = jsonAttributes.string("country", "")
            obj.ip = json.string("ip", "")
            obj.players = jsonAttributes.int("players", 0)
            obj.maxPlayers = jsonAttributes.int("maxPlayers", 0)
            obj.name = jsonAttributes.string("name", "")
            obj.gameMode = jsonDetails.string("gameMode", "")
            obj.map = jsonDetails.string("map", "")
            obj.licensedServer = jsonDetails.boolean("licensedServer", false)
            obj.numOpenPrivConn = jsonDetails.int("numOpenPrivConn", 0)
            obj.numPrivConn = jsonDetails.int("numPrivConn", 0)
            obj.numPubConn = jsonDetails.int("numPubConn", 0)
            obj.parseTime = System.currentTimeMillis()
            return obj
        }

        fun extractPlayers(serverDetailJson: JsonObject): JsonArray {
            return serverDetailJson.safeArray("players")
        }

        fun fromArray(json: JsonObject): List<Server> {
            return json.safeArray("data").mapNotNull {
                Server.fromJson(it.safeAsObject())
            }
        }
    }
}
