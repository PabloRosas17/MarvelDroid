/*
 * 2020 MarvelDroid , Attribution Images belong to Marvel Entertainment
 * Source Timestamps exist under: https://github.com/PabloRosas17
 * Written by Jose Pablo Rosas  <pablo.rosas.0170@gmail.com>, January 2020
 */

package organization.fizzy.marveldroid.view.screens.fragments

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.google.android.material.bottomnavigation.BottomNavigationView
import organization.fizzy.marveldroid.R
import organization.fizzy.marveldroid.controller.viewmodels.ViewModelAccount
import organization.fizzy.marveldroid.core.MarvelDroidApplication
import organization.fizzy.marveldroid.core.factory.FactoryFragment
import organization.fizzy.marveldroid.databinding.LayoutViewAccountBinding
import organization.fizzy.marveldroid.tools.constants.FirebaseAccountFields.FIELD_FACEBOOK
import organization.fizzy.marveldroid.tools.constants.FirebaseAccountFields.FIELD_GOOGLE
import organization.fizzy.marveldroid.tools.constants.FirebaseAccountFields.FIELD_ID
import organization.fizzy.marveldroid.tools.constants.FirebaseAccountFields.FIELD_KEY
import organization.fizzy.marveldroid.tools.constants.FirebaseAccountFields.FIELD_PASSWORD
import organization.fizzy.marveldroid.tools.constants.FirebaseAccountFields.FIELD_USERNAME
import organization.fizzy.marveldroid.tools.util.BtmNavIf
import organization.fizzy.marveldroid.tools.util.MarvelDroidUtility
import organization.fizzy.marveldroid.view.presenter.fragments.PresenterAccount
import javax.inject.Inject

/**
 * @author, evolandlupiz
 * @date, 5/17/2020
 * @property, MarvelDroid
 * @purpose, account ui.
 */

/**
 * @desc ViewAccount will display account for the user.
 */
class ViewAccount @Inject constructor() : Fragment(), BtmNavIf {

    @Inject lateinit var factory: ViewModelProvider.Factory

    @Inject lateinit var fragmentFactory: FactoryFragment

    /**
     * Binding object.
     * @return [ViewDataBinding]
     */
    lateinit var mBinding: LayoutViewAccountBinding

    /**
     * Generate view models of type get(T) through the factory provided, Inject a view-model by factory.
     * @return [ViewModelAccount]
     */
    val mViewModel by viewModels<ViewModelAccount> { factory }

    /**
     * Bottom Navigation bounded object.
     * @return [BottomNavigationView]
     */
    override lateinit var mBtmNavView: BottomNavigationView

    /**
     * @return [NavController] for navigation flow.
     */
    var mNavCntrl: NavController? = null

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
        this.mBinding = DataBindingUtil.inflate(inflater, R.layout.layout_view_account, container, false)
        return this.mBinding.root
    }

    /**
     * initialize navigation controller , load persistent data if any , and start nested fragment.
     */
    override fun onViewCreated(view: View, state: Bundle?) {
        super.onViewCreated(view, state)
        mNavCntrl = Navigation.findNavController(view)
        val mBundle = bundleOf(
            FIELD_ID to this.arguments?.getString(FIELD_ID)
            ,   FIELD_KEY to this.arguments?.getString(FIELD_KEY)
            ,   FIELD_FACEBOOK to this.arguments?.getString(FIELD_FACEBOOK)
            ,   FIELD_GOOGLE to this.arguments?.getString(FIELD_GOOGLE)
            ,   FIELD_USERNAME to this.arguments?.getString(FIELD_USERNAME)
            ,   FIELD_PASSWORD to this.arguments?.getString(FIELD_PASSWORD))
        this.fireUiBindings()
        this.requireActivity().supportFragmentManager.beginTransaction()
            .replace(R.id.default_container, ViewSettings()::class.java, mBundle)
            .commit()
    }

    /**
     * binding is generated through BR class, this will set the view through binding
     * bind presenter account instance with this view, execute pending bindings
     * @throws RuntimeException unknown layout type
     */
    private fun fireUiBindings(){
        mBinding.mPresenter = PresenterAccount(this)
        mBinding.executePendingBindings()
        this.registerUi()
    }

    /**
     *  Method used to enforce ui association to xml.
     */
    private fun registerUi(){
        this.mBtmNavView = mBinding.btmNavAccountView
        this.mBtmNavView.menu.findItem(R.id.action_menu_account).isEnabled = false
        this.mBtmNavView.menu.findItem(R.id.action_menu_comics).isEnabled = true
        mBtmNavView.setOnNavigationItemSelectedListener(this)
        val username: String? = this.arguments?.getString("FIELD_USERNAME")
        val password: String? = this.arguments?.getString("FIELD_PASSWORD")
        if (username != null && password != null) {
            MarvelDroidUtility().writeCredentialsToCacheInSavedPreferences(requireActivity(), username, password)
        } else {
            if(username == null){
                println("Username is null inside ViewAccount.kt, can't writeCredentialsToCacheInSavedPreferences")
            }
            if(password == null){
                println("Password is null inside ViewAccount.kt, can't writeCredentialsToCacheInSavedPreferences")
            }
        }
        MarvelDroidUtility().readCredentialsFromCacheInSavedPreferences(requireActivity())
    }

    /**
     * gracefully clear shared preferences.
     */
    override fun onDestroy() {
        super.onDestroy()
        MarvelDroidUtility().clearCredentialsFromCacheInSavedPreferences(requireActivity())
    }

    /**
     *  Listener handles bottom navigation ui changes.
     */
    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when(item.itemId) {
            R.id.action_menu_comics -> {
                val mBundle = bundleOf(
                    FIELD_USERNAME to this.arguments?.getString(FIELD_USERNAME)
                    ,   FIELD_PASSWORD to this.arguments?.getString(FIELD_PASSWORD))
                mNavCntrl?.navigate(R.id.nav_comics,mBundle)
            }
        }
        return true
    }
}