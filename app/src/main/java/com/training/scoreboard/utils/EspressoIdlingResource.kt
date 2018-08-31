package com.training.scoreboard.utils

import android.support.test.espresso.IdlingResource
import android.support.test.espresso.idling.CountingIdlingResource
import android.util.Log


class EspressoIdlingResource {

    companion object EspressoIdlingResource {

        private const val RESOURCE = "RESOURCE"
//
//        private val countingIdlingResource = CountingIdlingResource(RESOURCE)
//
//        open val idlingResource: IdlingResource
//            get() = countingIdlingResource
    }

    private val countingIdlingResource = CountingIdlingResource(RESOURCE)

    val idlingResource: IdlingResource
        get() = countingIdlingResource

    fun increment() {
        Log.i("EspressoIdlingResource", "incrementPrev")
        countingIdlingResource.increment()
    }

    fun decrement() {
        Log.i("EspressoIdlingResource", "decrementPrev")
        countingIdlingResource.decrement()
    }
}