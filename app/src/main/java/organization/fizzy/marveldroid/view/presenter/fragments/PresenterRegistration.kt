/*
 * 2020 MarvelDroid , Attribution Images belong to Marvel Entertainment
 * Source Timestamps exist under: https://github.com/PabloRosas17
 * Written by Jose Pablo Rosas  <pablo.rosas.0170@gmail.com>, January 2020
 */

package organization.fizzy.marveldroid.view.presenter.fragments

import android.graphics.PorterDuff
import android.graphics.PorterDuffColorFilter
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.os.bundleOf
import androidx.navigation.findNavController
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import organization.fizzy.marveldroid.R
import organization.fizzy.marveldroid.controller.viewmodels.ViewModelRegistration
import organization.fizzy.marveldroid.model.AccountWrapper
import organization.fizzy.marveldroid.tools.constants.FirebaseAccountFields
import organization.fizzy.marveldroid.tools.constants.FirebaseDbConst.FIREBASE_ACCOUNT_USERNAME_TAG
import organization.fizzy.marveldroid.tools.util.MarvelDroidUtility
import organization.fizzy.marveldroid.view.screens.fragments.ViewRegistration
import java.util.regex.Pattern

/**
 * @author, evolandlupiz
 * @date, 5/17/2020
 * @property, MarvelDroid
 * @purpose, presenter on ui.
 */

/**
 * @desc presenter for registration.
 */
class PresenterRegistration(private var context: ViewRegistration) {

    /**
     * item touch view model
     */
    private val vm: ViewModelRegistration = context.mViewModel

    /**
     * inflater from context.
     * @return [LayoutInflater]
     */
    private val inflater = LayoutInflater.from(this.context.requireContext())

    /**
     * inflate view used in context with dialog.
     * @return [View]
     */
    private val view = inflater.inflate(R.layout.dialog_registration_signup, null, false)

    /**
     * action for login button.
     */
    fun firePresenterLoginBtn(){
        this.verifyCredentialsOnLogIn()
    }

    /**
     * verifies the current credentials in the tiet username and tiet password, then reads the username from cache.
     */
    private fun verifyCredentialsOnLogIn(){
        /* username, at least one lowercase, at least one uppercase, at least one number, alphabet, 5 to 13 characters,
         * password, at least one lowercase, at least one uppercase, at least one number, alphabet, 5 to infinite characters. */
        val uPattern = Pattern.compile("^((?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@#\$%!])([a-zA-Z0-9@#$%!]).{4,13})$")
        val pPattern = Pattern.compile("^((?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@#$%!])([a-zA-Z0-9@#$%!]).{4,})$")
        /* if fields are blank or fields are invalid, then toggle effect, else proceed with verification */
        val user = context.mBinding.btmRegTietUsername.editText?.text.toString()
        val pass = context.mBinding.btmRegTietPassword.editText?.text.toString()
        if(!uPattern.matcher(user).matches() || !pPattern.matcher(pass).matches() || user == "" || pass == ""){
            this.uiCreateFieldsEffect()
            this.incorrectDialog()
        } else {
            vm.verifyOnLogInAccount(FIREBASE_ACCOUNT_USERNAME_TAG,user)
        }
    }

    /**
     * action for signup button.
     */

    fun firePresenterSignupBtn() {
        this.verifyCredentialsOnSignUp()
    }

    /**
     * verifies the current credentials in the tiet username and tiet password, then reads the username from firebase.
     */
    private fun verifyCredentialsOnSignUp() {
        /* username, at least one lowercase, at least one uppercase, at least one number, alphabet, 5 to 13 characters,
         * password, at least one lowercase, at least one uppercase, at least one number, alphabet, 5 to infinite characters. */
        val uPattern = Pattern.compile("^((?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@#\$%!])([a-zA-Z0-9@#$%!]).{4,13})$")
        val pPattern = Pattern.compile("^((?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@#$%!])([a-zA-Z0-9@#$%!]).{4,})$")
        /* if fields do not match, for example, fields are blank or fields are invalid, then toggle effect
         * else proceed with registration and check if user already exists otherwise register */
        val user = context.mBinding.btmRegTietUsername.editText?.text.toString()
        val pass = context.mBinding.btmRegTietPassword.editText?.text.toString()
        if (!uPattern.matcher(user).matches() || !pPattern.matcher(pass).matches()) {
            this.uiCreateFieldsEffect()
            this.incorrectDialog()
        } else {
            vm.verifyOnSignUpAccount(FIREBASE_ACCOUNT_USERNAME_TAG,user)
        }
    }

    /**
     * dialog welcomes user that registered.
     */
    fun uiRegisteredMessage(): AlertDialog {
        if(view.parent != null){
            (view.parent as ViewGroup).removeView(view)
        }
        val builder = MaterialAlertDialogBuilder(this.context.requireContext(), R.style.MtrlTheme_MtrlDialog)
        builder.setView(view)
        builder.setTitle("MarvelDroid")
        builder.setMessage("Enjoy it,  it's free :)")
        builder.setPositiveButton("Accept") { diag, _ ->
            val account =
                AccountWrapper(
                    "",
                    "",
                    "",
                    "",
                    context.mBinding.btmRegTietPasswordNestedEt.text.toString(),
                    context.mBinding.btmRegTietUsernameNestedEt.text.toString()
                )
            vm.signupAccount(account)
            diag.dismiss()
        }
        builder.setNegativeButton("Cancel") { diag, _ ->
            diag.dismiss()
        }
        return builder.show()
    }

    /**
     * action for unlock button.
     */
    fun firePresenterUnlockBtn() {
        val pair = MarvelDroidUtility().readCredentialsFromCacheInSavedPreferences(this.context.requireActivity())
        if(pair.first.isNullOrBlank() || pair.second.isNullOrBlank()){
            val builder = MaterialAlertDialogBuilder(this.context.requireContext(), R.style.MtrlThemeLight_UnlockDialog)
            val inflater = LayoutInflater.from(this.context.requireContext())
            val view = inflater.inflate(R.layout.dialog_registration_unlock, null)
            builder.setView(view)
            builder.setTitle(R.string.app_name)
            builder.setPositiveButton("Understood") { diag, _ ->
                diag.dismiss()
                uiRemoveFieldsEffect()
            }
            this.context.mDialog = builder.show()
        } else {
            val mBundle = bundleOf(
                FirebaseAccountFields.FIELD_USERNAME to pair.first
                ,   FirebaseAccountFields.FIELD_PASSWORD to pair.second)
            view?.findNavController()?.navigate(R.id.nav_account,mBundle)
        }
    }

    /**
     * show ui field effects for the username field and the password field.
     */
    fun uiCreateFieldsEffect() {
        context.mBinding.btmRegTietUsernameNestedEt.background.colorFilter =
            PorterDuffColorFilter(context.resources.getColor(R.color.colorLightOnError, null), PorterDuff.Mode.SRC_IN)
        context.mBinding.btmRegTietPasswordNestedEt.background.colorFilter =
            PorterDuffColorFilter(context.resources.getColor(R.color.colorLightOnError, null), PorterDuff.Mode.SRC_IN)
    }

    /**
     * dialog that will memo the user credentials are incorrect.
     */
    fun incorrectDialog() {
        val builder = MaterialAlertDialogBuilder(this.context.requireContext(), R.style.MtrlThemeLight_IncorrectDialog)
        val inflater = LayoutInflater.from(this.context.requireContext())
        val view = inflater.inflate(R.layout.dialog_registration_incorrect, null)
        builder.setView(view)
        builder.setTitle(R.string.app_name)
        builder.setPositiveButton("Understood") { diag, _ ->
            diag.dismiss()
            uiRemoveFieldsEffect()
        }
        this.context.mDialog = builder.show()
    }

    /**
     * dialog that will memo the user terms of service.
     */
    private fun termsOfServiceDialog() {
        val builder = MaterialAlertDialogBuilder(this.context.requireContext(), R.style.MtrlThemeLight_TermsOfServiceDialog)
        val inflater = LayoutInflater.from(this.context.requireContext())
        val view = inflater.inflate(R.layout.dialog_terms_of_service, null)
        builder.setView(view)
        builder.setTitle(R.string.app_name)
        builder.setPositiveButton("Understood") { diag, _ ->
            diag.dismiss()
            uiRemoveFieldsEffect()
        }
        this.context.mDialog = builder.show()
    }

    /**
     * hide ui field effects for the username field and the password field.
     */
    private fun uiRemoveFieldsEffect() {
        context.mBinding.btmRegTietUsernameNestedEt.background.clearColorFilter()
        context.mBinding.btmRegTietPasswordNestedEt.background.clearColorFilter()
    }

    /**
     * action for backdrop button, will adjusts the backdrop configuration.
     */
    fun fireBackDrop(){
        val m60Guideline = context.mBinding.gdReg60Hor
        val m75Guideline = context.mBinding.gdReg75Hor
        val mCard = context.mBinding.regBtmSheet
        val mCardParams = mCard.layoutParams as ConstraintLayout.LayoutParams
        when (mCardParams.topToTop) {
            m60Guideline.id -> {
                mCardParams.topToTop = m75Guideline.id
            }
            m75Guideline.id -> {
                mCardParams.topToTop = m60Guideline.id
            }
            else -> {
                mCardParams.topToTop = m60Guideline.id
            }
        }
        mCard.layoutParams = mCardParams
    }

    /**
     * action for terms of service button, will displays the terms of service dialog.
     */
    fun fireTermsOfServiceDialog(){
        this.termsOfServiceDialog()
    }
}