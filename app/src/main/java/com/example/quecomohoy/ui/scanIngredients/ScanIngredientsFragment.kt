package com.example.quecomohoy.ui.scanIngredients

import android.Manifest
import android.app.Activity
import android.content.ContentValues.TAG
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Matrix
import android.media.ExifInterface
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
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.quecomohoy.MainActivity
import com.example.quecomohoy.R
import com.example.quecomohoy.databinding.FragmentScanIngredientsBinding
import com.example.quecomohoy.ui.RecipeViewModel
import com.example.quecomohoy.ui.RecipeViewModelFactory
import com.example.quecomohoy.ui.Status
import com.example.quecomohoy.ui.favorites.FavouritesViewModel
import com.example.quecomohoy.ui.favorites.FavouritesViewModelFactory
import com.example.quecomohoy.ui.listeners.RecipeListener
import com.example.quecomohoy.ui.listeners.ScanListener
import com.example.quecomohoy.ui.searchrecipes.adapters.RecipesAdapter
import com.google.android.material.snackbar.Snackbar
import com.google.mlkit.common.model.LocalModel
import com.google.mlkit.vision.common.InputImage
import com.google.mlkit.vision.label.ImageLabeling
import com.google.mlkit.vision.label.custom.CustomImageLabelerOptions
import com.squareup.picasso.Picasso
import java.io.IOException
import java.io.InputStream
import java.util.*


class ScanIngredientsFragment: Fragment(), ScanListener, RecipeListener{
    private val favouriteViewModel : FavouritesViewModel by viewModels(factoryProducer = { FavouritesViewModelFactory() })
    private var _binding: FragmentScanIngredientsBinding? = null
    private val binding get() = _binding!!
    private val recipeViewModel: RecipeViewModel by viewModels(
        { requireParentFragment() },
        { RecipeViewModelFactory() }
    )
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
        binding.openCamera.setOnClickListener{
            findNavController().navigate(R.id.action_scanIngredientsFragment_to_cameraFragment);
        }

        // If image has been loaded from camera, use it to display and send to ML vision
        (activity as MainActivity).imageTakenUri?.also {
            performCloudVisionRequest(it, true)
        }

        val adapter = RecipesAdapter(this)

        binding.recipesRecycler.setLayoutManager(LinearLayoutManager(context));
        binding.recipesRecycler.setHasFixedSize(true);
        binding.recipesRecycler.adapter = adapter

        recipeViewModel.recipes.observe(viewLifecycleOwner) {
            when(it.status){
                Status.SUCCESS -> {
                    binding.imageView.visibility =  View.GONE;
                    adapter.updateData(it.data.orEmpty())
                    binding.recipesRecycler.visibility = View.VISIBLE
                }
                Status.ERROR -> {
                    Snackbar.make(view, "Hubo un error", Snackbar.LENGTH_SHORT)
                }
            }

        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        (activity as MainActivity).imageTakenUri = null
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
            (activity as MainActivity).imageTakenUri = data.getData()
            performCloudVisionRequest(data.getData());
        }
        else {
            super.onActivityResult(requestCode, resultCode, data)
        }
    }

    private fun performCloudVisionRequest(uri: Uri?, rotateImage: Boolean = false) {
        if (uri != null) {
            try {
                val bitmap: Bitmap = MediaStore.Images.Media.getBitmap(requireActivity().getApplicationContext().getContentResolver(), uri)
                callMLVision(bitmap)
                setImageView(bitmap, rotateImage)
            } catch (e: IOException) {
                Log.e(TAG, e.localizedMessage)
            }
        }
    }

    private fun setImageView(bitmap: Bitmap, rotateImage: Boolean) {
        val rotationDegrees = if (rotateImage) {
            90f
        } else {
            0f
        }
        Picasso.get()
            .load((activity as MainActivity?)!!.imageTakenUri).resize(2048, 1600)
            .rotate(rotationDegrees)
            .onlyScaleDown()
            .into(binding.selectedImage);

        binding.selectedImageTxt.text = "Imagen seleccionada"
    }

    private fun callMLVision(bitmap: Bitmap) {
        val localModel = LocalModel.Builder()
            .setAssetFilePath("lite-model_aiy_vision_classifier_food_V1_1.tflite")
            // or .setAbsoluteFilePath(absolute file path to model file)
            // or .setUri(URI to model file)
            .build()

        val customImageLabelerOptions = CustomImageLabelerOptions.Builder(localModel)
            .setMaxResultCount(15)
            .build()
        val labeler = ImageLabeling.getClient(customImageLabelerOptions)
        val image = InputImage.fromBitmap(bitmap, 0)
        binding.imageView.visibility =  View.GONE;

        labeler.process(image).addOnSuccessListener { labels ->
            val results = labels.filter { it.confidence > 0.3 }
            if (results.isEmpty()) {
                binding.recipesRecycler.visibility = View.GONE
                binding.emptyResultsLabel.visibility = View.VISIBLE
                binding.instructions.visibility =  View.GONE;

            } else {
                binding.instructions.visibility =  View.GONE;
                binding.emptyResultsLabel.visibility = View.GONE
                recipeViewModel.getRecipesByName(labels.first().text)
            }
        }
    }

    override fun onClickScanResult(title: String) {
        val args = Bundle()
        args.putString("searchTerm", title)
        findNavController().navigate(R.id.action_scanIngredientsFragment_to_recipesFragment, args)
    }

    override fun onMarkAsFavourite(recipeId: Int, marked: Boolean) {
        val sp = requireActivity().getPreferences(Context.MODE_PRIVATE)
        val userId = sp.getInt("userId", -1)
        favouriteViewModel.markAsFavourite(recipeId, userId, marked);    }

    override fun onClickRecipe(recipeId: Int) {
        val args = Bundle()
        args.putInt("id", recipeId)
        findNavController().navigate( R.id.action_scanIngredientsFragment_to_recipeViewFragment, args)
    }
    fun handleSamplingAndRotationBitmap(context: Context, selectedImage: Uri?): Bitmap? {
        val MAX_HEIGHT = 1024
        val MAX_WIDTH = 1024

        // First decode with inJustDecodeBounds=true to check dimensions
        val options = BitmapFactory.Options()
        options.inJustDecodeBounds = true
        var imageStream: InputStream? = context.contentResolver.openInputStream(selectedImage!!)
        BitmapFactory.decodeStream(imageStream, null, options)
        if (imageStream != null) {
            imageStream.close()
        }

        // Calculate inSampleSize
        options.inSampleSize = calculateInSampleSize(options, MAX_WIDTH, MAX_HEIGHT)

        // Decode bitmap with inSampleSize set
        options.inJustDecodeBounds = false
        imageStream = context.contentResolver.openInputStream(selectedImage)
        var img = BitmapFactory.decodeStream(imageStream, null, options)
        img = rotateImageIfRequired(img!!, selectedImage)
        return img
    }

    private fun calculateInSampleSize(
        options: BitmapFactory.Options,
        reqWidth: Int, reqHeight: Int
    ): Int {
        // Raw height and width of image
        val height = options.outHeight
        val width = options.outWidth
        var inSampleSize = 1
        if (height > reqHeight || width > reqWidth) {

            // Calculate ratios of height and width to requested height and width
            val heightRatio = Math.round(height.toFloat() / reqHeight.toFloat())
            val widthRatio = Math.round(width.toFloat() / reqWidth.toFloat())

            // Choose the smallest ratio as inSampleSize value, this will guarantee a final image
            // with both dimensions larger than or equal to the requested height and width.
            inSampleSize = if (heightRatio < widthRatio) heightRatio else widthRatio

            // This offers some additional logic in case the image has a strange
            // aspect ratio. For example, a panorama may have a much larger
            // width than height. In these cases the total pixels might still
            // end up being too large to fit comfortably in memory, so we should
            // be more aggressive with sample down the image (=larger inSampleSize).
            val totalPixels = (width * height).toFloat()

            // Anything more than 2x the requested pixels we'll sample down further
            val totalReqPixelsCap = (reqWidth * reqHeight * 2).toFloat()
            while (totalPixels / (inSampleSize * inSampleSize) > totalReqPixelsCap) {
                inSampleSize++
            }
        }
        return inSampleSize
    }

    private fun rotateImageIfRequired(img: Bitmap, selectedImage: Uri): Bitmap? {
        val ei = ExifInterface(selectedImage.path!!)
        val orientation: Int =
            ei.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL)
        return when (orientation) {
            ExifInterface.ORIENTATION_ROTATE_90 -> rotateImage(img, 90f)
            ExifInterface.ORIENTATION_ROTATE_180 -> rotateImage(img, 180f)
            ExifInterface.ORIENTATION_ROTATE_270 -> rotateImage(img, 270f)
            else -> img
        }
    }

    private fun rotateImage(img: Bitmap, degree: Float): Bitmap? {
        val matrix = Matrix()
        matrix.postRotate(degree)
        val rotatedImg = Bitmap.createBitmap(img, 0, 0, img.width, img.height, matrix, true)
        /*if (!img.isRecycled) {
            img.recycle()
        }*/
        return rotatedImg
    }
}