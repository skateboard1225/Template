package com.skateboard.library.request

import com.skateboard.library.application.Constants
import com.skateboard.library.application.manager.ConfigurationManager
import com.skateboard.library.response.account.LoginResponse
import com.skateboard.library.util.ParserUtil
import com.skateboard.network.okhttp.OkHttpRequest
import okhttp3.Response

/**
 * Created by skateboard on 2018/3/5.
 */
class LoginRequest:OkHttpRequest<LoginResponse>
{

    private lateinit var userId: String

    private lateinit var password: String

    private lateinit var token: String

    private val KEY_TOKEN="token"

    private val KEY_USERID="userId"

    private val KEY_PASSWORD="password"

    constructor(url:String,userId:String, password:String):super(url,Method.POST)
    {
        this.userId=userId
        this.password=password
    }

    constructor(token:String,url:String=ConfigurationManager.default.getUrl(Constants.NLID_FEED_HOST)):super(url)
    {
       this.token=token
    }

    override fun getFormParamsBody(): MutableMap<String, String>
    {
        val paramsMap= mutableMapOf<String,String>()
        if(token!=null)
        {
            paramsMap[KEY_TOKEN]=token
        }
        else
        {
            paramsMap[KEY_USERID] = userId
            paramsMap[KEY_PASSWORD] = password
        }
        return paramsMap
    }

    override fun parseResponse(response: Response): LoginResponse
    {
         return ParserUtil.parse(response, LoginResponse())
    }
}