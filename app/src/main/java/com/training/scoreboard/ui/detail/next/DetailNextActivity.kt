package com.training.scoreboard.ui.detail.next

import android.database.sqlite.SQLiteConstraintException
import android.graphics.Typeface
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.view.Gravity
import android.widget.ImageView
import android.widget.LinearLayout
import com.training.scoreboard.R
import com.training.scoreboard.data.db.Favorite
import com.training.scoreboard.data.db.database
import com.training.scoreboard.data.model.DetailTeam
import com.training.scoreboard.data.model.NextMatch
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

class DetailNextActivity : BaseActivity(), DetailNextView {

    companion object {
        const val DETAIL_NEXT_INTENT = "DETAIL_NEXT_INTENT"
    }

    @Inject
    internal lateinit var presenter: DetailNextPresenter<DetailNextView>

    private lateinit var match: NextMatch

    private lateinit var homeTeamImg: ImageView

    private lateinit var awayTeamImg: ImageView

    private var isFavorited: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        presenter.onAttach(this)

        match = intent.getParcelableExtra(DETAIL_NEXT_INTENT)

        presenter.getNextMatchHome(match.idHomeTeam)
        presenter.getNextMatchAway(match.idAwayTeam)

        favoriteState()

        ActivityUI().setContentView(this)
    }

    override fun showNextMatch(match: NextMatch?) { }

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

    inner class ActivityUI : AnkoComponent<DetailNextActivity> {

        override fun createView(ui: AnkoContext<DetailNextActivity>) = with(ui) {
            coordinatorLayout {
                relativeLayout {
                    textView {
                        id = R.id.next_date_tv
                        text = match.dateEvent
                        gravity = Gravity.CENTER
                        textSize = 22f
                        setTypeface(typeface, Typeface.ITALIC)
                    }.lparams(width = matchParent)
                    linearLayout {
                        id = R.id.next_layout_1
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
                            id = R.id.vs_tv
                            text = "vs"
                            gravity = Gravity.CENTER
                            textSize = 28f //sp
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
                        below(R.id.next_date_tv)
                    }
                    linearLayout {
                        id = R.id.next_layout_2
                        orientation = LinearLayout.HORIZONTAL
                        textView {
                            id = R.id.next_home_name_tv
                            text = match.strHomeTeam
                            gravity = Gravity.CENTER
                            textSize = 22f //sp
                        }.lparams(width = dip(0)) {
                            weight = 1f
                            gravity = Gravity.CENTER
                        }
                        textView {
                            id = R.id.next_away_name_tv
                            text = match.strAwayTeam
                            gravity = Gravity.CENTER
                            textSize = 22f //sp
                        }.lparams(width = dip(0)) {
                            weight = 1f
                            gravity = Gravity.CENTER
                        }
                    }.lparams(width = matchParent) {
                        below(R.id.next_layout_1)
                    }
                    view {
                        id = R.id.next_view
                        backgroundColor = resources.getColor(android.R.color.darker_gray)
                    }.lparams(width = matchParent, height = dip(1)) {
                        below(R.id.next_layout_2)
                        leftMargin = dip(16)
                        rightMargin = dip(16)
                        topMargin = dip(16)
                        bottomMargin = dip(16)
                    }
                }
                floatingActionButton {
                    id = R.id.add_to_favorite_next
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
