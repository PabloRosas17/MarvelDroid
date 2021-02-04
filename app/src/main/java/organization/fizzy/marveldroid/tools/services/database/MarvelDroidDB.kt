/*
 * 2020 MarvelDroid , Attribution Images belong to Marvel Entertainment
 * Source Timestamps exist under: https://github.com/PabloRosas17
 * Written by Jose Pablo Rosas  <pablo.rosas.0170@gmail.com>, January 2020
 */

package organization.fizzy.marveldroid.tools.services.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import organization.fizzy.marveldroid.R

/**
 * @author, evolandlupiz
 * @date, 8/7/2020
 * @property, MarvelDroid
 * @purpose, database loader.
 */

/**
 * @decs database definition.
 */
@Database(entities = [BookmarkedItems::class], version = 1, exportSchema = false)
abstract class MarvelDroidDB : RoomDatabase() {


    /**
     * @return [DaoBookmark] as DAO object
     */
    abstract fun mDaoBookmark() : DaoBookmark

    /**
     * class companion object.
     */
    companion object {

        /**
         * @return [MarvelDroidDB] thread-safe field.
         */
        @Volatile private var INSTANCE_MARVEL_DROID_DB: MarvelDroidDB? = null

        /**
         * getter for database.
         */
        fun getDatabase(context: Context) : RoomDatabase {
            val tInstance = INSTANCE_MARVEL_DROID_DB
            if(tInstance != null){
                return tInstance
            }
            synchronized(this){
                val instance =
                    Room.databaseBuilder(context, MarvelDroidDB::class.java, context.getString(R.string.db_bookmark_table)).build()
                this.INSTANCE_MARVEL_DROID_DB = instance
                return instance
            }
        }
    }
}