package com.code.tencentmap_flutter_plugin

import android.content.Context
import android.location.LocationManager
import android.os.Looper
import android.util.Log
import android.view.View
import android.widget.Switch
import androidx.annotation.NonNull
import androidx.lifecycle.Lifecycle
import com.google.gson.Gson
import com.tencent.map.geolocation.TencentLocation
import com.tencent.map.geolocation.TencentLocationListener
import com.tencent.map.geolocation.TencentLocationManager
import com.tencent.tencentmap.mapsdk.maps.CameraUpdate
import com.tencent.tencentmap.mapsdk.maps.CameraUpdateFactory
import com.tencent.tencentmap.mapsdk.maps.LocationSource.OnLocationChangedListener
import com.tencent.tencentmap.mapsdk.maps.MapView
import com.tencent.tencentmap.mapsdk.maps.TencentMap
import com.tencent.tencentmap.mapsdk.maps.TencentMap.CancelableCallback
import com.tencent.tencentmap.mapsdk.maps.model.LatLng

import io.flutter.embedding.engine.plugins.FlutterPlugin
import io.flutter.embedding.engine.plugins.activity.ActivityAware
import io.flutter.embedding.engine.plugins.activity.ActivityPluginBinding
import io.flutter.embedding.engine.plugins.lifecycle.HiddenLifecycleReference
import io.flutter.plugin.common.MethodCall
import io.flutter.plugin.common.MethodChannel
import io.flutter.plugin.common.MethodChannel.MethodCallHandler
import io.flutter.plugin.common.MethodChannel.Result
import io.flutter.plugin.common.StandardMessageCodec
import io.flutter.plugin.platform.PlatformView
import io.flutter.plugin.platform.PlatformViewFactory
import java.lang.ref.WeakReference

/** TencentmapFlutterPlugin */
class TencentmapFlutterPlugin : FlutterPlugin, ActivityAware, MethodCallHandler,
    TencentLocationListener {
    /// The MethodChannel that will the communication between Flutter and native Android
    ///
    /// This local reference serves to register the plugin with the Flutter Engine and unregister it
    /// when the Flutter Engine is detached from the Activity
    private lateinit var channel: MethodChannel
    private lateinit var locationManager: TencentLocationManager
    private var lifecycle: Lifecycle? = null
    private var nativeViewFactory: NativeViewFactory? = null
    private var iMapController: IMapController? = null
    private var iMapCallBack = object : IMapCallBack {
        override fun onMapLoaded() {

        }

        override fun onViewCreated() {
            iMapController = (nativeViewFactory?.view as? NativeTencentView)?.iMapController
        }
    }

    override fun onAttachedToEngine(@NonNull flutterPluginBinding: FlutterPlugin.FlutterPluginBinding) {
        channel = MethodChannel(flutterPluginBinding.binaryMessenger, "tencentmap_flutter_plugin")
        channel.setMethodCallHandler(this)
        locationManager =
            TencentLocationManager.getInstance(flutterPluginBinding.applicationContext)
        flutterPluginBinding.platformViewRegistry.registerViewFactory(
            "view-type-tencent-map",
            NativeViewFactory(iMapCallBack, object : LifecycleProvider {
                override fun getLifecycle(): Lifecycle? {
                    return lifecycle
                }
            }).apply {
                nativeViewFactory = this
            }
        )
    }


    override fun onMethodCall(@NonNull call: MethodCall, @NonNull result: Result) {
        Log.e("balance", "native get:${call.method}")
        when (call.method) {
            MethodMap.startSingleLocation -> {
                locationManager.requestSingleFreshLocation(null, this, Looper.getMainLooper())
                result.success(null)
            }
            MethodMap.setMapType -> {
                iMapController?.setMapType(call.arguments as? Int ?: TencentMap.MAP_TYPE_NORMAL)
                result.success(null)
            }
            MethodMap.animateCamera -> {
                val arguments = call.arguments as MutableMap<String, Any?>
                iMapController?.animateCamera(
                    CameraUpdateFactory.newLatLng(
                        LatLng(
                            arguments["latitude"] as Double,
                            arguments["longitude"] as Double
                        )
                    ), 500, object : CancelableCallback {
                        override fun onFinish() {
                        }

                        override fun onCancel() {
                        }

                    }
                )
                result.success(null)
            }
            else -> result.notImplemented()
        }
    }

    override fun onDetachedFromEngine(@NonNull binding: FlutterPlugin.FlutterPluginBinding) {
        channel.setMethodCallHandler(null)
    }

    override fun onLocationChanged(p0: TencentLocation?, p1: Int, p2: String?) {
        Log.e("balance", "native location finish:$p1 : $p2")

        channel.invokeMethod(
            MethodMap.startSingleLocation,
            Gson().toJson(LocationModel.transform(p0)),
            object : Result {
                override fun success(result: Any?) {
                    Log.e("balance", result?.toString() ?: "")
                }

                override fun error(errorCode: String, errorMessage: String?, errorDetails: Any?) {
                }

                override fun notImplemented() {
                }
            })
    }

    override fun onStatusUpdate(p0: String?, p1: Int, p2: String?) {
        var a = 1
        Log.e("balance", "native location finish:$p1 : $p2")
    }

    override fun onAttachedToActivity(binding: ActivityPluginBinding) {
        lifecycle = (binding.lifecycle as HiddenLifecycleReference).lifecycle
    }

    override fun onDetachedFromActivityForConfigChanges() {
        onDetachedFromActivity()
    }

    override fun onReattachedToActivityForConfigChanges(binding: ActivityPluginBinding) {
        onAttachedToActivity(binding)
    }

    override fun onDetachedFromActivity() {
        lifecycle = null
    }

}

class MethodMap {
    companion object {
        const val startSingleLocation = "startSingleLocation"
        const val setMapType = "setMapType"
        const val animateCamera = "animateCamera"
    }
}
