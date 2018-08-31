package com.training.scoreboard.ui.detail.prev

import android.annotation.SuppressLint
import android.database.sqlite.SQLiteConstraintException
import android.graphics.Typeface
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.util.Log
import android.view.Gravity
import android.widget.ImageView
import android.widget.LinearLayout
import com.training.scoreboard.R
import com.training.scoreboard.R.id.add_to_favorite_prev
import com.training.scoreboard.data.db.Favorite
import com.training.scoreboard.data.db.database
import com.training.scoreboard.data.model.DetailTeam
import com.training.scoreboard.data.model.PreviousMatch
import com.training.scoreboard.ui.base.BaseActivity
import com.training.scoreboard.utils.extension.loadImage
import org.jetbrains.anko.*
import org.jetbrains.anko.db.classParser
import org.jetbrains.anko.db.delete
import org.jetbrains.anko.db.insert
import org.jetbrains.anko.db.select
import org.jetbrains.anko.design.coordinatorLayout
import org.jetbrains.anko.design.floatingActionButton
import org.jetbrains.anko.design.snackbar
import javax.inject.Inject

class DetailPrevActivity : BaseActivity(), DetailPrevView {

    companion object {
        const val TAG = "DetailPrevActivity"
        const val DETAIL_PREV_INTENT = "DETAIL_PREV_INTENT"
    }

    @Inject
    internal lateinit var presenter: DetailPrevPresenter<DetailPrevView>

    private lateinit var match: PreviousMatch

    private lateinit var homeTeamImg: ImageView

    private lateinit var awayTeamImg: ImageView

    private var isFavorited: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        presenter.onAttach(this)

        match = intent.getParcelableExtra(DETAIL_PREV_INTENT)

        presenter.getPrevMatchHome(match.idHomeTeam)
        presenter.getPrevMatchAway(match.idAwayTeam)

        favoriteState()

        ActivityUI().setContentView(this)

    }

    override fun showPrevMatch(match: PreviousMatch?) { }

    override fun showDetailTeamHome(teams: List<DetailTeam>?) {
        teams?.get(0)?.strTeamBadge?.let {
            homeTeamImg.loadImage(it)
            match.homeBadge = it
        }
    }

    override fun showDetailTeamAway(teams: List<DetailTeam>?) {
        teams?.get(0)?.strTeamBadge?.let {
            awayTeamImg.loadImage(it)
            match.awayBadge = it
        }
    }

    override fun showProgress() { }

    override fun hideProgress() { }

    private fun favoriteState(){
        database.use {
            val result = select(Favorite.TABLE_FAVORITE).whereArgs("(ID_EVENT = {id})", "id" to match.idEvent!!)
            val favorite = result.parseList(classParser<Favorite>())
            if (!favorite.isEmpty()) isFavorited = true
        }
    }

    private fun addToFavorite() {
        val homeLineup = (match.strHomeLineupGoalkeeper + "\n" +
                match.strHomeLineupDefense + "\n" +
                match.strHomeLineupMidfield + "\n" +
                match.strHomeLineupForward).replace(";", ",\n")

        val awayLineup = (match.strAwayLineupGoalkeeper + "\n" +
                match.strAwayLineupDefense + "\n" +
                match.strAwayLineupMidfield + "\n" +
                match.strAwayLineupForward).replace(";", ",\n")
        try {
            database.use {
                insert(Favorite.TABLE_FAVORITE,
                        Favorite.ID_EVENT to match.idEvent,
                        Favorite.DATE_EVENT to match.dateEvent,
                        Favorite.INT_HOME_GOAL to match.intHomeScore,
                        Favorite.INT_AWAY_GOAL to match.intAwayScore,
                        Favorite.HOME_TEAM_NAME to match.strHomeTeam,
                        Favorite.AWAY_TEAM_NAME to match.strAwayTeam,
                        Favorite.HOME_BADGE_TEAM to match.homeBadge,
                        Favorite.AWAY_BADGE_TEAM to match.awayBadge,
                        Favorite.HOME_GOAL_DETAIL to match.strHomeGoalDetails,
                        Favorite.AWAY_GOAL_DETAIL to match.strAwayGoalDetails,
                        Favorite.HOME_LINEUP to homeLineup,
                        Favorite.AWAY_LINEUP to awayLineup)
            }
            snackbar(homeTeamImg, "Added to favorite").show()
        } catch (e: SQLiteConstraintException) {
            snackbar(homeTeamImg, e.localizedMessage).show()
        }
    }

    private fun removeFromFavorite() {
        try {
            database.use {
                delete(Favorite.TABLE_FAVORITE, "(ID_EVENT = {id})",
                        "id" to match.idEvent!!)
            }
            snackbar(homeTeamImg, "Removed to favorite").show()
        } catch (e: SQLiteConstraintException) {
            snackbar(homeTeamImg, e.localizedMessage).show()
        }
    }

    inner class ActivityUI : AnkoComponent<DetailPrevActivity> {

        @SuppressLint("SetTextI18n")
        override fun createView(ui: AnkoContext<DetailPrevActivity>) = with(ui) {
            coordinatorLayout {
                relativeLayout {
                    textView {
                        id = R.id.date_tv
                        text = match.dateEvent
                        gravity = Gravity.CENTER
                        textSize = 22f
                        setTypeface(typeface, Typeface.ITALIC)
                    }.lparams(width = matchParent)
                    linearLayout {
                        id = R.id.layout_1
                        orientation = LinearLayout.HORIZONTAL
                        homeTeamImg = imageView {
                            id = R.id.home_team_img
                            scaleType = ImageView.ScaleType.CENTER_CROP
                        }.lparams {
                            width = 100
                            height = 100
                            leftMargin = dip(16)
                            gravity = Gravity.CENTER
                        }
                        textView {
                            id = R.id.home_score_tv
                            text = match.intHomeScore
                            gravity = Gravity.CENTER
                            textSize = 42f
                            setTypeface(typeface, Typeface.BOLD)
                        }.lparams(width = dip(0)) {
                            weight = 1f
                            gravity = Gravity.CENTER
                        }
                        textView {
                            id = R.id.vs_tv
                            text = "vs"
                            gravity = Gravity.CENTER
                            textSize = 28f //sp
                        }.lparams(width = dip(0)) {
                            weight = 1f
                            gravity = Gravity.CENTER
                        }
                        textView {
                            id = R.id.away_score_tv
                            text = match.intAwayScore
                            gravity = Gravity.CENTER
                            textSize = 42f //sp
                            setTypeface(typeface, Typeface.BOLD)
                        }.lparams(width = dip(0)) {
                            weight = 1f
                            gravity = Gravity.CENTER
                        }
                        awayTeamImg = imageView {
                            id = R.id.away_team_img
                            scaleType = ImageView.ScaleType.CENTER_CROP
                        }.lparams {
                            width = 100
                            height = 100
                            rightMargin = dip(16)
                            gravity = Gravity.CENTER
                        }
                    }.lparams(width = matchParent) {
                        below(R.id.date_tv)
                    }
                    linearLayout {
                        id = R.id.layout_2
                        orientation = LinearLayout.HORIZONTAL
                        textView {
                            id = R.id.home_name_tv
                            text = match.strHomeTeam
                            gravity = Gravity.CENTER
                            textSize = 22f //sp
                        }.lparams(width = dip(0)) {
                            weight = 1f
                            gravity = Gravity.CENTER
                        }
                        textView {
                            id = R.id.away_name_tv
                            text = match.strAwayTeam
                            gravity = Gravity.CENTER
                            textSize = 22f //sp
                        }.lparams(width = dip(0)) {
                            weight = 1f
                            gravity = Gravity.CENTER
                        }
                    }.lparams(width = matchParent) {
                        below(R.id.layout_1)
                    }
                    view {
                        id = R.id.view
                        backgroundColor = resources.getColor(android.R.color.darker_gray)
                    }.lparams(width = matchParent, height = dip(1)) {
                        below(R.id.layout_2)
                        leftMargin = dip(16)
                        rightMargin = dip(16)
                        topMargin = dip(16)
                        bottomMargin = dip(16)
                    }
                    textView {
                        id = R.id.goal_scorer_tv
                        gravity = Gravity.CENTER
                        text = "Scorer"
                    }.lparams(width = matchParent) {
                        below(R.id.view)
                    }
                    relativeLayout {
                        id = R.id.goal_scorer_layout
                        textView {
                            id = R.id.detail_goal_home_name_tv
                            gravity = Gravity.LEFT
                            text = (match.strHomeGoalDetails)?.replace(";", ",\n")
                        }.lparams {
                            leftMargin = dip(16)
                        }
                        textView {
                            id = R.id.detail_goal_away_name_tv
                            gravity = Gravity.RIGHT
                            text = (match.strAwayGoalDetails)?.replace(";", ",\n")
                        }.lparams {
                            rightMargin = dip(16)
                            alignParentEnd()
                        }
                    }.lparams(width = matchParent) {
                        below(R.id.goal_scorer_tv)
                    }
                    view {
                        id = R.id.view2
                        backgroundColor = resources.getColor(android.R.color.darker_gray)
                    }.lparams(width = matchParent, height = dip(1)) {
                        below(R.id.goal_scorer_layout)
                        leftMargin = dip(16)
                        rightMargin = dip(16)
                        topMargin = dip(16)
                        bottomMargin = dip(16)
                    }
                    textView {
                        id = R.id.lineup_tv
                        gravity = Gravity.CENTER
                        text = "Lineup"
                    }.lparams(width = matchParent) {
                        below(R.id.view2)
                    }
                    relativeLayout {
                        id = R.id.lineup_layout
                        textView {
                            id = R.id.detail_lineup_home_tv
                            gravity = Gravity.LEFT
                            val txt = match.strHomeLineupGoalkeeper + "\n" +
                                    match.strHomeLineupDefense + "\n" +
                                    match.strHomeLineupMidfield + "\n" +
                                    match.strHomeLineupForward
                            val textAfterChange = txt.replace(";", ",\n")
                            text = textAfterChange
                        }.lparams {
                            leftMargin = dip(16)
                        }
                        textView {
                            id = R.id.detail_lineup_away_tv
                            gravity = Gravity.RIGHT
                            val txt = match.strAwayLineupGoalkeeper + "\n" +
                                    match.strAwayLineupDefense + "\n" +
                                    match.strAwayLineupMidfield + "\n" +
                                    match.strAwayLineupForward
                            val textAfterChange = txt.replace(";", ",\n")
                            text = textAfterChange
                        }.lparams {
                            rightMargin = dip(16)
                            alignParentEnd()
                        }
                    }.lparams(width = matchParent) {
                        below(R.id.lineup_tv)
                    }
                }
                floatingActionButton {
                    id = add_to_favorite_prev
                    setBackgroundColor(resources.getColor(R.color.colorPrimary))
                    backgroundTintList = ContextCompat.getColorStateList(ctx, R.color.colorPrimary)
                    if (isFavorited) {
                        imageResource = R.drawable.ic_added_to_favorites
                    } else {
                        imageResource = R.drawable.ic_add_to_favorites
                    }
                    setOnClickListener {
                        if (isFavorited) {
                            imageResource = R.drawable.ic_add_to_favorites
                            removeFromFavorite()
                        } else {
                            imageResource = R.drawable.ic_added_to_favorites
                            addToFavorite()
                        }
                        isFavorited = !isFavorited
                    }
                }.lparams {
                    width = wrapContent
                    height = wrapContent
                    rightMargin = dip(24)
                    bottomMargin = dip(24)
                    gravity = Gravity.BOTTOM or Gravity.END
                }
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.onDetach()
    }
}
