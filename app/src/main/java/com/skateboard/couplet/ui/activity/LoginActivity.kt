package com.skateboard.couplet.ui.activity

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.EditText
import com.skateboard.couplet.MainActivity
import com.skateboard.couplet.R
import com.skateboard.couplet.ui.activity.base.CBaseActivity
import com.skateboard.couplet.ui.widget.TransCircleButton
import com.skateboard.library.application.manager.AccountManager
import com.skateboard.library.response.account.LoginResponse
import com.skateboard.library.util.AccountUtil
import com.skateboard.library.util.showToast
import kotlinx.android.synthetic.main.activity_login.*
import okhttp3.Call
import java.io.IOException

/**
 * Created by skateboard on 2018/3/1.
 */
class LoginActivity : CBaseActivity(), View.OnClickListener
{


    override fun getToolbarTitle(): String
    {
        return getString(R.string.login)
    }

    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        loginBtn.setOnClickListener(this)
        register.setOnClickListener(this)
    }

    override fun onClick(v: View?)
    {

        when (v?.id)
        {
            R.id.loginBtn ->
            {
                if (loginBtn.getState() == TransCircleButton.STATE.NORMAL)
                {

                    startLogin()
                }
            }

            R.id.register ->
            {
                enterRegisterActivity()
            }
            else ->
            {

            }
        }
    }

    private fun startLogin()
    {
        val userName = userName.editableText?.toString()
        val passWord = password.editableText?.toString()

        if (checkInvalide(userName, passWord))
        {
            loginBtn.shrink()
            login(userName!!, passWord!!)
        }
    }

    private fun enterRegisterActivity()
    {
        val intent = Intent(this, RegisterActivity::class.java)

        startActivity(intent)
    }


    private fun checkInvalide(userName: String?, password: String?): Boolean
    {

        val useNameInvalid = checkIsInvalidUserName(userName)

        when (useNameInvalid)
        {
            AccountUtil.EMPTY ->
            {
                showError(this.userName, getString(R.string.username_empty))
                return false
            }

            AccountUtil.INVALID_PHONENUM ->
            {
                showError(this.userName, getString(R.string.invalid_username))
                return false
            }

        }

        val passwordInvalid = checkIsInvalidPassword(password)

        when (passwordInvalid)
        {
            AccountUtil.EMPTY ->
            {
                showError(this.password, getString(R.string.password_empty))
                return false
            }

            AccountUtil.INVALID_PHONENUM ->
            {
                showError(this.password, getString(R.string.invalid_password))
                return false
            }
        }

        return true
    }

    private fun login(userName: String, password: String)
    {
        AccountManager.default.logIn(userName, password, loginListener)

    }

    private var loginListener = object : AccountManager.LoginListsner
    {
        override fun onFailure(call: Call?, e: IOException?)
        {
            loginBtn.expand()
        }

        override fun onSuccess(call: Call?, response: LoginResponse)
        {
            when (response.code)
            {
                LoginResponse.SUCCESS ->
                {
                    enterMainActivity()
                }

                LoginResponse.INVALID_USERNAME_PASSWORD ->
                {
                    loginBtn.expand()
                    showToast(getString(R.string.invalid_username_password))
                }
                else ->
                {
                    loginBtn.expand()
                }

            }
        }
    }


    private fun enterMainActivity()
    {
        val intent = Intent(this, MainActivity::class.java)

        startActivity(intent)

        finish()
    }


    private fun checkIsInvalidUserName(userName: String?): Int
    {
        return AccountUtil.checkIsValidPhoneNum(userName)
    }

    private fun checkIsInvalidPassword(password: String?): Int
    {
        return AccountUtil.checkIsValidPassword(password)
    }

    private fun showError(editText: EditText, error: String)
    {
        editText.error = error
        editText.requestFocus()
    }

}