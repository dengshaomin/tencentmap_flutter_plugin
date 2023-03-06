package com.code.tencentmap_flutter_plugin

import android.content.Context
import android.view.View
import androidx.lifecycle.DefaultLifecycleObserver
import com.tencent.tencentmap.mapsdk.maps.MapView
import com.tencent.tencentmap.mapsdk.maps.TencentMap
import io.flutter.plugin.common.StandardMessageCodec
import io.flutter.plugin.platform.PlatformView
import io.flutter.plugin.platform.PlatformViewFactory

class NativeViewFactory(
    var iMapCallBack: IMapCallBack?,
    private val lifecycleProvider: LifecycleProvider
) :
    PlatformViewFactory(StandardMessageCodec.INSTANCE), PlatformView {
    private var mapView: NativeTencentView? = null

    override fun create(context: Context, viewId: Int, args: Any?): PlatformView {
        val creationParams = args as Map<String?, Any?>?
        mapView = NativeTencentView(context, iMapCallBack, lifecycleProvider)
        iMapCallBack?.onViewCreated()
        return this
    }

    override fun getView(): View? {
        return mapView
    }

    override fun dispose() {
        mapView?.tencent_map?.onDestroy()
    }


}