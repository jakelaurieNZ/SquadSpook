package com.jakelaurie.squadspook.model

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import com.google.gson.JsonArray
import com.google.gson.JsonObject
import java.io.Serializable

@Entity(tableName = "player")
class Player(@PrimaryKey val id: String) : Serializable {
    var name: String = ""
    var serverId: String = ""
    var score: Int = 0
    var parseTime: Long = 0

    companion object {
        fun fromJson(json: JsonObject, serverId: String): Player? {
            val id = json.string("player_id") ?: return null
            val obj = Player(id)
            obj.name = json.string("name", "")
            obj.serverId = serverId

            val metaData = json.safeArray("metadata")
            for(it in metaData) {
                if(it.safeAsObject().string("key") == "score") {
                    obj.score = it.safeAsObject().int("value")?: 0
                    break
                }
            }

            obj.parseTime = System.currentTimeMillis()
            return obj
        }

        fun fromArray(playersArray: JsonArray, serverId: String): List<Player> {
            return playersArray.mapNotNull{ Player.fromJson(it.safeAsObject(), serverId) }
        }
    }
}


