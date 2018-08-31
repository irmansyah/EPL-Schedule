package com.training.scoreboard.ui.nextMatch

import com.google.gson.Gson
import com.training.scoreboard.data.DataManager
import com.training.scoreboard.data.api.ApiRepository
import com.training.scoreboard.utils.CoroutineContextProvider
import com.training.scoreboard.utils.EspressoIdlingResource
import com.training.scoreboard.utils.SchedulerProvider
import dagger.Module
import dagger.Provides
import dagger.android.ContributesAndroidInjector

@Module
class NextMatchFragmentModule {

    @Provides
    internal fun provideNextMatchAdapter(): NextMatchAdapter = NextMatchAdapter(ArrayList())

    @Provides
    internal fun provideNextMatchPresenter(dataManager: DataManager, schedulerProvider: SchedulerProvider, apiRepository: ApiRepository, context: CoroutineContextProvider, gson: Gson, idlingResource: EspressoIdlingResource): NextMatchPresenter<NextMatchView> =
            NextMatchPresenter(dataManager = dataManager,
                    schedulerProvider = schedulerProvider,
                    apiRepository = apiRepository,
                    context = context,
                    gson = gson,
                    idlingResource = idlingResource)
}