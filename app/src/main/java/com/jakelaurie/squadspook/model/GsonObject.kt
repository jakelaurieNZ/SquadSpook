package com.jakelaurie.squadspook.model

import com.google.gson.JsonArray
import com.google.gson.JsonElement
import com.google.gson.JsonObject
import io.reactivex.Observable

fun JsonObject.safeObj(key: String): JsonObject {
    return when(this.has(key) && this.get(key).isJsonObject) {
        true -> this.getAsJsonObject(key)
        false -> JsonObject()
    }
}

fun JsonObject.safeArray(key: String): JsonArray {
    return when(this.has(key) && this.get(key).isJsonArray) {
        true -> this.getAsJsonArray(key)
        false -> JsonArray()
    }
}

fun JsonObject.nullableElement(key: String): JsonElement? {
    return if (this.has(key)) this.get(key) else null
}

fun JsonObject.string(key: String): String? {
    return nullableElement(key)?.asString
}

fun JsonObject.string(key: String, fallback: String): String {
    return nullableElement(key)?.asString ?: fallback
}

fun JsonObject.int(key: String): Int? {
    return nullableElement(key)?.asInt
}

fun JsonObject.int(key: String, fallback: Int): Int {
    return nullableElement(key)?.asInt ?: fallback
}

fun JsonObject.boolean(key: String): Boolean? {
    return nullableElement(key)?.asBoolean
}

fun JsonObject.boolean(key: String, fallback: Boolean): Boolean {
    return nullableElement(key)?.asBoolean ?: fallback
}

fun JsonObject.observableArray(key: String): Observable<JsonElement> {
    return Observable.fromArray(safeArray(key)).flatMapIterable { it }
}

fun JsonElement.safeAsObject(): JsonObject {
    return if (isJsonObject) asJsonObject else JsonObject()
}
