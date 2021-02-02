#import "CustomNotificationPlugin.h"
#if __has_include(<custom_notification_plugin/custom_notification_plugin-Swift.h>)
#import <custom_notification_plugin/custom_notification_plugin-Swift.h>
#else
// Support project import fallback if the generated compatibility header
// is not copied when this plugin is created as a library.
// https://forums.swift.org/t/swift-static-libraries-dont-copy-generated-objective-c-header/19816
#import "custom_notification_plugin-Swift.h"
#endif

@implementation CustomNotificationPlugin
+ (void)registerWithRegistrar:(NSObject<FlutterPluginRegistrar>*)registrar {
  [SwiftCustomNotificationPlugin registerWithRegistrar:registrar];
}
@end
