package com.skateboard.library.application.manager

import android.app.Application

/**
 * Created by skateboard on 2018/1/16.
 */
open class BaseManager
{

    companion object
    {
        val MANAGER_MAP= mutableMapOf<String,BaseManager>()

        fun dispatchManager(application: Application)
        {
            MANAGER_MAP.keys
                    .map { MANAGER_MAP[it] }
                    .forEach { it?.onCreate(application) }
        }


        fun getManager(key:String):BaseManager?
        {
            return MANAGER_MAP[key]

        }

    }

    protected lateinit  var application:Application



    open fun onCreate(application: Application)
    {
        this.application=application
    }



}