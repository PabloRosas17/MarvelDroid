/*
 * 2020 MarvelDroid , Attribution Images belong to Marvel Entertainment
 * Source Timestamps exist under: https://github.com/PabloRosas17
 * Written by Jose Pablo Rosas  <pablo.rosas.0170@gmail.com>, January 2020
 */

package organization.fizzy.marveldroid.model

import organization.fizzy.marveldroid.tools.services.database.BookmarkedItems

/**
 * @author, evolandlupiz
 * @date, 8/7/2020
 * @property, MarvelDroid
 * @purpose, defines bookmarks model.
 */

/**
 * @desc model for bookmarks.
 */
class ModelBookmarks {

    /**
     * @return bookmarks model containing [BookmarkedItems].
     */
    val mBookmarks = mutableListOf<BookmarkedItems>()
}