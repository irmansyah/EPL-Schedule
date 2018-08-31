package com.training.scoreboard.ui.previousMatch

import com.training.scoreboard.data.model.PreviousMatch
import com.training.scoreboard.ui.base.MVPView

interface PreviousMatchView : MVPView {

    fun showPreviousMatchList(data: List<PreviousMatch>)
}