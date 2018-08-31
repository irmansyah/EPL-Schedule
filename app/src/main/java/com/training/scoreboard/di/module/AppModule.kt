package com.training.scoreboard.di.module

import android.app.Application
import android.content.Context
import com.google.gson.Gson
import com.training.scoreboard.data.AppDataManager
import com.training.scoreboard.data.DataManager
import com.training.scoreboard.data.api.ApiHelper
import com.training.scoreboard.data.api.ApiRepository
import com.training.scoreboard.data.api.AppApiHelper
import com.training.scoreboard.data.db.ScheduleDatabaseOpenHelper
import com.training.scoreboard.utils.CoroutineContextProvider
import com.training.scoreboard.utils.EspressoIdlingResource
import com.training.scoreboard.utils.SchedulerProvider
import dagger.Module
import dagger.Provides
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Singleton

@Module
class AppModule {

    @Provides
    @Singleton
    internal fun provideContext(application: Application): Context = application

//    @Provides
//    @Singleton
//    internal fun provideCoroutineContextProvider(): CoroutineContextProvider = CoroutineContextProvider()

    @Provides
    @Singleton
    internal fun provideDataManager(appDataManager: AppDataManager): DataManager = appDataManager

    @Provides
    @Singleton
    internal fun provideApiHelper(appApiHelper: AppApiHelper): ApiHelper = appApiHelper

    @Provides
    internal fun provideCompositeDisposable(): CompositeDisposable = CompositeDisposable()

    @Provides
    internal fun provideSchedulerProvider(): SchedulerProvider = SchedulerProvider()

    @Provides
    internal fun provideCoroutineContextProvider(): CoroutineContextProvider = CoroutineContextProvider()

    @Provides
    internal fun provideAPiRepository(): ApiRepository = ApiRepository()

    @Provides
    internal fun provideGson(): Gson = Gson()

    @Provides
    internal fun provideIdlingResource(): EspressoIdlingResource = EspressoIdlingResource()
}