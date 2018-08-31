package com.training.scoreboard.ui.main

import android.graphics.Color
import android.os.Bundle
import android.support.design.widget.AppBarLayout
import android.support.design.widget.BottomNavigationView
import android.support.v4.app.Fragment
import android.view.Gravity
import android.view.MenuItem
import android.widget.LinearLayout
import com.irmansyah.masjid_finder.utils.extension.addFragment
import com.irmansyah.masjid_finder.utils.extension.replaceFragment
import com.training.scoreboard.R
import com.training.scoreboard.R.id.*
import com.training.scoreboard.ui.base.BaseActivity
import com.training.scoreboard.ui.favorite.FavoriteFragment
import com.training.scoreboard.ui.nextMatch.NextMatchFragment
import com.training.scoreboard.ui.previousMatch.PreviousMatchFragment
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.support.HasSupportFragmentInjector
import org.jetbrains.anko.*
import org.jetbrains.anko.design.bottomNavigationView
import org.jetbrains.anko.design.coordinatorLayout
import javax.inject.Inject
import android.support.test.espresso.IdlingResource
import android.support.annotation.VisibleForTesting
import com.training.scoreboard.utils.EspressoIdlingResource


class MainActivity : BaseActivity(), MainView, BottomNavigationView.OnNavigationItemSelectedListener, HasSupportFragmentInjector {

    companion object {
        const val TAG = "MainActivity"
    }

    private lateinit var bottomNav: BottomNavigationView

    @Inject
    internal lateinit var fragmentDispatchingAndroidInjector: DispatchingAndroidInjector<Fragment>

    @Inject
    internal lateinit var presenter: MainPresenter<MainView>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        presenter.onAttach(this)

        MainActivityUI().setContentView(this)

        bottomNav.setOnNavigationItemSelectedListener(this)
        bottomNav.selectedItemId = previous_bottom
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            previous_bottom -> openPrevFragment()
            next_bottom -> openNextFragment()
            favorite_bottom -> openFavoriteFragment()
        }
        return true
    }

    private fun addPrevFragment() {
        supportFragmentManager?.addFragment(R.id.f_root_view, PreviousMatchFragment.newInstance(), PreviousMatchFragment.TAG)
    }

    private fun openPrevFragment() {
        supportFragmentManager?.replaceFragment(R.id.f_root_view, PreviousMatchFragment.newInstance(), PreviousMatchFragment.TAG)
    }

    private fun openNextFragment() {
        supportFragmentManager?.replaceFragment(R.id.f_root_view, NextMatchFragment.newInstance(), NextMatchFragment.TAG)
    }

    private fun openFavoriteFragment() {
        supportFragmentManager?.replaceFragment(R.id.f_root_view, FavoriteFragment.newInstance(), FavoriteFragment.TAG)
    }

    override fun showProgress() {
    }

    override fun hideProgress() {
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.onDetach()
    }

    inner class MainActivityUI : AnkoComponent<MainActivity> {

        override fun createView(ui: AnkoContext<MainActivity>) = with(ui) {
            coordinatorLayout {
                fitsSystemWindows = true
                relativeLayout {
                    frameLayout {
                        id = f_root_view
                    }.lparams(width = matchParent, height = matchParent) {
                        above(R.id.bottom_layout)
                    }
                    linearLayout {
                        id = R.id.bottom_layout
                        orientation = LinearLayout.VERTICAL
                        view {
                            backgroundResource = R.drawable.shadow
                        }.lparams(width = matchParent, height = dip(4))
                        bottomNav = bottomNavigationView {
                            id = navigation_bottom
                            topPadding = dip(8)
                            bottomPadding = dip(8)
                            inflateMenu(R.menu.navigation_items)
                            foregroundGravity = Gravity.BOTTOM
                        }.lparams(width = matchParent)
                    }.lparams(width = matchParent) {
                        alignParentBottom()
                    }
                }.lparams(width = matchParent, height = matchParent)
            }
        }
    }

    override fun supportFragmentInjector(): AndroidInjector<Fragment> = fragmentDispatchingAndroidInjector
}
