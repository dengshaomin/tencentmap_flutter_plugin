import 'package:flutter/services.dart';
import 'package:flutter_test/flutter_test.dart';
import 'package:tencentmap_flutter_plugin/tencentmap_flutter_plugin_method_channel.dart';

void main() {
  MethodChannelTencentmapFlutterPlugin platform = MethodChannelTencentmapFlutterPlugin();
  const MethodChannel channel = MethodChannel('tencentmap_flutter_plugin');

  TestWidgetsFlutterBinding.ensureInitialized();

  setUp(() {
    channel.setMockMethodCallHandler((MethodCall methodCall) async {
      return '42';
    });
  });

  tearDown(() {
    channel.setMockMethodCallHandler(null);
  });

  test('getPlatformVersion', () async {
    expect(await platform.getPlatformVersion(), '42');
  });
}
