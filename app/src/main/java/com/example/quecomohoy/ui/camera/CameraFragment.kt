package com.example.quecomohoy.ui.camera

import android.annotation.SuppressLint
import android.content.ContentValues
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.camera.core.*
import androidx.camera.core.ImageCapture.FLASH_MODE_ON
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.fragment.app.Fragment
import com.example.quecomohoy.databinding.FragmentCameraBinding
import java.util.*
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors
import androidx.core.content.ContextCompat
import androidx.navigation.fragment.findNavController
import com.example.quecomohoy.MainActivity
import java.text.SimpleDateFormat
import com.example.quecomohoy.R



class CameraFragment : Fragment() {

    private var _binding: FragmentCameraBinding? = null

    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCameraBinding.inflate(inflater, container, false)

        val activity=(activity as MainActivity?)!!
        // Request camera permissions
        if (activity.allPermissionsGranted()) {
            startCamera()
        } else {
            activity.requestCameraPermissions(this);
        }
        cameraExecutor = Executors.newSingleThreadExecutor()

        binding.imageCaptureButton.setOnClickListener { takePhoto() }

        return binding.root
    }

    override fun onDestroy() {
        super.onDestroy()
        cameraExecutor.shutdown()
    }

    private var imageCapture: ImageCapture? = null

    private lateinit var cameraExecutor: ExecutorService

    fun onNoPermission(){
        findNavController().navigate(R.id.action_cameraFragment_to_askPermissionFragment)
    }

    @SuppressLint("RestrictedApi")
    fun startCamera() {
        val activity=(activity as MainActivity?)!!

        val cameraProviderFuture = ProcessCameraProvider.getInstance(activity)

        cameraProviderFuture.addListener({
            // Used to bind the lifecycle of cameras to the lifecycle owner
            val cameraProvider: ProcessCameraProvider = cameraProviderFuture.get()
            // Preview
            val preview = Preview.Builder()
                .build()
                .also {
                    it.setSurfaceProvider(binding.viewFinder.surfaceProvider)
                }

            imageCapture = ImageCapture.Builder()
                //.setDefaultResolution(android.util.Size(240,240))
                //.setFlashMode(FLASH_MODE_ON)
                .setJpegQuality(15)
                .build()
            // Select back camera as a default
            val cameraSelector = CameraSelector.DEFAULT_BACK_CAMERA

            try {
                // Unbind use cases before rebinding
                cameraProvider.unbindAll()

                // Bind use cases to camera
                cameraProvider.bindToLifecycle(
                    this, cameraSelector, preview, imageCapture)

            } catch(exc: Exception) {
                Toast.makeText(activity, "ERROR ABRIENDO LA CAMARA", Toast.LENGTH_SHORT).show()
            }

        }, ContextCompat.getMainExecutor(activity))


    }

    private fun takePhoto() {
        val activity=(activity as MainActivity?)!!
        val imageCapture = imageCapture ?: return

        // Create time stamped name and MediaStore entry.
        val name = SimpleDateFormat("dd-MM-yyyy-HH-mm-ss-SSS", Locale.ROOT)
            .format(System.currentTimeMillis())
        val contentValues = ContentValues().apply {
            put(MediaStore.MediaColumns.DISPLAY_NAME, name)
            put(MediaStore.MediaColumns.MIME_TYPE, "image/jpeg")
            if(Build.VERSION.SDK_INT > Build.VERSION_CODES.P) {
                put(MediaStore.Images.Media.RELATIVE_PATH, "Pictures/QueComoHoy")
            }
        }

        // Create output options object which contains file + metadata
        val outputOptions = ImageCapture.OutputFileOptions
            .Builder(activity.contentResolver,
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                contentValues)
            .build()

        imageCapture.imageFormat

        // Set up image capture listener, which is triggered after photo has
        // been taken
        imageCapture.takePicture(
            outputOptions,
            ContextCompat.getMainExecutor(activity),
            object : ImageCapture.OnImageSavedCallback {
                override fun onError(exc: ImageCaptureException) {
                    Toast.makeText(activity, "ERROR SACANDO LA FOTO", Toast.LENGTH_SHORT).show()
                }

                override fun
                        onImageSaved(output: ImageCapture.OutputFileResults){
                    //val msg = "Photo capture succeeded: ${output.savedUri}"
                    //Toast.makeText(activity, msg, Toast.LENGTH_SHORT).show()

                    activity.imageTakenUri=output.savedUri

                    findNavController().navigate(R.id.action_cameraFragment_to_imageFragment)
                }
            }
        )
    }
}