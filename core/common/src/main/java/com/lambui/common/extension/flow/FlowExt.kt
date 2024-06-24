package com.lambui.common.extension.flow

import androidx.lifecycle.MutableLiveData
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onStart

/**
 * Created by LamBD on 17/06/24.
 */

fun <T> Flow<T>.loading(isLoading: MutableLiveData<Boolean>): Flow<T> {
    return onStart { isLoading.postValue(true) }
        .onCompletion { isLoading.postValue(false) }
}
