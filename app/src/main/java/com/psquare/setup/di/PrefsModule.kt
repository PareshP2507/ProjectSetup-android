package com.psquare.setup.di

import android.content.Context
import android.content.SharedPreferences
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKey
import com.google.gson.Gson
import com.psquare.setup.R
import com.psquare.setup.util.AppPrefs
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val PrefsModule = module {

    single { createMasterKey(androidContext()) }

    single { createPreferences(androidContext(), get()) }

    single { createAppPrefs(get(), get()) }
}

fun createMasterKey(context: Context): MasterKey {
    return MasterKey.Builder(context)
        .setKeyScheme(MasterKey.KeyScheme.AES256_GCM)
        .build()
}

fun createPreferences(context: Context, masterKey: MasterKey): SharedPreferences {
    return EncryptedSharedPreferences.create(
        context,
        context.getString(R.string.app_name),
        masterKey,
        EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
        EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
    )
}

fun createAppPrefs(sharedPreferences: SharedPreferences, gson: Gson): AppPrefs {
    return AppPrefs(sharedPreferences, gson)
}