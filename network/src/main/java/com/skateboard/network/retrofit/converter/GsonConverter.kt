package com.skateboard.network.retrofit.converter

import com.google.gson.Gson
import com.google.gson.TypeAdapter
import okhttp3.Response
import okhttp3.ResponseBody
import retrofit2.Converter
import java.io.IOException

/**
 * Created by skateboard on 2018/1/16.
 */
class GsonConverter<T>(private val gson: Gson, private val adapter: TypeAdapter<T>) : Converter<ResponseBody, T>
{

    @Throws(IOException::class)
    override fun convert(value: ResponseBody): T
    {
        val jsonReader = gson.newJsonReader(value.charStream())
        try
        {
            return adapter.read(jsonReader)
        }
        finally
        {
            value.close()
        }
    }
}