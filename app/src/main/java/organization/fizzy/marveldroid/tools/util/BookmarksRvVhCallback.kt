/*
 * 2020 MarvelDroid , Attribution Images belong to Marvel Entertainment
 * Source Timestamps exist under: https://github.com/PabloRosas17
 * Written by Jose Pablo Rosas  <pablo.rosas.0170@gmail.com>, January 2020
 */

package organization.fizzy.marveldroid.tools.util

import organization.fizzy.marveldroid.tools.services.database.BookmarkedItems

/**
 * @author, evolandlupiz
 * @date, 8/8/2020
 * @property, MarvelDroid
 * @purpose, callback definition for recycler view holder.
 */

/**
 * required to inform view holder of gesture events.
 */
interface BookmarksRvVhCallback {
    fun fireBookmarksRvVhCallback(data: BookmarkedItems)
}