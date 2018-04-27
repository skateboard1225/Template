package com.skateboard.couplet

import android.os.Bundle
import com.skateboard.library.application.manager.LaunchManager
import com.skateboard.library.ui.activity.base.BaseActivity

class MainActivity : BaseActivity()
{

    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        LaunchManager.default.startLaunch()
    }
}
