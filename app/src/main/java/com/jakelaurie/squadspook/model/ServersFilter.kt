package com.jakelaurie.squadspook.model

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import java.io.Serializable

@Entity(tableName = "serversFilter")
class ServersFilter(@PrimaryKey val id: Int) : Serializable {
    var filterEmptyServers: Boolean = false
}