package com.training.scoreboard.ui.previousMatch

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
class PreviousMatchFragmentModule {

    @Provides
    internal fun providePreviousMatchAdapter(): PreviousMatchAdapter = PreviousMatchAdapter(ArrayList())

    @Provides
    internal fun providePreviousMatchPresenter(dataManager: DataManager, schedulerProvider: SchedulerProvider, apiRepository: ApiRepository, context: CoroutineContextProvider, gson: Gson, idlingResource: EspressoIdlingResource): PreviousMatchPresenter<PreviousMatchView> =
            PreviousMatchPresenter(dataManager = dataManager,
                    schedulerProvider = schedulerProvider,
                    apiRepository = apiRepository,
                    context = context,
                    gson = gson,
                    idlingResource = idlingResource)
}