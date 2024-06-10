package com.lambui.demomodular.utils

import android.app.Activity
import android.content.*
import android.content.pm.ApplicationInfo
import android.content.pm.PackageInfo
import android.content.pm.PackageManager
import android.content.res.Resources
import android.os.*


fun Context.toActivity(): Activity? {
    return when (this) {
        is Activity -> this
        is ContextWrapper -> baseContext.toActivity()
        else -> null
    }
}

fun PackageManager.getPackageInfoCompat(packageName: String, flags: Int = 0): PackageInfo =
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
        getPackageInfo(packageName, PackageManager.PackageInfoFlags.of(flags.toLong()))
    } else {
        @Suppress("DEPRECATION") getPackageInfo(packageName, flags)
    }

fun Context.getPackageInfo(): PackageInfo? {
    return try {
        this.packageManager.getPackageInfoCompat(this.packageName, 0)
    } catch (e: PackageManager.NameNotFoundException) {
        e.printStackTrace()
        null
    }
}

val Context.isDebugMode: Boolean
    get() = (this.applicationInfo.flags and ApplicationInfo.FLAG_DEBUGGABLE != 0)

fun Context.requireActivity(): Activity {
    return toActivity() ?: throw IllegalStateException("This is not an activity context")
}

val Float.dp
    get() = (this * Resources.getSystem().displayMetrics.density)

val Int.dp
    get() = (this * Resources.getSystem().displayMetrics.density).toInt()
