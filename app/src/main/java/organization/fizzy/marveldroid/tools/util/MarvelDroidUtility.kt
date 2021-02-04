/*
 * 2020 MarvelDroid , Attribution Images belong to Marvel Entertainment
 * Source Timestamps exist under: https://github.com/PabloRosas17
 * Written by Jose Pablo Rosas  <pablo.rosas.0170@gmail.com>, January 2020
 */

package organization.fizzy.marveldroid.tools.util

import android.app.Activity
import android.content.Context
import android.content.res.Configuration
import androidx.lifecycle.MutableLiveData
import com.google.gson.Gson
import com.google.gson.JsonObject
import organization.fizzy.marveldroid.BuildConfig.MARVEL_PRIVATE_API_KEY
import organization.fizzy.marveldroid.BuildConfig.MARVEL_PUBLIC_API_KEY
import organization.fizzy.marveldroid.model.AccountWrapper
import organization.fizzy.marveldroid.model.KeyWrapper
import organization.fizzy.marveldroid.tools.constants.FirebaseAccountFields.FIELD_PASSWORD
import organization.fizzy.marveldroid.tools.constants.FirebaseAccountFields.FIELD_USERNAME
import organization.fizzy.marveldroid.tools.constants.FirebaseStatusConst.STATUS_LOGIN_FAILURE
import organization.fizzy.marveldroid.tools.constants.FirebaseStatusConst.STATUS_LOGIN_SUCCESS
import organization.fizzy.marveldroid.tools.constants.FirebaseStatusConst.STATUS_SIGNON_FAILURE
import organization.fizzy.marveldroid.tools.constants.FirebaseStatusConst.STATUS_SIGNON_SUCCESS
import organization.fizzy.marveldroid.tools.constants.FirebaseStatusConst.STATUS_SIGNUP_FAILURE
import organization.fizzy.marveldroid.tools.constants.FirebaseStatusConst.STATUS_SIGNUP_SUCCESS
import organization.fizzy.marveldroid.tools.constants.FirebaseStatusConst.STATUS_UPDATE_FAILURE
import organization.fizzy.marveldroid.tools.constants.FirebaseStatusConst.STATUS_UPDATE_SUCCESS
import organization.fizzy.marveldroid.tools.services.api.FirebaseService
import organization.fizzy.marveldroid.tools.services.api.MarvelService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.math.BigInteger
import java.security.MessageDigest

/**
 * @author, evolandlupiz
 * @date, 2/28/2020
 * @property, MarvelDroid
 * @purpose, Application Utility.
 */

/**
 * @desc Application Utility that provides services for the application.
 */
class MarvelDroidUtility {

    /**
     *  time stamp utility, generates a time stamp for the marvel api.
     */
    fun genTimeStamp(): String {
        return System.currentTimeMillis().toString()
    }

    /**
     *  hash utility, generates a hash for the marvel api.
     */
    fun getHash(ts: String): String {
        val hash = ts + MARVEL_PRIVATE_API_KEY + MARVEL_PUBLIC_API_KEY
        return hash.md5()
    }

    /**
     *  extension function to generate md5
     */
    private fun String.md5(): String {
        val md = MessageDigest.getInstance("MD5")
        return BigInteger(1, md.digest(toByteArray())).toString(16).padStart(32, '0')
    }

    /**
     *  network code check utility
     */
    fun apiCodeCheck(code: Int) {
        when(code) {
            409 -> println("409 missing api key or 409 missing hash or 409 missing timestamp")
            401 -> println("401 invalid referer or 401 invalid hash")
            405 -> println("405 method not allowed")
            403 -> println("403 forbidden")
            200 -> println("200 ok!")
        }
    }

    /**
     *  network message check utility
     */
    fun apiMsgCheck(msg: String) {
        if(msg.isBlank() || msg.isEmpty()){
            println("MarvelDroidUtil.kt, api msg is null, blank, or empty!")
        }
    }

    /**
     * write an update to an account in the firebase database using the rest api.
     */
    fun utilWriteOnUpdateMarvelActs(account: AccountWrapper, firebaseapi: FirebaseService, ld: MutableLiveData<HashMap<Int, AccountWrapper?>>) {
        val call = firebaseapi.putUpdateOnMarvelAct(account.mKey!!.removeSurrounding("\""), account)
        call.enqueue(object : Callback<KeyWrapper> {
            override fun onFailure(call: Call<KeyWrapper>, t: Throwable) {
                println(t.message)
            }
            override fun onResponse(call: Call<KeyWrapper>, response: Response<KeyWrapper?>) {
                val map = hashMapOf<Int, AccountWrapper?>()
                if(response.isSuccessful) {
                    map.put(STATUS_UPDATE_SUCCESS,null)
                    ld.postValue(map)
                } else {
                    map.put(STATUS_UPDATE_FAILURE,null)
                    ld.postValue(map)
                }
            }
        })
    }

    /**
     * only registration view should have the ability to write on signup,
     * write an account to the firebase database using the rest api.
     */
    fun utilWriteOnSignUpMarvelActs(account: AccountWrapper, firebaseapi: FirebaseService, ld: MutableLiveData<HashMap<Int, AccountWrapper?>>) {
        val call = firebaseapi.postMarvelAct(account)
        call.enqueue(object : Callback<JsonObject> {
            override fun onFailure(call: Call<JsonObject>, t: Throwable) {
                println(t.message)
            }
            override fun onResponse(call: Call<JsonObject>, response: Response<JsonObject>) {
                var key = ""
                response.body()?.keySet()?.forEach { it -> key = it }
                account.mKey = response.body()?.get(key).toString().removeSurrounding("\"")
                val map = hashMapOf<Int, AccountWrapper?>()
                if(response.isSuccessful) {
                    map.put(STATUS_SIGNON_SUCCESS,account)
                    ld.postValue(map)
                } else {
                    map.put(STATUS_SIGNON_FAILURE,null)
                    ld.postValue(map)
                }
            }
        })
    }

    /**
     * only registration view should have the ability to read on signup,
     * read an account from the firebase database using the rest api.
     * gets an account , double quotes are necessary for the query filters
     */
    fun utilReadOnSignUpMarvelActs(firebaseapi: FirebaseService, orderBy: String, startAt: String, endAt: String, ld: MutableLiveData<HashMap<Int, AccountWrapper?>>) {
        val call = firebaseapi.getMarvelAct("\"$orderBy\"","\"$startAt\"","\"$endAt\"")
        call.enqueue(object : Callback<JsonObject>{
            override fun onFailure(call: Call<JsonObject>, t: Throwable) {
                println(t.message)
            }
            override fun onResponse(call: Call<JsonObject>, response: Response<JsonObject>) {
                /* iterate the key at the outer level, extract and save it.
                 * parse response data into a wrapper.
                 * update wrapper with account key */
                val map = hashMapOf<Int, AccountWrapper?>()
                if(response.isSuccessful) {
                    var key = ""
                    response.body()?.keySet()?.forEach { it -> key = it }
                    val data = response.body()?.get(key)
                    val wrapper = Gson().fromJson(data,
                        AccountWrapper::class.java)
                    if(wrapper == null){
                        map.put(STATUS_SIGNUP_SUCCESS,null)
                        ld.postValue(map)
                    } else {
                        wrapper.mKey = key
                        map.put(STATUS_SIGNUP_FAILURE,wrapper)
                        ld.postValue(map)
                    }
                } else {
                    map.put(STATUS_SIGNUP_FAILURE,null)
                    ld.postValue(map)
                }
            }
        })
    }

    /**
     * only registration view should have the ability to read on login,
     * read an account from the firebase database using the rest api.
     * gets an account , double quotes are necessary for the query filters
     */
    fun utilReadOnLogInMarvelActs(firebaseapi: FirebaseService, orderBy: String, startAt: String, endAt: String, ld: MutableLiveData<HashMap<Int, AccountWrapper?>>) {
        val call = firebaseapi.getMarvelAct("\"$orderBy\"","\"$startAt\"","\"$endAt\"")
        call.enqueue(object : Callback<JsonObject> {
            override fun onFailure(call: Call<JsonObject>, t: Throwable) {
                println(t.message)
            }
            override fun onResponse(call: Call<JsonObject>, response: Response<JsonObject>) {
                /* iterate the key at the outer level, extract and save it.
                 * parse response data into a wrapper.
                 * update wrapper with account key */
                var key = ""
                response.body()?.keySet()?.forEach { it -> key = it }
                val data = response.body()?.get(key)
                val wrapper = Gson().fromJson(data,
                    AccountWrapper::class.java)
                val map = hashMapOf<Int, AccountWrapper?>()
                if(response.isSuccessful) {
                    if(wrapper == null){
                        map.put(STATUS_LOGIN_FAILURE,null)
                        ld.postValue(map)
                    } else {
                        wrapper.mKey = key
                        map.put(STATUS_LOGIN_SUCCESS,wrapper)
                        ld.postValue(map)
                    }
                } else {
                    map.put(STATUS_LOGIN_FAILURE,wrapper)
                    ld.postValue(map)
                }
            }
        })
    }

    /**
     * queries marvel api with provided parameters and posts data.
     */
    fun utilLoadMarvelApi(marvelapi: MarvelService, mMarvelLiveData: MutableLiveData<Response<JsonObject>>){
        val ts = MarvelDroidUtility().genTimeStamp()
        val md5 = MarvelDroidUtility().getHash(ts)
        val limit = 25 /* default limit returns 20 */
        val call = marvelapi.getComics(
            ts
            , MARVEL_PUBLIC_API_KEY
            , md5
            , limit.toString()
        )
        call.enqueue(object : Callback<JsonObject> {
            override fun onFailure(call: Call<JsonObject>, t: Throwable) {
                println(t.message)
            }
            override fun onResponse(call: Call<JsonObject>, response: Response<JsonObject>) {
                MarvelDroidUtility().apiCodeCheck(response.code())
                MarvelDroidUtility().apiMsgCheck(response.message())
                mMarvelLiveData.postValue(response)
            }
        })
    }

    /**
     * helper determines if the application configuration is set to dark mode mask.
     */
    fun checkInDarkMode(context: Context) : Boolean {
        val mode = context.resources.configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK
        return mode == Configuration.UI_MODE_NIGHT_YES
    }

    /**
     * writes credential flags to shared preferences.
     */
    fun writeCredentialsToCacheInSavedPreferences(activity: Activity, username: String, password: String){
        val sharedPref = activity.getPreferences(Context.MODE_PRIVATE)
        with(sharedPref.edit()){
            putString(FIELD_USERNAME,username)
            putString(FIELD_PASSWORD,password)
            this.commit()
        }
    }

    /**
     * reads credential flags from shared preferences.
     */
    fun readCredentialsFromCacheInSavedPreferences(activity: Activity): Pair<String,String>{
        val sharedPref = activity.getPreferences(Context.MODE_PRIVATE) ?: null
        val user = sharedPref?.getString(FIELD_USERNAME,"").toString()
        val pass = sharedPref?.getString(FIELD_PASSWORD,"").toString()
        return Pair(user,pass)
    }

    /**
     * clears credential flags in shared preferences.
     */
    fun clearCredentialsFromCacheInSavedPreferences(activity: Activity) {
        val sharedPref = activity.getPreferences(Context.MODE_PRIVATE)
        with(sharedPref.edit()){
            this.clear()
            this.commit()
        }
    }
}
