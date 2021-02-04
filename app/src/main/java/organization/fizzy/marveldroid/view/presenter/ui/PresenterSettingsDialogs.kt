/*
 * 2020 MarvelDroid , Attribution Images belong to Marvel Entertainment
 * Source Timestamps exist under: https://github.com/PabloRosas17
 * Written by Jose Pablo Rosas  <pablo.rosas.0170@gmail.com>, January 2020
 */

package organization.fizzy.marveldroid.view.presenter.ui

import android.content.Context
import androidx.appcompat.app.AppCompatDelegate
import organization.fizzy.marveldroid.databinding.DialogSettingsThemeModeBinding
import organization.fizzy.marveldroid.tools.util.MarvelDroidUtility
import organization.fizzy.marveldroid.view.screens.fragments.ViewSettings

/**
 * @author, evolandlupiz
 * @date, 8/3/2020
 * @property, MarvelDroid
 * @purpose, presenter on ui.
 */

/**
 * @desc presenter for settings dialogs.
 */
class PresenterSettingsDialogs(private val vs: ViewSettings, private val binding: DialogSettingsThemeModeBinding?) {

    /**
     * launches dark mode.
     */
    fun fireDialogDarkThemePresenter(){
        println("fireDialogDarkThemePresenter")
        binding?.themeModeLightvalRadioBtn?.isChecked = false
        mode(vs.requireContext(),true)
    }

    /**
     * launches light mode.
     */
    fun fireDialogLightThemePresenter(){
        println("fireDialogLightThemePresenter")
        binding?.themeModeDarkvalRadioBtn?.isChecked = false
        mode(vs.requireContext(),false)
    }

    /**
     * mode determines which mode the application is in and sets the respective mode.
     */
    private fun mode(context: Context, mode: Boolean) {
        println("mode checkInDarkMode")
        if(MarvelDroidUtility().checkInDarkMode(context)) {
            if(!mode){
                println("mode MODE_NIGHT_NO")
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            }
        } else {
            if(mode){
                println("mode MODE_NIGHT_YES")
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            }
        }
    }
}