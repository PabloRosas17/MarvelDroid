/*
 * 2020 MarvelDroid , Attribution Images belong to Marvel Entertainment
 * Source Timestamps exist under: https://github.com/PabloRosas17
 * Written by Jose Pablo Rosas  <pablo.rosas.0170@gmail.com>, January 2020
 */

package organization.fizzy.marveldroid.core.di.modules

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentFactory
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap
import organization.fizzy.marveldroid.core.di.keys.KeyFragment
import organization.fizzy.marveldroid.core.factory.FactoryFragment
import organization.fizzy.marveldroid.view.screens.fragments.ViewRegistration
import organization.fizzy.marveldroid.view.screens.fragments.ViewAccount
import organization.fizzy.marveldroid.view.screens.fragments.ViewComics
import organization.fizzy.marveldroid.view.screens.fragments.ViewBookmarks
import organization.fizzy.marveldroid.view.screens.fragments.ViewSettings

/**
 * @author, evolandlupiz
 * @date, 5/12/2020
 * @property, MarvelDroid
 * @purpose, fragment module that defines which fragments will contribute to the dagger graph.
 */

@Module
interface ModuleFragment {
    @Binds
    fun bindFactoryFragment(factory: FactoryFragment): FragmentFactory

    @Binds
    @IntoMap
    @KeyFragment(ViewBookmarks::class)
    fun fragmentViewBookmarks(fragment: ViewBookmarks): Fragment

    @Binds
    @IntoMap
    @KeyFragment(ViewSettings::class)
    fun fragmentViewSettings(fragment: ViewSettings): Fragment

    @Binds
    @IntoMap
    @KeyFragment(ViewRegistration::class)
    fun fragmentViewRegistration(fragment: ViewRegistration): Fragment

    @Binds
    @IntoMap
    @KeyFragment(ViewAccount::class)
    fun fragmentViewAccount(fragment: ViewAccount): Fragment

    @Binds
    @IntoMap
    @KeyFragment(ViewComics::class)
    fun fragmentViewComics(fragment: ViewComics): Fragment
}