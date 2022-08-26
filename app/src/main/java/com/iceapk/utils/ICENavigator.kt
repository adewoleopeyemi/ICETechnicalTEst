package com.iceapk.utils

import android.os.Bundle
import android.view.View
import androidx.navigation.Navigation

object ICENavigator {
    fun navigate(view: View, destination: Int, bundle: Bundle? = null){
        val navController = Navigation.findNavController(view)
        navController.navigate(destination, bundle)
    }
}