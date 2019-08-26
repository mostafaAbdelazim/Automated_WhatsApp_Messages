package com.softray.whatsapptest

import android.accessibilityservice.AccessibilityService
import android.content.Context
import android.provider.Settings
import android.text.TextUtils


fun isAccessibilityOn(context: Context, clazz: Class<out AccessibilityService>): Boolean {
    var accessibilityEnabled = 0
    val service = context.packageName + "/" + clazz.canonicalName
    try {
        accessibilityEnabled = Settings.Secure.getInt(
            context.applicationContext.contentResolver,
            Settings.Secure.ACCESSIBILITY_ENABLED
        )
    } catch (ignored: Settings.SettingNotFoundException) {
    }

    val colonSplitter = TextUtils.SimpleStringSplitter(":".single())

    if (accessibilityEnabled == 1) {
        val settingValue = Settings.Secure.getString(
            context.applicationContext.contentResolver,
            Settings.Secure.ENABLED_ACCESSIBILITY_SERVICES
        )
        if (settingValue != null) {
            colonSplitter.setString(settingValue)
            while (colonSplitter.hasNext()) {
                val accessibilityService = colonSplitter.next()

                if (accessibilityService.equals(service, ignoreCase = true)) {
                    return true
                }
            }
        }
    }
    return false
}