package com.skateboard.network.retrofit

import com.skateboard.network.retrofit.factory.ConvertFactory
import com.skateboard.network.retrofit.listener.ResponseListener
import io.reactivex.Observable
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import okhttp3.OkHttpClient
import retrofit2.CallAdapter
import retrofit2.Retrofit

/**
 * Created by skateboard on 2018/1/21.
 */
class RetrofitClient private constructor()
{
    companion object
    {
        class Builder
        {
            lateinit var retrofitClient:RetrofitClient

            lateinit var retrofitBuilder:Retrofit.Builder

            constructor()
            {
                retrofitClient =RetrofitClient()
                retrofitBuilder=Retrofit.Builder()
            }

            fun baseUrl(url:String):Builder
            {
                retrofitBuilder.baseUrl(url)
                return this
            }

            fun addCallAdapterFactory(factory:CallAdapter.Factory):Builder
            {
                retrofitBuilder.addCallAdapterFactory(factory)
                return this
            }

            fun addConvertFactory(factory: ConvertFactory):Builder
            {
                retrofitBuilder.addConverterFactory(factory)
                return this
            }

            fun client(client:OkHttpClient):Builder
            {
                retrofitBuilder.client(client)
                return this
            }

            fun callFactory(factory:okhttp3.Call.Factory):Builder
            {
                retrofitBuilder.callFactory(factory)
                return this
            }

            fun build():RetrofitClient
            {
                this.retrofitClient.retrofit=this.retrofitBuilder.build()
                return this.retrofitClient
            }
        }

    }


    private lateinit var retrofit:Retrofit

    private var tagMap = mutableMapOf<Any, Disposable>()


    fun <T> createService(cla: Class<T>): T
    {
        return retrofit.create(cla)
    }

    fun <T> execute(observable: Observable<T>, listener: ResponseListener<T>?, tag: Any = observable::class)
    {
        subscribe(observable, listener, tag)
    }

    fun <T> enquee(observable: Observable<T>, listener: ResponseListener<T>?, tag: Any = observable::class)
    {
        subscribe(observable.subscribeOn(Schedulers.newThread()).observeOn(AndroidSchedulers.mainThread()), listener, tag)
    }


    private fun <T> subscribe(observable: Observable<T>, listener: ResponseListener<T>?, tag: Any)
    {

        observable
                .subscribe(object : Observer<T>
                {
                    override fun onComplete()
                    {
                        listener?.onCompleted()
                        cancelRequest(tag)
                    }

                    override fun onError(e: Throwable)
                    {
                        listener?.onError(e)
                    }

                    override fun onSubscribe(d: Disposable)
                    {
                        tagMap[tag] = d
                    }

                    override fun onNext(t: T)
                    {
                        listener?.onResponse(t)
                    }

                })
    }

    fun cancelRequest(tag: Any)
    {
        val d = tagMap[tag]
        if (d?.isDisposed == false)
        {
            d.dispose()
        }
        tagMap.remove(tag)
    }


    fun cancelAllRequest()
    {
        for (key in tagMap.keys)
        {
            cancelRequest(key)
        }
    }

}