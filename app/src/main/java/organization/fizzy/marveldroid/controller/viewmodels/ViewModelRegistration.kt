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
import organization.fizzy.marveldroid.model.AccountWrapper
import organization.fizzy.marveldroid.tools.services.api.FirebaseService
import organization.fizzy.marveldroid.tools.util.MarvelDroidUtility
import javax.inject.Inject

/**
 * @author, evolandlupiz
 * @date, 5/12/2020
 * @property, MarvelDroid
 * @purpose, view model for registration.
 */

/**
 * @desc defines view model for registration.
 */
class ViewModelRegistration @Inject constructor() : ViewModel() {

    @Inject lateinit var firebaseapi : FirebaseService

    /**
     * @return live data which is on-demand invoked and observes on firebase calls.
     */
    val mFirebaseLive: MutableLiveData<HashMap<Int, AccountWrapper?>> by lazy {
        MutableLiveData<HashMap<Int, AccountWrapper?>>()
    }

    /**
     * after user initiates a button click on sign in then read an account from firebase database using the rest api.
     * @param dexter is a delimiter to use with the query clauses: ORDER BY, START AT, END AT.
     */
    fun verifyOnSignUpAccount(orderBy: String, dexter: String) {
        viewModelScope.launch {
            MarvelDroidUtility().utilReadOnSignUpMarvelActs(firebaseapi, orderBy, dexter, dexter, mFirebaseLive)
        }
    }

    /**
     * after user initiates a button click on log in then read an account from firebase database using the rest api.
     * @param dexter is a delimiter to use with the query clauses: ORDER BY, START AT, END AT.
     */
    fun verifyOnLogInAccount(orderBy: String, dexter: String) {
        viewModelScope.launch {
            MarvelDroidUtility().utilReadOnLogInMarvelActs(firebaseapi, orderBy, dexter, dexter, mFirebaseLive)
        }
    }

    /**
     * update the account from firebase database using the rest api.
     */
    fun updateAccount(account: AccountWrapper) {
        viewModelScope.launch {
            MarvelDroidUtility().utilWriteOnUpdateMarvelActs(account, firebaseapi, mFirebaseLive)
        }
    }

    /**
     * after user initiates a button click on sign up then write an account to the firebase database using the rest api.
     */
    fun signupAccount(account: AccountWrapper) {
        viewModelScope.launch {
            MarvelDroidUtility().utilWriteOnSignUpMarvelActs(account, firebaseapi, mFirebaseLive)
        }
    }
}