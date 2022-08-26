package com.iceapk.utils

import android.view.View

interface UIController {

    fun displayProgressBar(isDisplayed: Boolean, message: String? = null)

    fun hideSoftKeyboard()

    fun onBack()

    fun hideBottomNav(hide: Boolean)

    fun setBottomNavColor(color: Int)

    fun isPermissionGranted(): Boolean

    fun requestPermissions()

    fun isLocationOn(): Boolean

    fun startLocationActivity()

    fun shouldShowRequestPermissionRationale(): Boolean

    fun statusBarColor(color: Int, lightText: Boolean)

    fun fadeInView(view: View, duration: Long)

    fun fadeOutView(view: View, duration: Long)

    fun slideUp(view: View, duration: Long)

    fun slideDown(view: View, duration: Long, hide: View)

    fun showToast(backGroundColor: Int, textColor: Int, viewColor: Int, text: String)

    fun showAcceptMoneyDialog()

    fun destroyApp()
}
