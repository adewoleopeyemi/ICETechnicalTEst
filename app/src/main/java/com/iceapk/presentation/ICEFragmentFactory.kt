package com.iceapk.presentation

import androidx.fragment.app.FragmentFactory
import com.iceapk.presentation.login.LoginFragment
import kotlinx.coroutines.ExperimentalCoroutinesApi
import javax.inject.Inject
import javax.inject.Singleton

@ExperimentalCoroutinesApi
@Singleton
class ICEFragmentFactory
@Inject
constructor(
): FragmentFactory() {

    override fun instantiate(classLoader: ClassLoader, className: String) =
        when(className){
            LoginFragment::class.java.name -> {
                LoginFragment()
            }

            else -> {
                super.instantiate(classLoader, className)
            }
        }
}