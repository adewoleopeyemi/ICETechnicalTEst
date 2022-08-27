package com.iceapk.presentation

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.location.LocationManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.Settings
import android.view.View
import android.view.Window
import android.view.animation.*
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.example.iceapk.R
import com.example.iceapk.databinding.ActivityMainBinding
import com.iceapk.utils.GlideToast
import com.iceapk.utils.UIController
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import javax.inject.Inject

@AndroidEntryPoint
@ExperimentalCoroutinesApi
@FlowPreview
class MainActivity : AppCompatActivity(), UIController {

    private lateinit var binding: ActivityMainBinding

    @Inject
    lateinit var fragmentFactory: ICEFragmentFactory
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportFragmentManager.fragmentFactory = ICEFragmentFactory()
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        setNavController()
        supportActionBar!!.hide()
    }

    override fun displayProgressBar(isDisplayed: Boolean, message: String?) {
        TODO("Not yet implemented")
    }

    override fun hideSoftKeyboard() {
        TODO("Not yet implemented")
    }

    override fun onBack() {
        onBackPressed()
    }

    override fun hideBottomNav(hide: Boolean) {
        if (!hide) {
            binding.bottomNav.visibility = View.VISIBLE
        } else {
            binding.bottomNav.visibility = View.GONE
        }
    }

    override fun setBottomNavColor(color: Int) {
        if (color == R.color.other_purple) {
            binding!!.bottomNav.background = resources.getDrawable(R.color.other_purple)
            binding!!.bottomNav.setItemIconTintList(resources.getColorStateList(R.color.bottom_nav_purple_selector))
        } else {
            binding!!.bottomNav.background = resources.getDrawable(R.color.white)
            binding!!.bottomNav.setItemIconTintList(resources.getColorStateList(R.color.bottom_navigation_selector))
        }
    }

    override fun isPermissionGranted() = ActivityCompat.checkSelfPermission(
        this,
        Manifest.permission.ACCESS_FINE_LOCATION
    ) == PackageManager.PERMISSION_GRANTED &&
            ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
        this,
        Manifest.permission.ACCESS_BACKGROUND_LOCATION
    ) == PackageManager.PERMISSION_GRANTED

    override fun requestPermissions() {
        if (!isPermissionGranted()) {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_COARSE_LOCATION,
                    Manifest.permission.ACCESS_BACKGROUND_LOCATION
                ),
                LOCATION_REQUEST
            )
        }
    }

    override fun isLocationOn(): Boolean {
        val locationManager: LocationManager =
            getSystemService(Context.LOCATION_SERVICE) as LocationManager
        return locationManager.isProviderEnabled((LocationManager.GPS_PROVIDER)) ||
                locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)
    }

    override fun startLocationActivity() {
        val intent = Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS)
        startActivity(intent)
    }

    override fun shouldShowRequestPermissionRationale() =
        ActivityCompat.shouldShowRequestPermissionRationale(
            this,
            Manifest.permission.ACCESS_FINE_LOCATION
        ) && ActivityCompat.shouldShowRequestPermissionRationale(
            this,
            Manifest.permission.ACCESS_COARSE_LOCATION
        )

    override fun statusBarColor(color: Int, darkText: Boolean) {
        val window: Window = getWindow()


        // change the color to black
        if (!darkText) {
            window.decorView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);//  set status text dark
        } else {
            window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_VISIBLE
        }


        // finally change the color
        window.statusBarColor = ContextCompat.getColor(this, color)
    }

    override fun fadeInView(view: View, duration: Long) {
        val fadeIn = AlphaAnimation(0f, 1f)
        fadeIn.interpolator = DecelerateInterpolator() //add this
        fadeIn.duration = duration

        val animation = AnimationSet(false) //change to false
        animation.addAnimation(fadeIn)
        view.animation = animation
    }

    override fun fadeOutView(view: View, duration: Long) {
        val fadeOut = AlphaAnimation(1f, 0f)
        fadeOut.interpolator = AccelerateInterpolator() //and this
        fadeOut.startOffset = 1000
        fadeOut.duration = duration

        val animation = AnimationSet(false) //change to false
        animation.addAnimation(fadeOut)
        view.animation = animation
    }

    override fun slideUp(view: View, duration: Long) {
        view.visibility = View.VISIBLE
        val animate = TranslateAnimation(
            0f,  // fromXDelta
            0f,  // toXDelta
            view.height.toFloat(),  // fromYDelta
            0f
        ) // toYDelta

        animate.setDuration(duration)
        animate.setFillAfter(true)
        view.startAnimation(animate)
    }

    override fun slideDown(view: View, duration: Long, hide: View) {
        val animate = TranslateAnimation(
            0f,  // fromXDelta
            0f,  // toXDelta
            0f,
            view.height.toFloat()  // fromYDelta
        ) // toYDelta

        animate.setDuration(duration)
        animate.setFillAfter(true)
        animate.setAnimationListener(object : Animation.AnimationListener {
            override fun onAnimationStart(p0: Animation?) {

            }

            override fun onAnimationEnd(p0: Animation?) {
                hide.visibility = View.GONE
            }

            override fun onAnimationRepeat(p0: Animation?) {

            }

        })
        view.startAnimation(animate)
    }

    override fun showToast(
        backGroundColor: Int,
        textColor: Int,
        viewColor: Int,
        textString: String
    ) {
        GlideToast.makeToast(
            this,
            textString,
            GlideToast.LENGTHLONG,
            GlideToast.CUSTOMTOAST,
            GlideToast.TOP,
            R.drawable.ic_home,
            backGroundColor,
            textColor
        ).show();
    }

    override fun showAcceptMoneyDialog() {

    }

    override fun destroyApp() {
        finishAffinity()
    }

    private fun setNavController() {
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.fragmentContainerView) as NavHostFragment
        binding.bottomNav.setupWithNavController(navHostFragment.navController)
    }

    override fun onStop() {
        super.onStop()
    }

    override fun onDestroy() {
        super.onDestroy()

    }
    companion object{
        const val LOCATION_REQUEST = 100
    }
}