package com.uruklabs.dictionaryapp.di.modules

import android.content.Context
import androidx.room.Room
import com.uruklabs.dictionaryapp.data.daos.WordDao
import com.uruklabs.dictionaryapp.data.databases.WordRoomDB
import com.uruklabs.dictionaryapp.data.repositorys.WordsRepositorys
import com.uruklabs.dictionaryapp.services.network.DicionatyAPI
import com.uruklabs.dictionaryapp.viewModels.FavoritesViewModel
import com.uruklabs.dictionaryapp.viewModels.HistoryViewModel
import com.uruklabs.dictionaryapp.viewModels.WordViewModel
import org.koin.android.ext.koin.androidApplication
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import kotlin.math.sin

val appModule = module {

    viewModel { WordViewModel() }

    viewModel { HistoryViewModel(get()) }

    viewModel { FavoritesViewModel() }

    single { provideWordDao(get()) }

    single { provideDatabase(androidApplication()) }

    single { provideRetrofit()}

    single { provideRepository(get(), get()) }

    single { provideDicionatyAPI(get()) }



}

fun provideRetrofit() : Retrofit{
    return Retrofit.Builder()
        .baseUrl("https://api.dictionaryapi.dev/api/v2/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()
}

fun provideDicionatyAPI(retrofit: Retrofit) : DicionatyAPI {
    return retrofit.create(DicionatyAPI::class.java)
}


fun provideRepository(wordDao: WordDao, api : DicionatyAPI): WordsRepositorys {
    return WordsRepositorys(wordDao, api)
}

fun provideDatabase(context: Context): WordRoomDB {
    return Room.databaseBuilder(context, WordRoomDB::class.java, WordRoomDB.DATABASE_NAME)
        .fallbackToDestructiveMigration()
        .build()
}

fun provideWordDao(db: WordRoomDB): WordDao {
    return db.wordDao()
}