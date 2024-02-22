package com.appsinvo.bigadstv.domain.local.useCases.tracksAds

data class AllTrackAdsUsecases(
    val insertTrackAdUsecase: InsertTrackAdUsecase,
    val getAllUserTrackAdsUsecase: GetAllUserTrackAdsUsecase,
    val getUserTrackAdUsecase: GetUserTrackAdUsercase,
    val deleteAllUserTrackAdsUsecase: DeleteAllUserTrackAdsUsecase,
    val deleteTrackAdUsecase: DeleteTrackAdUsecase,
)