package com.skateboard.couplet.ui.activity.base

import android.support.v7.widget.Toolbar
import com.skateboard.couplet.R
import com.skateboard.library.ui.activity.BaseActivity

/**
 * Created by skateboard on 2018/3/8.
 */
open class CBaseActivity:BaseActivity()
{

    protected  var toolbar: Toolbar?=null

    override fun onContentChanged()
    {
        super.onContentChanged()
        initToolbar()
    }

    private fun initToolbar()
    {
        toolbar=findViewById(R.id.toolBar)
        if(toolbar!=null)
        {
            toolbar?.title = getToolbarTitle()
            setSupportActionBar(toolbar)
        }
    }

    open fun getToolbarTitle():String
    {
        return ""
    }
}