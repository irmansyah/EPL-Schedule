package com.training.scoreboard.data.api

import com.training.scoreboard.BuildConfig


object TheSportDBApi {

    fun searchTeam(leagueId: String?) = BuildConfig.BASE_URL + "api/v1/json/${BuildConfig.TSDB_API_KEY}" + "/search_all_teams.php?id=" + leagueId

    fun scheduleNext(leagueId: String?): String = BuildConfig.BASE_URL + "api/v1/json/${BuildConfig.TSDB_API_KEY}" + "/eventsnextleague.php?id=" + leagueId

    fun schedulePrevious(leagueId: String?) = BuildConfig.BASE_URL + "api/v1/json/${BuildConfig.TSDB_API_KEY}" + "/eventspastleague.php?id=" + leagueId

    fun detailTeam(teamId: String?) = BuildConfig.BASE_URL + "api/v1/json/${BuildConfig.TSDB_API_KEY}" + "/lookupteam.php?id=" + teamId
    
//    const val SEARCH_TEAM = BuildConfig.BASE_URL + "api/v1/json/${BuildConfig.TSDB_API_KEY}" + "/search_all_teams.php"
//
//    const val SCHEDULE_NEXT = BuildConfig.BASE_URL + "api/v1/json/${BuildConfig.TSDB_API_KEY}" + "/eventsnextleague.php"
//
//    const val SCHEDULE_PREVIOUS = BuildConfig.BASE_URL + "api/v1/json/${BuildConfig.TSDB_API_KEY}" + "/eventspastleague.php"
//
//    const val DETAIL_TEAM = BuildConfig.BASE_URL + "api/v1/json/${BuildConfig.TSDB_API_KEY}" + "/lookupteam.php"
}