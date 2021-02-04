/*
 * 2020 MarvelDroid , Attribution Images belong to Marvel Entertainment
 * Source Timestamps exist under: https://github.com/PabloRosas17
 * Written by Jose Pablo Rosas  <pablo.rosas.0170@gmail.com>, January 2020
 */

package organization.fizzy.marveldroid.tools.services.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * @author, evolandlupiz
 * @date, 5/17/2020
 * @property, MarvelDroid
 * @purpose, define the bookmarks.
 */

/**
 * @desc [BookmarkedItems] database table.
 */
@Entity(tableName= "bookmark_table")
class BookmarkedItems(

    /**
     * @return [String] column name.
     */
    @PrimaryKey @ColumnInfo(name="cname") val cname: String

    /**
     * @return [String] column number.
     */
    , @ColumnInfo(name="cnumber") val cnumber: String

    /**
     * @return [String] column price.
     */
    , @ColumnInfo(name="cprice") val cprice: String){

    /**
     * @return [String] table name.
     */
    override fun toString(): String {
        return cname
    }
}