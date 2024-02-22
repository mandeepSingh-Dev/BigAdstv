package com.appsinvo.bigadstv.domain.data.useCases

import com.appsinvo.bigadstv.data.remote.model.realWorldDateTime.RealWorldDateTimeResponse
import com.appsinvo.bigadstv.data.remote.networkUtils.NetworkResult
import com.appsinvo.bigadstv.domain.data.repositories.RealWorldDateTimeRepository
import javax.inject.Inject

class RealWorldDateTimeUseCase @Inject constructor(private val realWorldDateTimeRepository: RealWorldDateTimeRepository) {

    suspend operator fun invoke(): NetworkResult<RealWorldDateTimeResponse> {
        return realWorldDateTimeRepository.getCurrentWorldTime()
    }
}