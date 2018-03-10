package com.skateboard.network.okhttp

import android.os.Handler
import android.os.Looper
import okhttp3.*
import java.io.IOException
import java.util.concurrent.TimeUnit

/**
 * Created by skateboard on 2018/1/6.
 */
class OkClient private constructor()
{
    companion object
    {

        val READ_TIME_OUT_MILLS = 30000L

        val WRIITE_TIME_OUT_MILLS = 30000L

        val CONNECTION_TIME_OUT_MILLS = 30000L


        class Builder
        {
            private lateinit var okClient: OkClient

            private var builder: OkHttpClient.Builder = OkHttpClient.Builder()


            fun readTimeout(timeout: Long, unit: TimeUnit): Builder
            {
                builder.readTimeout(timeout, unit)
                return this
            }

            fun writeTimeout(timeout: Long, unit: TimeUnit): Builder
            {
                builder.writeTimeout(timeout, unit)
                return this
            }

            fun connectTimeout(timeout: Long, unit: TimeUnit): Builder
            {
                builder.connectTimeout(timeout, unit)
                return this
            }

            fun retryOnConnectionFailure(retryOnConnectionFailure: Boolean): Builder
            {
                builder.retryOnConnectionFailure(retryOnConnectionFailure)
                return this
            }

            fun addInterceptor(interceptor: Interceptor): Builder
            {
                builder.addInterceptor(interceptor)
                return this
            }

            fun addNetworkInterceptor(interceptor: Interceptor): Builder
            {
                builder.addNetworkInterceptor(interceptor)
                return this
            }


            fun build(): OkClient
            {

                okClient=OkClient()
                okClient.okHttpClient = builder.build()
                return okClient
            }


        }

    }

    private lateinit var okHttpClient: okhttp3.OkHttpClient

    private var tagCallMap: MutableMap<Any, Call> = mutableMapOf()

    private var cHandler: Handler = Handler(Looper.getMainLooper())


    private fun buildRequest(okHttpRequest: OkHttpRequest<*>): Request
    {
        val requestBuilder = Request.Builder()
        requestBuilder.url(okHttpRequest.getUrl())
                .tag(okHttpRequest.getTag())
        for (key in okHttpRequest.getHeaders().keys)
        {
            requestBuilder.addHeader(key, okHttpRequest.getHeaders()[key])
        }

        when (okHttpRequest.getMethod())
        {

            OkHttpRequest.Method.POST ->
            {
                val filesMap = okHttpRequest.getUploadFiels()
                val formParamMap = okHttpRequest.getFormParamsBody()
                if (filesMap.isNotEmpty())
                {
                    val builder = MultipartBody.Builder()
                    for (key in formParamMap.keys)
                    {
                        val value = formParamMap[key]
                        builder.addFormDataPart(key, value)
                    }
                    for (key in filesMap.keys)
                    {
                        val file = filesMap[key]
                        builder.addFormDataPart(key, file?.name, RequestBody.create(MediaType.parse("multipart/form-data"), file))
                    }
                }
                else
                {
                    val formBodyBuilder=FormBody.Builder()
                    for (key in formParamMap.keys)
                    {
                        val value = formParamMap[key]
                        formBodyBuilder.add(key, value)
                    }
                    requestBuilder.post(formBodyBuilder.build())
                }

            }
        }

        return requestBuilder.build()
    }

    fun <T> execute(okHttpRequest: OkHttpRequest<T>): T?
    {
        val request = buildRequest(okHttpRequest)
        val response = executeRequest(request, request.tag())
        return okHttpRequest.parseResponse(response)
    }

    private fun executeRequest(request: Request, tag: Any?): Response
    {
        val call = okHttpClient.newCall(request)
        addTagMap(tag, call)
        val response = call.execute()
        cancelRequest(tag)
        return response
    }


    interface RequestCallback<T>
    {
        fun onFailure(call: Call?, e: IOException?)

        fun onResponse(call: Call?, response: T)
    }

    fun <T> enquee(okHttpRequest: OkHttpRequest<T>, callback: RequestCallback<T>?)
    {
        val request = buildRequest(okHttpRequest)
        enqueeRequest(request, okHttpRequest, callback)

    }

    private fun <T> enqueeRequest(request: Request, okHttpRequest: OkHttpRequest<T>, callback: RequestCallback<T>?)
    {
        val call = okHttpClient.newCall(request)
        addTagMap(okHttpRequest.getTag(), call)
        call.enqueue(object : Callback
        {
            override fun onFailure(call: Call?, e: IOException?)
            {
                cHandler.post {

                    cancelRequest(okHttpRequest.getTag())
                    callback?.onFailure(call, e)

                }
            }

            override fun onResponse(call: Call?, response: Response?)
            {

                if(response==null || !response.isSuccessful)
                {
                    cHandler.post {

                        cancelRequest(okHttpRequest.getTag())
                        callback?.onFailure(call, null)

                    }
                }
                else
                {
                    if(response.isSuccessful)
                    {
                        val result = okHttpRequest.parseResponse(response)
                        cHandler.post {

                            cancelRequest(okHttpRequest.getTag())
                            callback?.onResponse(call, result)

                        }
                    }
                }

            }
        })
    }


    private fun addTagMap(tag: Any?, call: Call)
    {
        if (tag != null)
        {
            tagCallMap[tag] = call
        }
    }

    fun cancelRequest(tag: Any?)
    {
        if (tag != null)
        {
            val call = tagCallMap[tag]
            call?.cancel()
            tagCallMap.remove(tag)
        }
    }

    fun cancelAllRequest()
    {
        okHttpClient.dispatcher().cancelAll()
        tagCallMap.clear()
    }


}