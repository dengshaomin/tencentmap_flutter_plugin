package com.code.tencentmap_flutter_plugin

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.FrameLayout
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import com.tencent.tencentmap.mapsdk.maps.CameraUpdate
import com.tencent.tencentmap.mapsdk.maps.MapView
import com.tencent.tencentmap.mapsdk.maps.TencentMap

class NativeTencentView @JvmOverloads constructor(
    context: Context,
    var iMapCallBack: IMapCallBack?,
    lifecycleProvider: LifecycleProvider,
    attrs: AttributeSet? = null
) : FrameLayout(context, attrs), DefaultLifecycleObserver {
    lateinit var tencent_map: MapView
    var iMapController: IMapController? = object : IMapController {
        override fun setMapType(type: Int) {
            tencent_map.map?.mapType = type
        }

        override fun animateCamera(
            cameraUpdate: CameraUpdate,
            duration: Long,
            cancelableCallback: TencentMap.CancelableCallback
        ) {
            tencent_map?.map?.animateCamera(cameraUpdate,duration,cancelableCallback)
        }
    }

    init {
        LayoutInflater.from(context).inflate(R.layout.view_native_tencent, this)
        initView()
        lifecycleProvider.getLifecycle()?.addObserver(this)
    }

    override fun onStart(owner: LifecycleOwner) {
        super.onStart(owner)
        tencent_map?.onStart()
    }

    override fun onResume(owner: LifecycleOwner) {
        super.onResume(owner)
        tencent_map?.onResume()
    }

    override fun onPause(owner: LifecycleOwner) {
        super.onPause(owner)
        tencent_map?.onPause()
    }

    override fun onDestroy(owner: LifecycleOwner) {
        super.onDestroy(owner)
        tencent_map?.onDestroy()
    }

    private fun initView() {
        tencent_map = findViewById(R.id.tencent_map)
        tencent_map.apply {
            map.apply {
                addOnMapLoadedCallback {
                    iMapCallBack?.onMapLoaded()
                }
            }
        }
    }

}
