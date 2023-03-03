import 'package:flutter_test/flutter_test.dart';
import 'package:tencentmap_flutter_plugin/tencentmap_flutter_plugin.dart';
import 'package:tencentmap_flutter_plugin/tencentmap_flutter_plugin_platform_interface.dart';
import 'package:tencentmap_flutter_plugin/tencentmap_flutter_plugin_method_channel.dart';
import 'package:plugin_platform_interface/plugin_platform_interface.dart';

class MockTencentmapFlutterPluginPlatform
    with MockPlatformInterfaceMixin
    implements TencentmapFlutterPluginPlatform {

  @override
  Future<String?> getPlatformVersion() => Future.value('42');
}

void main() {
  final TencentmapFlutterPluginPlatform initialPlatform = TencentmapFlutterPluginPlatform.instance;

  test('$MethodChannelTencentmapFlutterPlugin is the default instance', () {
    expect(initialPlatform, isInstanceOf<MethodChannelTencentmapFlutterPlugin>());
  });

  test('getPlatformVersion', () async {
    TencentmapFlutterPlugin tencentmapFlutterPlugin = TencentmapFlutterPlugin();
    MockTencentmapFlutterPluginPlatform fakePlatform = MockTencentmapFlutterPluginPlatform();
    TencentmapFlutterPluginPlatform.instance = fakePlatform;

    expect(await tencentmapFlutterPlugin.getPlatformVersion(), '42');
  });
}
