package com.example.quecomohoy.ui.camera

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.quecomohoy.MainActivity
import com.example.quecomohoy.databinding.FragmentImageBinding
import com.example.quecomohoy.R
import com.squareup.picasso.Picasso

class ImageFragment: Fragment() {
    private var _binding: FragmentImageBinding? = null

    private val binding get() = _binding!!

    public var imageTaken=""

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentImageBinding.inflate(inflater, container, false)

        binding.button.setOnClickListener{
          findNavController().navigate(R.id.action_imageFragment_to_scanIngredientsFragment)
        }

        Picasso.get()
            .load((activity as MainActivity?)!!.imageTakenUri).resize(500, 700)
            .rotate(90f)
            .onlyScaleDown() // the image will only be resized if it's bigger than 2048x 1600 pixels.
            .into(binding.imageCameraResult);

        return binding.root
    }
}