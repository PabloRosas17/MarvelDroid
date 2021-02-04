/*
 * 2020 MarvelDroid , Attribution Images belong to Marvel Entertainment
 * Source Timestamps exist under: https://github.com/PabloRosas17
 * Written by Jose Pablo Rosas  <pablo.rosas.0170@gmail.com>, January 2020
 */

package organization.fizzy.marveldroid.core.di.modules

import dagger.Module
import dagger.Provides
import organization.fizzy.marveldroid.tools.services.api.FirebaseService
import organization.fizzy.marveldroid.tools.services.api.MarvelService
import retrofit2.Retrofit
import javax.inject.Named
import javax.inject.Singleton

/**
 * @author, evolandlupiz
 * @date, 5/12/2020
 * @property, MarvelDroid
 * @purpose, Bind the network module to an appropriate service.
 */

@Module(includes = [ModuleNetwork::class])
class ModuleService{

    /**
     * Service for [FirebaseService]
     */
    @Provides
    @Singleton
    fun serviceFirebase(@Named("networkRetrofitFirebase") nrf: Retrofit): FirebaseService {
        return nrf.create(FirebaseService::class.java)
    }

    /**
     * Service for [MarvelService]
     */
    @Provides
    @Singleton
    fun serviceMarvel(@Named("networkRetrofitMarvel") nrm: Retrofit): MarvelService {
        return nrm.create(MarvelService::class.java)
    }
}