package com.skateboard.library.request

import com.skateboard.library.application.manager.ConfigurationManager
import com.skateboard.library.bean.appconfig.AppConfig
import com.skateboard.library.bean.appconfig.DynamicData
import com.skateboard.network.okhttp.OkHttpRequest
import okhttp3.Response
import org.json.JSONObject

/**
 * Created by skateboard on 2018/2/27.
 */
class AppConfigRequest(url: String) : OkHttpRequest<AppConfig>(url)
{

    val KEY_DATA_SOURCE = "dataSource"

    val KEY_NLID = "nlid"

    val KEY_URL = "url"

    val KEY_PARAMS = "params"

    val KEY_BASE = "base"

    override fun parseResponse(response: Response): AppConfig
    {
        val responeString = response.body()?.string() ?: ""

        val appConfig = parseAppConfig(responeString)

        ConfigurationManager.default.initAppConfig(appConfig)

        return appConfig
    }


    private fun parseAppConfig(data: String): AppConfig
    {

        val appConfig = AppConfig()

        val sourceJson = JSONObject(data)

        pareDataSource(sourceJson, appConfig)

        return appConfig
    }


    private fun pareDataSource(sourceJson: JSONObject, appConfig: AppConfig)
    {

        val baseJsonObject = sourceJson.optJSONObject(KEY_BASE)

        if (baseJsonObject != null)
        {
            val dataSource = baseJsonObject.optJSONArray(KEY_DATA_SOURCE)

            if (dataSource != null)
            {
                for (i in 0 until dataSource.length())
                {
                    val dataItem = dataSource.optJSONObject(i)
                    val dynamicData = DynamicData()
                    dynamicData.nlid = dataItem.optString(KEY_NLID)
                    dynamicData.url = dataItem.optString(KEY_URL)
                    val params = dataItem.optJSONObject(KEY_PARAMS)
                    if (params != null)
                    {
                        val keySet = params.keys()
                        for (key in keySet)
                        {
                            dynamicData.params[key] = params.optString(key)
                        }
                    }
                    appConfig.dataSource[dynamicData.nlid] = dynamicData
                }

            }
        }
    }

}