package com.skateboard.network.retrofit.listener

/**
 * Created by skateboard on 2018/1/19.
 */
interface ResponseListener<T>
{
    fun onResponse(t: T)

    fun onError(e: Throwable?)

    fun onCompleted()
}
