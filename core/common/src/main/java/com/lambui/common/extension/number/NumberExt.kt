@file:JvmName("KtExtNumber")
@file:JvmMultifileClass

package com.lambui.common.extension.number

import java.text.DecimalFormat

/**
 * Created by LamBD on 17/06/24.
 */

fun Int?.nullToDefault() = this ?: -1

fun Int?.nullToZero() = this ?: 0

fun Int?.isTrue() = this == 1

fun Double?.isNullOrZero() = this == 0.0 || this == null

fun Double?.nullToDefault() = this ?: -1.0

fun Double?.nullToZero() = this ?: 0.0

fun Double?.convertToPrice(currency: String) = String.format("%s%s", this.nullToZero().formatMoney(), currency)

fun Double.formatMoney(): String {
    val formatter = DecimalFormat("###,###,###")
    return formatter.format(this)
}
