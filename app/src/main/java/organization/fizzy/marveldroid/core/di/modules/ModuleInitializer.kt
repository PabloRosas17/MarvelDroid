/*
 * 2020 MarvelDroid , Attribution Images belong to Marvel Entertainment
 * Source Timestamps exist under: https://github.com/PabloRosas17
 * Written by Jose Pablo Rosas  <pablo.rosas.0170@gmail.com>, January 2020
 */

package organization.fizzy.marveldroid.core.di.modules

import android.app.Application
import com.jakewharton.threetenabp.AndroidThreeTen
import dagger.Module
import javax.inject.Inject
import javax.inject.Singleton

/**
 * @author, evolandlupiz
 * @date, 5/12/2020
 * @property, MarvelDroid
 * @purpose, Module that will initialize third-party modules.
 */

@Module
class ModuleInitializer @Inject constructor(private val mInitializer: Application) {

    /**
     * ThreeTen.
     */
    @Singleton
    fun initializeThreeTenBp() {
        return AndroidThreeTen.init(mInitializer)
    }
}