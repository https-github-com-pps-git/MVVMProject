package com.example.base.viewmodel

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.OnLifecycleEvent

/**
 * 感知生命周期
 */
interface BaseViewModelListener : LifecycleObserver {


    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    fun onLifeCreate()

    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    fun onLifeResume()

    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
    fun onLifeStop()

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    fun onLifeDestroy()

}