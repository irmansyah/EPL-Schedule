package com.training.scoreboard.di.builder

import com.training.scoreboard.ui.detail.next.DetailNextActivity
import com.training.scoreboard.ui.detail.next.DetailNextActivityModule
import com.training.scoreboard.ui.detail.prev.DetailPrevActivity
import com.training.scoreboard.ui.detail.prev.DetailPrevActivityModule
import com.training.scoreboard.ui.favorite.FavoriteFragmentProvider
import com.training.scoreboard.ui.main.MainActivity
import com.training.scoreboard.ui.main.MainActivityModule
import com.training.scoreboard.ui.nextMatch.NextMatchFragmentModule
import com.training.scoreboard.ui.nextMatch.NextMatchFragmentProvider
import com.training.scoreboard.ui.previousMatch.PreviousMatchFragmentModule
import com.training.scoreboard.ui.previousMatch.PreviousMatchFragmentProvider
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ActivityBuilder {

    @ContributesAndroidInjector(modules = [(MainActivityModule::class), (PreviousMatchFragmentProvider::class), (NextMatchFragmentProvider::class), (FavoriteFragmentProvider::class)])
    abstract fun bindMainActivity(): MainActivity

    @ContributesAndroidInjector(modules = [(DetailPrevActivityModule::class)])
    abstract fun bindDetailPrevActivity(): DetailPrevActivity

    @ContributesAndroidInjector(modules = [(DetailNextActivityModule::class)])
    abstract fun bindDetailNextActivity(): DetailNextActivity
}