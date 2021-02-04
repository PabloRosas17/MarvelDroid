/*
 * 2020 MarvelDroid , Attribution Images belong to Marvel Entertainment
 * Source Timestamps exist under: https://github.com/PabloRosas17
 * Written by Jose Pablo Rosas  <pablo.rosas.0170@gmail.com>, January 2020
 */

package organization.fizzy.marveldroid.model

import organization.fizzy.marveldroid.tools.constants.Settings.SETTING_ACCOUNT_NAME
import organization.fizzy.marveldroid.tools.constants.Settings.SETTING_ACCOUNT_PASSWORD
import organization.fizzy.marveldroid.tools.constants.Settings.SETTING_THEME_MODE
import organization.fizzy.marveldroid.tools.constants.Settings.SETTING_FEATURE_REQUEST
import organization.fizzy.marveldroid.tools.constants.Settings.SETTING_FEEDBACK
import organization.fizzy.marveldroid.tools.constants.Settings.SETTING_TERMS_OF_SERVICE

/**
 * @author, evolandlupiz
 * @date, 3/26/2020
 * @property, MarvelDroid
 * @purpose, defines settings model.
 */

/**
 * @desc model for settings.
 */
class ModelSettings {

    /**
     * @return settings model containing [String].
     */
    var mSettings: ArrayList<String> = mutableListOf<String>() as ArrayList<String>

    /**
     * initialize settings data.
     */
    init {
        this.mSettings.add(SETTING_THEME_MODE)
        this.mSettings.add(SETTING_ACCOUNT_NAME)
        this.mSettings.add(SETTING_ACCOUNT_PASSWORD)
        this.mSettings.add(SETTING_FEATURE_REQUEST)
        this.mSettings.add(SETTING_FEEDBACK)
        this.mSettings.add(SETTING_TERMS_OF_SERVICE)
    }
}