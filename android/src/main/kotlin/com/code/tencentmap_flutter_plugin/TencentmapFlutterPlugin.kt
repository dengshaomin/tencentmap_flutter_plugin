package com.code.tencentmap_flutter_plugin

import android.location.LocationManager
import android.os.Looper
import android.util.Log
import android.widget.Switch
import androidx.annotation.NonNull
import com.google.gson.Gson
import com.tencent.map.geolocation.TencentLocation
import com.tencent.map.geolocation.TencentLocationListener
import com.tencent.map.geolocation.TencentLocationManager

import io.flutter.embedding.engine.plugins.FlutterPlugin
import io.flutter.plugin.common.MethodCall
import io.flutter.plugin.common.MethodChannel
import io.flutter.plugin.common.MethodChannel.MethodCallHandler
import io.flutter.plugin.common.MethodChannel.Result

/** TencentmapFlutterPlugin */
class TencentmapFlutterPlugin : FlutterPlugin, MethodCallHandler, TencentLocationListener {
    /// The MethodChannel that will the communication between Flutter and native Android
    ///
    /// This local reference serves to register the plugin with the Flutter Engine and unregister it
    /// when the Flutter Engine is detached from the Activity
    private lateinit var channel: MethodChannel
    private lateinit var locationManager: TencentLocationManager
    override fun onAttachedToEngine(@NonNull flutterPluginBinding: FlutterPlugin.FlutterPluginBinding) {
        channel = MethodChannel(flutterPluginBinding.binaryMessenger, "tencentmap_flutter_plugin")
        channel.setMethodCallHandler(this)
        locationManager =
            TencentLocationManager.getInstance(flutterPluginBinding.applicationContext)
    }

    override fun onMethodCall(@NonNull call: MethodCall, @NonNull result: Result) {
        Log.e("balance", "native get:${call.method}")
        when (call.method) {
            "getPlatformVersion" -> {
                result.success("Android ${android.os.Build.VERSION.RELEASE}")
            }
            MethodMap.startSingleLocation -> {
                locationManager.requestSingleFreshLocation(null, this, Looper.getMainLooper())
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
}

class MethodMap {
    companion object {
        const val startSingleLocation = "startSingleLocation"
    }
}
