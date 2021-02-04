/*
 * 2020 MarvelDroid , Attribution Images belong to Marvel Entertainment
 * Source Timestamps exist under: https://github.com/PabloRosas17
 * Written by Jose Pablo Rosas  <pablo.rosas.0170@gmail.com>, January 2020
 */

package organization.fizzy.marveldroid.core.di.components

import android.app.Activity
import android.app.Application
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentFactory
import dagger.BindsInstance
import dagger.Component
import organization.fizzy.marveldroid.core.MarvelDroidApplication
import organization.fizzy.marveldroid.core.di.modules.*
import organization.fizzy.marveldroid.view.ViewMainActivity
import organization.fizzy.marveldroid.view.screens.fragments.ViewAccount
import organization.fizzy.marveldroid.view.screens.fragments.ViewComics
import organization.fizzy.marveldroid.view.screens.fragments.ViewRegistration
import organization.fizzy.marveldroid.view.screens.fragments.ViewBookmarks
import organization.fizzy.marveldroid.view.screens.fragments.ViewSettings
import javax.inject.Singleton

/**
 * @author, evolandlupiz
 * @date, 5/12/2020
 * @property, MarvelDroid
 * @purpose, Define component modules for injection
 */

/**
 * Components that will be used to map during builders lifecycle.
 */
@Component(
    modules = [
        ModuleFragment::class
        , ModuleViewModel::class
        , ModuleNetwork::class
        , ModuleService::class
        , ModuleFirebase::class
        , ModuleInitializer::class
    ]
)

/**
 * Contract for the application to generate components.
 */
@Singleton
interface ComponentApplication {

    /**
     * builder that builds an application.
     */
    @Component.Builder
    interface Builder{

        /**
         * builder for the application.
         */
        @BindsInstance
        fun application(application: Application): Builder

        /**
         * builder for the firebase.
         */
        @BindsInstance
        fun firemodule(firemodule: ModuleFirebase): Builder

        /**
         * builder component.
         */
        fun build(): ComponentApplication
    }

    /**
     * injection function used to inject a application from the factory.
     */
    fun inject(mMarvelDroidApplication: MarvelDroidApplication)

    /**
     * injection definition for activities.
     */
    fun inject(mActivity: Activity)

    /**
     * injection definition for fragments.
     */
    fun inject(mFragment: Fragment)

    /**
     * injection for navigation component types.
     */
    fun inject(mViewMainActivity: ViewMainActivity)

    /**
     * injection function used to inject fragments.
     */
    fun inject(mViewBookmarks: ViewBookmarks)
    fun inject(mViewSettings: ViewSettings)
    fun inject(mViewRegistration: ViewRegistration)
    fun inject(mViewAccount: ViewAccount)
    fun inject(mViewComics: ViewComics)

    /**
     * injection function used to inject a fragment from the factory.
     */
    fun fragmentFactory(): FragmentFactory
}