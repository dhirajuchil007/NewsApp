package com.velocityappsdj.sortlymobileproject.common

import com.velocityappsdj.newsapp.common.Constants
import okhttp3.Interceptor
import okhttp3.Response

class AuthInterceptor() : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {


        val newRequest = chain.request().newBuilder()
            .addHeader("Authorization", Constants.API_KEY)
            .build()

        return chain.proceed(newRequest)
    }


}