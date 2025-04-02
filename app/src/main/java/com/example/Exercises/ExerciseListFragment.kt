package com.example.Exercises

import android.annotation.SuppressLint
import android.app.AlertDialog
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.activity.addCallback
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.ajfit.R
import com.example.ajfit.databinding.FragmentExerciseListBinding
import com.example.musclegroups.MuscleGroupData
import com.example.roomDB.ExerciseInputText

//import com.example.musclegroups.MuscleGroupListFragmentArgs

class ExerciseListFragment : Fragment() {


    private lateinit var imageViewModel: ExerciseListViewModel
    private lateinit var selectedMuscleGroup: MuscleGroupData
    private lateinit var adapter: ExerciseListAdapter

    private lateinit var binding: FragmentExerciseListBinding



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentExerciseListBinding.inflate(inflater, container, false)
        val args = ExerciseListFragmentArgs.fromBundle(requireArguments())
        selectedMuscleGroup = args.MuscleGroupData
        imageViewModel = ViewModelProvider(this).get(ExerciseListViewModel::class.java)
        binding.rvexercise.layoutManager = LinearLayoutManager(requireContext())
        imageViewModel.getexercises(selectedMuscleGroup.Group)

        imageViewModel.allTexts.observe(viewLifecycleOwner, Observer { texts ->
            adapter = ExerciseListAdapter(requireContext(), texts) { item ->
                val action = ExerciseListFragmentDirections.actionExerciseListFragmentToExercisesFragment(item)
                findNavController().navigate(action)
            }
            binding.rvexercise.adapter = adapter
        })

        binding.btnSaveImage.setOnClickListener {
            showDialogWithEditText()
        }

        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner) {
            findNavController().popBackStack()
        }
        return  binding.root
    }
    @SuppressLint("MissingInflatedId")
    private fun showDialogWithEditText() {
        val dialogView = layoutInflater.inflate(R.layout.dialog_input, null)
        val input = dialogView.findViewById<EditText>(R.id.editTextInput)
        val builder = AlertDialog.Builder(requireContext())
        builder.setTitle("Please Enter Exercise")
        builder.setView(dialogView)

        builder.setPositiveButton("OK") { dialog, _ ->
            val exerciseName = input.text.toString()
            exerciseName?.let {
                val image = ExerciseInputText(name = exerciseName, muscle = selectedMuscleGroup.Group)
                imageViewModel.saveTexts(image,selectedMuscleGroup.Group)
            }
        }
        builder.setNegativeButton("Cancel", null)
        builder.show()
    }
}