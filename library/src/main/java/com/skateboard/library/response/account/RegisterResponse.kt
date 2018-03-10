package com.skateboard.library.response.account

import com.skateboard.library.response.base.APIBaseResponse

/**
 * Created by skateboard on 2018/3/9.
 */
class RegisterResponse : APIBaseResponse()
{

    companion object
    {
        val ALREADY_REGISTERED = "alreadyregistered"

        val REGISTERED = "registered"

        val CODE_EXPIRED = "codeexpired"

    }



    override fun isSuccess(): Boolean
    {
        return code == REGISTERED
    }
}