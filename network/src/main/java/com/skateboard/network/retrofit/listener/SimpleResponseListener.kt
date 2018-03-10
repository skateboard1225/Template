package com.skateboard.network.retrofit.listener

/**
 * Created by skateboard on 2018/1/19.
 */
open class SimpleResponseListener<T> : ResponseListener<T>
{
    override fun onResponse(t: T)
    {

    }

    override fun onError(e: Throwable?)
    {

    }

    override fun onCompleted()
    {

    }

}