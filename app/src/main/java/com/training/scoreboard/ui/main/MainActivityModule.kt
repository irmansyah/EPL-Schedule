package com.training.scoreboard.ui.main

import com.training.scoreboard.data.DataManager
import com.training.scoreboard.data.model.NextMatch
import com.training.scoreboard.utils.SchedulerProvider
import dagger.Module
import dagger.Provides

@Module
class MainActivityModule {

    @Provides
    internal fun provideMatchAdapter(): MatchAdapter = MatchAdapter(ArrayList())

    @Provides
    internal fun provideMainPresenter(dataManager: DataManager, schedulerProvider: SchedulerProvider): MainPresenter<MainView> =
            MainPresenter(dataManager = dataManager, schedulerProvider = schedulerProvider)
}