package com.example.Exercises

import android.app.Activity
import android.content.Intent
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.provider.MediaStore
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import androidx.lifecycle.Observer
import coil.load
import com.bumptech.glide.Glide
import com.example.ajfit.R
import com.example.musclegroups.MuscleGroupData
//import com.example.musclegroups.MuscleGroupListFragmentArgs
import com.example.roomDB.ExerciseInput

class ExerciseListFragment : Fragment() {


    private lateinit var imageViewModel: ExerciseListViewModel
    private lateinit var selectedMuscleGroup: MuscleGroupData
    private lateinit var imageView: ImageView
    private lateinit var btnPickImage: Button
    private lateinit var btnSaveImage: Button
    private var imageUri: String? = null
    private val PICK_IMAGE_REQUEST = 1


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val  binding=inflater.inflate(R.layout.fragment_exercise_list, container, false)
        val args = ExerciseListFragmentArgs.fromBundle(requireArguments())
        selectedMuscleGroup = args.MuscleGroupData
        imageViewModel = ViewModelProvider(this).get(ExerciseListViewModel::class.java)
        imageView = binding.findViewById(R.id.imageView)
        btnPickImage = binding.findViewById(R.id.btnPickImage)
        btnSaveImage = binding.findViewById(R.id.btnSaveImage)

        // Observe the LiveData from ViewModel for changes in images
        imageViewModel.allImages.observe(viewLifecycleOwner, Observer { images ->
            // Handle UI updates when the data changes
            images.forEach {
                Glide.with(requireContext())
                    .load(it.uri)
                    .into(imageView)
            }
        })

        // Pick an image from the gallery
        btnPickImage.setOnClickListener {
            val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
            startActivityForResult(intent, PICK_IMAGE_REQUEST)
        }

        // Save selected image to Room database via ViewModel
        btnSaveImage.setOnClickListener {
            val name = "Sample Image" // You can take user input for name if needed
            imageUri?.let {
                val image = ExerciseInput(name = name, uri = it)
                imageViewModel.saveImage(image)
//                Toast.makeText(requireContext(), "Image saved!", Toast.LENGTH_SHORT).show()
            }
        }

        return  binding
    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK && requestCode == PICK_IMAGE_REQUEST) {
            imageUri = data?.data.toString()
            imageUri?.let {
                imageView.load(it) // Using Coil to load the image
            }
        }
    }

}