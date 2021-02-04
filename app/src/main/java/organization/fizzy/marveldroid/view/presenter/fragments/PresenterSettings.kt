/*
 * 2020 MarvelDroid , Attribution Images belong to Marvel Entertainment
 * Source Timestamps exist under: https://github.com/PabloRosas17
 * Written by Jose Pablo Rosas  <pablo.rosas.0170@gmail.com>, January 2020
 */

package organization.fizzy.marveldroid.view.presenter.fragments

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import organization.fizzy.marveldroid.R
import organization.fizzy.marveldroid.databinding.*
import organization.fizzy.marveldroid.model.AccountWrapper
import organization.fizzy.marveldroid.tools.constants.FirebaseAccountFields.FIELD_FACEBOOK
import organization.fizzy.marveldroid.tools.constants.FirebaseAccountFields.FIELD_GOOGLE
import organization.fizzy.marveldroid.tools.constants.FirebaseAccountFields.FIELD_ID
import organization.fizzy.marveldroid.tools.constants.FirebaseAccountFields.FIELD_PASSWORD
import organization.fizzy.marveldroid.tools.constants.FirebaseAccountFields.FIELD_USERNAME
import organization.fizzy.marveldroid.tools.util.MarvelDroidUtility
import organization.fizzy.marveldroid.view.presenter.ui.PresenterSettingsDialogs
import organization.fizzy.marveldroid.view.screens.fragments.ViewSettings
import java.util.regex.Pattern

/**
 * @author, evolandlupiz
 * @date, 5/17/2020
 * @property, MarvelDroid
 * @purpose, presenter on ui.
 */

/**
 * @desc presenter for settings configuration.
 */
class PresenterSettings (private var vs: ViewSettings) {


    /**
     * dialog to set theme mode and confirm flag saved to shared preferences for persistence.
     */
    fun fireThemeModeSettings(){
        dialogForThemeMode()
    }


    /**
     * @return [MaterialAlertDialogBuilder] builder.
     */
    private lateinit var builder: MaterialAlertDialogBuilder

    /**
     * @return [View] view.
     */
    private lateinit var view: View

    /**
     * @return [ViewDataBinding] view.
     */
    private lateinit var binding: ViewDataBinding

    /**
     * generic setup for loading dialogs.
     */
    private fun setupDialogUiLoaders(theme: Int, layout: Int) {
        this.builder = MaterialAlertDialogBuilder(vs.requireContext(), theme)
        val inflater = LayoutInflater.from(vs.requireContext())
        this.view = inflater.inflate(layout, null)
    }

    /**
     * generic setup for finishing dialogs.
     */
    private fun setupDialogsUiFinishers(view: View, app: Int) {
        builder.setView(view)
        builder.setTitle(app)
    }

    /**
     * defines dialog for theme mode.
     */
    private fun dialogForThemeMode(){
        setupDialogUiLoaders(R.style.MtrlThemeLight_ThemeModeDialog, R.layout.dialog_settings_theme_mode)
        this.binding = DataBindingUtil.bind<DialogSettingsThemeModeBinding>(view)?.apply {
            this.mPresenter = PresenterSettingsDialogs(vs,this)
            this.executePendingBindings()
            this.themeModeCurvalTv.text = if(MarvelDroidUtility().checkInDarkMode(vs.requireContext())){
                vs.getString(R.string.dark_mode)
            } else {
                vs.getString(R.string.light_mode)
            }
        }!!
        setupDialogsUiFinishers(view, R.string.app_name)
        builder.setPositiveButton(vs.getString(R.string.dialog_done_label)) { diag, _ ->
            diag.dismiss()
        }
        this.vs.mDialog = builder.show()
    }

    /**
     * dialog to set account name.
     */
    fun fireAccountNameSettings() {
        dialogForAccountUsername()
    }

    /**
     * defines dialog for account username mode.
     */
    @SuppressLint("UseRequireInsteadOfGet") //same result,vs.arguments?.getString(FIELD_X)!!
    private fun dialogForAccountUsername(){
        val builder = MaterialAlertDialogBuilder(vs.requireContext(), R.style.MtrlThemeLight_AccountUsernameDialog)
        val inflater = LayoutInflater.from(vs.requireContext())
        val view = inflater.inflate(R.layout.dialog_settings_account_username, builder.create().findViewById(R.id.root_layout_dialog_account_username), false)
        val binding = DataBindingUtil.bind<DialogSettingsAccountUsernameBinding>(view)
        binding?.executePendingBindings()
        binding?.usernameCurvalTv?.text = if(vs.arguments?.getString(FIELD_USERNAME) == null){
            "null"
        } else {
            vs.arguments?.getString(FIELD_USERNAME)
        }
        setupDialogsUiFinishers(view, R.string.app_name)
        builder.setPositiveButton(vs.getString(R.string.dialog_update_label)) { diag, _ ->
            this.vs.mViewModel.updateAccount(
                AccountWrapper(
                    vs.arguments?.getString(FIELD_ID)
                    , vs.arguments?.getString(FIELD_FACEBOOK)
                    , vs.arguments?.getString(FIELD_GOOGLE)
                    , vs.arguments?.getString(FIELD_ID)
                    , vs.arguments?.getString(FIELD_PASSWORD)!!
                    , binding?.usernameNewvalNestedEt?.text.toString()
                )
            )
            vs.arguments?.putString(FIELD_USERNAME,binding?.usernameNewvalNestedEt?.text.toString())
            diag.dismiss()
        }
        builder.setNegativeButton(vs.getString(R.string.dialog_cancel_label)) { dialog, _ ->
            dialog.cancel()
        }
        this.vs.mDialog = builder.show()
        if(binding?.usernameNewvalNestedEt?.text?.length!! == 0){ //0 represents the EMPTY_TEXT_STATE
            vs.mDialog!!.getButton(AlertDialog.BUTTON_POSITIVE).isEnabled = false
            vs.mDialog!!.getButton(AlertDialog.BUTTON_POSITIVE).setBackgroundColor(vs.resources.getColor(R.color.colorDisabled,null))
        }
        binding.usernameNewvalNestedEt.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                /* username, at least one lowercase, at least one uppercase, at least one number, alphabet, 5 to 13 characters */
                val uPattern = Pattern.compile("^((?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@#\$%!])([a-zA-Z0-9@#$%!]).{4,13})$")
                if(s?.length!! in 5..13){ //5 represents the USERNAME_VALID_LENGTH
                    if(uPattern.matcher(s.toString()).matches()){
                        vs.mDialog!!.getButton(AlertDialog.BUTTON_POSITIVE).isEnabled = true
                        vs.mDialog!!.getButton(AlertDialog.BUTTON_POSITIVE).setBackgroundColor(vs.resources.getColor(R.color.colorLightPrimary,null))
                    }
                } else {
                    vs.mDialog!!.getButton(AlertDialog.BUTTON_POSITIVE).isEnabled = false
                    vs.mDialog!!.getButton(AlertDialog.BUTTON_POSITIVE).setBackgroundColor(vs.resources.getColor(R.color.colorDisabled,null))
                }
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                /* we're only interested in the text after it changes. */
            }
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                /* we're only interested in the text after it changes. */
            }
        })
    }

    /**
     * dialog to set account password.
     */
    fun fireAccountPasswordSettings() {
        dialogForAccountPassword()
    }

    /**
     * defines dialog for account password mode.
     */
    @SuppressLint("UseRequireInsteadOfGet") //same result,vs.arguments?.getString(FIELD_X)!!
    private fun dialogForAccountPassword(){
        setupDialogUiLoaders(R.style.MtrlThemeLight_AccountPasswordDialog, R.layout.dialog_settings_account_password)
        val binding = DataBindingUtil.bind<DialogSettingsAccountPasswordBinding>(view)
        binding?.executePendingBindings()
        binding?.passwordCurvalTv?.text = if(vs.arguments?.getString(FIELD_USERNAME) == null){
            "null"
        } else {
            vs.arguments?.getString(FIELD_PASSWORD)
        }
        setupDialogsUiFinishers(view, R.string.app_name)
        builder.setPositiveButton(vs.getString(R.string.dialog_update_label)) { diag, _ ->
            this.vs.mViewModel.updateAccount(
                AccountWrapper(
                    vs.arguments?.getString(FIELD_ID)
                    , vs.arguments?.getString(FIELD_FACEBOOK)
                    , vs.arguments?.getString(FIELD_GOOGLE)
                    , vs.arguments?.getString(FIELD_ID)
                    , vs.arguments?.getString(FIELD_PASSWORD)!!
                    , binding?.passwordNewvalNestedEt?.text.toString()
                )
            )
            vs.arguments?.putString(FIELD_PASSWORD,binding?.passwordNewvalNestedEt?.text.toString())
            diag.dismiss()
        }
        builder.setNegativeButton(vs.getString(R.string.dialog_cancel_label)) { dialog, _ ->
            dialog.cancel()
        }
        this.vs.mDialog = builder.show()
        vs.mDialog!!.getButton(AlertDialog.BUTTON_POSITIVE).isEnabled = false
        if(binding?.passwordNewvalNestedEt?.text?.length!! == 0){ //0 represents the EMPTY_TEXT_STATE
            vs.mDialog!!.getButton(AlertDialog.BUTTON_POSITIVE).isEnabled = false
            vs.mDialog!!.getButton(AlertDialog.BUTTON_POSITIVE).setBackgroundColor(vs.resources.getColor(R.color.colorDisabled,null))
        }
        binding.passwordNewvalNestedEt.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                /* password, at least one lowercase, at least one uppercase, at least one number, alphabet, 5 to infinite characters */
                val pPattern = Pattern.compile("^((?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@#$%!])([a-zA-Z0-9@#$%!]).{4,})$")
                if(s?.length!! in 5..13){ //5 represents the PASSWORD_VALID_LENGTH
                    if(pPattern.matcher(s.toString()).matches()){
                        vs.mDialog!!.getButton(AlertDialog.BUTTON_POSITIVE).isEnabled = true
                        vs.mDialog!!.getButton(AlertDialog.BUTTON_POSITIVE).setBackgroundColor(vs.resources.getColor(R.color.colorLightPrimary,null))
                    }
                } else {
                    vs.mDialog!!.getButton(AlertDialog.BUTTON_POSITIVE).isEnabled = false
                    vs.mDialog!!.getButton(AlertDialog.BUTTON_POSITIVE).setBackgroundColor(vs.resources.getColor(R.color.colorDisabled,null))
                }
            }
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                /* we're only interested in the text after it changes. */
            }
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                /* we're only interested in the text after it changes. */
            }
        })
    }

    /**
     * dialog to send feature request.
     */
    fun fireFeatureRequestSettings() {
        dialogForFeatureRequest()
    }

    /**
     * defines dialog for feature request mode.
     */
    private fun dialogForFeatureRequest(){
        setupDialogUiLoaders(R.style.MtrlThemeLight_FeatureRequestDialog, R.layout.dialog_settings_feature_request)
        val binding = DataBindingUtil.bind<DialogSettingsFeatureRequestBinding>(view)
        binding?.executePendingBindings()
        setupDialogsUiFinishers(view, R.string.app_name)
        builder.setPositiveButton(vs.getString(R.string.dialog_send_label)) { diag, _ ->
            diag.dismiss()
        }
        builder.setNegativeButton(vs.getString(R.string.dialog_cancel_label)) { dialog, _ ->
            dialog.cancel()
        }
        this.vs.mDialog = builder.show()
        vs.mDialog!!.getButton(AlertDialog.BUTTON_POSITIVE).isEnabled = false
        if(binding?.featureRequestNestedEt?.text?.length!! == 0){ //0 represents the EMPTY_TEXT_STATE
            vs.mDialog!!.getButton(AlertDialog.BUTTON_POSITIVE).isEnabled = false
            vs.mDialog!!.getButton(AlertDialog.BUTTON_POSITIVE).setBackgroundColor(vs.resources.getColor(R.color.colorDisabled,null))
        }
        binding.featureRequestNestedEt.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                if(s?.length!! in 10..500){ //represents the length a feature request should have
                    vs.mDialog!!.getButton(AlertDialog.BUTTON_POSITIVE).isEnabled = true
                    vs.mDialog!!.getButton(AlertDialog.BUTTON_POSITIVE).setBackgroundColor(vs.resources.getColor(R.color.colorLightPrimary,null))
                } else {
                    vs.mDialog!!.getButton(AlertDialog.BUTTON_POSITIVE).isEnabled = false
                    vs.mDialog!!.getButton(AlertDialog.BUTTON_POSITIVE).setBackgroundColor(vs.resources.getColor(R.color.colorDisabled,null))
                }
            }
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                /* we're only interested in the text after it changes. */
            }
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                /* we're only interested in the text after it changes. */
            }
        })
    }

    /**
     * dialog to view terms of service.
     */
    fun fireTermsOfServiceSettings() {
        dialogForTermsOfService()
    }

    /**
     * defines dialog for terms of service mode.
     */
    private fun dialogForTermsOfService(){
        setupDialogUiLoaders(R.style.MtrlThemeLight_TermsOfServiceDialog, R.layout.dialog_terms_of_service)
        setupDialogsUiFinishers(view, R.string.app_name)
        builder.setPositiveButton(vs.getString(R.string.dialog_understood_label)) { diag, _ ->
            diag.dismiss()
        }
        this.vs.mDialog = builder.show()
    }

    /**
     * dialog to set send feedback.
     */
    fun fireFeedbackSettings() {
        dialogForFeedbackSettings()
    }

    /**
     * defines dialog for feedback mode.
     */
    private fun dialogForFeedbackSettings(){
        setupDialogUiLoaders(R.style.MtrlThemeLight_FeedbackDialog, R.layout.dialog_settings_feedback)
        val binding = DataBindingUtil.bind<DialogSettingsFeedbackBinding>(view)
        binding?.executePendingBindings()
        setupDialogsUiFinishers(view, R.string.app_name)
        builder.setPositiveButton(vs.getString(R.string.dialog_send_label)) { diag, _ ->
            diag.dismiss()
        }
        builder.setNegativeButton(vs.getString(R.string.dialog_cancel_label)) { dialog, _ ->
            dialog.cancel()
        }
        this.vs.mDialog = builder.show()
        vs.mDialog!!.getButton(AlertDialog.BUTTON_POSITIVE).isEnabled = false
        if(binding?.feedbackNestedEt?.text?.length!! == 0){ //represents the length a feature request should have
            vs.mDialog!!.getButton(AlertDialog.BUTTON_POSITIVE).isEnabled = false
            vs.mDialog!!.getButton(AlertDialog.BUTTON_POSITIVE).setBackgroundColor(vs.resources.getColor(R.color.colorDisabled,null))
        }
        binding.feedbackNestedEt.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                if(s?.length!! in 10..500){ //represents the length a feature request should have
                    vs.mDialog!!.getButton(AlertDialog.BUTTON_POSITIVE).isEnabled = true
                    vs.mDialog!!.getButton(AlertDialog.BUTTON_POSITIVE).setBackgroundColor(vs.resources.getColor(R.color.colorLightPrimary,null))
                } else {
                    vs.mDialog!!.getButton(AlertDialog.BUTTON_POSITIVE).isEnabled = false
                    vs.mDialog!!.getButton(AlertDialog.BUTTON_POSITIVE).setBackgroundColor(vs.resources.getColor(R.color.colorDisabled,null))
                }
            }
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                /* we're only interested in the text after it changes. */
            }
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                /* we're only interested in the text after it changes. */
            }
        })
    }
}