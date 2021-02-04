/*
 * 2020 MarvelDroid , Attribution Images belong to Marvel Entertainment
 * Source Timestamps exist under: https://github.com/PabloRosas17
 * Written by Jose Pablo Rosas  <pablo.rosas.0170@gmail.com>, January 2020
 */

package organization.fizzy.marveldroid.core.di.modules

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.functions.FirebaseFunctions
import com.google.firebase.messaging.FirebaseMessaging
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

/**
 * @author, evolandlupiz
 * @date, 5/12/2020
 * @property, MarvelDroid
 * @purpose, Module that will provide firebase.
 */

@Module
class ModuleFirebase {

    /**
     * FirebaseAuth.
     */
    @Singleton
    @Provides
    fun provideFirebaseAuth(): FirebaseAuth {
        return FirebaseAuth.getInstance()
    }

    /**
     * FirebaseDatabase.
     */
    @Provides
    @Singleton
    fun provideFirebaseDatabase(): FirebaseDatabase = FirebaseDatabase.getInstance()

    /**
     * DatabaseReference.
     */
    @Provides
    @Singleton
    fun provideDatabaseReference(): DatabaseReference = FirebaseDatabase.getInstance().reference

    /**
     * FirebaseCloudFunctions.
     */
    @Provides
    @Singleton
    fun provideFirebaseCloudFunctions(): FirebaseFunctions = FirebaseFunctions.getInstance()

    /**
     * FirebaseCloudMessages.
     */
    @Provides
    @Singleton
    fun provideFirebaseCloudMessaging(): FirebaseMessaging = FirebaseMessaging.getInstance()
}