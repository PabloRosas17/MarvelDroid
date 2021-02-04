/*
 * 2020 MarvelDroid , Attribution Images belong to Marvel Entertainment
 * Source Timestamps exist under: https://github.com/PabloRosas17
 * Written by Jose Pablo Rosas  <pablo.rosas.0170@gmail.com>, January 2020
 */

package organization.fizzy.marveldroid.tools.util

import android.view.MenuItem
import com.google.android.material.bottomnavigation.BottomNavigationView

/**
 * @author, evolandlupiz
 * @date, 5/10/2020
 * @property, MarvelDroid
 * @purpose, will enforce the bottom navigation view expectations. */

/**
 * @desc contract for classes using bottom navigation.
 */
interface BtmNavIf: BottomNavigationView.OnNavigationItemSelectedListener {

    /**
     * @return [BottomNavigationView] defines the bottom nav.
     */
    var mBtmNavView: BottomNavigationView

    /**
     * requirement to handle navigation item selected.
     */
    override fun onNavigationItemSelected(item: MenuItem): Boolean
}