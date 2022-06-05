package com.example.quecomohoy.ui.scanIngredients

import android.Manifest
import android.app.Activity
import android.content.ContentValues.TAG
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat.checkSelfPermission
import androidx.fragment.app.Fragment
import com.example.quecomohoy.databinding.FragmentScanIngredientsBinding
import java.io.File
import java.io.IOException


class ScanIngredientsFragment: Fragment() {
    private var _binding: FragmentScanIngredientsBinding? = null
    private val binding get() = _binding!!

    companion object {
        private val REQUEST_GALLERY_IMAGE = 100
        private val REQUEST_PERMISSIONS = 13;
        private const val PICK_FROM_GALLERY = 1
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentScanIngredientsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
            binding.selectImageButton.setOnClickListener {
                if (checkSelfPermission(requireContext(), Manifest.permission.READ_EXTERNAL_STORAGE)==PackageManager.PERMISSION_DENIED){
                    val permissions = arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE)
                    requestPermissions(permissions, PICK_FROM_GALLERY)
                } else{
                    chooseImageGallery();
                }
            }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        when(requestCode){
            PICK_FROM_GALLERY -> {
                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    chooseImageGallery()
                }else{
                    Toast.makeText(requireContext(),"Permiso de camara denegado", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun chooseImageGallery() {
        val intent = Intent(Intent.ACTION_GET_CONTENT)
        intent.type = "image/*"
        startActivityForResult(
            Intent.createChooser(intent, "Select an image"), REQUEST_GALLERY_IMAGE)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if(requestCode == REQUEST_GALLERY_IMAGE && resultCode == Activity.RESULT_OK && data != null){
            performCloudVisionRequest(data.getData());
        }
        else {
            super.onActivityResult(requestCode, resultCode, data)
        }
    }

    private fun performCloudVisionRequest(uri: Uri?) {
        if (uri != null) {
            try {
                val bitmap: Bitmap = MediaStore.Images.Media.getBitmap(requireActivity().getApplicationContext().getContentResolver(), uri)
                // callCloudVision(bitmap)
                binding.selectedImage.setImageBitmap(bitmap)
            } catch (e: IOException) {
                Log.e(TAG, e.localizedMessage)
            }
        }
    }
}

private lateinit var filePhoto: File
private const val FILE_NAME = "photo.jpg"