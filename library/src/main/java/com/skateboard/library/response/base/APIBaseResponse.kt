package com.skateboard.library.response.base

import java.io.Serializable

/**
 * Created by skateboard on 2018/3/7.
 */
open class APIBaseResponse:Serializable
{
     var code:String=""

     open var data:MutableMap<String,String> = mutableMapOf()


    open fun isSuccess():Boolean
    {
        return true
    }

    open fun getString(key:String):String
    {
        return data[key]?:""
    }


}