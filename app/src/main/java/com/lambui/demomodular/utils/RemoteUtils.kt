package com.lambui.demomodular.utils

import com.lambui.demomodular.api.ResultApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

suspend fun <T> handleApiResponse(requestApi: suspend () -> T): Flow<ResultApi<T>> {
    return flow {
        try {
            emit(ResultApi.Success(requestApi.invoke()))
        } catch (e: Exception) {
            emit(ResultApi.Error(e))
        }
    }
}
