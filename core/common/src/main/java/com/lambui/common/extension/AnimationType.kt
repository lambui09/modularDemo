package com.lambui.common.extension

import androidx.annotation.IntDef

/**
 * Created by LamBD on 17/06/24.
 */

@IntDef(NONE, FADE, SLIDE_LEFT, SLIDE_RIGHT, SLIDE_DOWN, SLIDE_UP)
@Retention(AnnotationRetention.SOURCE)
annotation class AnimationType

const val NONE = 0x00
const val FADE = 0x01
const val SLIDE_RIGHT = 0x02
const val SLIDE_LEFT = 0x03
const val SLIDE_DOWN = 0x04
const val SLIDE_UP = 0x05
