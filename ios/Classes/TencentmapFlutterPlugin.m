#import "TencentmapFlutterPlugin.h"
#if __has_include(<tencentmap_flutter_plugin/tencentmap_flutter_plugin-Swift.h>)
#import <tencentmap_flutter_plugin/tencentmap_flutter_plugin-Swift.h>
#else
// Support project import fallback if the generated compatibility header
// is not copied when this plugin is created as a library.
// https://forums.swift.org/t/swift-static-libraries-dont-copy-generated-objective-c-header/19816
#import "tencentmap_flutter_plugin-Swift.h"
#endif

@implementation TencentmapFlutterPlugin
+ (void)registerWithRegistrar:(NSObject<FlutterPluginRegistrar>*)registrar {
  [SwiftTencentmapFlutterPlugin registerWithRegistrar:registrar];
}
@end
