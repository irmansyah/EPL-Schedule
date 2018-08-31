package com.training.scoreboard.ui.base

import android.support.v4.app.Fragment
import android.os.Bundle
import dagger.android.support.AndroidSupportInjection

abstract class BaseFragment : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidSupportInjection.inject(this)
        super.onCreate(savedInstanceState)
    }
}