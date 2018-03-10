package com.skateboard.library.presenter

/**
 * Created by skateboard on 2018/1/17.
 */
open class IPresenter<V : IView>
{
    var iView: V? = null


    fun attachView(view:V)
    {
        this.iView=view
    }


    fun detach()
    {
        this.iView=null
    }

}