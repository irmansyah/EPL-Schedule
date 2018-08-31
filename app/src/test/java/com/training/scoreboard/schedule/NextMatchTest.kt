package com.training.scoreboard.schedule

import android.support.test.espresso.IdlingRegistry
import android.util.Log
import com.google.gson.Gson
import com.training.scoreboard.TestContextProvider
import com.training.scoreboard.data.DataManager
import com.training.scoreboard.data.api.ApiRepository
import com.training.scoreboard.data.api.TheSportDBApi
import com.training.scoreboard.data.model.NextMatch
import com.training.scoreboard.data.model.NextMatchResponse
import com.training.scoreboard.ui.nextMatch.NextMatchPresenter
import com.training.scoreboard.ui.nextMatch.NextMatchView
import com.training.scoreboard.utils.EspressoIdlingResource
import com.training.scoreboard.utils.SchedulerProvider
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations

class NextMatchTest {

    @Mock
    private lateinit var dataManager: DataManager

    @Mock
    private lateinit var schedulerProvider: SchedulerProvider

    @Mock
    private lateinit var gson: Gson

    @Mock
    private lateinit var apiRepository: ApiRepository

    @Mock
    private lateinit var view: NextMatchView

    private lateinit var presenter: NextMatchPresenter<NextMatchView>

    @Mock
    private lateinit var idling: EspressoIdlingResource

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        presenter = NextMatchPresenter(dataManager, schedulerProvider, apiRepository, TestContextProvider(), gson, idling)
        presenter.onAttach(view)
    }

    @Test
    fun testGetNextMatch() {
        val matches: MutableList<NextMatch> = mutableListOf()
        val response = NextMatchResponse(matches)
        val id = "4328"

        Mockito.`when`(gson.fromJson(apiRepository
                .doRequest(TheSportDBApi.scheduleNext(id)),
                NextMatchResponse::class.java
        )).thenReturn(response)

        presenter.getNextMatchList(id)

        Mockito.verify(presenter.getView())?.showProgress()
        Mockito.verify(presenter.getView())?.showNextMatchList(matches)
        Mockito.verify(presenter.getView())?.hideProgress()

        presenter.onDetach()
    }
}