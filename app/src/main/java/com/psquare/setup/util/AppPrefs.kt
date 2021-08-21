package com.psquare.setup.util

import android.content.SharedPreferences
import com.google.gson.Gson

class AppPrefs(private val sharedPreferences: SharedPreferences, private val gson: Gson) {

    private inline fun SharedPreferences.edit(operation: (SharedPreferences.Editor) -> Unit) {
        val editor = edit()
        operation(editor)
        editor.apply()
    }

    fun clear() = sharedPreferences.edit().clear().apply()

    fun storeString(key: String, value: String) {
        sharedPreferences.edit { it.putString(key, value) }
    }

    fun storeInt(key: String, value: Int) {
        sharedPreferences.edit { it.putInt(key, value) }
    }

    fun storeBoolean(key: String, value: Boolean) {
        sharedPreferences.edit { it.putBoolean(key, value) }
    }

    fun storeFloat(key: String, value: Float) {
        sharedPreferences.edit { it.putFloat(key, value) }
    }

    fun storeLong(key: String, value: Long) {
        sharedPreferences.edit { it.putLong(key, value) }
    }

    fun storeObject(key: String, obj: Any) {
        storeString(key, gson.toJson(obj))
    }

    fun storeArrayList(key: String, list: ArrayList<Any>) {
        storeString(key, gson.toJson(list))
    }

    fun retrieveString(key: String): String? {
        return sharedPreferences.getString(key, "")
    }

    fun retrieveInt(key: String): Int {
        return sharedPreferences.getInt(key, -1)
    }

    fun retrieveBoolean(key: String): Boolean {
        return sharedPreferences.getBoolean(key, false)
    }

    fun retrieveFloat(key: String): Float {
        return sharedPreferences.getFloat(key, 0f)
    }

    fun retrieveLong(key: String): Long {
        return sharedPreferences.getLong(key, 0L)
    }
}