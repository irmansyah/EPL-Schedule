package com.training.scoreboard.api

import com.training.scoreboard.data.api.ApiRepository
import org.junit.Test
import org.mockito.Mockito

class ApiRepositoryTest {

//    @Test
//    fun testDoRequestSearchAllTeam() {
//        val apiRepository = Mockito.mock(ApiRepository::class.java)
//        val url = "https://www.thesportsdb.com/api/v1/json/1/search_all_teams.php?id=4328"
//        apiRepository.doRequest(url)
//        Mockito.verify(apiRepository).doRequest(url)
//    }

    @Test
    fun testDoRequestEventNextLeague() {
        val apiRepository = Mockito.mock(ApiRepository::class.java)
        val url = "https://www.thesportsdb.com/api/v1/json/1/eventsnextleague.php?id=4328"
        apiRepository.doRequest(url)
        Mockito.verify(apiRepository).doRequest(url)
    }

    @Test
    fun testDoRequestEventPastLeague() {
        val apiRepository = Mockito.mock(ApiRepository::class.java)
        val url = "https://www.thesportsdb.com/api/v1/json/1/eventspastleague.php?id=4328"
        apiRepository.doRequest(url)
        Mockito.verify(apiRepository).doRequest(url)
    }

    @Test
    fun testDoRequestLookupTeeam() {
        val apiRepository = Mockito.mock(ApiRepository::class.java)
        val url = "https://www.thesportsdb.com/api/v1/json/1/lookupteam.php?id=4328"
        apiRepository.doRequest(url)
        Mockito.verify(apiRepository).doRequest(url)
    }
}