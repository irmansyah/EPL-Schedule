package com.training.scoreboard.ui.nextMatch

import com.training.scoreboard.data.model.NextMatch
import com.training.scoreboard.ui.base.MVPView

interface NextMatchView : MVPView {

    fun showNextMatchList(data: List<NextMatch>)
}