package com.skateboard.library.request.base

import com.skateboard.library.application.Constants
import com.skateboard.library.application.manager.ConfigurationManager
import com.skateboard.library.response.base.APIBaseResponse
import com.skateboard.network.okhttp.OkHttpRequest
import okhttp3.Response
import java.io.File

/**
 * Created by skateboard on 2018/3/7.
 */
class TOkHttpRequest<T:APIBaseResponse>(val apiRequest:APIBaseRequest<T>): OkHttpRequest<T>(ConfigurationManager.default.getUrl(Constants.NLID_FEED_HOST)+apiRequest.getMethod(),
        apiRequest.method)
{


    override fun getHeaders(): MutableMap<String, String>
    {
        return apiRequest.headers
    }

    override fun getFormParamsBody(): MutableMap<String, String>
    {
        return apiRequest.getFormParamsBody()
    }

    override fun parseResponse(response: Response): T
    {
         return apiRequest.parseResponse(response)
    }

    override fun getUploadFiels(): MutableMap<String, File>
    {
        return apiRequest.getUploadFiels()
    }

    override fun getMediaType(): String
    {
        return apiRequest.getMediaType()
    }
}