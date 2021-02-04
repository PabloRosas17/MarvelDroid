/*
 * 2020 MarvelDroid , Attribution Images belong to Marvel Entertainment
 * Source Timestamps exist under: https://github.com/PabloRosas17
 * Written by Jose Pablo Rosas  <pablo.rosas.0170@gmail.com>, January 2020
 */

/*
 * 2020 MarvelDroid , Attribution Images belong to Marvel Entertainment
 * Source Timestamps exist under: https://github.com/PabloRosas17
 * Written by Jose Pablo Rosas  <pablo.rosas.0170@gmail.com>, January 2020
 */

package organization.fizzy.marveldroid.view.presenter.fragments

import android.os.Bundle
import organization.fizzy.marveldroid.R
import organization.fizzy.marveldroid.tools.constants.FirebaseAccountFields.FIELD_PASSWORD
import organization.fizzy.marveldroid.tools.constants.FirebaseAccountFields.FIELD_USERNAME
import organization.fizzy.marveldroid.tools.util.LogOutIf
import organization.fizzy.marveldroid.view.screens.fragments.ViewAccount
import organization.fizzy.marveldroid.view.screens.fragments.ViewBookmarks
import organization.fizzy.marveldroid.view.screens.fragments.ViewSettings

/**
 * @author, evolandlupiz
 * @date, 5/17/2020
 * @property, MarvelDroid
 * @purpose, presenter on ui.
 */

/**
 * @desc presenter for account.
 */
class PresenterAccount(var va: ViewAccount): LogOutIf {

    /**
     * settings button action.
     */
    fun firePresenterSettingsBtn(){
        va.mBinding.accountTopTitleIcon.background = va.resources.getDrawable(R.drawable.baseline_settings_white_18dp,null)
        va.mBinding.accountTopTitleText.text = va.getString(R.string.account_settings_title)
        val mBundle = Bundle()
        mBundle.putString(FIELD_USERNAME,this.va.arguments?.getString(FIELD_USERNAME))
        mBundle.putString(FIELD_PASSWORD,this.va.arguments?.getString(FIELD_PASSWORD))
        va.requireActivity().supportFragmentManager.beginTransaction()
            .replace(R.id.default_container, ViewSettings()::class.java, mBundle).commit()
    }

    /**
     * bookmarks button action.
     */
    fun firePresenterBookmarksBtn(){
        va.mBinding.accountTopTitleIcon.background = va.resources.getDrawable(R.drawable.baseline_bookmarks_white_18dp,null)
        va.mBinding.accountTopTitleText.text = va.getString(R.string.account_bookmarks_title)
        val mBundle = Bundle()
        mBundle.putString(FIELD_USERNAME,this.va.arguments?.getString(FIELD_USERNAME))
        mBundle.putString(FIELD_PASSWORD,this.va.arguments?.getString(FIELD_PASSWORD))
       va.requireActivity().supportFragmentManager.beginTransaction()
            .replace(R.id.default_container, ViewBookmarks()::class.java, mBundle).commit()
    }

    /**
     * logout button action.
     */
    fun firePresenterLogoutBtn(){
        va.mBinding.accountTopTitleIcon.background = va.resources.getDrawable(R.drawable.baseline_link_off_white_18dp,null)
        va.mBinding.accountTopTitleText.text = va.getString(R.string.account_logout_title)
        this.fireLogOut()
    }

    /**
     * logout helper.
     */
    override fun fireLogOut() {
        va.mNavCntrl?.popBackStack(R.id.nav_registration,true)
    }
}