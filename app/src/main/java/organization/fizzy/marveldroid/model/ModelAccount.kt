/*
 * 2020 MarvelDroid , Attribution Images belong to Marvel Entertainment
 * Source Timestamps exist under: https://github.com/PabloRosas17
 * Written by Jose Pablo Rosas  <pablo.rosas.0170@gmail.com>, January 2020
 */

package organization.fizzy.marveldroid.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import organization.fizzy.marveldroid.tools.constants.FirebaseDbConst.FIREBASE_ACCOUNT_FACEBOOK_TAG
import organization.fizzy.marveldroid.tools.constants.FirebaseDbConst.FIREBASE_ACCOUNT_GOOGLE_TAG
import organization.fizzy.marveldroid.tools.constants.FirebaseDbConst.FIREBASE_ACCOUNT_ID_TAG
import organization.fizzy.marveldroid.tools.constants.FirebaseDbConst.FIREBASE_ACCOUNT_KEY_TAG
import organization.fizzy.marveldroid.tools.constants.FirebaseDbConst.FIREBASE_ACCOUNT_PASSWORD_TAG
import organization.fizzy.marveldroid.tools.constants.FirebaseDbConst.FIREBASE_ACCOUNT_USERNAME_TAG

/**
 * @author, evolandlupiz
 * @date, 5/17/2020
 * @property, MarvelDroid
 * @purpose, account definition.
 */

/**
 * @desc definition for a firebase object, wraps key and json value, var types so internal entities can use this.
 */
@JsonClass(generateAdapter = true)
data class KeyWrapper(
    @field: Json(name = FIREBASE_ACCOUNT_KEY_TAG) var mKey: String? = "" /* will be added dynamically based on response */
)

/**
 * @desc definition for a firebase a unique account, var types so internal entities can use this.
 */
@JsonClass(generateAdapter = true)
data class AccountWrapper(
    @field: Json(name = FIREBASE_ACCOUNT_KEY_TAG) var mKey: String? = "" /* will be added dynamically based on response */
    , @field: Json(name = FIREBASE_ACCOUNT_FACEBOOK_TAG) var mFacebook: String? =  ""
    , @field: Json(name = FIREBASE_ACCOUNT_GOOGLE_TAG) var mGoogle: String? =  ""
    , @field: Json(name = FIREBASE_ACCOUNT_ID_TAG) var mId: String? = ""
    , @field: Json(name = FIREBASE_ACCOUNT_PASSWORD_TAG) var mPassWord: String = ""
    , @field: Json(name = FIREBASE_ACCOUNT_USERNAME_TAG) var mUserName: String = ""
)