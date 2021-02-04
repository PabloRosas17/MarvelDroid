/*
 * 2020 MarvelDroid , Attribution Images belong to Marvel Entertainment
 * Source Timestamps exist under: https://github.com/PabloRosas17
 * Written by Jose Pablo Rosas  <pablo.rosas.0170@gmail.com>, January 2020
 */

package organization.fizzy.marveldroid.controller.viewmodels

import android.app.Application
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.room.Room
import com.google.gson.JsonObject
import kotlinx.coroutines.launch
import organization.fizzy.marveldroid.R
import organization.fizzy.marveldroid.model.ModelComics
import organization.fizzy.marveldroid.tools.services.api.MarvelService
import organization.fizzy.marveldroid.tools.services.database.MarvelDroidDB
import organization.fizzy.marveldroid.tools.util.MarvelDroidUtility
import retrofit2.Response
import javax.inject.Inject

/**
 * @author, evolandlupiz
 * @date, 5/12/2020
 * @property, MarvelDroid
 * @purpose, view model for comics.
 */

/**
 * @desc defines view model for comics.
 * @param application reference required for database operations.
 */
class ViewModelComics @Inject constructor(val application: Application) : ViewModel() {

    @Inject lateinit var marvelapi : MarvelService

    /**
     * @return singleton DB.
     */
    companion object DB {
        /**
         * @return singleton reference to room database.
         */
        fun initDB(application: Application) : MarvelDroidDB {
            return Room.databaseBuilder(application
            , MarvelDroidDB::class.java
            , application.getString(R.string.db_bookmark_table)).build()
        }
    }

    /**
     * @return [MarvelDroidDB] reference.
     */
    val mMarvelDroidDB = DB.initDB(application)

    /**
     * @return model that represents the comics.
     */
    val mModelComics = ModelComics()

    /**
     * @return live data agent to share a response json object.
     */
    val mMarvelLiveData: MutableLiveData<Response<JsonObject>> by lazy {
        MutableLiveData<Response<JsonObject>>()
    }

    /**
     * @return live data agent to share a string, comics title.
     */
    val mDescriptionTitleLiveData: MutableLiveData<String> by lazy {
        MutableLiveData<String>()
    }

    /**
     * @return live data agent to share a string, comics information.
     */
    val mDescriptionInformationLiveData: MutableLiveData<String> by lazy {
        MutableLiveData<String>()
    }

    /**
     * @return live data agent to share a string, comics pages.
     */
    val mDescriptionPagesLiveData: MutableLiveData<String> by lazy {
        MutableLiveData<String>()
    }

    /**
     * @return live data agent to share a string, comics price.
     */
    val mDescriptionPriceLiveData: MutableLiveData<String> by lazy {
        MutableLiveData<String>()
    }

    /**
     * @return live data agent to share a string, comics issue number.
     */
    val mDescriptionIssueNoLiveData: MutableLiveData<String> by lazy {
        MutableLiveData<String>()
    }

    /**
     * loads marvel api through coroutine scope.
     */
    fun loadMarvelApi() {
        viewModelScope.launch {
            MarvelDroidUtility().utilLoadMarvelApi(marvelapi,mMarvelLiveData)
        }
    }
}