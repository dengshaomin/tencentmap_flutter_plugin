import 'package:plugin_platform_interface/plugin_platform_interface.dart';

import 'tencentmap_flutter_plugin_method_channel.dart';

abstract class TencentmapFlutterPluginPlatform extends PlatformInterface {
  /// Constructs a TencentmapFlutterPluginPlatform.
  TencentmapFlutterPluginPlatform() : super(token: _token);

  static final Object _token = Object();

  static TencentmapFlutterPluginPlatform _instance =
  TencentmapFlutterPlugin();

  /// The default instance of [TencentmapFlutterPluginPlatform] to use.
  ///
  /// Defaults to [MethodChannelTencentmapFlutterPlugin].
  static TencentmapFlutterPluginPlatform get instance => _instance;

  /// Platform-specific implementations should set this with their own
  /// platform-specific class that extends [TencentmapFlutterPluginPlatform] when
  /// they register themselves.
  static set instance(TencentmapFlutterPluginPlatform instance) {
    PlatformInterface.verifyToken(instance, _token);
    _instance = instance;
  }

  Future<String?> getPlatformVersion() {
    throw UnimplementedError('platformVersion() has not been implemented.');
  }

  Future<void> startSingleLocation() {
    throw UnimplementedError('platformVersion() has not been implemented.');
  }
  void addObserver(String path, Future<Map<String, dynamic>?> Function(Map<String, dynamic>?) callback) {
    throw UnimplementedError('platformVersion() has not been implemented.');
  }

  void removeObserver(String path) {
    throw UnimplementedError('platformVersion() has not been implemented.');
  }
}
