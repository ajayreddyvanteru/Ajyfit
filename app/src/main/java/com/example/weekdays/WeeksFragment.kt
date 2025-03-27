package com.example.weekdays

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.addCallback
import androidx.lifecycle.Observer
import androidx.navigation.Navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.ajfit.R

class WeeksFragment : Fragment() {

    private lateinit var weeksViewModel: WeeksViewModel
    private lateinit var adapter: weeksListAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val rootView = inflater.inflate(R.layout.fragment_weeks, container, false)

        // Initialize the ViewModel
        weeksViewModel = ViewModelProvider(this).get(WeeksViewModel::class.java)

        val recyclerView: RecyclerView = rootView.findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())


        weeksViewModel.weeksList.observe(viewLifecycleOwner, Observer { weeks ->
            adapter = weeksListAdapter(requireContext(), weeks) { selectedWeek ->
                weeksViewModel.selectWeek(selectedWeek)
            }
            recyclerView.adapter = adapter
        })

        weeksViewModel.selectedWeek.observe(viewLifecycleOwner, Observer { selectedWeek ->
            selectedWeek?.let {
                val action = WeeksFragmentDirections.actionWeeksFragmentToMuscleGroupListFragment(it)
                findNavController().navigate(action)
            }
        })
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner) {
            findNavController().popBackStack()
        }

        return rootView
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        weeksViewModel = ViewModelProvider(this).get(WeeksViewModel::class.java)
        // TODO: Use the ViewModel
    }

}