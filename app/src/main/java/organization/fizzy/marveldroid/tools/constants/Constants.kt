/*
 * 2020 MarvelDroid , Attribution Images belong to Marvel Entertainment
 * Source Timestamps exist under: https://github.com/PabloRosas17
 * Written by Jose Pablo Rosas  <pablo.rosas.0170@gmail.com>, January 2020
 */

package organization.fizzy.marveldroid.tools.constants

/**
 * @author, evolandlupiz
 * @date, 5/10/2020
 * @property, MarvelDroid
 * @purpose, Define consistent constants.
 */

/**
 * constants for the application scope.
 */
object ApplicationCns {
    const val SAFE_SIGNAL_COMMON_MODULE = "CommonCore.SHARED_TEST_RESOURCE"
}

/**
 * @desc Definitions that represent FireBase.
 */
object FirebaseDbConst {
    const val FIREBASE_RETROFIT_MARVEL_ENDPOINT = "marvel-accounts.json"
    const val FIREBASE_ACCOUNT_MARVEL_TYPE_TAG = "marvel-accounts"
    const val FIREBASE_ACCOUNT_KEY_TAG = "mKey"
    const val FIREBASE_ACCOUNT_ID_TAG = "mId"
    const val FIREBASE_ACCOUNT_PASSWORD_TAG = "mPassWord"
    const val FIREBASE_ACCOUNT_USERNAME_TAG = "mUserName"
    const val FIREBASE_ACCOUNT_FACEBOOK_TAG = "mFacebook"
    const val FIREBASE_ACCOUNT_GOOGLE_TAG = "mGoogle"
    const val QUERY_CLAUSE_ORDERBY = "orderBy"
    const val QUERY_CLAUSE_STARTAT = "startAt"
    const val QUERY_CLAUSE_ENDAT = "endAt"
}

/**
 * @desc Definitions that represent FireBase.
 */
object FirebaseAccountFields {
    const val FIELD_KEY: String = "key"
    const val FIELD_FACEBOOK: String = "facebook"
    const val FIELD_GOOGLE: String = "google"
    const val FIELD_ID: String = "id"
    const val FIELD_USERNAME: String = "username"
    const val FIELD_PASSWORD: String = "password"
}

/**
 *@desc Definitions that represent Firebase callbacks.
 */
object FirebaseStatusConst {
    const val STATUS_LOGIN_SUCCESS = 0
    const val STATUS_LOGIN_FAILURE = 1
    const val STATUS_SIGNUP_SUCCESS = 2
    const val STATUS_SIGNUP_FAILURE = 3
    const val STATUS_SIGNON_SUCCESS = 4
    const val STATUS_SIGNON_FAILURE = 5
    const val STATUS_UPDATE_SUCCESS = 6
    const val STATUS_UPDATE_FAILURE = 7
}

/**
 *@desc Definitions that represent Settings.
 */
object Settings {
    const val SETTING_THEME_MODE = "Theme Mode"
    const val SETTING_ACCOUNT_NAME = "Account Name"
    const val SETTING_ACCOUNT_PASSWORD = "Account Password"
    const val SETTING_FEATURE_REQUEST = "Feature Request"
    const val SETTING_TERMS_OF_SERVICE = "Terms Of Service"
    const val SETTING_FEEDBACK = "Feedback"
}