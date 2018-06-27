package com.jakelaurie.squadspook.data.preferences

import android.content.Context
import android.content.SharedPreferences
import com.jakelaurie.squadspook.SquadApplication

class Preferences constructor(app: SquadApplication, prefsName: String) {
    private val preferences: SharedPreferences
            = app.getSharedPreferences(prefsName, Context.MODE_PRIVATE)

    private inline fun SharedPreferences.edit(operation: (SharedPreferences.Editor) -> Unit) {
        val editor = this.edit()
        operation(editor)
        editor.apply()
    }
}
