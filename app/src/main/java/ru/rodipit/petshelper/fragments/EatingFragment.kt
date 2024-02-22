package ru.rodipit.petshelper.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import ru.rodipit.petshelper.databinding.FragmentEatingBinding


class EatingFragment : Fragment() {

    private var _binding: FragmentEatingBinding? = null
    private val binding get() = _binding!!

    companion object{
        fun getNewInstance(animalId: Int): EatingFragment{
            val eatingFragment = EatingFragment()
            val bundle = Bundle()
            bundle.putInt("animalId", animalId)
            eatingFragment.arguments = bundle
            return eatingFragment
        }
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentEatingBinding.inflate(inflater, container, false)

        val animalId = when(arguments){
            null -> -1
            else -> arguments?.getInt("animalId")
        }
        println(animalId)

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()

        _binding = null
    }
}