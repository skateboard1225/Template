package com.skateboard.library.request.base

import com.skateboard.library.response.base.APIBaseResponse
import com.skateboard.network.okhttp.OkHttpRequest
import okhttp3.Response
import java.io.File

/**
 * Created by skateboard on 2018/3/7.
 */
abstract class APIBaseRequest<out T: APIBaseResponse>
{
    var method:OkHttpRequest.Method=OkHttpRequest.Method.GET

    var headers= mutableMapOf<String,String>()


    open fun getMethod():String
    {
        return ""
    }

    open fun getFormParamsBody(): MutableMap<String, String>
    {
        return mutableMapOf()
    }

    open fun getUploadFiels(): MutableMap<String, File>
    {
        return mutableMapOf()
    }

    open fun getMediaType(): String
    {
        return ""
    }

    abstract fun parseResponse(response: Response):T



}