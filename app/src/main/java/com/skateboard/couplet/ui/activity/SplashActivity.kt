package com.skateboard.couplet.ui.activity

import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AlertDialog
import com.skateboard.couplet.MainActivity
import com.skateboard.couplet.R
import com.skateboard.couplet.ui.activity.base.CBaseActivity
import com.skateboard.library.application.manager.AccountManager
import com.skateboard.library.application.manager.LaunchManager
import com.skateboard.library.ui.activity.base.BaseActivity
import com.skateboard.library.util.DialogUtil

/**
 * Created by skateboard on 2018/2/28.
 */
class SplashActivity : CBaseActivity()
{


    private var errorDialog: AlertDialog? = null


    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_splash)

        LaunchManager.default.registerOnLaunchCompletedListener(launchCompletedListener)

        LaunchManager.default.startLaunch()
    }

    private val launchCompletedListener = object : LaunchManager.OnLaunchCompletedListener
    {
        override fun onLaunchCompleted(launchResult: String)
        {
            if (launchResult == LaunchManager.LAUNCHRESULT_SUCCESS)
            {
                enter()
            }
            else if (launchResult == LaunchManager.LAUNCHRESULT_NETWORKERROR)
            {
                showErrorDialog()
            }

        }
    }

    private fun enter()
    {
        if (AccountManager.default.isLogin())
        {
            enterMainActivity()
        }
        else
        {
            enterLoginActivity()
        }
    }


    private fun enterMainActivity()
    {
        val intent = Intent(this, MainActivity::class.java)

        startActivity(intent)

        finish()
    }

    private fun enterLoginActivity()
    {

        val intent = Intent(this, LoginActivity::class.java)

        startActivity(intent)

        finish()

    }


    override fun onDestroy()
    {
        super.onDestroy()
        LaunchManager.default.unRegisterOnLaunchCompletedListener(launchCompletedListener)
        errorDialog?.dismiss()
    }

    private fun showErrorDialog()
    {
        errorDialog = DialogUtil.createAlertDialog(this, getString(R.string.network_error_title), getString(R.string.network_error_message), getString(R.string.confirm), DialogInterface.OnClickListener({ _, _ ->
            finish()
        }))

        errorDialog?.show()
    }
}