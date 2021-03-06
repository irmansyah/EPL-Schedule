package com.training.scoreboard.ui.detail.next

import com.google.gson.Gson
import com.training.scoreboard.data.DataManager
import com.training.scoreboard.data.api.ApiRepository
import com.training.scoreboard.utils.CoroutineContextProvider
import com.training.scoreboard.utils.EspressoIdlingResource
import com.training.scoreboard.utils.SchedulerProvider
import dagger.Module
import dagger.Provides

@Module
class DetailNextActivityModule {

    @Provides
    internal fun provideDetailNextPresenter(dataManager: DataManager, schedulerProvider: SchedulerProvider, apiRepository: ApiRepository, context: CoroutineContextProvider, gson: Gson, idlingResource: EspressoIdlingResource): DetailNextPresenter<DetailNextView> =
            DetailNextPresenter(dataManager = dataManager,
                    schedulerProvider = schedulerProvider,
                    apiRepository = apiRepository,
                    context = context,
                    gson = gson,
                    idlingResource = idlingResource)
}