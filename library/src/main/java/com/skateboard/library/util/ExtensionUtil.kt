package com.skateboard.library.util

import android.app.Activity
import android.widget.Toast

/**
 * Created by skateboard on 2018/3/7.
 */

fun Activity.showToast(message: String, duration: Int = Toast.LENGTH_SHORT) {

    Toast.makeText(this, message, duration).show()
}