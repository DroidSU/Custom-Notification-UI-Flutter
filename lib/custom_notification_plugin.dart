import 'dart:async';

import 'package:flutter/material.dart';
import 'package:flutter/services.dart';

class CustomNotificationPlugin {
  static const MethodChannel _channel =
      const MethodChannel('custom_notification_plugin');

  static Future<String> get platformVersion async {
    final String version = await _channel.invokeMethod('getPlatformVersion');
    return version;
  }

  static Future<String> get getHello async {
    final String someData =
        await _channel.invokeMethod('helloWorld', {"one": 23, "two": 13});
    return someData;
  }

  static Future<String> showCustomNotificationLayout(
      {@required String gameTitle, @required String gameResult}) async {
    await _channel.invokeMethod('showCustomNotification', {
      "gameTitle": gameTitle,
      "gameResult": gameResult,
    });

    return "Done";
  }

  static Future<String> showBasicNotification(
      {@required String smallIconName,
      @required String smallIconDefType}) async {
    await _channel.invokeMethod('showBasicNotification', {
      "smallIconName": smallIconName,
      "smallIconDefType": smallIconDefType,
    });

    return "Done";
  }
}
