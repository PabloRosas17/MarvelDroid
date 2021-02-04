/*
 * 2020 MarvelDroid , Attribution Images belong to Marvel Entertainment
 * Source Timestamps exist under: https://github.com/PabloRosas17
 * Written by Jose Pablo Rosas  <pablo.rosas.0170@gmail.com>, January 2020
 */

package organization.fizzy.marveldroid.controller.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import organization.fizzy.marveldroid.model.ModelSettings
import organization.fizzy.marveldroid.model.AccountWrapper
import organization.fizzy.marveldroid.tools.services.api.FirebaseService
import organization.fizzy.marveldroid.tools.util.MarvelDroidUtility
import javax.inject.Inject

/**
 * @author, evolandlupiz
 * @date, 5/12/2020
 * @property, MarvelDroid
 * @purpose, view model for settings.
 */

/**
 * @desc defines view model for settings.
 */
class ViewModelSettings @Inject constructor() : ViewModel() {

    @Inject lateinit var firebaseapi : FirebaseService

    /**
     * @return live data which is on-demand invoked and observes on firebase calls.
     */
    private val mFirebaseLive: MutableLiveData<HashMap<Int, AccountWrapper?>> by lazy {
        MutableLiveData<HashMap<Int, AccountWrapper?>>()
    }

    /**
     * @return model that represents the settings.
     */
    val mModelSettings = ModelSettings()

    /**
     * update the account from firebase database using the rest api.
     */
    fun updateAccount(account: AccountWrapper) {
        viewModelScope.launch {
            MarvelDroidUtility().utilWriteOnUpdateMarvelActs(account, firebaseapi, mFirebaseLive)
        }
    }
}