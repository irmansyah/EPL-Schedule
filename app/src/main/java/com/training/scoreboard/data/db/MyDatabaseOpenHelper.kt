package com.training.scoreboard.data.db

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import org.jetbrains.anko.db.*

class ScheduleDatabaseOpenHelper(ctx: Context) : ManagedSQLiteOpenHelper(ctx,  "FavoriteTeam.db", null, 1) {

    companion object {
        private var instance: ScheduleDatabaseOpenHelper? = null

        @Synchronized
        fun getInstance(ctx: Context): ScheduleDatabaseOpenHelper {
            if (instance == null) {
                instance = ScheduleDatabaseOpenHelper(ctx.applicationContext)
            }
            return instance as ScheduleDatabaseOpenHelper
        }
    }

    override fun onCreate(db: SQLiteDatabase) {
        db.createTable(Favorite.TABLE_FAVORITE, true,
                Favorite.ID to INTEGER + PRIMARY_KEY + AUTOINCREMENT,
                Favorite.ID_EVENT to TEXT + UNIQUE,
                Favorite.DATE_EVENT to TEXT,
                Favorite.HOME_TEAM_NAME to TEXT,
                Favorite.AWAY_TEAM_NAME to TEXT,
                Favorite.HOME_BADGE_TEAM to TEXT,
                Favorite.AWAY_BADGE_TEAM to TEXT,
                Favorite.INT_HOME_GOAL to TEXT,
                Favorite.INT_AWAY_GOAL to TEXT,
                Favorite.HOME_GOAL_DETAIL to TEXT,
                Favorite.AWAY_GOAL_DETAIL to TEXT,
                Favorite.HOME_LINEUP to TEXT,
                Favorite.AWAY_LINEUP to TEXT)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.dropTable(Favorite.TABLE_FAVORITE, true)
    }
}

val Context.database: ScheduleDatabaseOpenHelper
    get() = ScheduleDatabaseOpenHelper.getInstance(applicationContext)