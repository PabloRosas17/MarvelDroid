/*
 * 2020 MarvelDroid , Attribution Images belong to Marvel Entertainment
 * Source Timestamps exist under: https://github.com/PabloRosas17
 * Written by Jose Pablo Rosas  <pablo.rosas.0170@gmail.com>, January 2020
 */

package organization.fizzy.marveldroid.controller.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import androidx.constraintlayout.widget.ConstraintLayout
import com.google.android.material.textview.MaterialTextView
import organization.fizzy.marveldroid.R
import organization.fizzy.marveldroid.tools.constants.Settings.SETTING_ACCOUNT_NAME
import organization.fizzy.marveldroid.tools.constants.Settings.SETTING_ACCOUNT_PASSWORD
import organization.fizzy.marveldroid.tools.constants.Settings.SETTING_THEME_MODE
import organization.fizzy.marveldroid.tools.constants.Settings.SETTING_FEATURE_REQUEST
import organization.fizzy.marveldroid.tools.constants.Settings.SETTING_FEEDBACK
import organization.fizzy.marveldroid.tools.constants.Settings.SETTING_TERMS_OF_SERVICE
import organization.fizzy.marveldroid.tools.util.MarvelDroidUtility
import organization.fizzy.marveldroid.view.screens.fragments.ViewSettings

/**
 * @author, evolandlupiz
 * @date, 5/17/2020
 * @property, MarvelDroid
 * @purpose, define the view settings adapter for an item inside the list view
 */

/**
 * @desc list view adapter.
 */
class SettingsListViewAdapter(private val vs: ViewSettings) : BaseAdapter() {

    /**
     * inflater used to inflate the list view.
     * @return [LayoutInflater]
     */
    private val mInflater: LayoutInflater = LayoutInflater.from(vs.requireContext())

    /**
     * defines the default item count.
     * @return item count.
     */
    override fun getCount(): Int {
        return this.vs.mViewModel.mModelSettings.mSettings.size
    }

    /**
     * defines the item based on position.
     * @return item.
     */
    override fun getItem(position: Int): Any {
        return this.vs.mViewModel.mModelSettings.mSettings.toList()[position]
    }

    /**
     * defines the item id based on position.
     * @return item id.
     */
    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    /**
     * defines the view and its holder based on position.
     * @return view.
     */
    @SuppressLint("ViewHolder") //list view is implemented on purpose for simple settings.
    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val view: View = mInflater.inflate(R.layout.item_settings, parent, false)
        ListItemHolder(view, position)
        return view
    }

    /**
     * @desc holds reference to an item (setting) in the list view.
     */
    inner class ListItemHolder(private val view: View, private val position: Int): View.OnClickListener {

        /**
         * @return layout of the setting.
         */
        private var mLayout: ConstraintLayout? = null

        /**
         * @return name of the setting.
         */
        private var mSettingName : MaterialTextView? = null

        /**
         * @return value of the setting.
         */
        private var mSettingValue : MaterialTextView? = null

        /**
         * initialization for setting item.
         */
        init {
            load()
            define()
            listen()
        }

        /**
         * onClick definition for each setting.
         */
        override fun onClick(v: View?) {
            when(v?.id){
                R.id.item_view_settings_layout -> {
                    when(this.mSettingName?.text){
                        SETTING_THEME_MODE -> { vs.mBinding.mPresenter?.fireThemeModeSettings() }
                        SETTING_ACCOUNT_NAME -> { vs.mBinding.mPresenter?.fireAccountNameSettings() }
                        SETTING_ACCOUNT_PASSWORD -> { vs.mBinding.mPresenter?.fireAccountPasswordSettings() }
                        SETTING_FEATURE_REQUEST -> { vs.mBinding.mPresenter?.fireFeatureRequestSettings() }
                        SETTING_TERMS_OF_SERVICE -> { vs.mBinding.mPresenter?.fireTermsOfServiceSettings() }
                        SETTING_FEEDBACK -> { vs.mBinding.mPresenter?.fireFeedbackSettings() }
                    }
                }
            }
        }

        /**
         * load method to set the properties of an item.
         */
        private fun load(){
            this.mLayout = view.findViewById(R.id.item_view_settings_layout)
            this.mSettingName = view.findViewById(R.id.list_item_settings_title)
            this.mSettingValue = view.findViewById(R.id.list_item_settings_value)
        }

        /**
         * define method to set the properties of an item.
         */
        private fun define(){
            mSettingName?.text = vs.mViewModel.mModelSettings.mSettings[position]
            when(mSettingName?.text){
                SETTING_THEME_MODE -> {
                    if(MarvelDroidUtility().checkInDarkMode(vs.requireContext())){
                        mSettingValue?.text = vs.resources.getString(R.string.dark_mode)
                    } else {
                        mSettingValue?.text = vs.resources.getString(R.string.light_mode)
                    }
                }
                SETTING_ACCOUNT_NAME -> { mSettingValue?.text = vs.getString(R.string.settings_adapter_secret_three) }
                SETTING_ACCOUNT_PASSWORD -> { mSettingValue?.text = vs.getString(R.string.settings_adapter_secret_three) }
                SETTING_FEATURE_REQUEST -> { mSettingValue?.text = vs.getString(R.string.settings_adapter_secret_one) }
                SETTING_TERMS_OF_SERVICE -> { mSettingValue?.text = vs.getString(R.string.settings_adapter_secret_one) }
                SETTING_FEEDBACK -> { mSettingValue?.text = vs.getString(R.string.settings_adapter_secret_one) }
            }
        }

        /**
         * listen method to detect click events.
         */
        private fun listen(){
            mLayout?.setOnClickListener(this)
        }
    }
}