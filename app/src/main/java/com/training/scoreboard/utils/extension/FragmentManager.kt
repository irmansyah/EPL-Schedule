package com.irmansyah.masjid_finder.utils.extension

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import com.training.scoreboard.R

/**
 * Created by irmansyah on 25/03/18.
 */
internal fun FragmentManager.removeFragment(tag: String) {
    this.beginTransaction()
            .disallowAddToBackStack()
            .remove(this.findFragmentByTag(tag))
            .commitNow()
}

internal fun FragmentManager.addFragment(containerViewId: Int,
                                         fragment: Fragment,
                                         tag: String) {
    this.beginTransaction()
            .disallowAddToBackStack()
            .add(containerViewId, fragment, tag)
            .commitNow()
}

internal fun FragmentManager.replaceFragment(containerViewId: Int,
                                             fragment: Fragment,
                                             tag: String) {
    this.beginTransaction()
            .disallowAddToBackStack()
            .replace(containerViewId, fragment, tag)
            .commitNow()
}