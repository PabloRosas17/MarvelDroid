/*
 * 2020 MarvelDroid , Attribution Images belong to Marvel Entertainment
 * Source Timestamps exist under: https://github.com/PabloRosas17
 * Written by Jose Pablo Rosas  <pablo.rosas.0170@gmail.com>, January 2020
 */

package organization.fizzy.marveldroid.controller.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.viewModelScope
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.launch
import organization.fizzy.marveldroid.R
import organization.fizzy.marveldroid.databinding.ItemBookmarksBinding
import organization.fizzy.marveldroid.tools.services.database.BookmarkedItems
import organization.fizzy.marveldroid.tools.util.BookmarksRvVhCallback
import organization.fizzy.marveldroid.tools.util.OnSwipeListener
import organization.fizzy.marveldroid.view.screens.fragments.ViewBookmarks

/**
 * @author, evolandlupiz
 * @date, 5/18/2020
 * @property, MarvelDroid
 * @purpose, definition for bookmarks adapter.
 */

/**
 * @desc bookmarks recycler view adapter.
 */
class BookmarksRecyclerViewAdapter(private val vb: ViewBookmarks) :
    RecyclerView.Adapter<BookmarksRecyclerViewAdapter.BookmarksViewHolder>()
    , BookmarksRvVhCallback {

    /**
     * definition for creating the view holder.
     * @return comics view holder.
     */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BookmarksViewHolder {
        val mCardInflater = LayoutInflater.from(parent.context)
        val mView = mCardInflater.inflate(R.layout.item_bookmarks, parent, false)
        return BookmarksViewHolder(mView)
    }

    /**
     * definition for creating the total items.
     * @return item count.
     */
    override fun getItemCount(): Int {
        return this.vb.mViewModel.mModelBookmarks.mBookmarks.size
    }

    /**
     * definition for binding the view holder.
     */
    override fun onBindViewHolder(holder: BookmarksViewHolder, position: Int) {
        holder.bind(position)
    }

    /**
     * @desc holds reference to an item (bookmark) in the recycler view.
     */
    inner class BookmarksViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        /**
         * @return binding type [ItemBookmarksBinding]
         */
        private val mBinding = DataBindingUtil.bind<ItemBookmarksBinding>(itemView)

        /**
         * bind method to initialize an item.
         */
        fun bind(position: Int) {
            binder()
            load(position)
            listen(position)
        }

        /**
         * binder method for data binding the item type.
         */
        private fun binder() {
            mBinding?.executePendingBindings()
        }

        /**
         * load method to set the properties of an item.
         */
        private fun load(position:Int) {
            mBinding?.itemBookmarkComicName?.text = vb.mViewModel.mModelBookmarks.mBookmarks[position].cname
        }

        /**
         * listen method to detect swipes.
         */
        private fun listen(position: Int){
            itemView.setOnTouchListener(OnSwipeListener(vb,vb.requireContext(),position))
        }
    }

    /**
     * performs delete operation on a valid SQL table and updates the recycler view.
     */
    private fun expunge(data: BookmarkedItems) {
        /* removes an item to the local database */
        vb.mViewModel.viewModelScope.launch {
            vb.mViewModel.mMarvelDroidDB.mDaoBookmark().delete(data.cname)
        }
        this.notifyDataSetChanged()
    }

    /**
     * callback method used to communicate swipe operation.
     */
    override fun fireBookmarksRvVhCallback(data: BookmarkedItems) {
        this.expunge(data)
    }
}