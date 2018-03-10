package com.skateboard.library.application

import android.app.Application
import com.skateboard.library.application.manager.*

/**
 * Created by skateboard on 2018/1/16.
 */
open class BaseApplication:Application()
{

    override fun onCreate()
    {
        super.onCreate()
        onBindManagers()
        dispatchManagers()
    }

    open fun onBindManagers()
    {
        bindManager(Constants.MANAGER_CONFIGURATION,ConfigurationManager())
        bindManager(Constants.MANAGER_APPLICATION,ApplicationManager())
        bindManager(Constants.MANAGER_LAUNCH,LaunchManager())
        bindManager(Constants.MANAGER_SHAREDATA,ShareDataManager())
        bindManager(Constants.MANAGER_OKCLIENT,OkClientManager())
        bindManager(Constants.MANAGER_ACCOUNT, AccountManager())
    }

    open fun dispatchManagers()
    {
        BaseManager.dispatchManager(this)
    }


    fun bindManager(key:String,manager:BaseManager)
    {
        BaseManager.MANAGER_MAP[key]=manager
    }



}