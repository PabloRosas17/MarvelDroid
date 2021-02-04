/*
 * 2020 MarvelDroid , Attribution Images belong to Marvel Entertainment
 * Source Timestamps exist under: https://github.com/PabloRosas17
 * Written by Jose Pablo Rosas  <pablo.rosas.0170@gmail.com>, January 2020
 */

package organization.fizzy.marveldroid.controller.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.textview.MaterialTextView
import com.squareup.picasso.Picasso
import organization.fizzy.marveldroid.R
import organization.fizzy.marveldroid.databinding.ItemComicsBinding
import organization.fizzy.marveldroid.tools.services.database.BookmarkedItems
import organization.fizzy.marveldroid.view.presenter.ui.PresenterComicsItem
import organization.fizzy.marveldroid.view.screens.fragments.ViewComics

/**
 * @author, evolandlupiz
 * @date, 5/17/2020
 * @property, MarvelDroid
 * @purpose, definition for comics adapter.
 */

/**
 * @desc comics recycler view adapter.
 */
class ComicsRecyclerViewAdapter(private val vc: ViewComics) : RecyclerView.Adapter<ComicsRecyclerViewAdapter.ComicsViewHolder>() {

    /**
     * definition for creating the view holder.
     * @return comics view holder.
     */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ComicsViewHolder {
        val mCardInflater = LayoutInflater.from(parent.context)
        val mView = mCardInflater.inflate(R.layout.item_comics, parent, false)
        return ComicsViewHolder(mView)
    }

    /**
     * definition for creating the total items.
     * @return item count.
     */
    override fun getItemCount(): Int {
        return this.vc.mViewModel.mModelComics.mComics.size
    }

    /**
     * definition for binding the view holder.
     */
    override fun onBindViewHolder(holder: ComicsViewHolder, position: Int) {
        holder.bind(position)
    }

    /**
     * @desc holds reference to an item (comic) in the recycler view.
     */
    inner class ComicsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        /**
         * @return binding type [ItemComicsBinding]
         */
        private val mBinding = DataBindingUtil.bind<ItemComicsBinding>(itemView)

        /**
         * bind method to initialize an item.
         */
        fun bind(position: Int) {
            load(position)
            live(position)
            binder(position)
            listen(position)
        }

        /**
         * load method to set the properties of an item.
         */
        private fun load(position: Int){
            Picasso.get()
                .load(vc.mViewModel.mModelComics.mComics[position].cthumbnail.path + "." + vc.mViewModel.mModelComics.mComics[position].cthumbnail.extension)
                .placeholder(R.drawable.outline_pending_black_18dp)
                .error(R.drawable.outline_error_outline_black_18dp)
                .into(itemView.findViewById<ImageView>(R.id.item_comic_artwork))
            itemView.findViewById<MaterialTextView>(R.id.item_comic_attribution).text = vc.mViewModel.mModelComics.mComics[position].attribution
            itemView.findViewById<MaterialTextView>(R.id.item_comic_title).text = vc.mViewModel.mModelComics.mComics[position].ctitle
            itemView.findViewById<MaterialTextView>(R.id.item_comic_published).text = vc.mViewModel.mModelComics.mComics[position].cdate
            itemView.findViewById<MaterialTextView>(R.id.item_comic_isbn).text = vc.mViewModel.mModelComics.mComics[position].cisbn
            itemView.findViewById<MaterialTextView>(R.id.item_comic_upc).text = vc.mViewModel.mModelComics.mComics[position].cupc
        }

        /**
         * live data agents post updates for the comic description ui fields.
         */
        private fun live(position: Int){
            vc.mViewModel.mDescriptionTitleLiveData.postValue(vc.mViewModel.mModelComics.mComics[position].ctitle)
            vc.mViewModel.mDescriptionInformationLiveData.postValue(vc.mViewModel.mModelComics.mComics[position].cdescription)
            vc.mViewModel.mDescriptionPagesLiveData.postValue("${vc.mViewModel.mModelComics.mComics[position].cpagecount} pages")
            vc.mViewModel.mDescriptionPriceLiveData.postValue("$${vc.mViewModel.mModelComics.mComics[position].cprice}")
            vc.mViewModel.mDescriptionIssueNoLiveData.postValue("IssueNo:#${vc.mViewModel.mModelComics.mComics[position].cissueno}")
        }

        /**
         * binder method for data binding the item type.
         */
        private fun binder(position: Int){
            mBinding?.mPresenter = PresenterComicsItem(vc,itemView, position)
            mBinding?.executePendingBindings()
        }

        /**
         * listen method to detect click events.
         */
        private fun listen(position: Int){
            /* adds an item to the local database */
            itemView.setOnLongClickListener {
                Toast.makeText(vc.requireContext(),"Bookmarked Title:${vc.mViewModel.mModelComics.mComics[position].ctitle}.",Toast.LENGTH_LONG).show()
                mBinding?.mPresenter?.supply(
                    BookmarkedItems(
                        cname = "Title:${vc.mViewModel.mModelComics.mComics[position].ctitle}"
                        ,cnumber = "IssueNo:#${vc.mViewModel.mModelComics.mComics[position].cissueno}"
                        ,cprice = "$${vc.mViewModel.mModelComics.mComics[position].cprice}"
                    )
                )
                false
            }
        }
    }
}