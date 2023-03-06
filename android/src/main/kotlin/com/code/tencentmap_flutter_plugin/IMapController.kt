package com.code.tencentmap_flutter_plugin

import com.tencent.tencentmap.mapsdk.maps.CameraUpdate
import com.tencent.tencentmap.mapsdk.maps.TencentMap.CancelableCallback

interface IMapController {
    fun setMapType(type: Int)
    fun animateCamera(
        cameraUpdate: CameraUpdate,
        duration: Long,
        cancelableCallback: CancelableCallback
    )
}