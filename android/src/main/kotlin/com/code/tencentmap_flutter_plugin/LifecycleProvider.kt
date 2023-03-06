package com.code.tencentmap_flutter_plugin

import androidx.lifecycle.Lifecycle

interface LifecycleProvider {
    fun getLifecycle(): Lifecycle?
}