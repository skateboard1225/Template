package com.skateboard.library.ui.activity.base

import android.os.Bundle
import com.skateboard.library.presenter.IPresenter
import com.skateboard.library.presenter.IView

/**
 * Created by skateboard on 2018/3/21.
 */
abstract class MVPBaseActivity<V : IView, P : IPresenter<V>> : BaseActivity()
{

    protected var presenter: P? = null

    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
    }

    abstract fun loadPresenter(): P


    override fun onDestroy()
    {
        super.onDestroy()
        presenter?.detach()
        presenter = null
    }
}