package com.training.scoreboard.ui.main

import android.util.Log.e
import com.training.scoreboard.data.DataManager
import com.training.scoreboard.ui.base.BasePresenter
import com.training.scoreboard.utils.SchedulerProvider
import javax.inject.Inject

class MainPresenter<V : MainView> @Inject constructor(dataManager: DataManager, schedulerProvider: SchedulerProvider) : BasePresenter<V>(dataManager = dataManager, schedulerProvider = schedulerProvider) {

}