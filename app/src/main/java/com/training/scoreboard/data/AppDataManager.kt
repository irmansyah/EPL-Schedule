package com.training.scoreboard.data

import android.content.Context
import com.training.scoreboard.data.api.AppApiHelper
import com.training.scoreboard.data.model.DetailTeamResponse
import com.training.scoreboard.data.model.NextMatchResponse
import com.training.scoreboard.data.model.PreviousMatchResponse
import com.training.scoreboard.data.model.TeamResponse
import io.reactivex.Single
import javax.inject.Inject

class AppDataManager @Inject constructor(private val context: Context, private val apiHelper: AppApiHelper) : DataManager {

}