/*
 * 2020 MarvelDroid , Attribution Images belong to Marvel Entertainment
 * Source Timestamps exist under: https://github.com/PabloRosas17
 * Written by Jose Pablo Rosas  <pablo.rosas.0170@gmail.com>, January 2020
 */

package organization.fizzy.marveldroid.core.di.keys

import androidx.lifecycle.ViewModel
import dagger.MapKey
import kotlin.reflect.KClass

/**
 * @author, evolandlupiz
 * @date, 5/12/2020
 * @property, MarvelDroid
 * @purpose, Resolve the type ViewModel at runtime during Dagger graph mapping.
 */

/**
 * Reflection technique used to determine a type at runtime.
 */
@Target(AnnotationTarget.FUNCTION, AnnotationTarget.PROPERTY_GETTER, AnnotationTarget.PROPERTY_SETTER)
@Retention(AnnotationRetention.RUNTIME)
@MapKey
annotation class KeyViewModel(val value: KClass<out ViewModel>)