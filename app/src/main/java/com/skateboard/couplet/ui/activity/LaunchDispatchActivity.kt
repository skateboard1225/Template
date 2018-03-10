package com.skateboard.couplet.ui.activity

import android.content.Intent
import android.os.Bundle
import com.skateboard.couplet.MainActivity
import com.skateboard.couplet.ui.activity.base.CBaseActivity
import com.skateboard.library.application.manager.ApplicationManager
import com.skateboard.library.ui.activity.BaseActivity

/**
 * Created by skateboard on 2018/2/28.
 */
class LaunchDispatchActivity : CBaseActivity()
{
    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        if (ApplicationManager.default.isIntilized())
        {
            enterMainActivity()
        }
        else
        {
            enterSplashActivity()
        }
    }

    private fun enterMainActivity()
    {
        val intent = Intent(this, MainActivity::class.java)

        startActivity(intent)

        finish()
    }

    private fun enterSplashActivity()
    {
        val intent = Intent(this, SplashActivity::class.java)

        startActivity(intent)

        finish()
    }
}