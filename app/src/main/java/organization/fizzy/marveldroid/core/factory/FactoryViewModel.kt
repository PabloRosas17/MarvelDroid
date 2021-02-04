/*
 * 2020 MarvelDroid , Attribution Images belong to Marvel Entertainment
 * Source Timestamps exist under: https://github.com/PabloRosas17
 * Written by Jose Pablo Rosas  <pablo.rosas.0170@gmail.com>, January 2020
 */

package organization.fizzy.marveldroid.core.factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import javax.inject.Inject
import javax.inject.Provider
import javax.inject.Singleton

/**
 * @author, evolandlupiz
 * @date, 5/12/2020
 * @property, MarvelDroid
 * @purpose, Factory that will generate view models.
 */

/**
 * Factory responsible for instantiating view-model.
 * @return instance of view-model requested.
 */
@Singleton
class FactoryViewModel @Inject constructor(
    creator: Map<Class<out ViewModel>
            , @JvmSuppressWildcards Provider<ViewModel>>): ViewModelProvider.Factory{

    /**
     * Map with current view-models stored.
     * @return Map<*,*>
     */
    private var creators: Map<Class<out ViewModel>,Provider<ViewModel>> = creator

    /**
     * Generates a view-model.
     * @return ViewModel of type [T]
     * @suppress [T] as it is in fact the generic type requested.
     * @throws RuntimeException if view-model isn't a registered type.
     */
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        var creator = this.creators[modelClass]

        if(creator == null){
            this.creators.forEach lift@ {
                if(modelClass.isAssignableFrom(it.key)){
                    creator = it.value
                    return@lift
                }
            }
        }

        try {
            /* this generic is in fact of ViewModel type, suppress the warning */
            @Suppress("unchecked_cast") return creator?.get() as T
        } catch (e: Exception){
            throw RuntimeException("FactoryViewModel.kt, unknown model class: $modelClass , e: $e")
        }
    }
}