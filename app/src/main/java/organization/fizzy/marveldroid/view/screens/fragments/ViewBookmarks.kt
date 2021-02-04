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
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import organization.fizzy.marveldroid.R
import organization.fizzy.marveldroid.controller.adapters.BookmarksRecyclerViewAdapter
import organization.fizzy.marveldroid.controller.viewmodels.ViewModelBookmarks
import organization.fizzy.marveldroid.core.MarvelDroidApplication
import organization.fizzy.marveldroid.databinding.LayoutViewBookmarksBinding
import organization.fizzy.marveldroid.view.presenter.fragments.PresenterBookmarks
import javax.inject.Inject

/**
 * @author, evolandlupiz
 * @date, 5/17/2020
 * @property, MarvelDroid
 * @purpose, bookmarks ui.
 */

/**
 * @desc ViewBookmarks will display bookmarks for the user.
 */
class ViewBookmarks @Inject constructor() : Fragment() {

    @Inject lateinit var factory: ViewModelProvider.Factory

    /**
     * Binding object.
     * @return [ViewDataBinding]
     */
    lateinit var mBinding: LayoutViewBookmarksBinding

    /**
     * Generate view models of type get(T) through the factory provided, Inject a view-model by factory.
     * @return [ViewModelBookmarks]
     */
    val mViewModel by viewModels<ViewModelBookmarks> { factory }

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
        this.mBinding = DataBindingUtil.inflate(inflater, R.layout.layout_view_bookmarks, container, false)
        this.fireUiBindings()
        return this.mBinding.root
    }

    /**
     * binding is generated through BR class, this will set the view through binding
     * bind presenter account instance with this view
     * execute pending bindings
     */
    private fun fireUiBindings(){
        mBinding.mPresenter = PresenterBookmarks()
        mBinding.executePendingBindings()
        this.registerUi()
        this.subscribeUi()
    }

    /**
     *  Method used to enforce ui association to xml.
     */
    private fun registerUi() {
        mBinding.accountBookmarksRv.layoutManager = LinearLayoutManager(this.requireContext())
        mBinding.accountBookmarksRv.adapter = BookmarksRecyclerViewAdapter(this)
    }

    /**
     * subscribes listeners, bookmarked live data will invoke when a bookmark is selected.
     */
    private fun subscribeUi(){
        this.mViewModel.mBookmarkedLD.observe(viewLifecycleOwner, Observer {
            it.let { l ->
                l.forEach { item ->
                    mViewModel.mModelBookmarks.mBookmarks.add(item)
                }
            }
            mBinding.accountBookmarksRv.adapter?.notifyDataSetChanged()
        })
    }
}