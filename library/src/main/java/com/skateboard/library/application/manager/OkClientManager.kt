package com.skateboard.library.application.manager

import android.app.Application
import com.skateboard.library.application.Constants
import com.skateboard.network.okhttp.OkClient
import com.skateboard.network.okhttp.OkHttpRequest
import java.util.concurrent.TimeUnit

/**
 * Created by skateboard on 2018/2/27.
 */
class OkClientManager : BaseManager()
{
    companion object
    {

        val default: OkClientManager
            get()
            {
                return MANAGER_MAP[Constants.MANAGER_OKCLIENT] as OkClientManager
            }

    }

    private lateinit var okClient: OkClient

    private val CONNECTION_TIME="connection-time"

    private val READ_TIME="read_time"

    private val WRITE_TIME="write-time"

    override fun onCreate(application: Application)
    {
        super.onCreate(application)
        initOkClient()
    }

    private fun initOkClient()
    {
        val builder = OkClient.Companion.Builder()
        builder.connectTimeout((ConfigurationManager.default.getValue(CONNECTION_TIME) ?: "30").toLong() * 1000, TimeUnit.SECONDS)
                .retryOnConnectionFailure(true)
                .readTimeout((ConfigurationManager.default.getValue(READ_TIME) ?: "30").toLong() * 1000, TimeUnit.SECONDS)
                .writeTimeout((ConfigurationManager.default.getValue(WRITE_TIME) ?: "30").toLong() * 1000, TimeUnit.SECONDS)

        okClient=builder.build()
    }


    fun <T> execute(okHttpRequest: OkHttpRequest<T>): T?
    {
       return okClient.execute(okHttpRequest)
    }

    fun <T> enquee(okHttpRequest: OkHttpRequest<T>, callback: OkClient.RequestCallback<T>?)
    {
        return okClient.enquee(okHttpRequest,callback)

    }


    fun cancelRequest(tag: Any?)
    {
        okClient.cancelRequest(tag)
    }

    fun cancelAllRequest()
    {
        okClient.cancelAllRequest()
    }
}