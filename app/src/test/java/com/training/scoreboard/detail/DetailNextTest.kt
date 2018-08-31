package com.training.scoreboard.detail

import android.support.test.espresso.IdlingRegistry
import android.util.Log
import com.google.gson.Gson
import com.training.scoreboard.TestContextProvider
import com.training.scoreboard.data.DataManager
import com.training.scoreboard.data.api.ApiRepository
import com.training.scoreboard.data.api.TheSportDBApi
import com.training.scoreboard.data.model.DetailTeam
import com.training.scoreboard.data.model.DetailTeamResponse
import com.training.scoreboard.ui.detail.next.DetailNextPresenter
import com.training.scoreboard.ui.detail.next.DetailNextView
import com.training.scoreboard.utils.EspressoIdlingResource
import com.training.scoreboard.utils.SchedulerProvider
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations

class DetailNextTest {

    @Mock
    private lateinit var dataManager: DataManager

    @Mock
    private lateinit var schedulerProvider: SchedulerProvider

    @Mock
    private lateinit var gson: Gson

    @Mock
    private lateinit var apiRepository: ApiRepository

    @Mock
    private lateinit var view: DetailNextView

    private lateinit var presenter: DetailNextPresenter<DetailNextView>

    @Mock
    private lateinit var idling: EspressoIdlingResource

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        presenter = DetailNextPresenter(dataManager, schedulerProvider, apiRepository, TestContextProvider(), gson, idling)
        presenter.onAttach(view)
    }

    @Test
    fun testMatch() {
        val teams: MutableList<DetailTeam> = mutableListOf()
        val response = DetailTeamResponse(teams)
        val teamId = "133604"

        Mockito.`when`(gson.fromJson(apiRepository
                .doRequest(TheSportDBApi.detailTeam(teamId)),
                DetailTeamResponse::class.java
        )).thenReturn(response)

        presenter.getNextMatchHome(teamId)

        Mockito.verify(presenter.getView())?.showProgress()
        Mockito.verify(presenter.getView())?.showDetailTeamHome(teams)
        Mockito.verify(presenter.getView())?.hideProgress()

        presenter.onDetach()
    }
}