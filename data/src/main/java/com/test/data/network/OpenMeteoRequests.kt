package com.test.data.network

import retrofit2.create

object OpenMeteoRequests : ApiFactoryBase("https://api.open-meteo.com/") {

    override val apiRequests: OpenMeteoApi
        get() = retrofit.create()

}