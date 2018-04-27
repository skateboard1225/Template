package com.skateboard.library.ui.adapter.base

import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import com.skateboard.library.R

/**
 * Created by skateboard on 2018/3/16.
 */
abstract class BaseAdapter<V : RecyclerView.ViewHolder> : RecyclerView.Adapter<V>(), View.OnClickListener
{

    var viewClickedListsner:OnViewClickedListener?=null


    interface OnViewClickedListener
    {
        fun onViewClicked(view: View, position: Int, data: Any)
    }

    override fun onClick(v: View?)
    {
        if (v != null)
        {
            viewClickedListsner?.onViewClicked(v, v.getTag(R.id.tag_recyclerview_position) as Int, v.getTag(R.id.tag_recyclerview_data))
        }
        else
        {

        }
    }

    protected fun <T> bindViewClickListener(view: View, position: Int, data: T)
    {
        view.setTag(R.id.tag_recyclerview_position, position)
        view.setTag(R.id.tag_recyclerview_data, data)
        view.setOnClickListener(this)
    }
}