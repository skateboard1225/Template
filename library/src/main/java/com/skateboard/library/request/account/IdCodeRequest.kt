package com.skateboard.library.request.account

import com.skateboard.library.request.base.APIBaseRequest
import com.skateboard.library.response.account.IdCodeResponse
import com.skateboard.library.util.ParserUtil
import com.skateboard.network.okhttp.OkHttpRequest
import okhttp3.Response

/**
 * Created by skateboard on 2018/3/8.
 */
class IdCodeRequest(private val userId:String) : APIBaseRequest<IdCodeResponse>()
{
    init
    {
        method= OkHttpRequest.Method.POST
    }

    override fun parseResponse(response: Response): IdCodeResponse
    {
        return ParserUtil.parse(response, IdCodeResponse())
    }

    override fun getMethod(): String
    {
        return "/account/get_code"
    }

    override fun getFormParamsBody(): MutableMap<String, String>
    {
        val paramsMap=mutableMapOf<String,String>()
        paramsMap["userId"]=userId
        return paramsMap
    }
}