package com.skateboard.library.util

import com.skateboard.library.response.base.APIBaseResponse
import okhttp3.Response
import org.json.JSONObject
import kotlin.reflect.KClass

/**
 * Created by skateboard on 2018/3/6.
 */
object ParserUtil
{

    private val KEY_CODE = "code"

    private val KEY_DATA = "data"

    fun <T : APIBaseResponse> parse(response: Response, result: T): T
    {
        val dataString = response.body()?.string() ?: ""

        return parse(dataString, result)

    }


    fun <T : APIBaseResponse> parse(response: String, result: T): T
    {

        val sourceJson = JSONObject(response)
        parseCode(sourceJson, result)
        parseData(sourceJson, result)
        return result
    }

    private fun <T : APIBaseResponse> parseCode(sourceJson: JSONObject, result: T): T
    {
        val code = sourceJson.optString(KEY_CODE)
        result.code = code
        return result
    }

    private fun <T : APIBaseResponse> parseData(sourceJson: JSONObject, result: T): T
    {
        val dataJson = sourceJson.optJSONObject(KEY_DATA)
        if (dataJson != null)
        {
            val dataMap = mutableMapOf<String, String>()
            for (key in dataJson.keys())
            {
                dataMap[key] = dataJson.optString(key)
            }
            result.data = dataMap
        }
        return result
    }

}