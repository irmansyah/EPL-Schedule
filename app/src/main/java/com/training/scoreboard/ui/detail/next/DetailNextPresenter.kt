package com.training.scoreboard.ui.detail.next

import com.google.gson.Gson
import com.training.scoreboard.data.DataManager
import com.training.scoreboard.data.api.ApiRepository
import com.training.scoreboard.data.api.TheSportDBApi
import com.training.scoreboard.data.model.DetailTeamResponse
import com.training.scoreboard.ui.base.BasePresenter
import com.training.scoreboard.utils.CoroutineContextProvider
import com.training.scoreboard.utils.EspressoIdlingResource
//import com.training.scoreboard.utils.EspressoIdlingResource.EspressoIdlingResource.decrement
//import com.training.scoreboard.utils.EspressoIdlingResource.EspressoIdlingResource.increment
import com.training.scoreboard.utils.SchedulerProvider
import kotlinx.coroutines.experimental.async
import org.jetbrains.anko.coroutines.experimental.bg
import javax.inject.Inject

class DetailNextPresenter<V : DetailNextView> @Inject constructor(dataManager: DataManager, schedulerProvider: SchedulerProvider,
                                                                  private val apiRepository: ApiRepository,
                                                                  private val context: CoroutineContextProvider,
                                                                  private val gson: Gson,
                                                                  private val idlingResource: EspressoIdlingResource):
        BasePresenter<V>(dataManager = dataManager, schedulerProvider = schedulerProvider) {


    fun getNextMatchHome(teamId: String?) {
        idlingResource.increment()
        getView()?.showProgress()
        async(context.main) {
            val data = bg {
                gson.fromJson(apiRepository
                        .doRequest(TheSportDBApi.detailTeam(teamId)),
                        DetailTeamResponse::class.java
                )
            }
            getView()?.showDetailTeamHome(data.await().teams)
        }
    }

    fun getNextMatchAway(teamId: String?) {
        async(context.main) {
            val data = bg {
                gson.fromJson(apiRepository
                        .doRequest(TheSportDBApi.detailTeam(teamId)),
                        DetailTeamResponse::class.java
                )
            }
            getView()?.showDetailTeamAway(data.await().teams)
            getView()?.hideProgress()
            idlingResource.decrement()
        }
    }
}