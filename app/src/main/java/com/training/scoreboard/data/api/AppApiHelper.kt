package com.training.scoreboard.data.api

import android.content.Context
import com.rx2androidnetworking.Rx2AndroidNetworking
import com.training.scoreboard.data.model.*
import com.training.scoreboard.utils.CoroutineContextProvider
import io.reactivex.Single
import javax.inject.Inject

class AppApiHelper @Inject constructor(private val context: Context) : ApiHelper {

//    override fun performTeamList(league: String?): Single<TeamResponse> =
//            Rx2AndroidNetworking.get(TheSportDBApi.SEARCH_TEAM)
//                    .addQueryParameter("l", league)
//                    .build()
//                    .getObjectSingle(TeamResponse::class.java)
//
//    override fun performNextMatchList(leagurId: String?): Single<NextMatchResponse> =
//            Rx2AndroidNetworking.get(TheSportDBApi.SCHEDULE_NEXT)
//                    .addQueryParameter("id", leagurId)
//                    .build()
//                    .getObjectSingle(NextMatchResponse::class.java)
//
//    override fun performPreviousMatchList(leagurId: String?): Single<PreviousMatchResponse> =
//            Rx2AndroidNetworking.get(TheSportDBApi.SCHEDULE_PREVIOUS)
//                    .addQueryParameter("id", leagurId)
//                    .build()
//                    .getObjectSingle(PreviousMatchResponse::class.java)
//
//    override fun performTeamDetail(teamId: String?): Single<DetailTeamResponse> =
//            Rx2AndroidNetworking.get(TheSportDBApi.DETAIL_TEAM)
//                    .addQueryParameter("id", teamId)
//                    .build()
//                    .getObjectSingle(DetailTeamResponse::class.java)

}