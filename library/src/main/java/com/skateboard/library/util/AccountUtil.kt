package com.skateboard.library.util

import java.util.regex.Pattern

/**
 * Created by skateboard on 2018/3/6.
 */
object AccountUtil
{

    val RIGHT=0

    val EMPTY=1

    val INVALID_PHONENUM=2

    val INVALID_PASSWORD=3

    fun checkIsValidPhoneNum(phoneNum:String?):Int
    {
        if (phoneNum?.isNotEmpty() == true)
        {
            val pattern = Pattern.compile("[1][34578]\\d{9}")
            val matcher = pattern.matcher(phoneNum)
            return if(matcher.matches())
            {
                RIGHT
            }
            else
            {
                INVALID_PHONENUM
            }
        }
        else
        {
            return EMPTY
        }
    }

    fun checkIsValidPassword(password:String?):Int
    {
        return if(password==null || password=="")
        {
            EMPTY
        }
        else
        {
            if(password.length<6)
            {
                INVALID_PASSWORD
            }
            else
            {
                RIGHT
            }
        }
    }

}