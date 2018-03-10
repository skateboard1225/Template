package com.skateboard.library.application.manager

import com.skateboard.library.application.Constants
import com.skateboard.library.bean.appconfig.AppConfig
import com.skateboard.library.request.AppConfigRequest
import com.skateboard.network.okhttp.OkClient
import okhttp3.Call
import java.io.IOException


/**
 * Created by skateboard on 2018/1/18.
 */
class LaunchManager : BaseManager()
{

    val launchCompletedListenerSet = mutableSetOf<OnLaunchCompletedListener>()

    companion object
    {

        private val KEY_APPCONFIG_URL="appconfig-url"

        val LAUNCHRESULT_NETWORKERROR="launchresult_networkerror"

        val LAUNCHRESULT_SUCCESS="launchresult_success"

        val default: LaunchManager
            get()
            {
                return MANAGER_MAP[Constants.MANAGER_LAUNCH] as LaunchManager
            }

    }


    interface OnLaunchCompletedListener
    {
        fun onLaunchCompleted(launchResult:String)
    }


    fun registerOnLaunchCompletedListener(listener:OnLaunchCompletedListener)
    {
        launchCompletedListenerSet.add(listener)
    }

    fun unRegisterOnLaunchCompletedListener(listener: OnLaunchCompletedListener)
    {
        launchCompletedListenerSet.remove(listener)
    }

    fun startLaunch()
    {
        val appConfigRequest = AppConfigRequest(ConfigurationManager.default.getValue(KEY_APPCONFIG_URL) ?: "")

        OkClientManager.default.enquee<AppConfig>(appConfigRequest, object : OkClient.RequestCallback<AppConfig>
        {
            override fun onFailure(call: Call?, e: IOException?)
            {
                notifyLaunchCompleted(LAUNCHRESULT_NETWORKERROR)
            }

            override fun onResponse(call: Call?, response: AppConfig)
            {
                if(response.dataSource.isNotEmpty())
                {
                    ConfigurationManager.default.initAppConfig(response)
                    ApplicationManager.default.notifyApplicationIntilized()
                    notifyLaunchCompleted(LAUNCHRESULT_SUCCESS)
                }
                else
                {
                    notifyLaunchCompleted(LAUNCHRESULT_NETWORKERROR)
                }
            }
        })

    }

    private fun notifyLaunchCompleted(launchResult:String)
    {
        for(listener in launchCompletedListenerSet)
        {
            listener.onLaunchCompleted(launchResult)
        }
    }


}