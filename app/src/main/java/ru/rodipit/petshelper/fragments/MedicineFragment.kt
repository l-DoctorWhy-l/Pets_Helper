package ru.rodipit.petshelper.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import ru.rodipit.petshelper.databinding.FragmentMedicineBinding


class MedicineFragment : Fragment() {

    private var _binding: FragmentMedicineBinding? = null
    private val binding get() = _binding!!

    companion object{
        fun getNewInstance(animalId: Int): MedicineFragment{
            val medicineFragment = MedicineFragment()
            val bundle = Bundle()
            bundle.putInt("animalId", animalId)
            medicineFragment.arguments = bundle
            return medicineFragment
        }
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentMedicineBinding.inflate(inflater, container, false)

        val animalId = when(arguments){
            null -> -1
            else -> {
                arguments?.getInt("animalId")
            }
        }
        println(animalId)

        return binding.root
    }

}