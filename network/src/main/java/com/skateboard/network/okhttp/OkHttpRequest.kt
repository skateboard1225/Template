package com.skateboard.network.okhttp

import okhttp3.Response
import java.io.File

/**
 * Created by skateboard on 2018/1/7.
 */
abstract class OkHttpRequest<T>(private var url:String,private var method: Method=Method.GET)
{
    enum class Method
    {
        GET,POST
    }


    private var tag:Any?=null

    init
    {

    }

    open fun getMethod(): Method
    {
        return method
    }


    open fun getUrl():String
    {
        return url
    }

    open fun getHeaders():MutableMap<String,String>
    {
        return mutableMapOf()
    }


    open fun getUploadFiels():MutableMap<String, File>
    {
        return mutableMapOf()
    }

    open fun getFormParamsBody():MutableMap<String,String>
    {
        return mutableMapOf()
    }

    open fun getMediaType():String
    {
        return ""
    }

    fun setTag(tag:Any?)
    {
        this.tag=tag
    }

    fun getTag():Any?
    {
        return tag
    }

    abstract fun parseResponse(response:Response):T


}