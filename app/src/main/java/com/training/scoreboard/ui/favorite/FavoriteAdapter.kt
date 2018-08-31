package com.training.scoreboard.ui.favorite

import android.graphics.Typeface
import android.support.v7.widget.RecyclerView
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import com.training.scoreboard.R
import com.training.scoreboard.data.db.Favorite
import org.jetbrains.anko.*
import org.jetbrains.anko.sdk25.coroutines.onClick

class FavoriteAdapter(private val favorite: ArrayList<Favorite>): RecyclerView.Adapter<FavoriteAdapter.FavoriteViewHolder>() {

    private lateinit var mListener: (Favorite) -> Unit

    fun setOnItemCLickListener(listener: (Favorite) -> Unit) {
        this.mListener = listener
    }

    override fun getItemCount(): Int = favorite.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoriteViewHolder =
            FavoriteViewHolder(UI().createView(AnkoContext.create(parent.context, parent)))

    override fun onBindViewHolder(holder: FavoriteViewHolder, position: Int) {
        holder.bindItem(favorite[position], mListener)
    }

    fun addToList(matches: List<Favorite>) {
        favorite.clear()
        favorite.addAll(matches)
        notifyDataSetChanged()
    }

    inner class UI : AnkoComponent<ViewGroup> {
        override fun createView(ui: AnkoContext<ViewGroup>): View = with(ui) {
            linearLayout {
                orientation = LinearLayout.VERTICAL
                topPadding = dip(8)
                bottomPadding = dip(8)
                linearLayout {
                    orientation = LinearLayout.HORIZONTAL
                    textView {
                        id = R.id.schedule_home_score
                        width = R.id.wrap_content
                        allCaps = true
                        text = "Team 1"
                        textSize = sp(24).toFloat()
                        setTypeface(typeface, Typeface.BOLD)
                        gravity = Gravity.CENTER
                    }.lparams {
                        weight = 1F
                    }
                    textView {
                        id = R.id.date_match
                        allCaps = true
                        text = "Date"
                    }
                    textView {
                        id = R.id.schedule_away_score
                        width = R.id.wrap_content
                        allCaps = true
                        text = "Team 2"
                        textSize = sp(24).toFloat()
                        setTypeface(typeface, Typeface.BOLD)
                        gravity = Gravity.CENTER
                    }.lparams {
                        weight = 1F
                    }
                }.lparams(width = matchParent, height = wrapContent)

                linearLayout {
                    orientation = LinearLayout.HORIZONTAL
                    textView {
                        id = R.id.schedule_team_home
                        width = R.id.wrap_content
                        allCaps = true
                        text = "Team 1"
                        setTypeface(typeface, Typeface.BOLD)
                        gravity = Gravity.CENTER
                    }.lparams {
                        weight = 1F
                    }
                    textView {
                        allCaps = true
                        text = "VS"
                    }
                    textView {
                        id = R.id.schedule_team_away
                        width = R.id.wrap_content
                        allCaps = true
                        text = "Team 2"
                        setTypeface(typeface, Typeface.BOLD)
                        gravity = Gravity.CENTER
                    }.lparams {
                        weight = 1F
                    }
                }.lparams(width = matchParent, height = wrapContent)
                view {
                    backgroundColor = resources.getColor(R.color.light_gray)
                }.lparams(width = matchParent, height = dip(1)) {
                    topMargin = dip(8)
                }
            }
        }
    }

    inner class FavoriteViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        private val homeTeamTv: TextView = view.find(R.id.schedule_team_home)
        private val awayTeamTv: TextView = view.find(R.id.schedule_team_away)
        private val dateTv: TextView = view.find(R.id.date_match)
        private val homeScoreTv: TextView = view.find(R.id.schedule_home_score)
        private val awayScoreTv: TextView = view.find(R.id.schedule_away_score)

        fun bindItem(match: Favorite, listener: (Favorite) -> Unit) {
            homeTeamTv.text = match.homeTeamName
            awayTeamTv.text = match.awayTeamName
            dateTv.text = match.dateEvent
            homeScoreTv.text = match.intHomeGoal
            awayScoreTv.text = match.intAwayGoal

            itemView.onClick { listener(match) }
        }
    }
}