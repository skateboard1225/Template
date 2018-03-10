package com.skateboard.library.application.manager

import android.app.Application
import com.skateboard.library.R
import com.skateboard.library.application.Constants
import com.skateboard.library.bean.appconfig.AppConfig
import org.json.JSONArray
import org.json.JSONObject
import org.xmlpull.v1.XmlPullParser

/**
 * Created by skateboard on 2018/1/21.
 */
class ConfigurationManager : BaseManager()
{
    companion object
    {
        val default: ConfigurationManager
            get()
            {
                return MANAGER_MAP[Constants.MANAGER_CONFIGURATION] as ConfigurationManager
            }

        val KEY_PARAMER="paramer"

    }

    interface OnConfigurationLoadedListener
    {

        fun onLocalConfigurationLoaded()

        fun onConfigurationLoaded()
    }


    private val configurationMap = mutableMapOf<String, String>()

    private lateinit var appConfig:AppConfig

    private val listenerSet = mutableSetOf<OnConfigurationLoadedListener>()


    fun initAppConfig(appConfig: AppConfig)
    {
        this.appConfig=appConfig
    }

    fun addConfigurationLoadedListener(listener: OnConfigurationLoadedListener)
    {
        listenerSet.add(listener)
    }

    fun removeConfigurationLoadedListener(listener: OnConfigurationLoadedListener)
    {
        listenerSet.remove(listener)
    }


    override fun onCreate(application: Application)
    {
        super.onCreate(application)
        parseLocal()
    }

    fun parseLocal()
    {
        val xmlParser = application.resources.getXml(R.xml.builtin_configuraiton)
        var eventType = xmlParser.eventType
        while (eventType != XmlPullParser.END_DOCUMENT)
        {
            when (eventType)
            {
                XmlPullParser.START_TAG ->
                {
                    if (xmlParser.name == KEY_PARAMER)
                    {
                        val key = xmlParser.getAttributeValue(0)
                        val value = xmlParser.getAttributeValue(1)
                        configurationMap[key] = value
                    }
                }

            }
            eventType = xmlParser.next()
        }

        xmlParser.close()
    }





    fun getValue(key:String):String?
    {
        return configurationMap[key]
    }

    fun getUrl(nlid:String):String
    {
        return appConfig.dataSource[nlid]?.url?:""
    }

    fun getParamer(nlid:String,key:String):String
    {
        return appConfig.dataSource[nlid]?.params?.get(key)?:""
    }

}