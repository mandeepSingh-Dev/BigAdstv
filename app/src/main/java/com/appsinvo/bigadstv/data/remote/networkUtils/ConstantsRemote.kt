package com.appsinvo.bigadstv.data.remote.networkUtils

object ConstantsRemote {

    const val BASE_URL = "http://50.18.184.132:9562/api/"
    const val WORLD_TIME_API_URL = "http://worldtimeapi.org/api/"
    private const val USER_ENDPOINT = "user"

    //API WRITE AND READ TIME OUT
    const val WRITE_TIME = 15L
    const val READ_TIME = 15L



    //EndPoints
    const val login = "$USER_ENDPOINT/login"
    const val logout = "$USER_ENDPOINT/logout"
    const val getAllAds = "$USER_ENDPOINT/getAllAds"
    const val userAdTrack = "$USER_ENDPOINT/userAdTrack"
    const val userEarnDetail = "$USER_ENDPOINT/userEarnDetail"
    const val getNotifications = "$USER_ENDPOINT/getNotification"

    //param-keys
    const val page = "page"
    const val limit = "limit"
    const val adType = "adType"
    const val month = "month"


}