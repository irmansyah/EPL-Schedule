package com.training.scoreboard.ui.nextMatch

import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class NextMatchFragmentProvider {

    @ContributesAndroidInjector(modules = [NextMatchFragmentModule::class])
    internal abstract fun provideNextMatchFragmentFactory(): NextMatchFragment
}