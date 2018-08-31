package com.training.scoreboard

import android.app.Activity
import android.app.Application
import android.content.BroadcastReceiver
import com.androidnetworking.AndroidNetworking
import com.androidnetworking.interceptors.HttpLoggingInterceptor
import com.training.scoreboard.di.component.DaggerAppComponent
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasActivityInjector
import dagger.android.HasBroadcastReceiverInjector
import javax.inject.Inject

class ScoreBoardApp: Application(), HasActivityInjector , HasBroadcastReceiverInjector {

    @Inject
    internal lateinit var  activityDispatchingAndroidInjector: DispatchingAndroidInjector<Activity>

    @Inject
    internal lateinit var  broadcastReceiverInjector: DispatchingAndroidInjector<BroadcastReceiver>

    override fun activityInjector(): AndroidInjector<Activity> = activityDispatchingAndroidInjector

    override fun broadcastReceiverInjector(): AndroidInjector<BroadcastReceiver> = broadcastReceiverInjector

    override fun onCreate() {
        super.onCreate()

        DaggerAppComponent.builder()
                .application(this)
                .build()
                .inject(this)

        AndroidNetworking.initialize(applicationContext)
        if (BuildConfig.DEBUG) {
            AndroidNetworking.enableLogging(HttpLoggingInterceptor.Level.BODY)
        }
    }
}