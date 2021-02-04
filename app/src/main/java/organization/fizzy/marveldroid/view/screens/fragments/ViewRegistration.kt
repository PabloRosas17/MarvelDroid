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
import androidx.core.os.bundleOf
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import com.google.gson.JsonObject
import organization.fizzy.marveldroid.R
import organization.fizzy.marveldroid.controller.viewmodels.ViewModelRegistration
import organization.fizzy.marveldroid.core.MarvelDroidApplication
import organization.fizzy.marveldroid.core.factory.FactoryFragment
import organization.fizzy.marveldroid.databinding.LayoutViewRegistrationBinding
import organization.fizzy.marveldroid.model.AccountWrapper
import organization.fizzy.marveldroid.tools.constants.FirebaseAccountFields.FIELD_FACEBOOK
import organization.fizzy.marveldroid.tools.constants.FirebaseAccountFields.FIELD_GOOGLE
import organization.fizzy.marveldroid.tools.constants.FirebaseAccountFields.FIELD_ID
import organization.fizzy.marveldroid.tools.constants.FirebaseAccountFields.FIELD_KEY
import organization.fizzy.marveldroid.tools.constants.FirebaseAccountFields.FIELD_PASSWORD
import organization.fizzy.marveldroid.tools.constants.FirebaseAccountFields.FIELD_USERNAME
import organization.fizzy.marveldroid.tools.constants.FirebaseStatusConst.STATUS_LOGIN_FAILURE
import organization.fizzy.marveldroid.tools.constants.FirebaseStatusConst.STATUS_LOGIN_SUCCESS
import organization.fizzy.marveldroid.tools.constants.FirebaseStatusConst.STATUS_SIGNON_FAILURE
import organization.fizzy.marveldroid.tools.constants.FirebaseStatusConst.STATUS_SIGNON_SUCCESS
import organization.fizzy.marveldroid.tools.constants.FirebaseStatusConst.STATUS_SIGNUP_FAILURE
import organization.fizzy.marveldroid.tools.constants.FirebaseStatusConst.STATUS_SIGNUP_SUCCESS
import organization.fizzy.marveldroid.tools.constants.FirebaseStatusConst.STATUS_UPDATE_FAILURE
import organization.fizzy.marveldroid.tools.constants.FirebaseStatusConst.STATUS_UPDATE_SUCCESS
import organization.fizzy.marveldroid.tools.util.FirebaseRegistrationIf
import organization.fizzy.marveldroid.tools.util.MarvelDroidUtility
import organization.fizzy.marveldroid.view.presenter.fragments.PresenterRegistration
import javax.inject.Inject

/**
 * @author, evolandlupiz
 * @date, 5/17/2020
 * @property, MarvelDroid
 * @purpose, registration ui.
 */

/**
 * @desc ViewMarvelDroid will display the registration for the user.
 */
class ViewRegistration @Inject constructor() : Fragment(), FirebaseRegistrationIf<JsonObject> {

    @Inject lateinit var factory: ViewModelProvider.Factory

    @Inject lateinit var fragmentFactory: FactoryFragment

    /**
     * Callback handler after network operations.
     */
    private var firelistener : FirebaseRegistrationIf<JsonObject> = this

    /**
     * Binding object.
     * @return [ViewDataBinding]
     */
    lateinit var mBinding: LayoutViewRegistrationBinding

    /**
     * Generate view models of type get(T) through the factory provided, Inject a view-model by factory.
     * @return [FactoryViewModel]
     */
    val mViewModel by viewModels<ViewModelRegistration> { factory }

    /**
     * Dialog element used to interact with the user in the presenter.
     */
    var mDialog: AlertDialog? = null

    /**
     * @return [NavController] for navigation flow.
     */
    private var mNavCntrl: NavController? = null

    /**
     * Dagger dependency injection requirement.
     */
    override fun onAttach(context: Context) {
        super.onAttach(context)
        (this.activity?.application as MarvelDroidApplication).mComponent.inject(this)
        this.requireActivity().supportFragmentManager.fragmentFactory = fragmentFactory
    }

    /**
     * binding is generated through BR class, this will set the view through bindings
     */
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, state: Bundle?): View? {
        this.mBinding = DataBindingUtil.inflate(inflater, R.layout.layout_view_registration, container, false)
        this.fireUiBindings()
        return this.mBinding.root
    }

    /**
     * initialize navigation controller.
     */
    override fun onViewCreated(view: View, state: Bundle?) {
        super.onViewCreated(view, state)
        mNavCntrl = Navigation.findNavController(view)
    }

    /**
     * binding is generated through BR class, this will set the view through binding
     * bind presenter account instance with this view
     * execute pending bindings
     */
    private fun fireUiBindings(){
        mBinding.mPresenter =
            PresenterRegistration(
                this
            )
        mBinding.executePendingBindings()
        this.subscribeUi()
    }

    /**
     * Method used to enforce ui listeners for active elements.
     */
    private fun subscribeUi() {
        /* observer receives a hashmap with unique keys
            , each key corresponds to a unique firebase operation status
            , each status returns a wrapper with data (can be null) */
        val mFirebase = Observer<HashMap<Int, AccountWrapper?>> {
            it.forEach { item ->
                when(item.key){
                    STATUS_LOGIN_SUCCESS -> {
                        firelistener.onLogInFx(true, item.value!!.mKey!!, item.value!!)
                    }
                    STATUS_LOGIN_FAILURE -> {
                        firelistener.onLogInFx(false,null,null)
                    }
                    STATUS_SIGNUP_SUCCESS -> {
                        if(item.value != null){
                            firelistener.onSignUpFx(false, item.value!!.mKey!!, item.value!!)
                        } else {
                            firelistener.onSignUpFx(true,null, null)
                        }
                    }
                    STATUS_SIGNUP_FAILURE -> {
                        firelistener.onSignUpFx(false,null,null)
                    }
                    STATUS_SIGNON_SUCCESS -> {
                        firelistener.onSignOnFx(true,item.value!!)
                    }
                    STATUS_SIGNON_FAILURE -> {
                        firelistener.onSignOnFx(false,null)
                    }
                    STATUS_UPDATE_SUCCESS -> {
                        println("STATUS_UPDATE_SUCCESS")
                    }
                    STATUS_UPDATE_FAILURE -> {
                        println("STATUS_UPDATE_FAILURE")
                    }
                }
            }
            it.clear()
        }
        this.mViewModel.mFirebaseLive.observe(viewLifecycleOwner,mFirebase)

        var user: String? = this.arguments?.getString("FIELD_USERNAME")
        var pass: String? = this.arguments?.getString("FIELD_PASSWORD")
        if(user.isNullOrBlank()){ user = "" }
        if(pass.isNullOrBlank()){ pass = "" }
        MarvelDroidUtility().writeCredentialsToCacheInSavedPreferences(requireActivity(), user, pass)
        MarvelDroidUtility().readCredentialsFromCacheInSavedPreferences(requireActivity())
    }

    /**
     * gracefully release dialog resources, clear shared preferences.
     */
    override fun onDestroy() {
        super.onDestroy()
        if(this.mDialog != null && this.mDialog!!.isShowing) {
            this.mDialog!!.dismiss()
        }
        MarvelDroidUtility().clearCredentialsFromCacheInSavedPreferences(requireActivity())
    }

    /**
     * callback type for successful signup to firebase.
     * callback type for failed signup to firebase.
     */
    override fun onSignUpFx(status: Boolean, key: String?, data: AccountWrapper?){
        if(status){
            mBinding.mPresenter?.uiRegisteredMessage()
        } else{
            mBinding.mPresenter?.uiCreateFieldsEffect()
            mBinding.mPresenter?.incorrectDialog()
        }
    }

    /**
     * callback type for a successful login to firebase.
     * callback type for a failed login to firebase.
     */
    override fun onLogInFx(status: Boolean, key: String?, data: AccountWrapper?) {
        if(status){
            if (data != null) {
                MarvelDroidUtility().writeCredentialsToCacheInSavedPreferences(requireActivity(),data.mUserName,data.mPassWord)
                val mBundle = bundleOf(
                        FIELD_ID to data.mKey
                    ,   FIELD_KEY to data.mId
                    ,   FIELD_FACEBOOK to data.mFacebook
                    ,   FIELD_GOOGLE to data.mGoogle
                    ,   FIELD_USERNAME to data.mUserName
                    ,   FIELD_PASSWORD to data.mPassWord)
                view?.findNavController()?.navigate(R.id.nav_account,mBundle)
            }
            else {
                this.mBinding.mPresenter?.incorrectDialog()
            }
        } else{
            this.mBinding.mPresenter?.incorrectDialog()
        }
    }

    /**
     * action after sign up is [status].
     */
    override fun onSignOnFx(status: Boolean, data: AccountWrapper?) {
        if(status){
            if (data != null) {
                mViewModel.updateAccount(data)
            }
        }
    }
}