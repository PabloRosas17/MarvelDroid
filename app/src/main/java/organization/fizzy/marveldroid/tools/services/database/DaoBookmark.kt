/*
 * 2020 MarvelDroid , Attribution Images belong to Marvel Entertainment
 * Source Timestamps exist under: https://github.com/PabloRosas17
 * Written by Jose Pablo Rosas  <pablo.rosas.0170@gmail.com>, January 2020
 */

package organization.fizzy.marveldroid.tools.services.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

/**
 * @author, evolandlupiz
 * @date, 8/5/2020
 * @property, MarvelDroid
 * @purpose, DAO object .
 */

/**
 * DAO definition.
 */
@Dao
interface DaoBookmark {

    /**
     * queries the entire bookmark table.
     */
    @Query("SELECT * FROM bookmark_table")
    fun query(): LiveData<List<BookmarkedItems>>

    /**
     * inserts a bookmark.
     */
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(bookmark: BookmarkedItems)

    /**
     * deletes a bookmark.
     */
    @Query("DELETE FROM bookmark_table WHERE cname = :pk")
    suspend fun delete(pk: String)
}