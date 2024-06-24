package com.lambui.common.extension.collection

/**
 * Created by LamBD on 17/06/24.
 */

inline fun <reified T> List<T>?.equalsExt(listCompare: MutableList<T>?) = this?.size == listCompare?.size &&
    this?.containsAll(listCompare ?: mutableListOf()) == true
