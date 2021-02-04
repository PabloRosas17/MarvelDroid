/*
 * 2020 MarvelDroid , Attribution Images belong to Marvel Entertainment
 * Source Timestamps exist under: https://github.com/PabloRosas17
 * Written by Jose Pablo Rosas  <pablo.rosas.0170@gmail.com>, January 2020
 */

package organization.fizzy.marveldroid.view.screens.fragments

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import organization.fizzy.marveldroid.R
import organization.fizzy.marveldroid.controller.adapters.SettingsListViewAdapter
import organization.fizzy.marveldroid.controller.viewmodels.ViewModelSettings
import organization.fizzy.marveldroid.core.MarvelDroidApplication
import organization.fizzy.marveldroid.databinding.LayoutViewSettingsBinding
import organization.fizzy.marveldroid.view.presenter.fragments.PresenterSettings
import javax.inject.Inject

/**
 * @author, evolandlupiz
 * @date, 5/17/2020
 * @property, MarvelDroid
 * @purpose, settings ui.
 */

/**
 * @desc ViewSettings will display settings for the user.
 */
class ViewSettings @Inject constructor() : Fragment() {

    @Inject lateinit var factory: ViewModelProvider.Factory

    /**
     * Binding object.
     * @return [ViewDataBinding]
     */
    lateinit var mBinding: LayoutViewSettingsBinding

    /**
     * Generate view models of type get(T) through the factory provided, Inject a view-model by factory.
     * @return [ViewModelSettings]
     */
    val mViewModel by viewModels<ViewModelSettings> { factory }

    /**
     * Dialog element used to interact with the user in the presenter.
     */
    var mDialog: AlertDialog? = null

    /**
     * Dagger dependency injection requirement.
     */
    override fun onAttach(context: Context) {
        super.onAttach(context)
        (this.activity?.application as MarvelDroidApplication).mComponent.inject(this)
    }

    /**
     * binding is generated through BR class, this will set the view through bindings
     */
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, state: Bundle?): View? {
        this.mBinding = DataBindingUtil.inflate(inflater, R.layout.layout_view_settings, container, false)
        this.fireUiBindings()
        return this.mBinding.root
    }

    /**
     * Gracefully release resources.
     */
    override fun onDestroy() {
        super.onDestroy()
        if(this.mDialog != null){
            if(this.mDialog!!.isShowing){
                this.mDialog!!.dismiss()
            }
        }
    }

    /**
     * binding is generated through BR class, this will set the view through binding
     * bind presenter account instance with this view
     * execute pending bindings
     */
    private fun fireUiBindings(){
        mBinding.mPresenter = PresenterSettings(this)
        mBinding.executePendingBindings()
        this.registerUi()
    }

    /**
     *  Method used to enforce ui association to xml.
     */
    private fun registerUi(){
        mBinding.accountSettingsLv.adapter = SettingsListViewAdapter(this)
    }
}