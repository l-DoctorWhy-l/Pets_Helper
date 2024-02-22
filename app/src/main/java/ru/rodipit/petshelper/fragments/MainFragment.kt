package ru.rodipit.petshelper.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import ru.rodipit.petshelper.databinding.FragmentMainBinding
import ru.rodipit.petshelper.viewModels.MainFragmentViewModel


class MainFragment : Fragment() {

    private var _binding: FragmentMainBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel : MainFragmentViewModel

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
        viewModel = ViewModelProvider(this)[MainFragmentViewModel::class.java]

        viewModel.animal.observe(viewLifecycleOwner){
            if (it != null && it.id != -1 ){
                with(binding){
                    println(it)
                    fullnameTv.text = it.fullName
                    breedTv.text = it.breed
                    ageTv.text = it.getAge().toString()
                    birthdayTv .text = it.getBDay()
                }
            }
        }

        viewModel.currentTasks.observe(viewLifecycleOwner){
            println(it)
        }

        val animalId = when(arguments){
            null -> -1
            else -> requireArguments().getInt("animalId")
        }
        println(animalId)

        viewModel.loadAnimal(animalId)




        return binding.root
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}