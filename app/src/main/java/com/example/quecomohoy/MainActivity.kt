package com.example.quecomohoy

import android.Manifest
import android.app.*
import android.content.IntentFilter
import android.content.pm.PackageManager
import android.graphics.drawable.ColorDrawable
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.quecomohoy.notifications.DINNER_CHANNEL_ID
import com.example.quecomohoy.notifications.LUNCH_CHANNEL_ID
import com.example.quecomohoy.notifications.LUNCH_NOTIFICATION_ID
import com.example.quecomohoy.notifications.MealRecommendationsNotification
import com.example.quecomohoy.ui.camera.CameraFragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import java.util.*
import java.util.Calendar.*


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottomNavigationView)
        val navController = findNavController(R.id.fragment)
        val appBarConfiguration = AppBarConfiguration(setOf(R.id.recomendationsFragment, R.id.searchFragment, R.id.favouritesFragment, R.id.profileFragment))
        setupActionBarWithNavController(navController, appBarConfiguration)
        bottomNavigationView.setupWithNavController(navController)
        supportActionBar!!.setBackgroundDrawable(ColorDrawable(resources.getColor(R.color.white)))
        notShowBottomNavBarAndActionBarInFragments(navController, bottomNavigationView)
        createNotificationChannel()
        val now = Calendar.getInstance()
        now[SECOND] = now[SECOND] + 20
        MealRecommendationsNotification.scheduleNotification(applicationContext, LUNCH_CHANNEL_ID, LUNCH_NOTIFICATION_ID, now.timeInMillis)
    }

    private fun notShowBottomNavBarAndActionBarInFragments(navController:NavController, bottomNavigationView:BottomNavigationView){

        navController.addOnDestinationChangedListener { _, destination, _ ->
            bottomNavigationView.visibility =
                if(destination.id == R.id.loginFragment || destination.id == R.id.registrationFragment
                    || destination.id == R.id.cameraFragment) {
                View.GONE
            } else {
                View.VISIBLE
            }
        }
        navController.addOnDestinationChangedListener { _, destination, _ ->
            supportActionBar!!.hide()
        }
    }


    //estas funciones para pedir permisos parecen necesitar estar en el activity, intente ponerlas en el fragment pero rompia las bolas
    //igual no intente mucho, seguro que se puede
    fun allPermissionsGranted() = REQUIRED_PERMISSIONS.all {
        ContextCompat.checkSelfPermission(
            baseContext, it) == PackageManager.PERMISSION_GRANTED
    }

    lateinit var CameraFragmentCallback: CameraFragment;//sospechoso
    fun requestCameraPermissions(callback: CameraFragment){
        CameraFragmentCallback=callback
        ActivityCompat.requestPermissions(this, MainActivity.REQUIRED_PERMISSIONS, MainActivity.REQUEST_CODE_PERMISSIONS)
    }
    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == REQUEST_CODE_PERMISSIONS) {
            if (allPermissionsGranted()) {
                CameraFragmentCallback.startCamera()
            } else {
                CameraFragmentCallback.onNoPermission()
            }
        }
    }
    companion object {
        private const val REQUEST_CODE_PERMISSIONS = 10
        private val REQUIRED_PERMISSIONS =
            mutableListOf (
                Manifest.permission.CAMERA
            ).apply {
                if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.P) {
                    add(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                }
            }.toTypedArray()
    }

    private fun createNotificationChannel()
    {
        val notificationManager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager

        // The id of the group.
        val groupId = "recomendations"
        val groupName = "Recomendaciones"

        var group = notificationManager.getNotificationChannelGroup(groupId)
        if(group == null){
            group = NotificationChannelGroup(groupId, groupName)
            notificationManager.createNotificationChannelGroup(group)
        }

        if(notificationManager.getNotificationChannel(LUNCH_CHANNEL_ID) == null){
            val name = getString(R.string.lunch_channel)
            val desc = getString(R.string.lunch_channel_desc)
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel = NotificationChannel(LUNCH_CHANNEL_ID, name, importance)
            channel.group = groupId
            channel.description = desc
            notificationManager.createNotificationChannel(channel)
        }

        if(notificationManager.getNotificationChannel(DINNER_CHANNEL_ID) == null){
            val name = getString(R.string.dinner_channel)
            val desc = getString(R.string.dinner_channel_desc)
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel = NotificationChannel(DINNER_CHANNEL_ID, name, importance)
            channel.group = groupId
            channel.description = desc
            notificationManager.createNotificationChannel(channel)
        }
    }

    //necesito una forma de comunicar fragments
    //segun lei puedo hacer un viewmodel o un bundle pero agregaban bastante complejidad al pedo esto es mas simple y anda
    public var imageTakenUri:Uri? = null;
}