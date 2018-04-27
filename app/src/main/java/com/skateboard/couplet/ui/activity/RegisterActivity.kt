package com.skateboard.couplet.ui.activity

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.EditText
import com.skateboard.couplet.MainActivity
import com.skateboard.couplet.R
import com.skateboard.couplet.ui.activity.base.CBaseActivity
import com.skateboard.library.application.manager.AccountManager
import com.skateboard.library.response.account.LoginResponse
import com.skateboard.library.response.account.RegisterResponse
import com.skateboard.library.util.AccountUtil
import com.skateboard.library.util.showToast
import kotlinx.android.synthetic.main.activity_register.*
import okhttp3.Call
import java.io.IOException

/**
 * Created by skateboard on 2018/3/8.
 */
class RegisterActivity : CBaseActivity(), View.OnClickListener
{
    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)
        codeBtn.setOnClickListener(this)
        registerBtn.setOnClickListener(this)
    }

    override fun getToolbarTitle(): String
    {
        return getString(R.string.register)
    }

    override fun onClick(v: View?)
    {
        when (v?.id)
        {

            R.id.codeBtn ->
            {
                getCode()
            }

            R.id.registerBtn ->
            {
                register()
            }
            else ->
            {

            }

        }
    }

    private fun getCode()
    {
        if(checkUserIdInvalid(userName.editableText.toString()) && checkPasswordInvalid(passwordInp.editableText.toString()))
        {
            AccountManager.default.getIdCode(userName.editableText.toString())
            codeBtn.startCountDown()
        }
    }

    private fun register()
    {
        val userId = userName.editableText.toString()
        val password = passwordInp.editableText.toString()
        val code = codeInp.editableText.toString()
        if (checkInput(userId, password, code))
        {
            AccountManager.default.register(userId, password, code, registerListener)
        }

    }


    private val registerListener = object : AccountManager.RegisterListener
    {
        override fun onFailure(call: Call?, e: IOException?)
        {

        }

        override fun onSuccess(call: Call?, response: RegisterResponse)
        {
            if (response.isSuccess())
            {
                login(userName.editableText.toString(), passwordInp.editableText.toString())
            }
            when(response.code)
            {
                RegisterResponse.ALREADY_REGISTERED->
                {
                    showToast(getString(R.string.user_already_registered))
                }

                RegisterResponse.CODE_EXPIRED->
                {
                    showToast(getString(R.string.id_code_expired))
                }
                else->
                {

                }
            }
        }
    }

    private fun login(userId: String, password: String)
    {
        AccountManager.default.logIn(userId, password, loginListener)

    }

    private fun checkInput(userId: String?, password: String?, code: String?): Boolean
    {
        if (checkUserIdInvalid(userId))
        {
            if (checkPasswordInvalid(password))
            {
                if (checkIdCodeInvalid(code))
                {
                    return true
                }
            }
        }

        return false
    }

    private fun checkUserIdInvalid(userId: String?): Boolean
    {
        val useNameInvalid = checkIsInvalidUserName(userId)

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
        return true
    }

    private fun checkPasswordInvalid(password: String?): Boolean
    {
        val passwordInvalid = checkIsInvalidPassword(password)

        when (passwordInvalid)
        {
            AccountUtil.EMPTY ->
            {
                showError(this.passwordInp, getString(R.string.password_empty))
                return false
            }

            AccountUtil.INVALID_PHONENUM ->
            {
                showError(this.passwordInp, getString(R.string.invalid_password))
                return false
            }
        }

        return true
    }

    private fun checkIdCodeInvalid(code: String?): Boolean
    {

        if (code?.isEmpty() != false)
        {
            showError(this.codeInp, getString(R.string.idcode_empty))
            return false
        }

        return true
    }


    private var loginListener = object : AccountManager.LoginListsner
    {
        override fun onFailure(call: Call?, e: IOException?)
        {
            registerBtn.expand()
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
                    registerBtn.expand()
                    showToast(getString(R.string.invalid_username_password))
                }
                else ->
                {
                    registerBtn.expand()
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

    override fun onDestroy()
    {
        super.onDestroy()
        codeBtn.cancelCountDown()
    }
}