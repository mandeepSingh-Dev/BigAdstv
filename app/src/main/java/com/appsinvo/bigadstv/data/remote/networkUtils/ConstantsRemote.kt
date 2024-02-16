package com.appsinvo.bigadstv.data.remote.networkUtils

object ConstantsRemote {

    const val BASE_URL = "http://50.18.184.132:9562/api/"
    private const val USER_ENDPOINT = "user"

    //API WRITE AND READ TIME OUT
    const val WRITE_TIME = 15L
    const val READ_TIME = 15L



    //EndPoints
    const val login = "$USER_ENDPOINT/login"
    const val logout = "$USER_ENDPOINT/logout"
    const val getAllAds = "$USER_ENDPOINT/getAllAds"
    const val userAdTrack = "$USER_ENDPOINT/userAdTrack"

    //param-keys
    const val page = "page"
    const val limit = "limit"
    const val adType = "adType"


}