package com.skateboard.library.application.manager

import com.skateboard.library.application.Constants

/**
 * Created by skateboard on 2018/1/16.
 */
class ApplicationManager : BaseManager()
{
    private var isIntilized = false

    private val onApplicationIntilizedListsenerSet = mutableSetOf<OnApplicationIntilizedListener>()


    companion object
    {
        val default: ApplicationManager
            get()
            {
                return MANAGER_MAP[Constants.MANAGER_APPLICATION] as ApplicationManager
            }
    }


    fun isIntilized(): Boolean
    {
        return isIntilized
    }


    interface OnApplicationIntilizedListener
    {
        fun applicationIntilized()
    }


    fun registerOnApplicationIntilizedListener(listener: OnApplicationIntilizedListener)
    {
        onApplicationIntilizedListsenerSet.add(listener)
    }

    fun unRegisterOnApplicationIntilizedListener(listener: OnApplicationIntilizedListener)
    {
        onApplicationIntilizedListsenerSet.remove(listener)
    }

    fun notifyApplicationIntilized()
    {
        isIntilized = true
        for (listener in onApplicationIntilizedListsenerSet)
        {
            listener.applicationIntilized()
        }
    }

}