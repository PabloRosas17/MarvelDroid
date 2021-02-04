/*
 * 2020 MarvelDroid , Attribution Images belong to Marvel Entertainment
 * Source Timestamps exist under: https://github.com/PabloRosas17
 * Written by Jose Pablo Rosas  <pablo.rosas.0170@gmail.com>, January 2020
 */

package organization.fizzy.marveldroid.core.di.modules

import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Named
import javax.inject.Singleton

/**
 * @author, evolandlupiz
 * @date, 5/12/2020
 * @property, MarvelDroid
 * @purpose, Define network modules.
 */

@Module
class ModuleNetwork {

    /**
     * Builder for [OkHttpClient]
     */
    @Provides
    @Singleton
    fun networkOkHttp(): OkHttpClient {
        return OkHttpClient.Builder().build()
    }

    /**
     * Builder for [networkRetrofitFirebase]
     */
    @Provides
    @Singleton
    @Named("networkRetrofitFirebase")
    fun networkRetrofitFirebase(okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .client(okHttpClient)
            .baseUrl(BASE_URL_FIREBASE)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    /**
     * Builder for [networkRetrofitMarvel]
     */
    @Provides
    @Singleton
    @Named("networkRetrofitMarvel")
    fun networkRetrofitMarvel(okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .client(okHttpClient)
            .baseUrl(BASE_URL_MARVEL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    /**
     * Base URL's for the network builders, tightly couple these into this class.
     */
    companion object {
        const val BASE_URL_FIREBASE = "https://marveldroid-firebase.firebaseio.com/"
        const val BASE_URL_MARVEL = "http://gateway.marvel.com/"
    }
}