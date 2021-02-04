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
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.gson.Gson
import com.google.gson.JsonObject
import organization.fizzy.marveldroid.R
import organization.fizzy.marveldroid.controller.adapters.ComicsRecyclerViewAdapter
import organization.fizzy.marveldroid.controller.viewmodels.ViewModelComics
import organization.fizzy.marveldroid.core.MarvelDroidApplication
import organization.fizzy.marveldroid.core.factory.FactoryFragment
import organization.fizzy.marveldroid.databinding.LayoutViewComicsBinding
import organization.fizzy.marveldroid.model.ModelDroidComic
import organization.fizzy.marveldroid.model.data.*
import organization.fizzy.marveldroid.tools.constants.FirebaseAccountFields.FIELD_PASSWORD
import organization.fizzy.marveldroid.tools.constants.FirebaseAccountFields.FIELD_USERNAME
import organization.fizzy.marveldroid.tools.util.BtmNavIf
import organization.fizzy.marveldroid.tools.util.MarvelDroidUtility
import organization.fizzy.marveldroid.view.presenter.fragments.PresenterComic
import retrofit2.Response
import javax.inject.Inject

/**
 * @author, evolandlupiz
 * @date, 5/17/2020
 * @property, MarvelDroid
 * @purpose, comics ui.
 */

/**
 * @desc ViewComics will display comics for the user.
 */
class ViewComics @Inject constructor() : Fragment(), BtmNavIf {

    @Inject lateinit var factory: ViewModelProvider.Factory

    @Inject lateinit var fragmentFactory: FactoryFragment

    /**
     * Binding object.
     * @return [ViewDataBinding]
     */
    lateinit var mBinding: LayoutViewComicsBinding

    /**
     * Generate view models of type get(T) through the factory provided, Inject a view-model by factory.
     * @return [ViewModelComics]
     */
    val mViewModel by viewModels<ViewModelComics> { factory }

    /**
     * Bottom Navigation bounded object.
     * @return [BottomNavigationView]
     */
    override lateinit var mBtmNavView: BottomNavigationView

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
        this.mBinding = DataBindingUtil.inflate(inflater, R.layout.layout_view_comics, container, false)
        this.mViewModel.loadMarvelApi()
        return this.mBinding.root
    }

    /**
     * initialize navigation controller.
     */
    override fun onViewCreated(view: View, state: Bundle?) {
        super.onViewCreated(view, state)
        mNavCntrl = Navigation.findNavController(view)
        this.fireUiBindings()
    }

    /**
     * binding is generated through BR class, this will set the view through binding
     * bind presenter account instance with this view, execute pending bindings
     * @throws RuntimeException unknown layout type
     */
    private fun fireUiBindings(){
        mBinding.mPresenter = PresenterComic(this)
        mBinding.executePendingBindings()
        this.registerUi()
        this.subscribeUi()
    }

    /**
     * Method used to enforce ui listeners for active elements.
     */
    private fun subscribeUi() {
        val mData = Observer<Response<JsonObject>> { data ->
            val response = Gson().fromJson(data.body(),ComicDataWrapper::class.java)
            val wrapper = Gson().fromJson(response.data,ComicDataContainer::class.java)
            val comiclist = wrapper.results
            comiclist.forEach {itComic ->
                val datelist = itComic.dates
                val pricelist = itComic.prices
                var date =  ""
                var price = ""
                datelist.forEach {itDate ->
                    if(itDate.type == "onsaleDate"){
                        date = if(itDate.date.isEmpty() || itDate.date.isBlank()){ "N/A" }
                        else { itDate.date }
                    }
                }
                pricelist.forEach { itPrice ->
                    if(itPrice.type == "printPrice"){
                        price = if(itPrice.price.isEmpty() || itPrice.price.isBlank()){ "N/A" }
                        else { itPrice.price }
                    }
                }
                val comic = ModelDroidComic(
                    Thumbnail(itComic.thumbnail.extension, itComic.thumbnail.path)
                    , ctitle = if(itComic.title.isEmpty() || itComic.title.isBlank()){ "N/A" } else { itComic.title }
                    , cissueno = if(itComic.issueNumber.isEmpty() || itComic.issueNumber.isBlank()){ "N/A" } else { itComic.issueNumber }
                    , cdescription = "Description:${if(itComic.description.isEmpty() || itComic.description.isBlank()){ "N/A" } else { itComic.description }}"
                    , cisbn = "ISBN:${if(itComic.pageCount.isEmpty() || itComic.pageCount.isBlank()){ "N/A" } else { itComic.pageCount }}"
                    , cupc = "UPC:${if(itComic.upc.isEmpty() || itComic.upc.isBlank()){ "N/A" } else { itComic.upc }}"
                    , cpagecount = if(itComic.pageCount.isEmpty() || itComic.pageCount.isBlank()){ "N/A" } else { itComic.pageCount }
                    , cdate = "OnSaleDate:$date"
                    , cprice = price
                    , attribution = "Data provided by Marvel. © 2014 Marvel"
                )
                this.mViewModel.mModelComics.mComics.add(comic)
                mBinding.comicsComicRv.adapter?.notifyDataSetChanged()
            }
        }
        this.mViewModel.mMarvelLiveData.observe(viewLifecycleOwner,mData)

        val mDescriptionTitle = Observer<String> {
            val title = mBinding.comicsDesTitleTxt
            title.text = it
        }
        this.mViewModel.mDescriptionTitleLiveData.observe(viewLifecycleOwner,mDescriptionTitle)

        val mDescriptionInformation = Observer<String> {
            val info = mBinding.comicsDesInfoTxt
            info.text = it
        }
        this.mViewModel.mDescriptionInformationLiveData.observe(viewLifecycleOwner,mDescriptionInformation)

        val mDescriptionPages = Observer<String> {
            val pages = mBinding.comicsDesPagesTxt
            pages.text = it
        }
        this.mViewModel.mDescriptionPagesLiveData.observe(viewLifecycleOwner,mDescriptionPages)

        val mDescriptionPrice = Observer<String> {
            val price = mBinding.comicsDesPriceTxt
            price.text = it
        }
        this.mViewModel.mDescriptionPriceLiveData.observe(viewLifecycleOwner,mDescriptionPrice)

        val mDescriptionIssueNo = Observer<String> {
            val issueno = mBinding.comicsDesIssuenoTxt
            issueno.text = it
        }
        this.mViewModel.mDescriptionIssueNoLiveData.observe(viewLifecycleOwner,mDescriptionIssueNo)
    }

    /**
     *  Method used to enforce ui association to xml.
     */
    private fun registerUi(){
        this.mBtmNavView = mBinding.btmNavComicsView
        this.mBtmNavView.menu.findItem(R.id.action_menu_account).isEnabled = true
        this.mBtmNavView.menu.findItem(R.id.action_menu_comics).isEnabled = false
        mBtmNavView.setOnNavigationItemSelectedListener(this)
        mBinding.comicsComicRv.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        mBinding.comicsComicRv.adapter = ComicsRecyclerViewAdapter(this)
        val username: String? = this.arguments?.getString(FIELD_USERNAME)
        val password: String? = this.arguments?.getString(FIELD_PASSWORD)
        if (username != null && password != null) {
            MarvelDroidUtility().writeCredentialsToCacheInSavedPreferences(requireActivity(), username, password)
        } else {
            if(username == null){
                println("Username is null inside ViewComic.kt, can't writeCredentialsToCacheInSavedPreferences")
            }
            if(password == null){
                println("Password is null inside ViewComic.kt, can't writeCredentialsToCacheInSavedPreferences")
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
            R.id.action_menu_account -> {
                val mBundle = bundleOf(
                    FIELD_USERNAME to this.arguments?.getString(FIELD_USERNAME)
                    ,   FIELD_PASSWORD to this.arguments?.getString(FIELD_PASSWORD))
                mNavCntrl?.navigate(R.id.nav_account,mBundle)
            }
        }
        return true
    }
}
