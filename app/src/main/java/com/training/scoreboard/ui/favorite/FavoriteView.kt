package com.training.scoreboard.ui.favorite

import com.training.scoreboard.data.db.Favorite
import com.training.scoreboard.ui.base.MVPView

interface FavoriteView : MVPView {

    fun showFavoriteList(favorites: List<Favorite>)
}