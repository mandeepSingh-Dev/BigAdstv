package com.appsinvo.bigadstv.domain.data.useCases.ads

import com.appsinvo.bigadstv.data.remote.model.ads.getUserEarnings.response.UserEarningResponse
import com.appsinvo.bigadstv.data.remote.networkUtils.NetworkResult
import com.appsinvo.bigadstv.domain.data.repositories.AdsRepository
import javax.inject.Inject

class GetUserEarningsUseCase @Inject constructor(private val adsRepository : AdsRepository) {

    suspend operator fun invoke(page : String? = null, limit : String? = null, month : Int?): NetworkResult<UserEarningResponse> {
        return adsRepository.getUserEarning(page = page,limit = limit, month = month)
    }
}

