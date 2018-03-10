package com.skateboard.network.retrofit.factory

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.skateboard.network.retrofit.annotation.ResponseString
import com.skateboard.network.retrofit.converter.GsonConverter
import com.skateboard.network.retrofit.converter.StringConverter
import okhttp3.ResponseBody
import retrofit2.Converter
import retrofit2.Retrofit
import java.lang.reflect.Type

/**
 * Created by skateboard on 2018/1/16.
 */
class ConvertFactory : Converter.Factory()
{

    private val gson = Gson()

    companion object
    {
        fun create(): ConvertFactory
        {
            return ConvertFactory()
        }
    }


    override fun responseBodyConverter(type: Type?, annotations: Array<out Annotation>?, retrofit: Retrofit?): Converter<ResponseBody, *>?
    {
        if(annotations!=null)
        {
            for (annotation in annotations)
            {
                println("annotion is "+annotation.annotationClass)
            }
        }
        annotations?.filter { it.annotationClass == ResponseString::class }?.forEach { return StringConverter() }
        val adapter = gson.getAdapter(TypeToken.get(type))
        return GsonConverter(gson, adapter)

    }
}