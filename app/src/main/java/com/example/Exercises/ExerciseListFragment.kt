package com.example.Exercises

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.addCallback
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.room.Room
import com.example.ajfit.R
import com.example.musclegroups.MuscleGroupData
//import com.example.musclegroups.MuscleGroupListFragmentArgs
import com.example.roomDB.AppDatabase
import com.example.roomDB.ExerciseListViewModelFactory
import com.example.weekdays.WeeksData

class ExerciseListFragment : Fragment() {


    private lateinit var viewModel: ExerciseListViewModel
    private lateinit var selectedMuscleGroup: MuscleGroupData


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val  rootView=inflater.inflate(R.layout.fragment_exercise_list, container, false)
        val args = ExerciseListFragmentArgs.fromBundle(requireArguments())
        selectedMuscleGroup = args.MuscleGroupData
        val db = Room.databaseBuilder(
            requireContext().applicationContext,
            AppDatabase::class.java, "Exercise-database"
        ).build()

        val userInputDao = db.userInputDao()
        val viewModelFactory = ExerciseListViewModelFactory(userInputDao)
        viewModel = ViewModelProvider(this, viewModelFactory).get(ExerciseListViewModel::class.java)

        viewModel.insertUserInput("hi")
        viewModel.insertUserInput("ajay")
        viewModel.insertUserInput("how")

        viewModel.userInputs.observe(viewLifecycleOwner, Observer { userInputs ->
            userInputs.forEach {
                var name=it.inputText
            }
        })

        return  rootView
    }


}