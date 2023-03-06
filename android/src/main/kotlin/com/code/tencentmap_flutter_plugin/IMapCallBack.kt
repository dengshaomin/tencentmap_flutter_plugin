package com.code.tencentmap_flutter_plugin

import android.view.View
import com.tencent.tencentmap.mapsdk.maps.TencentMap.OnMapLoadedCallback

interface IMapCallBack {
    fun onMapLoaded()
    abstract fun onViewCreated()
}