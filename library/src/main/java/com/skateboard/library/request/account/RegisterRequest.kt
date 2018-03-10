package com.skateboard.library.request.account

import com.skateboard.library.request.base.APIBaseRequest
import com.skateboard.library.response.account.RegisterResponse
import com.skateboard.library.util.ParserUtil
import com.skateboard.network.okhttp.OkHttpRequest
import okhttp3.Response

/**
 * Created by skateboard on 2018/3/9.
 */
class RegisterRequest(val userId:String,val password:String,val idCode:String):APIBaseRequest<RegisterResponse>()
{

    init
    {
        method=OkHttpRequest.Method.POST
    }

    override fun parseResponse(response: Response): RegisterResponse
    {
        return ParserUtil.parse(response,RegisterResponse())
    }

    override fun getFormParamsBody(): MutableMap<String, String>
    {
        val paramsMap= mutableMapOf<String,String>()
        paramsMap["userId"]=userId
        paramsMap["password"]=password
        paramsMap["code"]=idCode
        return paramsMap
    }

    override fun getMethod(): String
    {
        return "/account/register"
    }
}