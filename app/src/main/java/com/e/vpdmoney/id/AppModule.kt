package com.e.vpdmoney.id

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import androidx.room.Room

import com.e.vpdmoney.BuildConfig
import com.e.vpdmoney.db.AppDBs
import com.e.vpdmoney.util.Constants
import com.google.gson.Gson

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor

import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module()
@InstallIn(SingletonComponent::class)
object AppModule {




    @Provides
    @Singleton
    fun provideGson(): Gson = Gson()

    @Provides
    @Singleton
    fun provideGsonConverterFactory(gson: Gson): GsonConverterFactory =
        GsonConverterFactory.create(gson)


  /*  @Provides
    @Singleton
    fun providePrefsUtils(prefs: SharedPreferences, gson: Gson): PrefsUtils =
        PrefsUtils(prefs, gson)*/

    @Provides
    @Singleton
    fun provideGlobalSharedPreference(app: Application): SharedPreferences =
        app.getSharedPreferences("global_shared_prefs", Context.MODE_PRIVATE)


    @Provides
    @Singleton
    fun provideVPDMoneyDatabase(app: Application): AppDBs = Room.databaseBuilder(
        app,
        AppDBs::class.java,
        Constants.Database.DATABASE_NAME)
        //TODO In production, ensure to always write DB Migrations to avoid data loss
        // Check this out: https://medium.com/androiddevelopers/understanding-migrations-with-room-f01e04b07929
        // and https://developer.android.com/training/data-storage/room/migrating-db-versions
        .fallbackToDestructiveMigration()
        .build()




    @Provides
    @Singleton
    fun provideGenericOkHttpClient(
        interceptor: HttpLoggingInterceptor): OkHttpClient =
        OkHttpClient.Builder()
            .connectTimeout(60, TimeUnit.SECONDS)
            .readTimeout(60, TimeUnit.SECONDS)
            .writeTimeout(60, TimeUnit.SECONDS)
            .addInterceptor(interceptor)
            .build()


    @Provides
    @Singleton
    fun provideLoggingInterceptor(): HttpLoggingInterceptor =
        HttpLoggingInterceptor().apply {
            level = if (BuildConfig.DEBUG) {
                HttpLoggingInterceptor.Level.BODY
            } else {
                HttpLoggingInterceptor.Level.NONE
            }
        }
}


