package com.example.Exercises

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.net.toUri
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import coil.load
import com.bumptech.glide.Glide
import com.example.ajfit.R
import com.example.ajfit.databinding.FragmentExerciseListBinding
import com.example.ajfit.databinding.FragmentExercisesBinding
import com.example.musclegroups.MuscleGroupData
import com.example.roomDB.ExerciseInput
import com.example.roomDB.ExerciseInputText
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.SimpleExoPlayer
import com.google.android.exoplayer2.source.ProgressiveMediaSource
import com.google.android.exoplayer2.upstream.DefaultDataSource
import java.io.File


class ExercisesFragment : Fragment() {
    private lateinit var imageViewModel: ExerciseListViewModel

    private lateinit var binding: FragmentExercisesBinding
    private lateinit var selectedExercise: ExerciseInputText
    private var imageUri: String? = null
    private var videoUri: String? = null
    private val PICK_IMAGE_REQUEST = 1
    private val PICK_VIDEO_REQUEST = 2
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentExercisesBinding.inflate(inflater, container, false)
        imageViewModel = ViewModelProvider(this).get(ExerciseListViewModel::class.java)
        val args = ExercisesFragmentArgs.fromBundle(requireArguments())
        selectedExercise = args.ExerciseData

        imageViewModel.getmedia(selectedExercise.name)
        imageViewModel.allImages.observe(viewLifecycleOwner, Observer { images ->
            images.forEach {
                if (it.name.equals(selectedExercise.name)) {
                    Glide.with(requireContext())
                        .load(it.uri)
                        .into(binding.imageView)
                }else if (it.name.equals(selectedExercise.name+"video")) {
                    setupVideoPlayer(images.get(images.lastIndex).uri) // Pass the URI string directly
                }
            }
        })

        // Pick an image from the gallery
        binding.btnPickImage.setOnClickListener {
            val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
            startActivityForResult(intent, PICK_IMAGE_REQUEST)
        }
        binding.btnPickVideo.setOnClickListener {
            val intent = Intent(Intent.ACTION_OPEN_DOCUMENT, MediaStore.Video.Media.EXTERNAL_CONTENT_URI)
            startActivityForResult(intent, PICK_VIDEO_REQUEST)
        }

        // Save selected image to Room database via ViewModel
        binding.btnSaveImage.setOnClickListener {
            imageUri=saveImageToAppStorage(Uri.parse(imageUri))
            imageUri?.let {
                val image = ExerciseInput(name = selectedExercise.name, uri = it)
                imageViewModel.saveImage(image)
            }
        }
        binding.btnSaveVideo.setOnClickListener {

            videoUri=saveVideoToAppStorage(Uri.parse(videoUri))
            videoUri?.let {
                println("Storing video URI: $it") // Add this for debugging
                val video = ExerciseInput(name = selectedExercise.name+"video", uri = it)
                imageViewModel.saveImage(video)
            }
        }


        return  binding.root
    }
    private fun saveImageToAppStorage(uri: Uri): String {
        return try {
            // 1. Create a unique filename with timestamp
            val imageFile = File(requireContext().filesDir, "images/img_${System.currentTimeMillis()}.jpg")

            // 2. Ensure parent directory exists
            imageFile.parentFile?.mkdirs()

            // 3. Copy the image stream to app storage
            requireContext().contentResolver.openInputStream(uri)?.use { inputStream ->
                imageFile.outputStream().use { outputStream ->
                    inputStream.copyTo(outputStream)
                }
            }

            // 4. Return the file's URI as string
            imageFile.toUri().toString()
        } catch (e: Exception) {
            Log.e("ImageSave", "Failed to save image: ${e.message}")
            throw e // Or return a default/error URI
        }
    }
    private fun saveVideoToAppStorage(uri: Uri): String {
        val inputStream = requireContext().contentResolver.openInputStream(uri)!!
        val videoFile = File(requireContext().filesDir, "videos/${System.currentTimeMillis()}.mp4")
        videoFile.parentFile?.mkdirs()
        inputStream.use { input ->
            videoFile.outputStream().use { output ->
                input.copyTo(output)
            }
        }
        return videoFile.toUri().toString()
    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK) {
            when (requestCode) {
                PICK_IMAGE_REQUEST -> {
                    data?.data?.let { uri ->
                        imageUri = uri.toString()
                        binding.imageView.load(uri) // Using Coil for image loading
                    }
                }
                PICK_VIDEO_REQUEST -> {
                    data?.data?.let { uri ->
                        // Take persistable permission
                        requireContext().contentResolver.takePersistableUriPermission(
                            uri,
                            Intent.FLAG_GRANT_READ_URI_PERMISSION
                        )
                        videoUri = uri.toString()
                        setupVideoPlayer(uri.toString())
                    }
                }
            }
        }
    }
    private fun setupVideoPlayer(uriString: String) {
        try {
            val uri = Uri.parse(uriString)

            // 1. Check if URI is valid
            if (uri.scheme == null) {
                Toast.makeText(requireContext(), "Invalid video URI", Toast.LENGTH_SHORT).show()
                return
            }

            // 2. Take persistable permission (for content:// URIs)
            if (uri.scheme == "content") {
                try {
                    requireContext().contentResolver.takePersistableUriPermission(
                        uri,
                        Intent.FLAG_GRANT_READ_URI_PERMISSION
                    )
                } catch (e: SecurityException) {
                    Toast.makeText(requireContext(), "Permission denied. Re-pick the video.", Toast.LENGTH_SHORT).show()
                    return
                }
            }

            // 3. Initialize ExoPlayer
            val exoPlayer = SimpleExoPlayer.Builder(requireContext()).build()
            binding.videoView.player = exoPlayer

            // 4. Create MediaSource
            val mediaSource = ProgressiveMediaSource.Factory(
                DefaultDataSource.Factory(requireContext())
            ).createMediaSource(MediaItem.fromUri(uri))

            // 5. Prepare and play
            exoPlayer.apply {
                setMediaSource(mediaSource)
                prepare()
                playWhenReady = true // Auto-play
            }

        } catch (e: Exception) {
            Toast.makeText(requireContext(), "Error playing video: ${e.message}", Toast.LENGTH_SHORT).show()
            Log.e("ExoPlayer", "Error: ${e.stackTraceToString()}")
        }
    }
}