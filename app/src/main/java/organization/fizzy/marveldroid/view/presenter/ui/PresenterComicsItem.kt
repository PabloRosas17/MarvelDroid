/*
 * 2020 MarvelDroid , Attribution Images belong to Marvel Entertainment
 * Source Timestamps exist under: https://github.com/PabloRosas17
 * Written by Jose Pablo Rosas  <pablo.rosas.0170@gmail.com>, January 2020
 */

package organization.fizzy.marveldroid.view.presenter.ui

import android.view.View
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import organization.fizzy.marveldroid.controller.viewmodels.ViewModelComics
import organization.fizzy.marveldroid.tools.services.database.BookmarkedItems
import organization.fizzy.marveldroid.view.screens.fragments.ViewComics

/**
 * @author, evolandlupiz
 * @date, 5/11/2020
 * @property, MarvelDroid
 * @purpose, presenter on ui.
 */

/**
 * @desc presenter for comics item.
 */
class PresenterComicsItem(private val context: ViewComics, private val itemView: View, private val position: Int) {

    /**
     * item touch view model
     */
    private val vm: ViewModelComics = context.mViewModel

    /**
     * item touch listener
     */
    fun fireItemListener(){
        vm.mDescriptionTitleLiveData.postValue(vm.mModelComics.mComics[position].ctitle)
        vm.mDescriptionInformationLiveData.postValue(vm.mModelComics.mComics[position].cdescription)
        vm.mDescriptionPagesLiveData.postValue("${vm.mModelComics.mComics[position].cpagecount} pages")
        vm.mDescriptionPriceLiveData.postValue("$${vm.mModelComics.mComics[position].cprice}")
        vm.mDescriptionIssueNoLiveData.postValue("IssueNo:#${vm.mModelComics.mComics[position].cissueno}")
    }

    /**
     * adds an item to the database.
     */
    fun supply(data: BookmarkedItems) {
        vm.viewModelScope.launch {
            vm.mMarvelDroidDB.mDaoBookmark().insert(data)
        }
    }
}