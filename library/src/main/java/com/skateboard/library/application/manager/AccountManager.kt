package com.skateboard.library.application.manager

import com.skateboard.library.application.Constants
import com.skateboard.library.application.manager.ShareDataManager.*
import com.skateboard.library.request.account.IdCodeRequest
import com.skateboard.library.response.account.LoginResponse
import com.skateboard.library.request.account.LoginRequest
import com.skateboard.library.request.account.RegisterRequest
import com.skateboard.library.request.base.APIBaseRequest
import com.skateboard.library.request.base.TOkHttpRequest
import com.skateboard.library.response.account.IdCodeResponse
import com.skateboard.library.response.account.RegisterResponse
import com.skateboard.library.response.base.APIBaseResponse
import com.skateboard.network.okhttp.OkClient
import io.reactivex.Observable
import okhttp3.Call
import java.io.IOException


/**
 * Created by skateboard on 2018/3/1.
 */
class AccountManager : BaseManager()
{

    companion object
    {
        val default: AccountManager
            get()
            {
                return MANAGER_MAP[Constants.MANAGER_ACCOUNT] as AccountManager
            }

        val KEY_ACCOUNT_IS_LOGIN = "key_account_is_login"

        val KEY_USERID = "key_userId"

        val KEY_NICK_NAME = "key_nick_name"

        val KEY_AUTOGRAPH = "key_autograph"

        val KEY_PORTRAIT = "key_portrait"

        val KEY_DATE = "key_date"

        val KEY_TOKEN = "key_token"
    }

    fun isLogin(): Boolean
    {
        return ShareData.getPreferenceBoolean(KEY_ACCOUNT_IS_LOGIN, false)
    }


    fun getIdCode(userId: String)
    {
        val idCodeRequest = IdCodeRequest(userId)
        OkClientManager.default.enquee(transFormRequest<IdCodeResponse>(idCodeRequest), null)
    }

    private fun <T : APIBaseResponse> transFormRequest(apiBaseRequest: APIBaseRequest<T>): TOkHttpRequest<T>
    {
        val tRequest = TOkHttpRequest<T>(apiBaseRequest)
        tRequest.setTag(this::class.java)
        return tRequest
    }

    fun register(userId: String, password: String, idCode: String, listener: RegisterListener?)
    {
        val registerRequest = RegisterRequest(userId, password, idCode)
        OkClientManager.default.enquee(transFormRequest<RegisterResponse>(registerRequest), object : OkClient.RequestCallback<RegisterResponse>
        {
            override fun onFailure(call: Call?, e: IOException?)
            {
                listener?.onFailure(call, e)
            }

            override fun onResponse(call: Call?, response: RegisterResponse)
            {
                listener?.onSuccess(call, response)
            }
        })
    }


    fun logIn(userName: String, password: String, listener: LoginListsner?)
    {

        val loginRequest = LoginRequest(userId = userName, password = password)
        Observable.just(loginRequest)
        val tRequest = transFormRequest<LoginResponse>(loginRequest)
        OkClientManager.default.enquee(tRequest, object : OkClient.RequestCallback<LoginResponse>
        {
            override fun onFailure(call: Call?, e: IOException?)
            {
                listener?.onFailure(call, e)
            }

            override fun onResponse(call: Call?, response: LoginResponse)
            {
                if (response.isSuccess())
                {
                    saveUserInfo(response)
                }
                listener?.onSuccess(call, response)
            }
        })

    }

    private fun saveUserInfo(response: LoginResponse)
    {
        if (response.data.isNotEmpty())
        {
            ShareData.savePreferenceString(KEY_USERID, response.getUserId())

            ShareData.savePreferenceString(KEY_NICK_NAME, response.getNickName())

            ShareData.savePreferenceString(KEY_AUTOGRAPH, response.getAutograph())

            ShareData.savePreferenceString(KEY_PORTRAIT, response.getPortrait())

            ShareData.savePreferenceString(KEY_DATE, response.getDate())

            ShareData.savePreferenceString(KEY_TOKEN, response.getToken())

            ShareData.savePreferenceBoolean(KEY_ACCOUNT_IS_LOGIN, true)
        }
    }

    interface LoginListsner
    {
        fun onFailure(call: Call?, e: IOException?)

        fun onSuccess(call: Call?, response: LoginResponse)
    }

    interface RegisterListener
    {
        fun onFailure(call: Call?, e: IOException?)

        fun onSuccess(call: Call?, response: RegisterResponse)
    }


    fun cancelRequest()
    {
        OkClientManager.default.cancelRequest(this::class.java.toString())
    }

}