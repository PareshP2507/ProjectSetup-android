package com.psquare.setup.di

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.psquare.setup.BuildConfig
import com.psquare.setup.domain.IUserRepository
import com.psquare.setup.data.repository.UserRepository
import com.psquare.setup.data.source.UserService
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import timber.log.Timber
import java.util.concurrent.TimeUnit

private const val TIME_OUT = 60L

val NetworkModule = module {

    single { createGson() }

    single { createOkHttpClient() }

    single { createRetrofit(get(), get(), BuildConfig.BASE_URL) }

    single { createUserService(get()) }
}

fun createOkHttpClient(): OkHttpClient {
    val loggingInterceptor = HttpLoggingInterceptor {
        Timber.i("OkHttp: %s", it)
    }
    return OkHttpClient.Builder()
        .addInterceptor(loggingInterceptor)
        .connectTimeout(TIME_OUT, TimeUnit.SECONDS)
        .readTimeout(TIME_OUT, TimeUnit.SECONDS)
        .build()
}

fun createRetrofit(okHttpClient: OkHttpClient, gson: Gson, baseUrl: String): Retrofit {
    return Retrofit.Builder()
        .baseUrl(baseUrl)
        .client(okHttpClient)
        .addConverterFactory(GsonConverterFactory.create(gson)).build()
}

fun createGson(): Gson = GsonBuilder().serializeNulls().create()

fun createUserService(retrofit: Retrofit): UserService {
    return retrofit.create(UserService::class.java)
}

fun createUserRepository(userService: UserService): IUserRepository {
    return UserRepository(userService)
}