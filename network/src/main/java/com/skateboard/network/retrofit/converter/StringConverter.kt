package com.skateboard.network.retrofit.converter

import okhttp3.ResponseBody
import retrofit2.Converter
import java.io.IOException

/**
 * Created by skateboard on 2018/1/16.
 */
class StringConverter:Converter<ResponseBody, String>
{
    @Throws(IOException::class)
    override fun convert(value: ResponseBody?): String
    {
        val body=value
        return value?.string()?:""
    }

}