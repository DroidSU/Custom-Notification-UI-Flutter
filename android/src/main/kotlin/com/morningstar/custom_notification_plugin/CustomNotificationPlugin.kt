package com.morningstar.custom_notification_plugin

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import android.widget.RemoteViews
import androidx.annotation.NonNull
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import io.flutter.embedding.engine.plugins.FlutterPlugin
import io.flutter.plugin.common.MethodCall
import io.flutter.plugin.common.MethodChannel
import io.flutter.plugin.common.MethodChannel.MethodCallHandler
import io.flutter.plugin.common.MethodChannel.Result

/** CustomNotificationPlugin */
class CustomNotificationPlugin : FlutterPlugin, MethodCallHandler {
    /// The MethodChannel that will the communication between Flutter and native Android
    ///
    /// This local reference serves to register the plugin with the Flutter Engine and unregister it
    /// when the Flutter Engine is detached from the Activity
    private lateinit var channel: MethodChannel
    private lateinit var context: Context

    override fun onAttachedToEngine(@NonNull flutterPluginBinding: FlutterPlugin.FlutterPluginBinding) {
        channel = MethodChannel(flutterPluginBinding.binaryMessenger, "custom_notification_plugin")
        channel.setMethodCallHandler(this)
        context = flutterPluginBinding.applicationContext
    }

    override fun onMethodCall(@NonNull call: MethodCall, @NonNull result: Result) {
        if (call.method == "getPlatformVersion") {
            result.success("Android ${android.os.Build.VERSION.RELEASE}")
        } else if (call.method == "helloWorld") {
            val one = call.argument<Int>("one")
            val two = call.argument<Int>("two")

            if (one != null && two != null) {
                result.success("${one + two}")
            } else {
                result.error(
                        "Error code",
                        "Error message",
                        "Error detail"
                )
            }
        }
        else if (call.method == "showCustomNotification") {
            val gameTitle = call.argument<String>("gameTitle")
            val gameResult = call.argument<String>("gameResult")

            val smallIcon = context.resources.getIdentifier("royal_sporty_logo", "drawable", context.packageName);

            with(NotificationManagerCompat.from(context)) {
                // notificationId is a unique int for each notification that you must define


                // Create the NotificationChannel, but only on API 26+ because
                // the NotificationChannel class is new and not in the support library
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    val service: String = Context.NOTIFICATION_SERVICE

                    val name = "Kuber Games"
                    val descriptionText = "Kuber Notifications"
                    val importance = NotificationManager.IMPORTANCE_DEFAULT
                    val channel = NotificationChannel("9656", name, importance).apply {
                        description = descriptionText
                    }

                    val notificationManager: NotificationManager =
                            context.getSystemService(service) as NotificationManager
                    notificationManager.createNotificationChannel(channel)
                }

                val notificationLayout = RemoteViews(context.packageName, R.layout.custom_notification_layout)
                val notificationLayoutExpanded = RemoteViews(context.packageName, R.layout.custom_notification_layout)

                val builder = NotificationCompat.Builder(context, "9656")
                        .setContentTitle(gameTitle)
                        .setContentText(gameResult)
                        .setCustomContentView(notificationLayout)
                        .setCustomBigContentView(notificationLayoutExpanded)
                        .setSmallIcon(smallIcon)
                        .setPriority(NotificationCompat.PRIORITY_DEFAULT)

                val someNotificationId = 21

                notify(someNotificationId, builder.build())
            }
        }

        else {
            result.notImplemented()
        }
    }

    override fun onDetachedFromEngine(@NonNull binding: FlutterPlugin.FlutterPluginBinding) {
        channel.setMethodCallHandler(null)
    }
}
