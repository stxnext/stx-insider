@file:JvmName("ActivityUtil")
//@file:JvmMultifileClass
package com.stxnext.stxinsider.util

import android.Manifest
import android.animation.LayoutTransition
import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import android.graphics.Color
import android.graphics.drawable.Drawable
import android.net.ConnectivityManager
import android.net.wifi.WifiManager
import android.support.design.widget.AppBarLayout
import android.support.design.widget.CollapsingToolbarLayout
import android.support.v4.app.ActivityCompat
import android.support.v4.content.ContextCompat
import android.support.v4.view.ViewCompat
import android.util.DisplayMetrics
import android.util.Log
import android.view.View
import android.widget.LinearLayout
import android.widget.Toast
import com.stxnext.stxinsider.R
import com.stxnext.stxinsider.SliderActivity
import okhttp3.OkHttpClient
import java.io.IOException
import kotlin.reflect.KClass

/**
 * Created by bkosarzycki on 22.02.16.
 */

class Util {
    companion object { val kViewsMap = mutableMapOf<String, MutableList<KViewEntry>> () }
}

fun WifiManager.getNetworkId(wifiManager: WifiManager, SSID: String): Int {
    val list = wifiManager.configuredNetworks
    if (list == null || list.size == 0)
        return -1

    for (i in list)
        if (i.SSID != null && i.SSID == "\"" + SSID + "\"")
            return i.networkId
    return -1
}


fun Activity.getAppVersion(activity: Activity): String {
    try {
        val manager = activity.packageManager
        return manager.getPackageInfo(activity.packageName, 0).versionName
    } catch (e: PackageManager.NameNotFoundException) { /* ignore */ }
    return "0.0.0"
}

/**
 * Inside activity display toast like this:
 *
 * this displayToast "This is a message"
 */
infix fun Activity.displayToast(txt : String) {
    Toast.makeText(this, txt, Toast.LENGTH_SHORT).show()
}

/**
 * Inside activity log errors like this:
 *
 * this loge "This is an error message"
 */
infix fun Activity.loge(txt : String) {
    Log.e(this.javaClass.simpleName, txt)
}

/**
 * this logw "This is a warning message"
 */
infix fun Activity.logw(txt : String) {
    Log.w(this.javaClass.simpleName, txt)
}

/**
 * Checks for active permission
 *
 * # Usage
 * if (this hasPermission Manifest.permission.READ_CONTACTS) {
 *     //do something here
 *  }
 */
infix fun Activity.hasPermission(permissionStr : String) : Boolean {
    if (ContextCompat.checkSelfPermission(this,
            permissionStr) != PackageManager.PERMISSION_GRANTED)
        return false

    return true
}

fun Util.isDeviceOnline(context: Context): Boolean {
    val connMgr = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    val networkInfo = connMgr.activeNetworkInfo
    return networkInfo != null && networkInfo.isConnected
}

fun Activity.isDeviceOnline(context: Context) = { Util().isDeviceOnline(context) }
fun OkHttpClient.isDeviceOnline(context: Context) = { Util().isDeviceOnline(context) }

/**
 * In-place forEach loop (discouraged in 1.0 release)
 */
infix fun <T> kotlin.collections.Iterable<T>.forEachLoop(action: (T) -> kotlin.Unit): kotlin.Unit {
    this.forEach { action }
}

/**
 * Fluent sort
 */
fun <T : kotlin.Comparable<T>> kotlin.collections.MutableList<T>.sortList(): MutableList<T> {
    sort()
    return this
}

/**
 * For-each fluent interface
 */
fun <T : kotlin.Comparable<T>> kotlin.collections.MutableList<T>.forEachList(action: (T) -> kotlin.Unit): MutableList<T> {
    for (elem in this)
        action.invoke(elem)
    return this
}


/**
 *   init {
 *      R.id.loginButton bind KClick(this, { v: View -> onLoginButtonClick(v) })
 *      R.id.logoutButton bind KClick(this, { v: View -> onLogoutButtonClick(v) })
 *   }
 *
 */
infix fun Int.bind(kClick: KClick): kotlin.Unit {
    var kViewEntries = Util.kViewsMap[(kClick.activity.javaClass.name)]
    if (kViewEntries == null)
        kViewEntries = mutableListOf()
    kViewEntries.add(KViewEntry(this, { v: View -> kClick.action.invoke(v) }) )
    Util.kViewsMap.put(kClick.activity.javaClass.name, kViewEntries)
}
data class KClick(val activity: Activity, val action: (View) -> kotlin.Unit)
data class KViewEntry(val id: Int, val action: (View) -> kotlin.Unit)
fun Activity.bindKViews() {
    for (mutEntry in Util.kViewsMap)
        if (mutEntry.key == this.javaClass.name) {
            val kViewEntries = mutEntry.value;
            for (kViewEntry in kViewEntries)
                findViewById(kViewEntry.id).setOnClickListener { v: View -> kViewEntry.action.invoke(v)  }

            Util.kViewsMap.remove(mutEntry.key)
        }
}

/**
 * Change color intensity:  Color.BLUE.colorIntensity(0.5f)
 */
fun Int.colorIntensity(factor: Float): Int {
    val a = Color.alpha(this)
    val r = Color.red(this)
    val g = Color.green(this)
    val b = Color.blue(this)

    return Color.argb(a,
            Math.max((r * factor).toInt(), 0),
            Math.max((g * factor).toInt(), 0),
            Math.max((b * factor).toInt(), 0))
}

fun Int.colorAlpha(alpha: Float): Int {
    return Color.argb((alpha*255.0).toInt(), Color.red(this), Color.green(this), Color.blue(this))
}

fun Color.fromIntVal(color: Int) : String {
    return String.format("#%06X", (0xFFFFFF and color));
}

fun Util.convertDpToPixel(dp: Float, context: Context): Float {
    val resources = context.resources
    val metrics: DisplayMetrics = resources.getDisplayMetrics();
    val px: Float = dp * (metrics.densityDpi / DisplayMetrics.DENSITY_DEFAULT);
    return px;
}

/**
 * Adds elevation to given element when used with collapsing toolbar layout.
 */
fun Activity.addElevationAnimationWhenScroll(appBar: AppBarLayout, mCollapsingToolbarLayout: CollapsingToolbarLayout,
                                             elementToAddElevation: LinearLayout) {
    val activity = this
    if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
        appBar.addOnOffsetChangedListener(object : AppBarLayout.OnOffsetChangedListener {
            override fun onOffsetChanged(appBarLayout: AppBarLayout, verticalOffset: Int) {
                val currentHeight = mCollapsingToolbarLayout.getHeight() + verticalOffset
                val startingElevationHeight: Float = 1.4f * ViewCompat.getMinimumHeight(mCollapsingToolbarLayout)
                Log.d(activity.javaClass.simpleName, "staring height: " + startingElevationHeight)
                if (currentHeight < startingElevationHeight) {
                    Log.d(activity.javaClass.simpleName, "Toolbar collapsed. offset is: " + verticalOffset + " current toolbarHeight is:" + mCollapsingToolbarLayout.getHeight() + " where minimum toolbar height is: " + ViewCompat.getMinimumHeight(mCollapsingToolbarLayout))
                    elementToAddElevation.elevation = getElevationForOffset(currentHeight, ViewCompat.getMinimumHeight(mCollapsingToolbarLayout), startingElevationHeight)
                } else {
                    Log.d(this.javaClass.simpleName, "Toolbar uncollapsed. offset is: " + verticalOffset + " current toolbarHeight is:" + mCollapsingToolbarLayout.getHeight() + " where minimum toolbar height is: " + ViewCompat.getMinimumHeight(mCollapsingToolbarLayout))
                    elementToAddElevation.elevation = Util().convertDpToPixel(0f, activity)
                }
            }
        })
    }
}

private fun Activity.getElevationForOffset(currentHeight: Int, destinationHeight: Int, startingHeight: Float): Float {
    val currentHeightDifference = currentHeight - destinationHeight
    val heightRange = startingHeight - destinationHeight
    val elevationLevel = 1 - (currentHeightDifference / heightRange)
    val destinationElevation = 3f
    return Util().convertDpToPixel(elevationLevel * destinationElevation, this)
}

/**
 * Sets animations when there are changes inside layout.
 */
fun Activity.setTransitionAnimationsForElementsLayout(resourceToSetAnimation: Int) {
    val elementsLayout = this.findViewById(resourceToSetAnimation) as LinearLayout
    val layoutTransition = LayoutTransition()
    // There is a need to disable animation when view disappears because it is badly implemented.
//    layoutTransition.disableTransitionType(LayoutTransition.CHANGE_DISAPPEARING)
    elementsLayout.layoutTransition = layoutTransition
}

//fun Drawable.loadImageDrawable() {
//    try {
//        val file = context.assets.open(teamCategoryHeader.imagePath)
//        val d = Drawable.createFromStream(file, null)
//        img.setImageDrawable(d)
//
//        if (teamCategoryHeader.background != null && !teamCategoryHeader.background!!.isEmpty()) {
//            val backgFile = context.assets.open(teamCategoryHeader.background)
//            val backDraw = Drawable.createFromStream(backgFile, null)
//            outerLL.background = backDraw
//        }
//    } catch (e: IOException) {
//        Log.e(TAG, "Cannot read image from assets: " + e.toString())
//    }
//}