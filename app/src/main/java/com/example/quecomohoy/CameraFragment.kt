package com.example.quecomohoy

import android.Manifest
import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.camera.core.ImageCapture
import androidx.camera.video.Recorder
import androidx.camera.video.Recording
import androidx.camera.video.VideoCapture
import androidx.core.app.ActivityCompat
import androidx.fragment.app.viewModels

import com.example.quecomohoy.databinding.FragmentCameraBinding
import com.example.quecomohoy.databinding.FragmentSearchBinding
import com.example.quecomohoy.ui.searchrecipes.SearchViewModel
import com.example.quecomohoy.ui.searchrecipes.SearchViewModelFactory
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors


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

        //binding.imageCaptureButton.setOnClickListener(takePhoto())

        cameraExecutor = Executors.newSingleThreadExecutor()

        return binding.root
    }

    override fun onDestroy() {
        super.onDestroy()
        cameraExecutor.shutdown()
    }

    private var imageCapture: ImageCapture? = null

    private var videoCapture: VideoCapture<Recorder>? = null
    private var recording: Recording? = null

    private lateinit var cameraExecutor: ExecutorService

    //private fun takePhoto(): View.OnClickListener? {}

    fun startCamera() {
        binding.videoCaptureButton.text="ANDANDO";
    }

}