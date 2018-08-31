package com.training.scoreboard.ui.base

import com.training.scoreboard.data.DataManager
import com.training.scoreboard.utils.SchedulerProvider
import io.reactivex.disposables.CompositeDisposable

abstract class BasePresenter<V : MVPView> internal constructor(var dataManager: DataManager, var schedulerProvider: SchedulerProvider) {

    val compositeDisposable: CompositeDisposable = CompositeDisposable()

    private var view: V? = null
    private val isViewAttached: Boolean get() = view != null

    fun onAttach(view: V?) {
        this.view = view
    }

    fun getView(): V? = view

    fun onDetach() {
        compositeDisposable.dispose()
        view = null
    }
}