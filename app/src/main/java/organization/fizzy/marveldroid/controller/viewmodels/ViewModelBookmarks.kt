/*
 * 2020 MarvelDroid , Attribution Images belong to Marvel Entertainment
 * Source Timestamps exist under: https://github.com/PabloRosas17
 * Written by Jose Pablo Rosas  <pablo.rosas.0170@gmail.com>, January 2020
 */

package organization.fizzy.marveldroid.controller.viewmodels

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.room.Room
import organization.fizzy.marveldroid.R
import organization.fizzy.marveldroid.model.ModelBookmarks
import organization.fizzy.marveldroid.tools.services.database.BookmarkedItems
import organization.fizzy.marveldroid.tools.services.database.MarvelDroidDB
import javax.inject.Inject

/**
 * @author, evolandlupiz
 * @date, 5/12/2020
 * @property, MarvelDroid
 * @purpose, view model for bookmarks.
*/

/**
 * @desc defines view model for a bookmark
 * @param application reference required for database operations.
 */
class ViewModelBookmarks @Inject constructor(private val application: Application) : ViewModel() {

    /**
     * @return singleton DB.
     */
    companion object DB {
        /**
         * @return singleton reference to room database.
         */
        fun initDB(application: Application) : MarvelDroidDB {
            return Room.databaseBuilder(application
                , MarvelDroidDB::class.java
                , application.getString(R.string.db_bookmark_table)).build()
        }
    }

    /**
     * @return [MarvelDroidDB] reference.
     */
    val mMarvelDroidDB = DB.initDB(application)

    /**
     * @return model that represents the bookmarks.
     */
    val mModelBookmarks = ModelBookmarks()

    /**
     * @return live data agent queries database.
     */
    var mBookmarkedLD: LiveData<List<BookmarkedItems>> =
        ((MarvelDroidDB.getDatabase(application)) as (MarvelDroidDB)).mDaoBookmark().query()
}