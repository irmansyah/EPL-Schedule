package com.training.scoreboard.schedule

import android.support.test.espresso.IdlingRegistry
import com.google.gson.Gson
import com.training.scoreboard.TestContextProvider
import com.training.scoreboard.data.DataManager
import com.training.scoreboard.data.api.ApiRepository
import com.training.scoreboard.data.api.TheSportDBApi
import com.training.scoreboard.data.model.PreviousMatch
import com.training.scoreboard.data.model.PreviousMatchResponse
import com.training.scoreboard.ui.previousMatch.PreviousMatchPresenter
import com.training.scoreboard.ui.previousMatch.PreviousMatchView
import com.training.scoreboard.utils.EspressoIdlingResource
import com.training.scoreboard.utils.SchedulerProvider
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations

class PreviousMatchTest {

    @Mock
    private lateinit var dataManager: DataManager

    @Mock
    private lateinit var schedulerProvider: SchedulerProvider

    @Mock
    private lateinit var gson: Gson

    @Mock
    private lateinit var apiRepository: ApiRepository

    @Mock
    private lateinit var view: PreviousMatchView

    private lateinit var presenter: PreviousMatchPresenter<PreviousMatchView>

    @Mock
    private lateinit var idling: EspressoIdlingResource

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        presenter = PreviousMatchPresenter(dataManager, schedulerProvider, apiRepository, TestContextProvider(), gson, idling)
        presenter.onAttach(view)
    }

    @Test
    fun testGetPrevMatch() {
        val matches: MutableList<PreviousMatch> = mutableListOf()
        val response = PreviousMatchResponse(matches)
        val id = "4328"

        Mockito.`when`(gson.fromJson(apiRepository
                .doRequest(TheSportDBApi.schedulePrevious(id)),
                PreviousMatchResponse::class.java
        )).thenReturn(response)

        presenter.getPreviousMatchList(id)

        Mockito.verify(presenter.getView())?.showProgress()
        Mockito.verify(presenter.getView())?.showPreviousMatchList(matches)
        Mockito.verify(presenter.getView())?.hideProgress()

        presenter.onDetach()
    }
}