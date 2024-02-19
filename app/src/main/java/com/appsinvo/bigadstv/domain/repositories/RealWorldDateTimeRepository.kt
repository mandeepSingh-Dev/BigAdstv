package com.appsinvo.bigadstv.domain.repositories

import com.appsinvo.bigadstv.data.remote.model.realWorldDateTime.RealWorldDateTimeResponse
import com.appsinvo.bigadstv.data.remote.networkUtils.NetworkResult

interface RealWorldDateTimeRepository {
    suspend fun getCurrentWorldTime() : NetworkResult<RealWorldDateTimeResponse>
}