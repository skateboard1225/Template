package com.skateboard.library.util

import android.content.Context
import android.content.DialogInterface
import android.support.v7.app.AlertDialog

/**
 * Created by skateboard on 2018/2/28.
 */
class DialogUtil
{

    companion object
    {


        fun createAlertDialog(context: Context, title: String = "", message: String = "", posText: String = "", posClickListener: DialogInterface.OnClickListener? = null, negText: String = "",
                              negClickListener:
                              DialogInterface
                              .OnClickListener? = null): AlertDialog
        {
            val dialogBuilder = AlertDialog.Builder(context)

            dialogBuilder.setTitle(title)

            dialogBuilder.setMessage(message)

            dialogBuilder.setPositiveButton(posText, posClickListener)

            dialogBuilder.setNegativeButton(negText, negClickListener)

            return dialogBuilder.create()
        }


    }


}