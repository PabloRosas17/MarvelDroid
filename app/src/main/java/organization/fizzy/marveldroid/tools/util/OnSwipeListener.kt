/*
 * 2020 MarvelDroid , Attribution Images belong to Marvel Entertainment
 * Source Timestamps exist under: https://github.com/PabloRosas17
 * Written by Jose Pablo Rosas  <pablo.rosas.0170@gmail.com>, January 2020
 */

package organization.fizzy.marveldroid.tools.util

import android.content.Context
import android.util.Log
import android.view.GestureDetector
import android.view.MotionEvent
import android.view.View
import androidx.fragment.app.Fragment
import organization.fizzy.marveldroid.controller.adapters.BookmarksRecyclerViewAdapter
import organization.fizzy.marveldroid.view.screens.fragments.ViewBookmarks

/**
 * @author, evolandlupiz
 * @date, 8/8/2020
 * @property, MarvelDroid
 * @purpose, swipe listener.
 */

/**
 * swipe listener definition for gestures.
 */
class OnSwipeListener(private val caller: Fragment, private val context: Context, private val position: Int): View.OnTouchListener {

    /**
     * @return [GestureDetector] detector that detects gestures.
     */
    private var mDetector: GestureDetector? = GestureDetector(context,GestureListener())

    /**
     * @return [Int] position to the item swiped.
     */
    private var mPosition: Int = position

    /**
     * @return [Fragment] as the caller for this.
     */
    private var mFragment: Fragment? = caller

    /**
     * @return [Boolean] determined from [event].
     */
    override fun onTouch(v: View?, event: MotionEvent?): Boolean {
        return mDetector?.onTouchEvent(event).let { true }
    }

    /**
     * inner definition handles gesture logic.
     */
    private inner class GestureListener: GestureDetector.SimpleOnGestureListener() {

        /**
         * [SWIPE_VELOCITY_THRESHOLD]
         */
        private val SWIPE_VELOCITY_THRESHOLD = 100

        /**
         * [SWIPE_THRESHOLD]
         */
        private val SWIPE_THRESHOLD = 100

        /**
         * logic for each type of calculated event.
         */
        override fun onFling(e1: MotionEvent?, e2: MotionEvent?, velocityX: Float, velocityY: Float): Boolean {
            Log.d("DEBUG_TAG", "onFling: $e1 $e2")
            val diffY = e2!!.y - e1!!.y
            val diffX = e2.x - e1.x
            if (Math.abs(diffX) > Math.abs(diffY)) {
                if (Math.abs(diffX) > SWIPE_THRESHOLD && Math.abs(velocityX) > SWIPE_VELOCITY_THRESHOLD) {
                    if (diffX > 0) {
                        println("onSwipeRight")
                    } else {
                        onSwipeLeft()
                    }
                }
            } else if (Math.abs(diffY) > SWIPE_THRESHOLD && Math.abs(velocityY) > SWIPE_VELOCITY_THRESHOLD) {
                if (diffY > 0) {
                    println("onSwipeBottom")
                } else {
                    println("onSwipeTop")
                }
            }
            return true
        }

        /**
         * swipe left action.
         */
        fun onSwipeLeft() {
            println("Swiped Left")
            if(mFragment is ViewBookmarks){
                val mBookmarksRvVhCallback: BookmarksRvVhCallback =
                    (((mFragment as (ViewBookmarks)).mBinding.accountBookmarksRv.adapter) as BookmarksRecyclerViewAdapter)
                val data = ((mFragment as (ViewBookmarks)).mViewModel).mModelBookmarks.mBookmarks.get(mPosition)
                mBookmarksRvVhCallback.fireBookmarksRvVhCallback(data)
            }
        }
    }
}