package com.skateboard.library.bean.appconfig

import java.io.Serializable

/**
 * Created by skateboard on 2018/2/27.
 */
class AppConfig:Serializable
{
     val dataSource:MutableMap<String, DynamicData> = mutableMapOf()



}