/*
 * 2020 MarvelDroid , Attribution Images belong to Marvel Entertainment
 * Source Timestamps exist under: https://github.com/PabloRosas17
 * Written by Jose Pablo Rosas  <pablo.rosas.0170@gmail.com>, January 2020
 */

package organization.fizzy.marveldroid.core.factory

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentFactory
import javax.inject.Inject
import javax.inject.Provider
import javax.inject.Singleton

/**
 * @author, evolandlupiz
 * @date, 5/12/2020
 * @property, MarvelDroid
 * @purpose, Generate factory for Fragment.
 */

/**
 * Dagger bindings require factory type, this factory ensures
 * that fragments generated through factory are of this type.
 * @return Factory required by Dagger for Fragments.
 */
@Singleton
class FactoryFragment @Inject constructor(
    private val provider: Map<Class<out Fragment>
            , @JvmSuppressWildcards Provider<Fragment>>) : FragmentFactory() {

    /**
     * serve a fragment.
     */
    override fun instantiate(classLoader: ClassLoader, className: String): Fragment {
        val loader = loadFragmentClass(classLoader,className)
        val provider = provider[loader]
        return if(provider != null){
            provider.get()
        } else{
            super.instantiate(classLoader, className)
        }
    }
}