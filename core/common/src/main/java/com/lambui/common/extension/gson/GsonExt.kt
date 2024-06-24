package com.lambui.common.extension.gson

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

/**
 * Created by LamBD on 17/06/24.
 */

inline fun <reified T> Gson.fromJsonType(json: String): T = fromJson(json, object : TypeToken<T>() {}.type)

inline fun <reified T> Gson.toJsonType(obj: T): String = toJson(obj)

inline fun <reified T> T.clone(gson: Gson = Gson()): T {
    return gson.fromJsonType(json = gson.toJsonType(this))
}
