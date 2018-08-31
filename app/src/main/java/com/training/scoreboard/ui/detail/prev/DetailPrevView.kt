package com.training.scoreboard.ui.detail.prev

import com.training.scoreboard.data.model.DetailTeam
import com.training.scoreboard.data.model.PreviousMatch
import com.training.scoreboard.ui.base.MVPView

interface DetailPrevView : MVPView {

    fun showPrevMatch(match: PreviousMatch?)

    fun showDetailTeamHome(teams: List<DetailTeam>?)

    fun showDetailTeamAway(teams: List<DetailTeam>?)
}