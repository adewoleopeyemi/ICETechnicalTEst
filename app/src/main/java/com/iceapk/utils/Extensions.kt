package com.iceapk.utils

import android.content.Context
import android.util.Log
import android.view.View.VISIBLE
import android.view.animation.AnimationUtils
import android.widget.ImageView
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.example.iceapk.R
import java.security.KeyFactory
import java.security.PrivateKey
import java.security.interfaces.RSAPublicKey
import java.security.spec.PKCS8EncodedKeySpec
import java.security.spec.X509EncodedKeySpec
import java.text.SimpleDateFormat
import java.util.*
import java.util.Base64.getDecoder
import javax.crypto.Cipher

fun ImageView.loadCircularImage(int: Int){
    Glide
        .with(context)
        .load(int)
        .apply(
            RequestOptions()
                .circleCrop()
                .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
                .centerCrop()
        )
        .apply(RequestOptions.circleCropTransform())
        .into(this)
}

fun ImageView.loadCircularImage(url:String){
    Glide
        .with(context)
        .load(url)
        .apply(
            RequestOptions()
                .circleCrop()
                .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
                .centerCrop()
        )
        .apply(RequestOptions.circleCropTransform())
        .into(this)
}


fun String.saveInSharedPreference(context: Context, key: String){
    val preference=context.getSharedPreferences(context.resources.getString(R.string.app_name), Context.MODE_PRIVATE)
    val editor=preference.edit()
    editor.putString(key,this)
    editor.apply()
}

fun Int.saveInSharedPreference(context: Context, key:String){
    val preference=context.getSharedPreferences(context.resources.getString(R.string.app_name), Context.MODE_PRIVATE)
    val editor=preference.edit()
    editor.putInt(key,this)
    editor.apply()
}

fun Boolean.saveInSharedPreference(context: Context, key:String){
    val preference=context.getSharedPreferences(context.resources.getString(R.string.app_name), Context.MODE_PRIVATE)
    val editor=preference.edit()
    editor.putBoolean(key,this)
    editor.apply()
}

fun Float.calculateTotalTransactionAmount(): Float{
    val increments = 5000
    val baseFees = 100
    val transactionFees = (this/increments) * baseFees
    return transactionFees + this
}

fun getStringInSharedPreference(context: Context, key: String):String{
    val preference=context.getSharedPreferences(context.resources.getString(R.string.app_name), Context.MODE_PRIVATE)
    return preference.getString(key,"")!!
}

fun getIntInSharedPreference(context: Context, key: String): Int{
    val preference=context.getSharedPreferences(context.resources.getString(R.string.app_name), Context.MODE_PRIVATE)
    return preference.getInt(key,-100000000)
}

fun getBooleanInSharedPreference(context: Context, key: String): Boolean{
    val preference=context.getSharedPreferences(context.resources.getString(R.string.app_name), Context.MODE_PRIVATE)
    return preference.getBoolean(key, false)
}

interface onPinclicked{
    fun onPressed(digit: String)
    fun onComplete(pin: String)
}

fun getCurrentDate(): String {
    val sdf = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
    return sdf.format(Date())
}

fun getCurrentTime(): String{
    val sdf = SimpleDateFormat("hh:mm", Locale.getDefault())
    return sdf.format(Date())
}

fun <T> LiveData<T>.reobserve(owner: LifecycleOwner, func: (T?) -> (Unit)) {
    removeObservers(owner)
    observe(owner, Observer<T> { t -> func(t) })
}

fun distanceTo(lat1: Double, lon1: Double, lat2: Double, lon2: Double): Double {
    val theta = lon1 - lon2
    var dist = (Math.sin(deg2rad(lat1))
            * Math.sin(deg2rad(lat2))
            + (Math.cos(deg2rad(lat1))
            * Math.cos(deg2rad(lat2))
            * Math.cos(deg2rad(theta))))
    dist = Math.acos(dist)
    dist = rad2deg(dist)
    dist = dist * 60 * 1.1515
    return dist * 1.609344
}



private fun deg2rad(deg: Double): Double {
    return deg * Math.PI / 180.0
}

private fun rad2deg(rad: Double): Double {
    return rad * 180.0 / Math.PI
}


