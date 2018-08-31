package com.training.scoreboard.detail

import android.support.test.espresso.IdlingRegistry
import com.google.gson.Gson
import com.training.scoreboard.TestContextProvider
import com.training.scoreboard.data.DataManager
import com.training.scoreboard.data.api.ApiRepository
import com.training.scoreboard.data.api.TheSportDBApi
import com.training.scoreboard.data.model.DetailTeam
import com.training.scoreboard.data.model.DetailTeamResponse
import com.training.scoreboard.ui.detail.prev.DetailPrevPresenter
import com.training.scoreboard.ui.detail.prev.DetailPrevView
import com.training.scoreboard.utils.EspressoIdlingResource
import com.training.scoreboard.utils.SchedulerProvider
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations

class DetailPrevTest {

    @Mock
    private lateinit var dataManager: DataManager

    @Mock
    private lateinit var schedulerProvider: SchedulerProvider

    @Mock
    private lateinit var gson: Gson

    @Mock
    private lateinit var apiRepository: ApiRepository

    @Mock
    private lateinit var view: DetailPrevView

    private lateinit var presenter: DetailPrevPresenter<DetailPrevView>

    @Mock
    private lateinit var idling: EspressoIdlingResource

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        presenter = DetailPrevPresenter(dataManager, schedulerProvider, apiRepository, TestContextProvider(), gson, idling)
        presenter.onAttach(view)
    }

    @Test
    fun testGetDetailPrevMatchHome() {
        val teams: MutableList<DetailTeam> = mutableListOf()
        val response = DetailTeamResponse(teams)
        val teamId = "133632"

        Mockito.`when`(gson.fromJson(apiRepository
                .doRequest(TheSportDBApi.detailTeam(teamId)),
                DetailTeamResponse::class.java
        )).thenReturn(response)

        presenter.getPrevMatchHome(teamId)

        Mockito.verify(presenter.getView())?.showProgress()
        Mockito.verify(presenter.getView())?.showDetailTeamHome(teams)
        Mockito.verify(presenter.getView())?.hideProgress()

        presenter.onDetach()
    }
}