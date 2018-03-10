package com.skateboard.library.request.account

import com.skateboard.library.response.account.LoginResponse
import com.skateboard.library.request.base.APIBaseRequest
import com.skateboard.library.util.ParserUtil
import com.skateboard.network.okhttp.OkHttpRequest
import okhttp3.Response

/**
 * Created by skateboard on 2018/3/7.
 */
class LoginRequest : APIBaseRequest<LoginResponse>
{


    private val KEY_TOKEN = "token"

    private val KEY_USERID = "userId"

    private val KEY_PASSWORD = "password"

    var userId: String = ""

    var password: String = ""

    var token: String = ""


    constructor(userId: String, password: String)
    {
        this.userId = userId

        this.password = password

        method = OkHttpRequest.Method.POST

    }

    constructor(token: String)
    {
        this.token = token

        method = OkHttpRequest.Method.POST


    }

    override fun getFormParamsBody(): MutableMap<String, String>
    {
        val formParamsBody = mutableMapOf<String, String>()
        if (token.isNotEmpty())
        {
            formParamsBody[KEY_TOKEN] = token
        }
        else
        {
            formParamsBody[KEY_USERID] = userId

            formParamsBody[KEY_PASSWORD] = password
        }
        return formParamsBody
    }

    override fun parseResponse(response: Response): LoginResponse
    {
        return ParserUtil.parse(response, LoginResponse())
    }

    override fun getMethod(): String
    {
        return "/account/login"
    }
}