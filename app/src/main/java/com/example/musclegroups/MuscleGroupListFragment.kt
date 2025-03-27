package com.example.musclegroups

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.addCallback
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.ajfit.R
import com.example.weekdays.WeeksData
//import com.example.weekdays.WeeksFragmentDirections
import com.example.weekdays.WeeksViewModel
import com.example.weekdays.weeksListAdapter

class MuscleGroupListFragment : Fragment() {


    private lateinit var muscleGroupListViewModel: MuscleGroupListViewModel
    private lateinit var adapter: MuscleGroupListAdapter

    private lateinit var selectedWeek: WeeksData
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val rootView =inflater.inflate(R.layout.fragment_muscle_group_list, container, false)
        val args = MuscleGroupListFragmentArgs.fromBundle(requireArguments())
        selectedWeek = args.selectedWeek
        muscleGroupListViewModel = ViewModelProvider(this).get(MuscleGroupListViewModel::class.java)
        muscleGroupListViewModel.updateSelectedWeekData(selectedWeek)
        val recyclerView: RecyclerView = rootView.findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())


        muscleGroupListViewModel.musclesGroupData.observe(viewLifecycleOwner, Observer { Groups ->
            adapter = MuscleGroupListAdapter(requireContext(), Groups) { it ->
                val action = MuscleGroupListFragmentDirections.actionMuscleGroupListFragmentToExerciseListFragment(it)
                findNavController().navigate(action)
            }
            recyclerView.adapter = adapter
        })
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner) {
            findNavController().popBackStack()
        }


        return rootView
    }


}