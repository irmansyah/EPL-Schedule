package com.training.scoreboard.ui.previousMatch

import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class PreviousMatchFragmentProvider {

    @ContributesAndroidInjector(modules = [PreviousMatchFragmentModule::class])
    internal abstract fun providePreviousMatchFragmentFactory(): PreviousMatchFragment
}