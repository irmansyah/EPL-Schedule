package com.training.scoreboard.ui.favorite

import dagger.Module
import dagger.Provides

@Module
class FavoriteFragmentModule {

    @Provides
    internal fun provideFavoriteAdapter(): FavoriteAdapter = FavoriteAdapter(ArrayList())
}