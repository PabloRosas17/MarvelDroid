/*
 * 2020 MarvelDroid , Attribution Images belong to Marvel Entertainment
 * Source Timestamps exist under: https://github.com/PabloRosas17
 * Written by Jose Pablo Rosas  <pablo.rosas.0170@gmail.com>, January 2020
 */

package organization.fizzy.marveldroid.tools.util

import organization.fizzy.marveldroid.model.AccountWrapper

/**
 * @author, evolandlupiz
 * @date, 7/16/2020
 * @property, MarvelDroid
 * @purpose, listener definition for classes that require callback capability.
 */

/**
 * @desc defines firebase broadcast if.
 */
interface FirebaseRegistrationIf<JsonObject> {

    /**
     * handler after sing on operation.
     * @param status callback state.
     * @param data account information.
     */
    fun onSignOnFx(status: Boolean, data: AccountWrapper?)

    /**
     * Account username and password will be authenticated
     * , if successful then continue with the registration dialog
     * , otherwise continue with the error dialog.
     * @param status callback state.
     * @param key unique identifier from the account.
     * @param data account information.
     */
    fun onSignUpFx(status: Boolean, key: String?, data: AccountWrapper?)

    /**
     * Account username and password will be authenticated
     * , if successful then continue to the broadcast view for this account
     * , otherwise continue with the error dialog.
     * @param status callback state.
     * @param key unique identifier from the account.
     * @param data account information.
     */
    fun onLogInFx(status: Boolean, key: String?, data: AccountWrapper?)
}