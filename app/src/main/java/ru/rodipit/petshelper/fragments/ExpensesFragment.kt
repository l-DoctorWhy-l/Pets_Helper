package ru.rodipit.petshelper.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import ru.rodipit.petshelper.databinding.FragmentExpensesBinding
import ru.rodipit.petshelper.viewModels.ExpensesViewModel

class ExpensesFragment : Fragment() {

    private var _binding: FragmentExpensesBinding? = null
    private val binding get() = _binding!!

    private lateinit var viewModel: ExpensesViewModel

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

        viewModel = ViewModelProvider(this)[ExpensesViewModel::class.java]

        val animalId = when(arguments){
            null -> -1
            else -> arguments?.getInt("animalId")
        }
        println(animalId)

        return binding.root
    }

}