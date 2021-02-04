/*
 * 2020 MarvelDroid , Attribution Images belong to Marvel Entertainment
 * Source Timestamps exist under: https://github.com/PabloRosas17
 * Written by Jose Pablo Rosas  <pablo.rosas.0170@gmail.com>, January 2020
 */

package organization.fizzy.marveldroid.core

import android.app.Application
import organization.fizzy.marveldroid.core.di.components.ComponentApplication
import organization.fizzy.marveldroid.core.di.components.DaggerComponentApplication
import organization.fizzy.marveldroid.core.di.modules.ModuleFirebase

/**
 * @author, evolandlupiz
 * @date, 5/17/2020
 * @property, MarvelDroid
 * @purpose, Application module.
 */

/**
 * @desc MarvelDroidApplication will generate an application that is dependency-injection ready.
 */
class MarvelDroidApplication : Application() {

    /**
     * @return application component
     */
    lateinit var mComponent: ComponentApplication
        private set

    /**
     * Initial module declares injection components for the Application, then build the Application.
     */
    override fun onCreate() {
        super.onCreate()
        this.mComponent = DaggerComponentApplication.builder()
            .application(this)
            .firemodule(ModuleFirebase())
            .build()
    }
}