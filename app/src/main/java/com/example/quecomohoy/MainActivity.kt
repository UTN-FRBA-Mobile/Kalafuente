package com.example.quecomohoy

import android.graphics.drawable.ColorDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import android.Manifest
import android.content.pm.PackageManager
import android.net.Uri
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import android.os.Build
import com.example.quecomohoy.ui.camera.CameraFragment


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

    }

    private fun notShowBottomNavBarAndActionBarInFragments(navController:NavController, bottomNavigationView:BottomNavigationView){
        navController.addOnDestinationChangedListener { _, destination, _ ->
            if( destination.id == R.id.loginFragment ||
                destination.id == R.id.registrationFragment ||
                destination.id == R.id.cameraFragment ||
                destination.id == R.id.recipeViewFragment) {
                bottomNavigationView.visibility=View.GONE
                supportActionBar!!.hide()
            } else {
                bottomNavigationView.visibility=View.VISIBLE
                supportActionBar!!.show()
            }
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

    //necesito una forma de comunicar fragments
    //segun lei puedo hacer un viewmodel o un bundle pero agregaban bastante complejidad al pedo esto es mas simple y anda
    public var imageTakenUri:Uri? = null;
}