package ru.rodipit.petshelper.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import ru.rodipit.petshelper.adapters.TasksAdapter
import ru.rodipit.petshelper.databinding.FragmentMainBinding
import ru.rodipit.petshelper.viewModels.MainScreenViewModel

@AndroidEntryPoint
class MainFragment : Fragment() {

    private var _binding: FragmentMainBinding? = null
    private val binding get() = _binding!!
    private val viewModel : MainScreenViewModel by viewModels()
    private lateinit var adapter: TasksAdapter

    companion object{
        fun getNewInstance(animalId: Int): MainFragment{
            val mainFragment = MainFragment()
            val bundle = Bundle()
            bundle.putInt("animalId", animalId)
            mainFragment.arguments = bundle
            return mainFragment
        }
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMainBinding.inflate(inflater, container, false)
        adapter = TasksAdapter(requireContext())

//        createObservers()

        val animalId = when(arguments){
            null -> -1
            else -> requireArguments().getInt("animalId")
        }

        viewModel.loadAnimal(animalId)

        binding.todayTasksRecView.adapter = adapter
        binding.todayTasksRecView.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)

        binding.addTaskBtn.setOnClickListener {
            viewModel.addTask()
        }


        return binding.root
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

//    private fun createObservers(){
//
//        lifecycleScope.launch {
//            viewModel.animal.collect{
//                if (it.id != -1){
//                    with(binding){
//                        fullnameTv.text = it.fullName
//                        breedTv.text = it.breed
//                        ageTv.text = it.getAge().toString()
////                        birthdayTv .text = it.getBDay()
//                    }
//                }
//            }
//        }
//
//        lifecycleScope.launch {
//            viewModel.currentTasks.collect{
//                adapter.data = it
//                viewModel.updateTasks()
//            }
//        }
//
//    }

}