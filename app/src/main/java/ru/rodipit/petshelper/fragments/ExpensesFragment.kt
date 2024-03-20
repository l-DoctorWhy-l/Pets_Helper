package ru.rodipit.petshelper.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import dagger.hilt.android.AndroidEntryPoint
import ru.rodipit.petshelper.databinding.FragmentExpensesBinding
import ru.rodipit.petshelper.viewModels.ExpensesViewModel
@AndroidEntryPoint
class ExpensesFragment : Fragment() {

    private var _binding: FragmentExpensesBinding? = null
    private val binding get() = _binding!!

    private val viewModel: ExpensesViewModel by viewModels()

    companion object{
        fun getNewInstance(animalId: Int): ExpensesFragment{
            val expensesFragment = ExpensesFragment()
            val bundle = Bundle()
            bundle.putInt("animalId", animalId)
            expensesFragment.arguments = bundle
            return expensesFragment
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentExpensesBinding.inflate(inflater, container, false)


        val animalId = when(arguments){
            null -> -1
            else -> arguments?.getInt("animalId")
        }
        println(animalId)

        return binding.root
    }

}