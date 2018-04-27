package com.skateboard.library.ui.activity.base

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View


/**
 * Created by skateboard on 2018/1/18.
 */
open class BaseActivity: AppCompatActivity()
{


    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
    }


    override fun onDestroy()
    {
        super.onDestroy()
    }
}