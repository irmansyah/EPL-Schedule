package com.training.scoreboard.ui.favorite

import android.content.Context
import android.os.Bundle
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.ProgressBar
import com.training.scoreboard.R
import com.training.scoreboard.data.db.Favorite
import com.training.scoreboard.data.db.database
import com.training.scoreboard.data.model.PreviousMatch
import com.training.scoreboard.ui.base.BaseFragment
import com.training.scoreboard.ui.detail.next.DetailNextActivity
import com.training.scoreboard.ui.detail.prev.DetailPrevActivity
import com.training.scoreboard.utils.extension.invisible
import com.training.scoreboard.utils.extension.visible
import kotlinx.coroutines.experimental.selects.select
import org.jetbrains.anko.*
import org.jetbrains.anko.db.classParser
import org.jetbrains.anko.db.select
import org.jetbrains.anko.recyclerview.v7.recyclerView
import org.jetbrains.anko.support.v4.ctx
import org.jetbrains.anko.support.v4.onRefresh
import org.jetbrains.anko.support.v4.swipeRefreshLayout
import javax.inject.Inject


class FavoriteFragment : BaseFragment(), AnkoComponent<Context>, FavoriteView {

    companion object {
        const val TAG = "FavoriteFragment"
        fun newInstance(): FavoriteFragment = FavoriteFragment()
    }

    private lateinit var favoriteListEvent: RecyclerView
    private lateinit var progressBar: ProgressBar
    private lateinit var swipeRefresh: SwipeRefreshLayout

    @Inject
    internal lateinit var mAdapter: FavoriteAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mAdapter.setOnItemCLickListener {
            val match = PreviousMatch()
            match.idEvent = it.idEvent
            match.dateEvent = it.dateEvent
            match.strHomeTeam = it.homeTeamName
            match.strAwayTeam = it.awayTeamName
            match.homeBadge = it.homeBadgeTeam
            match.awayBadge = it.awayBadgeTeam
            match.intHomeScore = it.intHomeGoal
            match.intAwayScore = it.intAwayGoal
            match.strHomeGoalDetails = it.homeGoalDetail
            match.strAwayGoalDetails = it.awayGoalDetail
            match.strHomeLineupGoalkeeper = it.homeLineup
            match.strHomeLineupDefense = ""
            match.strHomeLineupMidfield = ""
            match.strHomeLineupForward = ""
            match.strAwayLineupGoalkeeper = it.awayLineup
            match.strAwayLineupDefense = ""
            match.strAwayLineupMidfield = ""
            match.strAwayLineupForward = ""

            ctx.startActivity<DetailPrevActivity>(DetailPrevActivity.DETAIL_PREV_INTENT to match)
        }

        favoriteListEvent.adapter = mAdapter
        showFavorite()
        swipeRefresh.onRefresh {
            showFavorite()
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? =
            createView(AnkoContext.create(ctx))

    private fun showFavorite(){
        context?.database?.use {
            swipeRefresh.isRefreshing = false
            hideProgress()
            val result = select(Favorite.TABLE_FAVORITE)
            val favorites = result.parseList(classParser<Favorite>())
            mAdapter.addToList(favorites)
        }
    }

    override fun showFavoriteList(favorites: List<Favorite>) { }

    override fun showProgress() {
        progressBar.visible()
    }

    override fun hideProgress() {
        progressBar.invisible()
    }

    override fun createView(ui: AnkoContext<Context>): View = with(ui) {
        linearLayout {
            id = R.id.favorite_match_fragment
            lparams (width = matchParent, height = wrapContent)
            orientation = LinearLayout.VERTICAL
            topPadding = dip(16)
            leftPadding = dip(16)
            rightPadding = dip(16)

            swipeRefresh = swipeRefreshLayout {
                setColorSchemeResources(R.color.colorAccent,
                        android.R.color.holo_green_light,
                        android.R.color.holo_orange_light,
                        android.R.color.holo_red_light)

                relativeLayout{
                    lparams (width = matchParent, height = wrapContent)

                    favoriteListEvent  = recyclerView {
                        id = R.id.favoriteListEvent
                        lparams (width = matchParent, height = wrapContent)
                        layoutManager = LinearLayoutManager(ctx)
                    }

                    progressBar = progressBar {
                    }.lparams{
                        centerHorizontally()
                    }
                }
            }
        }
    }
}
