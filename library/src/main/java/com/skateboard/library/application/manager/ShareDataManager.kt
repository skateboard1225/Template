package com.skateboard.library.application.manager

import android.content.Context
import android.preference.PreferenceManager
import com.skateboard.library.application.Constants
import java.io.IOException
import java.io.ObjectInputStream
import java.io.ObjectOutputStream
import java.io.Serializable

/**
 * Created by skateboard on 2018/2/27.
 */
class ShareDataManager : BaseManager()
{
    companion object
    {
        val default: ShareDataManager
            get()
            {
                return MANAGER_MAP[Constants.MANAGER_SHAREDATA] as ShareDataManager
            }
    }


    fun savePreferenceBoolean(key: String, value: Boolean)
    {
        PreferenceManager.getDefaultSharedPreferences(application).edit().putBoolean(key, value).commit()
    }


    fun getPreferenceBoolean(key: String, value: Boolean): Boolean
    {
        return PreferenceManager.getDefaultSharedPreferences(application).getBoolean(key, value)
    }

    fun savePreferenceInt(key: String, value: Int)
    {
        PreferenceManager.getDefaultSharedPreferences(application).edit().putInt(key, value).commit()
    }

    fun getPreferenceInt(key: String): Int
    {
        return PreferenceManager.getDefaultSharedPreferences(application).getInt(key, -1)
    }

    fun savePreferenceLong(key: String, value: Long)
    {
        PreferenceManager.getDefaultSharedPreferences(application).edit().putLong(key, value).commit()
    }

    fun getPreferenceLong(key: String): Long
    {

        return PreferenceManager.getDefaultSharedPreferences(application).getLong(key, -1L)
    }

    fun savePreferenceString(key: String, value: String)
    {
        PreferenceManager.getDefaultSharedPreferences(application).edit().putString(key, value).commit()
    }

    fun getPreferenceString(key: String, value: String): String
    {
        return PreferenceManager.getDefaultSharedPreferences(application).getString(key, value)
    }


    fun savePreferenceFloat(key: String, value: Float)
    {
        PreferenceManager.getDefaultSharedPreferences(application).edit().putFloat(key, value).commit()
    }

    fun getPreferenceFloat(key: String): Float
    {
        return PreferenceManager.getDefaultSharedPreferences(application).getFloat(key, -1f)
    }

    fun saveSerializable(fileName: String, obj: Serializable?)
    {
        try
        {
            val objectOutputStream = ObjectOutputStream(application.openFileOutput(fileName, Context.MODE_PRIVATE))
            objectOutputStream.writeObject(obj)
            objectOutputStream.close()
        }
        catch (e: IOException)
        {
            e.printStackTrace()
        }

    }

    fun getSerializable(fileName: String): Serializable?
    {
        var obj: Serializable? = null
        try
        {
            val objectInputStream = ObjectInputStream(application.openFileInput(fileName))
            obj = objectInputStream.readObject() as? Serializable
            objectInputStream.close()
        }
        catch (e: IOException)
        {
            e.printStackTrace()
        }
        catch (e: ClassNotFoundException)
        {
            e.printStackTrace()
        }

        return obj
    }


    object ShareData
    {

        fun savePreferenceBoolean(key: String, value: Boolean)
        {
            ShareDataManager.default.savePreferenceBoolean(key, value)
        }


        fun getPreferenceBoolean(key: String, value: Boolean): Boolean
        {
            return ShareDataManager.default.getPreferenceBoolean(key, value)
        }

        fun savePreferenceInt(key: String, value: Int)
        {
            ShareDataManager.default.savePreferenceInt(key, value)
        }

        fun getPreferenceInt(key: String): Int
        {
            return ShareDataManager.default.getPreferenceInt(key)
        }

        fun savePreferenceLong(key: String, value: Long)
        {
            ShareDataManager.default.savePreferenceLong(key, value)
        }

        fun getPreferenceLong(key: String): Long
        {
            return ShareDataManager.default.getPreferenceLong(key)
        }

        fun savePreferenceString(key: String, value: String)
        {
            ShareDataManager.default.savePreferenceString(key, value)
        }

        fun getPreferenceString(key: String, value: String): String
        {
            return ShareDataManager.default.getPreferenceString(key, value)
        }


        fun savePreferenceFloat(key: String, value: Float)
        {
            ShareDataManager.default.savePreferenceFloat(key, value)
        }

        fun getPreferenceFloat(key: String): Float
        {
            return ShareDataManager.default.getPreferenceFloat(key)
        }

        fun getSerializable(fileName: String): Serializable?
        {
            return ShareDataManager.default.getSerializable(fileName)
        }

        fun saveSerializable(fileName: String, obj: Serializable)
        {
            ShareDataManager.default.saveSerializable(fileName, obj)
        }
    }


}