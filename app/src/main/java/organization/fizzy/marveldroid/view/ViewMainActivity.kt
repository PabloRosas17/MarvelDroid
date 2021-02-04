/*
 * 2020 MarvelDroid , Attribution Images belong to Marvel Entertainment
 * Source Timestamps exist under: https://github.com/PabloRosas17
 * Written by Jose Pablo Rosas  <pablo.rosas.0170@gmail.com>, January 2020
 */

package organization.fizzy.marveldroid.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import organization.fizzy.marveldroid.R
import organization.fizzy.marveldroid.core.MarvelDroidApplication
import javax.inject.Inject

/**
 * @author, evolandlupiz
 * @date, 8/12/2020
 * @property, MarvelDroid
 * @purpose, Provides the entry point for navigation.
 */

/**
 * @desc Top level, Single activity.
 */
class ViewMainActivity @Inject constructor() : AppCompatActivity() {

    /**
     * Perform injection, initialize bindings, and register ui elements.
     */
    override fun onCreate(state: Bundle?) {
        (application as MarvelDroidApplication).mComponent.inject(this)
        super.onCreate(state)
        setContentView(R.layout.layout_main_activity)
    }
}