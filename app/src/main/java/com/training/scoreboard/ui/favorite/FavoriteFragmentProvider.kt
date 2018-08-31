package com.training.scoreboard.ui.favorite

import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class FavoriteFragmentProvider {

    @ContributesAndroidInjector(modules = [FavoriteFragmentModule::class])
    internal abstract fun provideFavoriteFragmentFactory(): FavoriteFragment
}