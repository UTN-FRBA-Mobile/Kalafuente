package com.example.quecomohoy.ui.camera

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.Settings
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.example.quecomohoy.MainActivity
import com.example.quecomohoy.R
import com.example.quecomohoy.databinding.FragmentAskPermissionBinding
import com.example.quecomohoy.databinding.FragmentCameraBinding
import com.squareup.picasso.BuildConfig

class AskPermissionFragment : Fragment() {
    private var _binding: FragmentAskPermissionBinding? = null

    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding= FragmentAskPermissionBinding.inflate(inflater, container, false)

        binding.buttonOkPermission.setOnClickListener{
            startActivity(Intent().apply{
                action=Settings.ACTION_APPLICATION_DETAILS_SETTINGS
                data=Uri.fromParts("package",(activity as MainActivity?)!!.packageName,null)
            })
        }
        return binding.root
    }

    override fun onResume() {
        super.onResume()
        if ((activity as MainActivity?)!!.allPermissionsGranted()) {
            findNavController().navigate(R.id.action_askPermissionFragment_to_cameraFragment)
        }
    }
}