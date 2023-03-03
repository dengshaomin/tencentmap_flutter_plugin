import 'dart:convert';

import 'package:flutter/foundation.dart';
import 'package:flutter/services.dart';

import 'tencentmap_flutter_plugin_platform_interface.dart';

/// An implementation of [TencentmapFlutterPluginPlatform] that uses method channels.
class TencentmapFlutterPlugin extends TencentmapFlutterPluginPlatform {
  /// The method channel used to interact with the native platform.
  @visibleForTesting
  final _methodChannel = const MethodChannel('tencentmap_flutter_plugin');
  final _channelObservers = <String, ChannelObserver>{};

  TencentmapFlutterPlugin() {
    _methodChannel.setMethodCallHandler((call) async {
      var observer = _channelObservers[call.method];
      if (observer != null) {
        observer.callback(jsonDecode(call.arguments) as Map<String, dynamic?>);
      }
    });
  }

  @override
  Future<String?> getPlatformVersion() async {
    final version =
        await _methodChannel.invokeMethod<String>('getPlatformVersion');
    return version;
  }

  @override
  Future<void> startSingleLocation() async {
    // TODO: implement startSingleLocation
    _methodChannel.invokeMethod(MethodMap.startSingleLocation);
  }

  @override
  void addObserver(
      String path,
      Future<Map<String, dynamic>?> Function(Map<String, dynamic>? p1)
          callback) {
    _channelObservers[path] = ChannelObserver(path, callback);
  }

  @override
  void removeObserver(String path) {
    _channelObservers.remove(path);
  }
}

class ChannelObserver {
  String path;
  Future<Map<String, dynamic>?> Function(Map<String, dynamic>?) callback;

  ChannelObserver(this.path, this.callback);
}

class MethodMap {
  static const String startSingleLocation = "startSingleLocation";
}
