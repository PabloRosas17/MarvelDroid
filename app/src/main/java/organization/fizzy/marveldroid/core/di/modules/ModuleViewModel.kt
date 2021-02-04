/*
 * 2020 MarvelDroid , Attribution Images belong to Marvel Entertainment
 * Source Timestamps exist under: https://github.com/PabloRosas17
 * Written by Jose Pablo Rosas  <pablo.rosas.0170@gmail.com>, January 2020
 */

package organization.fizzy.marveldroid.core.di.modules

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap
import organization.fizzy.marveldroid.controller.viewmodels.*
import organization.fizzy.marveldroid.core.di.keys.KeyViewModel
import organization.fizzy.marveldroid.core.factory.FactoryViewModel

/**
 * @author, evolandlupiz
 * @date, 5/12/2020
 * @property, MarvelDroid
 * @purpose, Binding module to make the view-models available and firebase instances.
 */

@Module
abstract class ModuleViewModel {

    @Binds
    abstract fun bindFactoryViewModel(factory: FactoryViewModel): ViewModelProvider.Factory

    @Binds
    @IntoMap
    @KeyViewModel(ViewModelAccount::class)
    abstract fun provideViewModelAccount(mViewModelAccount: ViewModelAccount): ViewModel

    @Binds
    @IntoMap
    @KeyViewModel(ViewModelBookmarks::class)
    abstract fun provideViewModelBookmarks(mViewModelBookmarks: ViewModelBookmarks): ViewModel

    @Binds
    @IntoMap
    @KeyViewModel(ViewModelComics::class)
    abstract fun provideViewModelComics(mViewModelComics: ViewModelComics): ViewModel

    @Binds
    @IntoMap
    @KeyViewModel(ViewModelRegistration::class)
    abstract fun provideViewModelRegistration(mViewModelRegistration: ViewModelRegistration): ViewModel

    @Binds
    @IntoMap
    @KeyViewModel(ViewModelSettings::class)
    abstract fun provideViewModelSettings(mViewModelSettings: ViewModelSettings): ViewModel
}