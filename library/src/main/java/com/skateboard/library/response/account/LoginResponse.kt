package com.skateboard.library.response.account

import com.skateboard.library.response.base.APIBaseResponse

/**
 * Created by skateboard on 2018/3/5.
 */
class LoginResponse: APIBaseResponse()
{
    private val KEY_USERID="userId"

    private val KEY_PASSWORD="password"

    private val KEY_NICK_NAME="nick_name"

    private val KEY_AUTOGRAPH="autograph"

    private val KEY_PORTRAIT="portrait"

    private val KEY_DATE="date"

    private val KEY_TOKEN="token"

    companion object
    {
        val SUCCESS="success"

        val TOKEN_EXPIRED="tokenexpired"

        val INVALID_USERNAME_PASSWORD="invalid_username_password"
    }

    override fun isSuccess(): Boolean
    {
        return code!="" && code== SUCCESS
    }

    fun getUserId():String
    {
        return getString(KEY_USERID)
    }

    fun getPassword():String
    {
        return getString(KEY_PASSWORD)
    }

    fun getNickName():String
    {
        return getString(KEY_NICK_NAME)
    }

    fun getAutograph():String
    {
        return getString(KEY_AUTOGRAPH)
    }

    fun getPortrait():String
    {
        return getString(KEY_PORTRAIT)
    }

    fun getDate():String
    {
        return getString(KEY_DATE)
    }

    fun getToken():String
    {
        return getString(KEY_TOKEN)
    }

}