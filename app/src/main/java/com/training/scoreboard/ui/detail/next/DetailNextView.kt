package com.training.scoreboard.ui.detail.next

import com.training.scoreboard.data.model.DetailTeam
import com.training.scoreboard.data.model.NextMatch
import com.training.scoreboard.ui.base.MVPView

interface DetailNextView : MVPView {

    fun showNextMatch(match: NextMatch?)

    fun showDetailTeamHome(teams: List<DetailTeam>?)

    fun showDetailTeamAway(teams: List<DetailTeam>?)
}