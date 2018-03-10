package com.skateboard.library.ui.fragment

import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.skateboard.library.presenter.IPresenter
import com.skateboard.library.presenter.IView

/**
 * Created by skateboard on 2018/1/17.
 */
abstract class MVPBaseFragment<V : IView, P : IPresenter<V>>:Fragment(),IView
{

    protected var presenter:P?=null

    abstract  fun onLoadPresenter():P

    override fun onAttach(context: Context?)
    {
        super.onAttach(context)
        presenter=onLoadPresenter()
    }

    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View?
    {
        return super.onCreateView(inflater, container, savedInstanceState)
    }



    override fun onDestroy()
    {
        super.onDestroy()
        presenter?.detach()
        presenter=null
    }
}